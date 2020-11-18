package service;

import buaa.edu.global.AppPlatConstant;
import org.apache.log4j.Logger;
import serviceInterface.IAccessStatisic;
import entity.OperationResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AccessStatisicService implements IAccessStatisic{
    private  static Logger logger  = Logger.getLogger(AccessStatisicService.class);
    public OperationResponse insertAccessStatic(String userId,String account, String domain,
                                                String url, String describe, String ip) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(AppPlatConstant.CLOUD_PLATFORM_HOST+"/_visitor/"+userId);
        httpPost.setHeader("Host", "192.168.3.132");
        httpPost.setHeader("Accept","application/json");
        httpPost.setHeader("Content-Type","application/json");

        //���ò���
        JSONObject jsonVisitor = new JSONObject();
        try {
            jsonVisitor.put("account", account);
            jsonVisitor.put("domain", domain);
            jsonVisitor.put("url", url);
            jsonVisitor.put("describe", describe);
            jsonVisitor.put("ip", ip);
            httpPost.setEntity(new StringEntity(jsonVisitor.toString()));
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        CloseableHttpResponse response;
        OperationResponse accessStatisicInfo=new OperationResponse();

        try {
            response = httpclient.execute(httpPost);
            logger.info(response);
            HttpEntity myEntity = response.getEntity();
            System.out.println(myEntity.getContentType());
            System.out.println(myEntity.getContentLength());

            String resString = EntityUtils.toString(myEntity);

//            String resString = "{"
//                    + "'errMsg': '',"
//                    + "'isOK': true"
//                    + "}";
//            System.out.println(resString);


            // ʹ�÷��ص��ַ�ֱ�ӹ���һ��JSONObject
            JSONObject jsonobj=new JSONObject(resString);
            String errMsg=jsonobj.getString("errMsg");
            accessStatisicInfo.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            accessStatisicInfo.setIsOK(isOK);
        }catch(ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return accessStatisicInfo;
    }
}
