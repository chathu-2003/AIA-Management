package edu.example.aia.aia_management.Model;

import edu.example.aia.aia_management.dto.FeedBackDto;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; // Ensure consistency with java.sql.Date for database operations
import java.util.ArrayList;
import java.util.List;

public class FeedBackModel {

    // Retrieve all feedback entries from the database
    public List<FeedBackDto> getAllFeedback() throws SQLException {
        String sql = "SELECT * FROM Feedback";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<FeedBackDto> feedbackList = new ArrayList<>();

        while (resultSet.next()) {
            feedbackList.add(new FeedBackDto(
                    resultSet.getInt("FeedbackId"),
                    resultSet.getInt("ManagerId"),
                    resultSet.getDate("FeedbackDate"),
                    resultSet.getString("Comment")
            ));
        }
        return feedbackList;
    }

    // Save a new feedback entry to the database
    public boolean saveFeedback(FeedBackDto feedback) throws SQLException {
        String sql = "INSERT INTO Feedback (FeedbackId, ManagerId, FeedbackDate, Comment) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, feedback.getFeedbackId(), feedback.getManagerId(), feedback.getFeedbackDate(), feedback.getComment());
    }

    // Delete a feedback entry from the database
    public boolean deleteFeedback(int feedbackId) throws SQLException {
        String sql = "DELETE FROM Feedback WHERE FeedbackId = ?";
        return CrudUtil.execute(sql, feedbackId);
    }

    // Update an existing feedback entry in the database
    public boolean updateFeedback(FeedBackDto feedback) throws SQLException {
        String sql = "UPDATE Feedback SET ManagerId = ?, FeedbackDate = ?, Comment = ? WHERE FeedbackId = ?";
        return CrudUtil.execute(sql, feedback.getManagerId(), feedback.getFeedbackDate(), feedback.getComment(), feedback.getFeedbackId());
    }

    // Retrieve the next feedback ID
    public String getNextFeedbackId() throws SQLException {
        String sql = "SELECT MAX(FeedbackId) AS maxId FROM Feedback";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt("maxId");
            return String.valueOf(maxId + 1); // Increment maxId by 1 for the next ID
        } else {
            return "1"; // Start with 1 if there are no feedback records
        }
    }

    // Search for a feedback entry by ID
    public FeedBackDto searchFeedbackById(int feedbackId) throws SQLException {
        String sql = "SELECT * FROM Feedback WHERE FeedbackId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, feedbackId);

        if (resultSet.next()) {
            return new FeedBackDto(
                    resultSet.getInt("FeedbackId"),
                    resultSet.getInt("ManagerId"),
                    resultSet.getDate("FeedbackDate"), // java.sql.Date
                    resultSet.getString("Comment")
            );
        }
        return null; // Return null if no matching feedback is found
    }
}
