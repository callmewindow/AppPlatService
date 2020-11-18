package webService.operator;

import entity.Struct;
import entity.databaseEntity.Node;
import entity.NodeInfo;
import entity.SaveNode;
import entity.databaseEntity.PersonalTask;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by winter on 2014/9/20.
 */
public interface ICoverAnalysisManage {
    //创建文件夹
    @GET
    @Path("/getTreeNodes")
    List<Node> getTreeNodes(@QueryParam("id") String id, @QueryParam("targetNodeType") String targetNodeType);
    @POST
    @Path("/addTreeNode")
    NodeInfo addTreeNode(@QueryParam("userId") String userId, Node node);
    @PUT
    @Path("/updateTreeNode")
    Boolean updateTreeNode(Node node);
    @DELETE
    @Path("/deleteTreeNode")
    NodeInfo deleteTreeNode(@QueryParam("userId") String userId, Node node);
    @GET
    @Path("/getTaskNameByUserId")
    List<PersonalTask> getTaskNameByUserId(@QueryParam("userId") String userId);
    @GET
    @Path("/getNameByParentId")
    List<SaveNode> getNameByParentId(@QueryParam("nodeId") String nodeId);
    //获取文件内容
    @GET
    @Path("/getFileByString")
    String getFileByString(@QueryParam("path") String path);
    @GET
    @Path("/coverCompute")//覆盖分析计算
    String coverCompute(@QueryParam("jsonstr") String jsonstr, @QueryParam("type") int type);
    @GET
    @Path("/tansferSchedulViewTable")//SchedulView表头生成
    String tansferSchedulViewTable(@QueryParam("jsonstr") String jsonstr);
    //创建文件夹
    @POST
    @Path("/saveDataTransFile")
    Boolean saveDataTransFile(Struct s);

    @GET
    @Path("/getTaskTree")
    String getTaskTree( @QueryParam("perTaskId") String perTaskId);
    @POST
    @Path("/coverComputeExt")//覆盖分析计算
    String coverComputeExt(@FormParam("jsonstr") String jsonstr, @FormParam("type") int type);
}
