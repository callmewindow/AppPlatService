package service;

import buaa.edu.global.AppPlatConstant;
import buaa.edu.global.AppPlatHttpClientBuilder;
import dao.AllDAO;
import entity.databaseEntity.User;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import serviceInterface.IDigitalSignatures;
import entity.OperationResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class DigitalSignatureService implements IDigitalSignatures{
    private AllDAO allDAO;

    public AllDAO getAllDAO() {
        return allDAO;
    }

    public void setAllDAO(AllDAO allDAO) {
        this.allDAO = allDAO;
    }

    private  static Logger logger  = Logger.getLogger(DigitalSignatureService.class);
	public OperationResponse digitalSignatures(String signature) {
		// TODO Auto-generated method stub
//         System.out.println("已接受到前台的请求");
		 CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith3SecsTimeOut();
		 HttpGet httpGet = new HttpGet(AppPlatConstant.CLOUD_PLATFORM_HOST+"/_digital/"+signature);
		 httpGet.setHeader("Host", "192.168.3.132");
		 httpGet.setHeader("Accept","application/json");
		 CloseableHttpResponse response;
		 OperationResponse digitalSignatures=new OperationResponse();
		//真实测试代码
		try {
		    response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info(response);
            if(statusCode == 200){
                HttpEntity myEntity = response.getEntity();
                String resString = EntityUtils.toString(myEntity);
                JSONObject jsonobj=new JSONObject(resString);
                String errMsg = jsonobj.getString("errMsg");
                digitalSignatures.setErrMsg(errMsg);
                boolean isOK = jsonobj.getBoolean("isOK");
                digitalSignatures.setIsOK(isOK);
                boolean isValid = jsonobj.getBoolean("isValid");
                digitalSignatures.setIsValid(isValid);
                System.out.println(isOK);
                System.out.println(isValid);
            }

		}catch(ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return digitalSignatures;
		}
        catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return digitalSignatures;
		}
		return digitalSignatures;
	}
    public Boolean insertUserToDB(String userId, String account, String domain, String permission, String name){
        try {
             name = URLDecoder.decode(name, "UTF-8");
            account = URLDecoder.decode(account,"UTF-8");
            domain = URLDecoder.decode(domain,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        User isSavedUser = allDAO.getUserDAO().findById(userId);
        Boolean isSaved = true;
        if(isSavedUser == null){
            User user = new User();
            user.setUserName(name);
            user.setUserAccount(account);
            user.setUserId(userId);
            user.setUserDomain(domain);
            try {
                allDAO.getUserDAO().save(user);
            } catch (Exception e) {
                isSaved =false;
                e.printStackTrace();
            }
        }
        return  isSaved;
    }

}
