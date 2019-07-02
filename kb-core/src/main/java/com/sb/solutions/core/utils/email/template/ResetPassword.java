package com.sb.solutions.core.utils.email.template;

public class ResetPassword {

    public static String resetPasswordTemplate(String username, String urlWithToken, String expiryDurationInDays) {
        String template = "<div\n" +
                "    style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\n" +
                "  <table align=\"center\" bgcolor=\"#EDF0F3\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "         style=\"background-color:#edf0f3;table-layout:fixed\" width=\"100%\">\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "      <td align=\"center\">\n" +
                "        <table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "               style=\"background-color:#ffffff;margin:0 auto;max-width:512px;width:inherit\"\n" +
                "               width=\"512\">\n" +
                "          <tbody>\n" +
                "          <tr>\n" +
                "            <td bgcolor=\"#F6F8FA\"\n" +
                "                style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec\">\n" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                     style=\"width:100%!important;min-width:100%!important\" width=\"100%\">\n" +
                "                <tbody>\n" +
                "                <tr>\n" +
                "                  <td align=\"left\" valign=\"middle\">\n" +
                "                    <a href=\"\" style=\"color:#008cc9;display:inline-block;text-decoration:none\"\n" +
                "                       target=\"_blank\">\n" +
                "                      <img alt=\"\" border=\"0\" height=\"50\" src=\"logo.png\"\n" +
                "                           style=\"outline:none;color:#ffffff;text-decoration:none\" width=\"150\">\n" +
                "                    </a>\n" +
                "                  </td>\n" +
                "                  <td align=\"right\" style=\"padding:0 0 0 10px\" valign=\"middle\" width=\"100%\">\n" +
                "                    <a href=\"\"\n" +
                "                       style=\"margin:0;color:#008cc9;display:inline-block;text-decoration:none\"\n" +
                "                       target=\"_blank\">\n" +
                "                      <span\n" +
                "                          style=\"word-wrap:break-word;color:#4c4c4c;word-break:break-word;font-weight:400;font-size:14px;line-height:1.429\">" + username + "</span>\n" +
                "                    </a>\n" +
                "                  </td>\n" +
                "                  <td width=\"1\">&nbsp;</td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "              </table>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td>\n" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                <tbody>\n" +
                "                <tr>\n" +
                "                  <td style=\"padding:20px 24px 32px 24px\">\n" +
                "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                      <tbody>\n" +
                "                      <tr>\n" +
                "                        <td style=\"padding-bottom:20px\"><h2\n" +
                "                            style=\"margin:0;color:#262626;font-weight:700;font-size:20px;line-height:1.2\">\n" +
                "                          Hi " + username + ",</h2></td>\n" +
                "                      </tr>\n" +
                "                      <tr>\n" +
                "                        <td style=\"padding-bottom:20px\"><p\n" +
                "                            style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\n" +
                "                          Reset your password, and we'll get you on your way.</p></td>\n" +
                "                      </tr>\n" +
                "                      <tr>\n" +
                "                        <td style=\"padding-bottom:20px\"><p\n" +
                "                            style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\n" +
                "                          To change your password, click the link below.</p></td>\n" +
                "                      </tr>\n" +
                "                      <tr>\n" +
                "                        <td style=\"padding-bottom:20px\"><p\n" +
                "                            style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\n" +
                "                          <a href=\"" + urlWithToken + "\"\n" +
                "                             style=\"color:#008cc9;display:inline-block;text-decoration:none\"\n" +
                "                             target=\"_blank\">Reset\n" +
                "                            my password</a></p></td>\n" +
                "                      </tr>\n" +
                "                      <tr>\n" +
                "                        <td style=\"padding-bottom:20px\"><p\n" +
                "                            style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\n" +
                "                          This link will expire in " + expiryDurationInDays + " , so be sure to use it right away.</p>\n" +
                "                        </td>\n" +
                "                      </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "              </table>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td>\n" +
                "              <table align=\"center\" bgcolor=\"#EDF0F3\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                     style=\"background-color:#edf0f3;padding:0 24px;color:#6a6c6d;text-align:center\"\n" +
                "                     width=\"100%\">\n" +
                "                <tbody>\n" +
                "                <tr>\n" +
                "                  <td align=\"center\" style=\"padding:16px 0 0 0;text-align:center\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td>\n" +
                "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                      <tbody>\n" +
                "                      <tr>\n" +
                "                        <td align=\"center\" style=\"padding:0 0 12px 0;text-align:center\"><p\n" +
                "                            style=\"margin:0;color:#6a6c6d;font-weight:400;font-size:12px;line-height:1.333\"></p>\n" +
                "                        </td>\n" +
                "                      </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "              </table>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "  </table>\n" +
                "</div>\n";
        return template;
    }

}
