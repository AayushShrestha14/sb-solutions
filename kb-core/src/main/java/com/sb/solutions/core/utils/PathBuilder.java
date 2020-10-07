package com.sb.solutions.core.utils;

import org.apache.commons.lang.StringUtils;

import com.sb.solutions.core.utils.string.StringUtil;

/**
 * this utility method is use for chaining folder path for json file or document file
 *
 * @author Sunil Babu Shrestha on 9/6/2019
 */
public class PathBuilder {

    private static final String FILE_SEPARATOR = "/";
    private String basePath;
    private String branchName;
    private String customerName;
    private String customerCitizenship;
    private String loanType;
    // action can be either "new" or "close" or "renew"
    private String action;
    private boolean jsonPath;


    public PathBuilder(String basePath) {
        this.basePath = basePath;
    }

    private static String getDigitsFromString(String citizenNumber) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < citizenNumber.length(); i++) {
            char c = citizenNumber.charAt(i);
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public PathBuilder withBranch(String branchName) {
        this.branchName = branchName;
        return this;
    }

    public PathBuilder withCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public PathBuilder withCitizenship(String customerCitizenship) {
        this.customerCitizenship = customerCitizenship;
        return this;
    }

    public PathBuilder withLoanType(String loanType) {
        this.loanType = loanType;
        return this;
    }

    public PathBuilder withAction(String action) {
        this.action = action;
        return this;
    }

    public PathBuilder isJsonPath(boolean jsonPath) {
        this.jsonPath = jsonPath;
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder(this.basePath);
        return sb.append(branchName).append(FILE_SEPARATOR)
            .append(customerName.trim().replace(" ", "_")).append("_")
            .append(getDigitsFromString(customerCitizenship)).append(FILE_SEPARATOR)
            .append(loanType.trim().replace(" ", "_")).append(FILE_SEPARATOR)
            .append(action).append(FILE_SEPARATOR)
            .append(jsonPath ? "json/" : "doc/").toString();
    }

    public String buildAccountOpening() {
        StringBuilder sb = new StringBuilder(this.basePath);
        return sb.append(branchName)
            .append(FILE_SEPARATOR)
            .append(customerName.trim().replace(" ", "_"))
            .append("_")
            .append(getDigitsFromString(customerCitizenship)).append(FILE_SEPARATOR)
            .append("AccountOpening")
            .append(FILE_SEPARATOR)
            .append(jsonPath ? "json/" : "doc/")
            .toString();
    }

    public String buildBuildFormDownloadPath(String formName) {
        StringBuilder sb = new StringBuilder(this.basePath);
        sb.append("download/").append(formName).append(FILE_SEPARATOR);
        return sb.toString();
    }

    public String buildCustomerInfoBasePath(Long customerInfoId, String name, String branch,
        String customerType) {
        return new StringBuilder(this.basePath)
            .append("customers")
            .append(FILE_SEPARATOR)
            .append(StringUtil.getStringWithoutWhiteSpaceAndWithCapitalize(branch))
            .append(FILE_SEPARATOR)
            .append(StringUtils.deleteWhitespace(customerType).toUpperCase())
            .append(FILE_SEPARATOR)
            .append(
                customerInfoId + "-" + StringUtil.getStringWithoutWhiteSpaceAndWithCapitalize(name))
            .append(FILE_SEPARATOR)
            .toString();
    }

    public String buildLoanDocumentUploadBasePath(Long customerInfoId, String customerName,
        String branch,
        String customerType, String loanType, String loanName) {
        return new StringBuilder(this.basePath)
            .append("customers")
            .append(FILE_SEPARATOR)
            .append(StringUtil.getStringWithoutWhiteSpaceAndWithCapitalize(branch))
            .append(FILE_SEPARATOR)
            .append(StringUtils.deleteWhitespace(customerType).toUpperCase())
            .append(FILE_SEPARATOR)
            .append(
                customerInfoId + "-" + StringUtil
                    .getStringWithoutWhiteSpaceAndWithCapitalize(customerName))
            .append(FILE_SEPARATOR)
            .append(StringUtil.getStringWithoutWhiteSpaceAndWithCapitalize(loanName.toLowerCase()))
            .append(FILE_SEPARATOR)
            .append(StringUtils.deleteWhitespace(loanType).toUpperCase())
            .append(FILE_SEPARATOR)
            .append("doc")
            .append(FILE_SEPARATOR)
            .toString();
    }
}
