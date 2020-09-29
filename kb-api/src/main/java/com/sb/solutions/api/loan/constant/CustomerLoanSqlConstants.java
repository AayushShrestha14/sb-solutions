package com.sb.solutions.api.loan.constant;

/**
 * @author : Rujan Maharjan on  9/28/2020
 **/
public class CustomerLoanSqlConstants {

    public static final String GET_LOAN_BY_GROUP_ID_AND_NOT_IN_CURRENT_LOAN_HOLDER =
        "SELECT ci.id     "
            + "         as loanHolderId,\n"
            + "       ci.name            as customerName,\n"
            + "       cg.id as groupId,\n"
            + "       cg.group_code as groupCode,\n"
            + "       cg.group_limit as groupLimit,\n"
            + "       cl.id            as customerLoanId,\n"
            + "       p.proposed_limit as proposedLimit,\n"
            + "       cl.document_status as documentStatus\n"
            + "from customer_info ci\n"
            + "         join customer_group cg on cg.id = ci.customer_info_customer_group_id and cg.id = :gId\n"
            + "         join customer_loan cl on ci.id = cl.loan_holder_id  and cl.loan_holder_id <> :lId\n"
            + "         join proposal p on cl.proposal_id = p.id\n"
            + "\n"
            + ";\n";

}
