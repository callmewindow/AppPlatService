package webService.operator;

import entity.*;
import entity.databaseEntity.Node;

import javax.jws.WebService;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by winter on 2014/6/15.
 */
public interface ITaskManage {

    @GET
    @Path("/getMyColTask")
    List<TaskInfo> getMyColTask(@QueryParam("userId")String userId);
    @GET
    @Path("/getAllMyTask")
    List<PersonalTaskInfo> getAllMyTask(@QueryParam("userId") String userId );
    @GET
    @Path("/getTaskInfo")
    TaskInfo getTaskInfo(@QueryParam("id") String id);
    @GET
    @Path("/getPersonalTaskRootNode")
    Node getPersonalTaskRootNode(@QueryParam("perTaskId") String perTaskId);
    @POST
    @Path("/addTaskInfo")
    TaskInfo addTaskInfo(@QueryParam("userId") String userId,TaskInfo taskInfo);
    @GET
    @Path("/deleteTask")
    NodeInfo deleteTaskInfo(@QueryParam("userId")String userId, @QueryParam("id")String delTaskId);

    @GET
    @Path("/deletePersonalTaskInfo")
    NodeInfo deletePersonalTaskInfo(@QueryParam("userId")String userId, @QueryParam("id")String delTaskId);

    @GET
    @Path("/isTaskCreater")
    boolean isTaskCreater(@QueryParam("userId")String userId, @QueryParam("taskId") String taskId);
}
