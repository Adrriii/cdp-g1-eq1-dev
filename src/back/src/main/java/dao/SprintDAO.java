package dao;

import domain.Sprint;

import java.util.List;

public interface SprintDAO {
    Sprint getById(int project_id, int id) throws Exception;
    List<Sprint> getAllForProject(int projectId) throws Exception;
    Sprint insert(Sprint sprint) throws Exception;
    void update(Sprint sprint) throws Exception;
    void delete(Sprint sprint) throws Exception;
}
