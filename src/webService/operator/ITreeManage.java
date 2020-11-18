package webService.operator;

import entity.databaseEntity.Node;
import entity.NodeInfo;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by winter on 2014/6/7.
 */
public interface ITreeManage {

    @GET
    @Path("/getTreeNodes")
    List<Node> getTreeNodes(@QueryParam("id") String id,@QueryParam("node") String nodeId);

    @GET
    @Path("/getRootNode")
    Node getRootNode(@QueryParam("id") String id);

    @POST
    @Path("/addTreeNode")
    NodeInfo addTreeNode(@QueryParam("userId")String userId,  Node node);

    @PUT
    @Path("/updateTreeNode")
    Boolean updateTreeNode( Node node);
    @DELETE
    @Path("/deleteTreeNode")
    NodeInfo deleteTreeNode(@QueryParam("userId") String userId,Node node);

    @GET
    @Path("/processSubmit")
    Boolean processSubmit(@QueryParam("currentProcess") String currentProcess);

    @GET
    @Path("/getAllSensorsBySolutionId")
    List<String> getAllSensorsBySolutionId(@QueryParam("solutionId") String solutionId);

    @GET
    @Path("/getCoverAnaNodeBySolutionId")
    List<Node> getCoverAnaNodeBySolutionId(@QueryParam("solutionId") String solutionId);
}
