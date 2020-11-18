package webService.operator;

import entity.collaborativeDesignEntity.RefreshData;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * Created by winter on 2014/11/29.
 */
public interface ICollaDesignManage {

    @GET
    @Path("/getCachedSceneData")
    String getCachedSceneData(@QueryParam("userId") String userId,@QueryParam("taskId") String taskId,@QueryParam("solutionId") String solutionId,@QueryParam("nodeId") String nodeId,@QueryParam("toolType") String toolType);

    @GET
    @Path("/getLockIdList")
    String getLockIdList(@QueryParam("taskId") String taskId,@QueryParam("solutionId") String solutionId,@QueryParam("nodeId") String nodeId);
    @GET
    @Path("/saveSceneDataByTimer")
    void saveSceneDataByTimer();
    @POST
    @Path("/refreshSceneData")
    void refreshSceneData(RefreshData data);
    @POST
    @Path("/getGsAndSatData")
    String getGsAndSatData(String gsAndSatString);
}
