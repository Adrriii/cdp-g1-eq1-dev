package routes;

import dao.TaskDAO;
import dao.UserStoryDAO;
import domain.Task;
import domain.UserStory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("projects/{projectId}/us")
public class UserStories {
    @Inject UserStoryDAO userStoryDAO;
    @Inject TaskDAO taskDAO;

    @GET
    @Produces("application/json")
    public Response getAll(@PathParam("projectId") int projectId) {
        try {
            return Response.status(200).entity(userStoryDAO.getAllForProject(projectId)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{usId}")
    @Produces("application/json")
    public Response get(@PathParam("projectId") int projectId, @PathParam("usId") int usId) {
        try {
            return Response.status(200).entity(userStoryDAO.getById(projectId, usId)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{usId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response putTest(@PathParam("projectId") int projectId, @PathParam("usId") int usId, UserStory userStory) {
        userStory = new UserStory(usId, projectId, userStory.description, userStory.priority, userStory.difficulty);

        try {
            userStoryDAO.update(userStory);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).entity(userStory).build();
    }

    @Path("{usId}")
    @DELETE
    @Produces("application/json")
    public Response deleteTest(@PathParam("projectId") int projectId, @PathParam("usId") int usId) {
        try {
            UserStory userStory = userStoryDAO.getById(projectId, usId);

            for (Task task : taskDAO.getAllForUserStory(projectId, usId)) {
                taskDAO.update(new Task(task.id, task.projectId, null, task.title, task.duration, task.status));
            }

            userStoryDAO.delete(userStory);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).build();
    }
}