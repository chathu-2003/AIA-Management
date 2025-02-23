package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.FeedBackBo;
import edu.example.aia.aia_management.dao.DAOFactory;
import edu.example.aia.aia_management.dao.custom.FeedBackDao;
import edu.example.aia.aia_management.dto.FeedBackDto;
import edu.example.aia.aia_management.entity.FeedBack;

import java.sql.SQLException;
import java.util.ArrayList;

public class FeedBackBoImpl implements FeedBackBo {

    FeedBackDao feedBackDao=(FeedBackDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.FEEDBACK);

    // Retrieve all feedback entries from the database
    @Override
    public  ArrayList<FeedBackDto> getAllFeedback() throws SQLException, ClassNotFoundException {
//
        ArrayList<FeedBack> feedBackArrayList=feedBackDao.getAll();
        ArrayList<FeedBackDto> feedbackdtos=new ArrayList<>();

        for (FeedBack feedBack:feedBackArrayList){
            feedbackdtos.add(new FeedBackDto(feedBack.getFeedbackId(),feedBack.getManagerId(),feedBack.getFeedbackDate(),feedBack.getComment()));

        }
        return feedbackdtos;
    }

    // Save a new feedback entry to the database
    @Override
    public boolean saveFeedback(FeedBackDto feedback) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO Feedback (FeedbackId, ManagerId, FeedbackDate, Comment) VALUES (?, ?, ?, ?)";
//        return CrudUtil.execute(sql, feedback.getFeedbackId(), feedback.getManagerId(), feedback.getFeedbackDate(), feedback.getComment());
    FeedBack feedBack=new FeedBack(feedback.getFeedbackId(),feedback.getManagerId(),feedback.getFeedbackDate(),feedback.getComment());
    return feedBackDao.save(feedBack);
     }

    // Delete a feedback entry from the database
    @Override
    public boolean deleteFeedback(int feedbackId) throws SQLException, ClassNotFoundException {
//      String sql = "DELETE FROM Feedback WHERE FeedbackId = ?";
//        return CrudUtil.execute(sql, feedbackId);
    feedBackDao.delete(feedbackId);
    return true;
    }

    // Update an existing feedback entry in the database
    @Override
    public boolean updateFeedback(FeedBackDto feedback) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE Feedback SET ManagerId = ?, FeedbackDate = ?, Comment = ? WHERE FeedbackId = ?";
//        return CrudUtil.execute(sql, feedback.getManagerId(), feedback.getFeedbackDate(), feedback.getComment(), feedback.getFeedbackId());
   FeedBack feedBack=new FeedBack(feedback.getManagerId(),feedback.getManagerId(),feedback.getFeedbackDate(),feedback.getComment());
   return feedBackDao.update(feedBack);
    }

    // Retrieve the next feedback ID
    @Override
    public int getNextFeedbackId() throws SQLException, ClassNotFoundException {
//        String sql = "SELECT MAX(FeedbackId) AS maxId FROM Feedback";
//        ResultSet resultSet = CrudUtil.execute(sql);
//
//        if (resultSet.next()) {
//            int maxId = resultSet.getInt("maxId");
//            return String.valueOf(maxId + 1); // Increment maxId by 1 for the next ID
//        } else {
//            return "1"; // Start with 1 if there are no feedback records
//        }
        return feedBackDao.getNextId();
    }

    @Override
    public FeedBackDto searchFeedbackById(int feedbackId) throws SQLException {
        return null;
    }

    // Search for a feedback entry by ID
    @Override
    public  ArrayList<FeedBackDto> searchFeedbackById(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<FeedBack> feedBacks=feedBackDao.search(newValue);
        ArrayList<FeedBackDto> feedBackDtos=new ArrayList<>();
        for (FeedBack feedBack:feedBacks){
            FeedBackDto feedBackDto=new FeedBackDto();
            feedBackDto.setFeedbackId(feedBack.getFeedbackId());
            feedBackDto.setManagerId(feedBack.getManagerId());
            feedBackDto.setFeedbackDate(feedBack.getFeedbackDate());
            feedBackDto.setComment(feedBack.getComment());
        }
        return feedBackDtos;
//        String sql = "SELECT * FROM Feedback WHERE FeedbackId = ?";
//        ResultSet resultSet = CrudUtil.execute(sql, feedbackId);
//
//        if (resultSet.next()) {
//            return new FeedBackDto(
//                    resultSet.getInt("FeedbackId"),
//                    resultSet.getInt("ManagerId"),
//                    resultSet.getDate("FeedbackDate"), // java.sql.Date
//                    resultSet.getString("Comment")
//            );
//        }
//        return null; // Return null if no matching feedback is found
    }
}
