package edu.example.aia.aia_management.dao;

//import com.example.layeredarchitecture.dao.custom.CustomerDAO;
//import com.example.layeredarchitecture.dao.custom.impl.*;
import edu.example.aia.aia_management.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {
    }
    public static DAOFactory getInstance() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }
    public enum DAOType{
        CLAIM,COMMISSION,CUSTOMER,FEEDBACK,INUSRANCETYPE,MANAGER,NOTIFICATION,POLICY,SUPPORTTICKET,USER,USERPOLICYDETAILS,PAYMENT
    }
    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case CLAIM:
                return new ClaimDaoImpl();
            case COMMISSION:
                return new CommissionDaoImpl();
            case CUSTOMER:
                return new CustomerDaoImpl();
            case FEEDBACK:
                return new FeedBackDaoImpl();
            case INUSRANCETYPE:
                return new InsurancetypeDaoImpl();
            case MANAGER:
                return new ManagerDaoImpl();
            case NOTIFICATION:
                return new NotificationDaoImpl();
            case POLICY:
                return new PolicyDaoImpl();
            case SUPPORTTICKET:
                return new SupportTicketDaoImpl();
            case USER:
                return new UserDaoImpl();
            case USERPOLICYDETAILS:
                return new UserDetailsDaoImpl();
            case PAYMENT:
                return new PaymentDaoImpl();
            default:
                return null;
        }
    }
}
