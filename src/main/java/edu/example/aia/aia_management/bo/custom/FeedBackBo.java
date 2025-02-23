package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.FeedBackDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface FeedBackBo extends SuperBo {
    // Retrieve all feedback entries from the database
    List<FeedBackDto> getAllFeedback() throws SQLException, ClassNotFoundException;

    // Save a new feedback entry to the database
    boolean saveFeedback(FeedBackDto feedback) throws SQLException, ClassNotFoundException;

    // Delete a feedback entry from the database
    boolean deleteFeedback(int feedbackId) throws SQLException, ClassNotFoundException;

    // Update an existing feedback entry in the database
    boolean updateFeedback(FeedBackDto feedback) throws SQLException, ClassNotFoundException;

    // Retrieve the next feedback ID
    int getNextFeedbackId() throws SQLException, ClassNotFoundException;

    // Search for a feedback entry by ID
    FeedBackDto searchFeedbackById(int feedbackId) throws SQLException;

    // Search for a feedback entry by ID
    ArrayList<FeedBackDto> searchFeedbackById(String newValue) throws SQLException, ClassNotFoundException;

    // Search for a feedback entry by ID
   // ArrayList<FeedBackDto> searchFeedbackById(String newValue) throws SQLException, ClassNotFoundException;
}
