package edu.example.aia.aia_management.bo;

import edu.example.aia.aia_management.bo.custom.impl.*;
//import edu.example.aia.aia_management.bo.custom.impl.PlaceOrderBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {}
    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() :boFactory;
    }

    public enum BOType{
        CLAIM,COMMISSION,PAYMENTDETAIL,CUSTOMER,FEEDBACK,INSURANCETYPE,MANAGER,NOTIFICATION,POLICY,SUPPORTTICKET,USER,USERDETAILS,PAYMENT
    }

    public SuperBo getBO(BOType type) {
        switch (type) {
            case CLAIM:
                return new ClaimBoImpl();
            case COMMISSION:
                return new CommissionBoImpl();
            case CUSTOMER:
                return new CustomerBoImpl();
            case FEEDBACK:
                return new FeedBackBoImpl();
            case INSURANCETYPE:
                return new InsuranceTypeBoImpl();
            case MANAGER:
                return new ManagerBoImpl();
            case NOTIFICATION:
                return new NotificationBoImpl();
            case POLICY:
                return new PolicyBoImpl();
            case SUPPORTTICKET:
                return new SupportTicketBoImpl();
            case USER:
                return new UserBoImpl();
            case USERDETAILS:
                return new UserPolicyDetailsBoImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PAYMENTDETAIL:
                return new PaymentDetailsBOImpl();
            default:
                return null;
        }
    }
}
