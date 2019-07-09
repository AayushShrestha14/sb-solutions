package com.sb.solutions.core.utils.email.template;

public class ResetPassword {

    public static String resetPasswordTemplate(String username, String urlWithToken,
        String expiryDurationInDays) {
        return
            "<div" + "    style=\"padding:0;margin:0 auto;width:100%!important;"
                + "font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">"
                + "  <table align=\"center\" bgcolor=\"#EDF0F3\" border=\"0\" cellpadding=\"0\""
                + " cellspacing=\"0\" style=\"background-color:#edf0f3;table-layout:fixed\""
                + " width=\"100%\">"
                + "    <tbody>"
                + "    <tr>"
                + "      <td align=\"center\">"
                + "        <table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\""
                + "     style=\"background-color:#ffffff;margin:0 auto;max-width:512px;width:inherit\""
                + "               width=\"512\">"
                + "          <tbody>"
                + "          <tr>"
                + "            <td bgcolor=\"#F6F8FA\""
                + "                style=\"background-color:#f6f8fa;padding:12px;"
                + "border-bottom:1px solid #ececec\">"
                + "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\""
                + "                     style=\"width:100%!important;min-width:100%!important\""
                + " width=\"100%\">"
                + "                <tbody>"
                + "                <tr>"
                + "                  <td align=\"left\" valign=\"middle\">"
                + "                    <a href=\"\" style=\"color:#008cc9;display:inline-block;"
                + "text-decoration:none\""
                + "                       target=\"_blank\">"
                + "                      <img alt=\"\" border=\"0\" height=\"50\" src=\"logo.png\""
                + "                           style=\"outline:none;color:#ffffff;text-decoration:none\""
                + " width=\"150\">"
                + "                    </a>"
                + "                  </td>"
                + "                  <td align=\"right\" style=\"padding:0 0 0 10px\""
                + " valign=\"middle\" width=\"100%\">"
                + "                    <a href=\"\""
                + "                       style=\"margin:0;color:#008cc9;display:inline-block;"
                + "text-decoration:none\""
                + "                       target=\"_blank\">"
                + "                      <span"
                + "                          style=\"word-wrap:break-word;color:#4c4c4c;word-break:"
                + "break-word;font-weight:400;font-size:14px;line-height:1.429\">"
                + username + "</span>"
                + "                    </a>"
                + "                  </td>"
                + "                  <td width=\"1\">&nbsp;</td>"
                + "                </tr>"
                + "                </tbody>"
                + "              </table>"
                + "            </td>"
                + "          </tr>"
                + "          <tr>"
                + "            <td>"
                + "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "                <tbody>"
                + "                <tr>"
                + "                  <td style=\"padding:20px 24px 32px 24px\">"
                + "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\""
                + " width=\"100%\">"
                + "                      <tbody>"
                + "                      <tr>"
                + "                        <td style=\"padding-bottom:20px\"><h2"
                + "                            style=\"margin:0;color:#262626;font-weight:700;"
                + "font-size:20px;line-height:1.2\">"
                + "                          Hi " + username + ",</h2></td>"
                + "                      </tr>"
                + "                      <tr>"
                + "                        <td style=\"padding-bottom:20px\"><p"
                + "                            style=\"margin:0;color:#4c4c4c;font-weight:400;"
                + "font-size:16px;line-height:1.25\">"
                + "                          Reset your password, and we'll get you on your way.</p></td>"
                + "                      </tr>"
                + "                      <tr>"
                + "                        <td style=\"padding-bottom:20px\"><p"
                + "                            style=\"margin:0;color:#4c4c4c;font-weight:400;"
                + "font-size:16px;line-height:1.25\">"
                + "                          To change your password, click the link below.</p></td>"
                + "                      </tr>"
                + "                      <tr>"
                + "                        <td style=\"padding-bottom:20px\"><p"
                + "                            style=\"margin:0;color:#4c4c4c;font-weight:400;"
                + "font-size:16px;line-height:1.25\">"
                + "                          <a href=\"" + urlWithToken + "\""
                + "                             style=\"color:#008cc9;display:inline-block;"
                + "text-decoration:none\""
                + "                             target=\"_blank\">Reset"
                + "                            my password</a></p></td>"
                + "                      </tr>"
                + "                      <tr>"
                + "                        <td style=\"padding-bottom:20px\"><p"
                + "                            style=\"margin:0;color:#4c4c4c;font-weight:400;"
                + "font-size:16px;line-height:1.25\">"
                + "                          This link will expire in " + expiryDurationInDays
                + " , so be sure to use it right away.</p>"
                + "                        </td>"
                + "                      </tr>"
                + "                      </tbody>"
                + "                    </table>"
                + "                  </td>"
                + "                </tr>"
                + "                </tbody>"
                + "              </table>"
                + "            </td>"
                + "          </tr>"
                + "          <tr>"
                + "            <td>"
                + "              <table align=\"center\" bgcolor=\"#EDF0F3\" border=\"0\""
                + " cellpadding=\"0\" cellspacing=\"0\""
                + "                     style=\"background-color:#edf0f3;padding:0 24px;"
                + "color:#6a6c6d;text-align:center\""
                + "                     width=\"100%\">"
                + "                <tbody>"
                + "                <tr>"
                + "                  <td align=\"center\" style=\"padding:16px 0 0 0;"
                + "text-align:center\"></td>"
                + "                </tr>"
                + "                <tr>"
                + "                  <td>"
                + "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\""
                + " width=\"100%\">"
                + "                      <tbody>"
                + "                      <tr>"
                + "                        <td align=\"center\" style=\"padding:0 0 12px 0;"
                + "text-align:center\"><p"
                + "                            style=\"margin:0;color:#6a6c6d;font-weight:400;"
                + "font-size:12px;line-height:1.333\"></p>"
                + "                        </td>"
                + "                      </tr>"
                + "                      </tbody>"
                + "                    </table>"
                + "                  </td>"
                + "                </tr>"
                + "                </tbody>"
                + "              </table>"
                + "            </td>"
                + "          </tr>"
                + "          </tbody>"
                + "        </table>"
                + "      </td>"
                + "    </tr>"
                + "    </tbody>"
                + "  </table>"
                + "</div>";
    }

}