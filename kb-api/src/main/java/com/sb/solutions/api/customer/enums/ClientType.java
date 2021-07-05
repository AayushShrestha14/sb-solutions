package com.sb.solutions.api.customer.enums;

/**
 * @author Sunil Babu Shrestha on 11/12/2020
 */
public enum ClientType {
    CORPORATE, MSME, SME, RETAIL,MICRO;

    public ClientType getClientType(String str) {
        ClientType[] clientTypes = ClientType.values();
        for (ClientType clientType : clientTypes) {
            if (clientType.toString().equals(str)) {
                return clientType;
            }
        }

        throw new RuntimeException("No enum match for String " + str);
    }
}
