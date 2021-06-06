package com.sb.solutions.web.accountOpening.v1;

import lombok.Data;

/**
 * @author Sunil Babu Shrestha on 4/30/2021
 */
@Data
public class PumoriAccountOpeningDto {

    private String UserId;
    private String StationId="API";
    private String BranchCode;
    private String AcType;
    private String CyCode;
    private String GFathersName;
    private String FathersName;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String Gender;
    private String MaritalStatus;
    private String Salutation;
    private String DOB;
    private String District;
    private String VDC;
    private String WardNo;
    private String CitizenshipNo;
    private String CitizenNumIssued;
    private String CitizenDistrict;
    private String MobileNo;
    private String eMail;
    private String ExtraInfo;


}
