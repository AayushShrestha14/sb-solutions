package com.sb.solutions.core.config.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class CustomJdbcTokenStore implements TokenStore {

    private static final Log LOG = LogFactory.getLog(CustomJdbcTokenStore.class);

    private static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT
        = "insert into oauth_access_token (token_id, token, authentication_id, user_name,"
        + " client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";

    private static final String DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT
        = "select token_id, token from oauth_access_token where token_id = ?";

    private static final String DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT
        = "select token_id, authentication from oauth_access_token where token_id = ?";

    private static final String DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT
        = "select token_id, token from oauth_access_token where authentication_id = ?";

    private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_AND_CLIENT_SELECT_STATEMENT
        = "select token_id, token from oauth_access_token where user_name = ? and client_id = ?";

    private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT
        = "select token_id, token from oauth_access_token where user_name = ?";

    private static final String DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT
        = "select token_id, token from oauth_access_token where client_id = ?";

    private static final String DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT
        = "delete from oauth_access_token where token_id = ?";

    private static final String DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT
        = "delete from oauth_access_token where refresh_token = ?";

    private static final String DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT
        = "insert into oauth_refresh_token (token_id, token, authentication) values (?, ?, ?)";

    private static final String DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT
        = "select token_id, token from oauth_refresh_token where token_id = ?";

    private static final String DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT
        = "select token_id, authentication from oauth_refresh_token where token_id = ?";

    private static final String DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT
        = "delete from oauth_refresh_token where token_id = ?";

    private CustomAuthenticationKeyGenerator customAuthenticationKeyGenerator
        = new CustomAuthenticationKeyGenerator();

    private final JdbcTemplate jdbcTemplate;

    public CustomJdbcTokenStore(DataSource dataSource) {
        Assert.notNull(dataSource, "DataSource required");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setAuthenticationKeyGenerator(
        CustomAuthenticationKeyGenerator authenticationKeyGenerator) {
        this.customAuthenticationKeyGenerator = authenticationKeyGenerator;
    }

    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;

        String key = customAuthenticationKeyGenerator.extractKey(authentication);
        try {
            accessToken = jdbcTemplate.queryForObject(
                DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT,
                (rs, rowNum) -> deserializeAccessToken(rs.getBytes(2)), key);
        } catch (EmptyResultDataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failed to find access token for authentication " + authentication);
            }
        } catch (IllegalArgumentException e) {
            LOG.error("Could not extract access token for authentication " + authentication, e);
        }

        if (accessToken != null
            && !key.equals(customAuthenticationKeyGenerator
            .extractKey(readAuthentication(accessToken.getValue())))) {
            removeAccessToken(accessToken.getValue());
            // Keep the store consistent (maybe the same user is represented by this authentication but the details have
            // changed)
            storeAccessToken(accessToken, authentication);
        }
        return accessToken;
    }

    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }

        if (readAccessToken(token.getValue()) != null) {
            removeAccessToken(token.getValue());
        }

        jdbcTemplate.update(DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT,
            new Object[]{extractTokenKey(token.getValue()),
                new SqlLobValue(serializeAccessToken(token)),
                customAuthenticationKeyGenerator.extractKey(authentication),
                authentication.isClientOnly() ? null : authentication.getName(),
                authentication.getOAuth2Request().getClientId(),
                new SqlLobValue(serializeAuthentication(authentication)),
                extractTokenKey(refreshToken)},
            new int[]{Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.BLOB, Types.VARCHAR});
    }

    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = null;

        try {
            accessToken = jdbcTemplate
                .queryForObject(DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT,
                    (rs, rowNum) -> deserializeAccessToken(rs.getBytes(2)),
                    extractTokenKey(tokenValue));
        } catch (EmptyResultDataAccessException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find access token for token " + tokenValue);
            }
        } catch (IllegalArgumentException e) {
            LOG.warn("Failed to deserialize access token for " + tokenValue, e);
            removeAccessToken(tokenValue);
        }

        return accessToken;
    }

    public void removeAccessToken(OAuth2AccessToken token) {
        removeAccessToken(token.getValue());
    }

    public void removeAccessToken(String tokenValue) {
        jdbcTemplate.update(DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT, extractTokenKey(tokenValue));
    }

    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    public OAuth2Authentication readAuthentication(String token) {
        OAuth2Authentication authentication = null;

        try {
            authentication = jdbcTemplate
                .queryForObject(DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT,
                    (rs, rowNum) -> deserializeAuthentication(rs.getBytes(2)),
                    extractTokenKey(token));
        } catch (EmptyResultDataAccessException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find access token for token " + token);
            }
        } catch (IllegalArgumentException e) {
            LOG.warn("Failed to deserialize authentication for " + token, e);
            removeAccessToken(token);
        }

        return authentication;
    }

    public void storeRefreshToken(OAuth2RefreshToken refreshToken,
        OAuth2Authentication authentication) {
        jdbcTemplate
            .update(DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT,
                new Object[]{extractTokenKey(refreshToken.getValue()),
                    new SqlLobValue(serializeRefreshToken(refreshToken)),
                    new SqlLobValue(serializeAuthentication(authentication))},
                new int[]{Types.VARCHAR, Types.BLOB,
                    Types.BLOB});
    }

    public OAuth2RefreshToken readRefreshToken(String token) {
        OAuth2RefreshToken refreshToken = null;

        try {
            refreshToken = jdbcTemplate
                .queryForObject(DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT,
                    (rs, rowNum) -> deserializeRefreshToken(rs.getBytes(2)),
                    extractTokenKey(token));
        } catch (EmptyResultDataAccessException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find refresh token for token " + token);
            }
        } catch (IllegalArgumentException e) {
            LOG.warn("Failed to deserialize refresh token for token " + token, e);
            removeRefreshToken(token);
        }

        return refreshToken;
    }

    public void removeRefreshToken(OAuth2RefreshToken token) {
        removeRefreshToken(token.getValue());
    }

    public void removeRefreshToken(String token) {
        jdbcTemplate.update(DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT, extractTokenKey(token));
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
        OAuth2Authentication authentication = null;

        try {
            authentication = jdbcTemplate
                .queryForObject(DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT,
                    (rs, rowNum) -> deserializeAuthentication(rs.getBytes(2)),
                    extractTokenKey(value));
        } catch (EmptyResultDataAccessException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find access token for token " + value);
            }
        } catch (IllegalArgumentException e) {
            LOG.warn("Failed to deserialize access token for " + value, e);
            removeRefreshToken(value);
        }

        return authentication;
    }

    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    public void removeAccessTokenUsingRefreshToken(String refreshToken) {
        jdbcTemplate.update(DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT,
            new Object[]{extractTokenKey(refreshToken)},
            new int[]{Types.VARCHAR});
    }

    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();

        try {
            accessTokens = jdbcTemplate
                .query(DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT,
                    new SafeAccessTokenRowMapper(),
                    clientId);
        } catch (EmptyResultDataAccessException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find access token for clientId " + clientId);
            }
        }
        accessTokens = removeNulls(accessTokens);

        return accessTokens;
    }

    public Collection<OAuth2AccessToken> findTokensByUserName(String userName) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();

        try {
            accessTokens = jdbcTemplate
                .query(DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT,
                    new SafeAccessTokenRowMapper(),
                    userName);
        } catch (EmptyResultDataAccessException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find access token for userName " + userName);
            }
        }
        accessTokens = removeNulls(accessTokens);

        return accessTokens;
    }

    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId,
        String userName) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();

        try {
            accessTokens = jdbcTemplate
                .query(DEFAULT_ACCESS_TOKENS_FROM_USERNAME_AND_CLIENT_SELECT_STATEMENT,
                    new SafeAccessTokenRowMapper(),
                    userName, clientId);
        } catch (EmptyResultDataAccessException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find access token for clientId " + clientId + " and userName "
                    + userName);
            }
        }
        accessTokens = removeNulls(accessTokens);

        return accessTokens;
    }

    private List<OAuth2AccessToken> removeNulls(List<OAuth2AccessToken> accessTokens) {
        List<OAuth2AccessToken> tokens = new ArrayList<OAuth2AccessToken>();
        for (OAuth2AccessToken token : accessTokens) {
            if (token != null) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                "MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(
                "UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }

    private final class SafeAccessTokenRowMapper implements RowMapper<OAuth2AccessToken> {

        public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                return deserializeAccessToken(rs.getBytes(2));
            } catch (IllegalArgumentException e) {
                String token = rs.getString(1);
                jdbcTemplate.update(DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT, token);
                return null;
            }
        }
    }

    private byte[] serializeAccessToken(OAuth2AccessToken token) {
        return SerializationUtils.serialize(token);
    }

    private byte[] serializeRefreshToken(OAuth2RefreshToken token) {
        return SerializationUtils.serialize(token);
    }

    private byte[] serializeAuthentication(OAuth2Authentication authentication) {
        return SerializationUtils.serialize(authentication);
    }

    private OAuth2AccessToken deserializeAccessToken(byte[] token) {
        return SerializationUtils.deserialize(token);
    }

    private OAuth2RefreshToken deserializeRefreshToken(byte[] token) {
        return SerializationUtils.deserialize(token);
    }

    private OAuth2Authentication deserializeAuthentication(byte[] authentication) {
        return SerializationUtils.deserialize(authentication);
    }
}
