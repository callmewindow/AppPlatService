package webService.operator;

import entity.Struct;
import entity.SaveNode;
import entity.databaseEntity.PersonalTask;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by dpy on 2014/07/03.
 */
public interface IStructFileManage {
    //创建文件夹
    @POST
    @Path("/createFolders")
    Boolean createFolders(Struct s);

    @POST
    @Path("/createFile")
    Boolean createFile(Struct s);

    @POST
    @Path("/saveTaskToDB")
    Boolean saveTaskToDB(Struct s);

    @GET
    @Path("/getSaveList")
    List<SaveNode> getSaveList(@QueryParam("path") String path);

    //获取文件内容
    @GET
    @Path("/getFileByString")
    String getFileByString(@QueryParam("path") String path);

    @POST
    @Path("/testStorerage")
    Boolean testStorerage(Struct s);

    @GET
    @Path("/getFilesNameWithoutExtension")
    List<SaveNode> getFilesNameWithoutExtension(@QueryParam("path") String path);
    @GET
    @Path("/getTaskNameByUserId")
    List<PersonalTask> getTaskNameByUserId(@QueryParam("userId") String userId);
    @GET
    @Path("/exportFile")
    String exportFile(@QueryParam("path") String path);
}