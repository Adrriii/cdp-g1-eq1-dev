package dao;

import domain.UserStory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserStoryDAO extends SQLDAO<UserStory> implements UserStoryDAO {
    
    @Override
    protected UserStory getObjectFromResult(ResultSet resultSet) throws SQLException {
        return new UserStory(
            resultSet.getInt("project"),
            resultSet.getInt("id"),
            resultSet.getString("description"),
            resultSet.getString("priority"),
            resultSet.getInt("difficulty")
        );
    }

    @Override
    public UserStory getById(int project_id, int id) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND id=?";

        List<Object> opt = new ArrayList<>();
        opt.add(project_id);
        opt.add(id);

        ResultSet resultSet = SQLDAOFactory.query(statement, opt);

        if (resultSet.next()) {

            UserStory us = getObjectFromResult(resultSet);

            resultSet.close();

            return us;
        }

        throw new SQLException("Can't find user story with this id and project");
    }

    @Override
    public List<UserStory> getAllForProject(int project_id) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=?";

        List<Object> opt = new ArrayList<>();
        opt.add(project_id);

        ResultSet resultSet = SQLDAOFactory.query(statement, opt);

        return getAllObjectsFromResult(resultSet);
    }

    @Override
    public UserStory insert(UserStory us) throws SQLException {
        String statement = "INSERT INTO us (project, description, priority, difficulty)";
        statement += "VALUES (?,?,?,?)";

        List<Object> opt = new ArrayList<>();
        opt.add(us.projectId);
        opt.add(us.description);
        opt.add(us.priority);
        opt.add(us.difficulty);

        return doInsert(statement, opt);
    }

    @Override
    public void update(UserStory us) throws SQLException {
        String statement = "UPDATE us SET description = ?, priority = ?, difficulty = ? WHERE project = ? AND id = ?";

        List<Object> opt = new ArrayList<>();
        opt.add(us.description);
        opt.add(us.priority);
        opt.add(us.difficulty);

        opt.add(us.projectId);
        opt.add(us.id);

        SQLDAOFactory.exec(statement, opt);
    }

    @Override
    public void delete(UserStory us) throws SQLException {
        String statement = "DELETE FROM us WHERE project = ? AND id = ?";

        List<Object> opt = new ArrayList<>();
        opt.add(us.projectId);
        opt.add(us.id);
        
        SQLDAOFactory.exec(statement, opt);
    }

    @Override
    public List<UserStory> getByPriority(int project_id, String priority) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND priority = ?";

        List<Object> opt = new ArrayList<>();
        opt.add(project_id);
        opt.add(priority);

        ResultSet resultSet = SQLDAOFactory.query(statement, opt);

        return getAllObjectsFromResult(resultSet);
    }

    @Override
    public List<UserStory> getByDifficulty(int project_id, int difficulty) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND difficulty = ?";

        List<Object> opt = new ArrayList<>();
        opt.add(project_id);
        opt.add(difficulty);

        ResultSet resultSet = SQLDAOFactory.query(statement, opt);

        return getAllObjectsFromResult(resultSet);
    }
}
