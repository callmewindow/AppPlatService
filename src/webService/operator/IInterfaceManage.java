package webService.operator;

import entity.CoTask;
import entity.OperationResponse;

import javax.jws.WebService;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by winter on 2014/7/19.
 */
@WebService
public interface IInterfaceManage {

    @GET
    @Path("/verifyDigitalSignature")
   OperationResponse verifyDigitalSignature(@QueryParam("signature") String signature,@QueryParam("userId")String userId,@QueryParam("account")String account,@QueryParam("domain")String domain,@QueryParam("permission")String permission,@QueryParam("name")String name);
    @GET
    @Path("/insertAccessInfo")
    OperationResponse insertAccessInfo(@QueryParam("account") String account,@QueryParam("name")String name,@QueryParam("domain")String domain,@QueryParam("ip")String ip,@QueryParam("dateTime")String dateTime,@QueryParam("service")String service,@QueryParam("url")String url,@QueryParam("describe")String describe);
    @GET
    @Path("/getMyTaskInfo")
    List<CoTask> getMyTaskInfo(@QueryParam("userId")String userId);
    @GET
    @Path("/insertAccessStatic")
   OperationResponse insertAccessStatic(@QueryParam("userId")String userId,@QueryParam("account")String account, @QueryParam("domain")String domain,@QueryParam("url")String url, @QueryParam("describe")String describe,@QueryParam("ip")String ip);
    @PUT
    @Path("/fileUpload/{fileName}")
    OperationResponse fileUpload(@PathParam("fileName") String fileName ,byte[] byteStream);
}
