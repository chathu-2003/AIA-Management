package edu.example.aia.aia_management.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO {
    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException ;

    public boolean save(T Dto) throws SQLException, ClassNotFoundException ;

    public boolean update(T Dto) throws SQLException, ClassNotFoundException;

    public boolean delete(int id) throws SQLException, ClassNotFoundException;

    public int getNextId() throws SQLException, ClassNotFoundException;

    public ArrayList<T> search(String newValue) throws SQLException, ClassNotFoundException;
}
