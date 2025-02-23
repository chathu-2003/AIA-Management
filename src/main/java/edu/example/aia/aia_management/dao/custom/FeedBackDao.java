package edu.example.aia.aia_management.dao.custom;

import edu.example.aia.aia_management.dao.CrudDAO;
import edu.example.aia.aia_management.dto.FeedBackDto;
import edu.example.aia.aia_management.entity.FeedBack;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FeedBackDao extends CrudDAO<FeedBack> {
    // Retrieve all feedback entries from the database
    //ArrayList<FeedBack> getAll() throws SQLException;

    // Save a new feedback entry to the database
   // boolean save(FeedBackDto feedback) throws SQLException;
}
