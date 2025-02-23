package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.FeedBackDao;
import edu.example.aia.aia_management.dto.FeedBackDto;
import edu.example.aia.aia_management.entity.FeedBack;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FeedBackDaoImpl implements FeedBackDao {

    // Retrieve all feedback entries from the database
    @Override
    public  ArrayList<FeedBack> getAll() throws SQLException {
//        String sql = "SELECT * FROM Feedback";
//        ResultSet resultSet = CrudUtil.execute(sql);
//        List<FeedBackDto> feedbackList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            feedbackList.add(new FeedBackDto(
//                    resultSet.getInt("FeedbackId"),
//                    resultSet.getInt("ManagerId"),
//                    resultSet.getDate("FeedbackDate"),
//                    resultSet.getString("Comment")
//            ));
//        }
//        return feedbackList;
        ResultSet rst=CrudUtil.execute( "SELECT * FROM Feedback");
        ArrayList<FeedBack> feedBacks=new ArrayList<>();

        while (rst.next()){
            FeedBack feedBack=new FeedBack(
                    rst.getInt("FeedbackId"),
                    rst.getInt("ManagerId"),
                    rst.getDate("FeedbackDate"),
                    rst.getString("Comment")

            );
            feedBacks.add(feedBack);
        }
        return feedBacks;
    }



    // Save a new feedback entry to the database
    @Override
    public boolean save(FeedBack feedbackEntity) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO Feedback (FeedbackId, ManagerId, FeedbackDate, Comment) VALUES (?, ?, ?, ?)";
//        return CrudUtil.execute(sql, feedback.getFeedbackId(), feedback.getManagerId(), feedback.getFeedbackDate(), feedback.getComment());
    return SQlUtil.execute("INSERT INTO Feedback (FeedbackId, ManagerId, FeedbackDate, Comment) VALUES (?, ?, ?, ?)",
            feedbackEntity.getFeedbackId(),
            feedbackEntity.getManagerId(),
            feedbackEntity.getFeedbackDate(),
            feedbackEntity.getComment()
       );
    }

    // Delete a feedback entry from the database
    @Override
    public boolean delete(int feedbackId) throws SQLException {
//        String sql = "DELETE FROM Feedback WHERE FeedbackId = ?";
//        return CrudUtil.execute(sql, feedbackId);
     CrudUtil.execute("DELETE FROM Feedback WHERE FeedbackId = ?");
     return true;
    }

    // Update an existing feedback entry in the database
    @Override
    public boolean update(FeedBack feedbackEntity) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE Feedback SET ManagerId = ?, FeedbackDate = ?, Comment = ? WHERE FeedbackId = ?";
//        return CrudUtil.execute(sql, feedback.getManagerId(), feedback.getFeedbackDate(), feedback.getComment(), feedback.getFeedbackId());
    return SQlUtil.execute("UPDATE Feedback SET ManagerId = ?, FeedbackDate = ?, Comment = ? WHERE FeedbackId = ?",
            feedbackEntity.getManagerId(),
            feedbackEntity.getFeedbackDate(),
            feedbackEntity.getComment(),
            feedbackEntity.getFeedbackId()
      );
    }

    // Retrieve the next feedback ID
    public int getNextId() throws SQLException {
        String sql = "SELECT MAX(FeedbackId) AS maxId FROM Feedback";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt("maxId");
            return maxId+1; // Increment maxId by 1 for the next ID
        } else {
            return 1; // Start with 1 if there are no feedback records
        }
    }

    // Search for a feedback entry by ID
    public  ArrayList<FeedBack> search(String newValue) throws SQLException, ClassNotFoundException {
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
   ResultSet rst=SQlUtil.execute("SELECT * FROM Feedback WHERE FeedbackId = ?",newValue);
   ArrayList<FeedBack> searchResults=new ArrayList<>();
   while (rst.next()){
       FeedBack feedBack=new FeedBack(
                    rst.getInt("FeedbackId"),
                    rst.getInt("ManagerId"),
                    rst.getDate("FeedbackDate"), // java.sql.Date
                    rst.getString("Comment")
       );
       searchResults.add(feedBack);
   }
   return searchResults;
    }
}
