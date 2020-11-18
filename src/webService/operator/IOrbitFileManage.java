package webService.operator;

import entity.OrbitSection;
import entity.databaseEntity.Node;
import entity.NodeInfo;
import entity.SaveNode;
import entity.Struct;
import entity.databaseEntity.PersonalTask;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by winter on 2014/8/23.
 */
public interface IOrbitFileManage {
    //创建文件夹
    @POST
    @Path("/saveOrbit")
    Boolean saveOrbit(Struct s);
    //删除卫星文件
    @POST
    @Path("/deleteOrbit")
    Boolean deleteOrbit(Struct s);
    //删除场景文件夹
    @POST
    @Path("/deleteSceneDir")
    Boolean deleteSceneDir(Struct s);
    @GET
    @Path("/getTreeNodes")
    List<Node> getTreeNodes(@QueryParam("id") String id);
    @POST
    @Path("/addTreeNode")
    NodeInfo addTreeNode(@QueryParam("userId") String userId, Node node);

    @POST
    @Path("/mergeTaskToDB")
    Boolean mergeTaskToDB(Struct s);
    @PUT
    @Path("/updateTreeNode")
    Boolean updateTreeNode( Node node);

//    @GET
//    @Path("/deletePerTaskInDB")
//    Boolean deletePerTaskInDB(@QueryParam("userId") String userId,@QueryParam("perTaskName") String perTaskName);

    @DELETE
    @Path("/deleteTreeNode")
    NodeInfo deleteTreeNode(@QueryParam("userId") String userId,Node node);
    //从DLL文件中获取行星位置
    @GET
    @Path("/getPlanetPosition")
    String getPlanetPosition(@QueryParam("y") int y, @QueryParam("m") int m, @QueryParam("d") int d, @QueryParam("h") int h, @QueryParam("mi") int mi, @QueryParam("sec") int sec, @QueryParam("a") int a, @QueryParam("aT") int aT, @QueryParam("b") int b, @QueryParam("bT") int bT);
    //从Dll文件中获得卫星转移时的起始速度和到达速度
    @GET
    @Path("/getSatTransferVelocity")
    String getSatTransferVelocity(@QueryParam("R1") double [] R1, @QueryParam("R2") double [] R2, @QueryParam("FTIME") double FTIME);
    //获取文件内容
    @GET
    @Path("/getFileByString")
    String getFileByString(@QueryParam("path") String path, @QueryParam("nodeId") String nodeId);
    //获取卫星轨道文件
    @GET
    @Path("/getFilesNameWithoutExtension")
    List<SaveNode> getFilesNameWithoutExtension(@QueryParam("userId") String userId);
    @GET
    @Path("/getTaskNameByUserId")
    List<PersonalTask> getTaskNameByUserId(@QueryParam("userId") String userId);
    //获取辐射带的点
    @GET
    @Path("/getRadBeltPoint")
    String getRadBeltPoint();
    //获取磁力线的点
    @GET
    @Path("/getTrackPoints")
    String getTrackPoints(@QueryParam("lineId") int lineId);
    //获取指定能级的辐射带粒子通量
    @GET
    @Path("/getRadBeltData")
    double[][][] getRadBeltData(@QueryParam("degree") int degree);
    //获取卫星轨道文件
    @GET
    @Path("/getSatelliteNameByParentId")
    List<SaveNode> getSatelliteNameByParentId(@QueryParam("nodeId") String nodeId);
    @GET
    @Path("/getAllNodesByRootId")
    List<Node> getAllNodesByRootId(@QueryParam("rootId") String rootId);

    @POST
    @Path("/addOrbitSection")
    Object addOrbitSection(@QueryParam("id")String id,  OrbitSection orbitSection);
    @GET
    @Path("/getOrbitSection")
    Object getOrbitSection(@QueryParam("id") String id);
    @PUT
    @Path("/updateOrbitSection")
    Object updateOrbitSection(@QueryParam("id") String id ,OrbitSection orbitSection);
    @DELETE
    @Path("/deleteOrbitSection")
    Object deleteOrbitSection(@QueryParam("id") String id,OrbitSection orbitSection);
}
