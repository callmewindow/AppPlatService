package buaa.edu.global;

import entity.OperationResponse;
import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * Created by winter on 2014/7/23.
 */
public class OperationResponseBuilder {
    public  static OperationResponse createNot200Response(CloseableHttpResponse response){
        OperationResponse accessStatisicInfo=new OperationResponse();
        accessStatisicInfo.setResponseString(response.toString());
        return accessStatisicInfo;
    }

    public static OperationResponse createHttpRequestExceptionResponse(Exception e){
        OperationResponse accessStatisicInfo=new OperationResponse();
        accessStatisicInfo.setResponseString(e.getMessage());
        return accessStatisicInfo;
    }
}
