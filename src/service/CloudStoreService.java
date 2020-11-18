package service;

import java.io.*;

import buaa.edu.global.AppPlatConstant;
import buaa.edu.global.AppPlatHttpClientBuilder;
import buaa.edu.global.CFileOperate;
import buaa.edu.global.OperationResponseBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import serviceInterface.ILocalStoreOperate;
import entity.DirInfo;
import entity.OperationResponse;

public class CloudStoreService implements ILocalStoreOperate {
    private static Logger logger = Logger.getLogger(CloudStoreService.class);

    public OperationResponse getDirInfo(String userId, String directory) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpGet httpGet = null;
        if (directory == null || directory.isEmpty()) {
            httpGet = new HttpGet(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_dir/" + userId);
        } else {
            httpGet = new HttpGet(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_dir/" + userId + "/" + directory);
        }

        httpGet.setHeader("Host", "192.168.3.132");
        httpGet.setHeader("Accept", "application/json");

        CloseableHttpResponse response;
        OperationResponse dirInfo = new OperationResponse();
        try {
            response = httpclient.execute(httpGet);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");

            JSONObject jsonobj = new JSONObject(resString);

            String errMsg = jsonobj.getString("errMsg");
            dirInfo.setErrMsg(errMsg);
            boolean isOK = jsonobj.getBoolean("isOK");
            dirInfo.setIsOK(isOK);
            JSONArray dirDatas = jsonobj.getJSONArray("contentList");
            DirInfo dir[] = new DirInfo[dirDatas.length()];
            for (int i = 0; i < dirDatas.length(); i++) {
                JSONObject dirData = dirDatas.getJSONObject(i);
                dir[i] = new DirInfo();
                dir[i].setCreateTime(dirData.getString("createTime"));
                dir[i].setFilePath(dirData.getString("filePath"));
                dir[i].setIsDirectory(dirData.getInt("isDirectory"));
                dir[i].setObjectName(dirData.getString("objectName"));
                dir[i].setSize(dirData.getInt("size"));
            }
            dirInfo.setData(dir);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return dirInfo;
    }

    public OperationResponse createDir(String userId, String directory) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpPut httpPut = new HttpPut(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_dir/" + userId + "/" + directory + "?operationType=create");
        httpPut.setHeader("Host", "192.168.3.132");
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-Type", "application/json");

        CloseableHttpResponse response;
        OperationResponse creDir = new OperationResponse();

        try {
            response = httpclient.execute(httpPut);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");
            JSONObject jsonobj = new JSONObject(resString);

            String errMsg = jsonobj.getString("errMsg");
            creDir.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            creDir.setIsOK(isOK);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return creDir;

    }

    public OperationResponse copyDir(String userId, String directory,
                                     String type, String destDirectory) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpPost httpPost = new HttpPost(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_dir/" + userId + "/" + directory);
        httpPost.setHeader("Host", "192.168.3.132");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        //���ò���
        JSONObject typeDir = new JSONObject();
        try {
            typeDir.put("type", type);
            typeDir.put("destDirectory", destDirectory);
            httpPost.setEntity(new StringEntity(typeDir.toString()));
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        CloseableHttpResponse response;
        OperationResponse cpyDir = new OperationResponse();

        try {
            response = httpclient.execute(httpPost);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");

            JSONObject jsonobj = new JSONObject(resString);

            String errMsg = jsonobj.getString("errMsg");
            cpyDir.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            cpyDir.setIsOK(isOK);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return cpyDir;
    }

    public OperationResponse moveDir(String userId, String directory,
                                     String type, String destDirectory) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpPost httpPost = new HttpPost(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_dir/" + userId + "/" + directory);
        httpPost.setHeader("Host", "192.168.3.132");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        JSONObject typeDir = new JSONObject();
        try {
            typeDir.put("type", type);
            typeDir.put("destDirectory", destDirectory);
            httpPost.setEntity(new StringEntity(typeDir.toString()));
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        CloseableHttpResponse response;
        OperationResponse mvDir = new OperationResponse();

        try {
            response = httpclient.execute(httpPost);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();

            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");

            JSONObject jsonobj = new JSONObject(resString);

            String errMsg = jsonobj.getString("errMsg");
            mvDir.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            mvDir.setIsOK(isOK);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return mvDir;
    }

    public OperationResponse renameDir(String userId, String directory,
                                       String type, String destDirectory) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpPut httpPut = new HttpPut(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_dir/" + userId + "/" + directory);
        httpPut.setHeader("Host", "192.168.3.132");
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-Type", "application/json");

        //���ò���
        JSONObject typeDir = new JSONObject();
        try {
            typeDir.put("operationType", type);
            typeDir.put("newName", destDirectory);
            httpPut.setEntity(new StringEntity(typeDir.toString()));
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        CloseableHttpResponse response;
        OperationResponse rnDir = new OperationResponse();

        try {
            response = httpclient.execute(httpPut);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");

            JSONObject jsonobj = new JSONObject(resString);

            String errMsg = jsonobj.getString("errMsg");
            rnDir.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            rnDir.setIsOK(isOK);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return rnDir;
    }

    public OperationResponse deletDir(String userId, String directory) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpDelete httpDelete = new HttpDelete(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_dir/" + userId + "/" + directory);
        httpDelete.setHeader("Host", "192.168.3.132");
        httpDelete.setHeader("Accept", "application/json");

        CloseableHttpResponse response;
        OperationResponse delDir = new OperationResponse();

        try {
            response = httpclient.execute(httpDelete);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");

            JSONObject jsonobj = new JSONObject(resString);

            String errMsg = jsonobj.getString("errMsg");
            delDir.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            delDir.setIsOK(isOK);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return delDir;
    }


    public OperationResponse downloadFile(String userId, String filePath) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpGet httpGet = new HttpGet(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_file/" + userId + "/" + filePath);
        httpGet.setHeader("Host", "192.168.3.132");
        httpGet.setHeader("Accept", "application/json");
        CloseableHttpResponse response;
        OperationResponse dlFile = new OperationResponse();
        String resString = null;
        try {
            response = httpclient.execute(httpGet);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            InputStream in = myEntity.getContent();
            byte[] fileBytes = new byte[(int)myEntity.getContentLength()];
            in.read(fileBytes);
            in.close();
            if (fileBytes != null && fileBytes.length > 0) {
                OutputStream outputStream = new FileOutputStream(new File("D:\\"+ CFileOperate.getFileName(filePath)));
                outputStream.write(fileBytes);
                outputStream.flush();
                outputStream.close();
            } else {
                HttpEntity fileEntity = myEntity;
                in = (InputStream) fileEntity.getContent();
                int bufLen = 1024 * 1024;
                byte[] buffer = new byte[bufLen]; // 1M buffer
                OutputStream outputStream = new FileOutputStream(new File("D:\\buffer"));
                int offset = 0;
                int readLen = 0;
                while ((readLen = in.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, readLen);
                    outputStream.flush();
                    offset += readLen;
                }

                outputStream.flush();
                outputStream.close();
                in.close();
            }


//            resString = EntityUtils.toString(myEntity);

//			String resString = null;
//			resString = "{"
//					+ "'errMsg': '',"
//					+ "'isOK': true"
//					+ "}";
//			System.out.println(resString);


//            String errMsg = jsonobj.getString("errMsg");
//            dlFile.setErrMsg(errMsg);
//
//            boolean isOK = jsonobj.getBoolean("isOK");
//            dlFile.setIsOK(isOK);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
        }
        return dlFile;
    }

    public OperationResponse findFileMetadata(String userId, String filePath,
                                              boolean getFileInfo) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpGet httpGet = new HttpGet(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_file/" + userId + "/" + filePath + "?getMetadata=" + getFileInfo);
        httpGet.setHeader("Host", "192.168.3.132");
        httpGet.setHeader("Accept", "application/json");

        CloseableHttpResponse response;
        OperationResponse findFile = new OperationResponse();

        try {
            response = httpclient.execute(httpGet);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");
            JSONObject jsonobj = new JSONObject(resString);

            String errMsg = jsonobj.getString("errMsg");
            findFile.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            findFile.setIsOK(isOK);

            JSONObject dirData = jsonobj.getJSONObject("content");
            DirInfo dir[] = new DirInfo[1];
            dir[0] = new DirInfo();
            dir[0].setCreateTime(dirData.getString("createTime"));
            dir[0].setFilePath(dirData.getString("filePath"));
            dir[0].setIsDirectory(dirData.getInt("isDirectory"));
            dir[0].setObjectName(dirData.getString("objectName"));
            dir[0].setSize(dirData.getDouble("size"));
            findFile.setData(dir);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return findFile;
    }

    public OperationResponse uploadFile(String userId, String localFilePath, String fileName) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpPut httpPut = new HttpPut(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_file/" + userId + "/" + fileName);
//      	HttpPut httpPut = new HttpPut(AppPlatConstant.CLOUD_PLATFORM_HOST+"/services/InterfaceManage/fileUpload/"+fileName);
        httpPut.setHeader("Host", "192.168.3.132");
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-Type", "text/plain");
        File file = new File(localFilePath);
        FileEntity fileEntity = new FileEntity(file);
        httpPut.setEntity(fileEntity);
        CloseableHttpResponse response;
        OperationResponse ulFile = new OperationResponse();
        try {
            response = httpclient.execute(httpPut);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");
            JSONObject jsonobj = new JSONObject(resString);

            String errMsg = jsonobj.getString("errMsg");
            ulFile.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            ulFile.setIsOK(isOK);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        if(ulFile.getIsOK()){
            System.out.printf(fileName + "保存成功");
        }
        else{
            System.out.printf(fileName + "保存失败");
        }
        return ulFile;
    }

    public OperationResponse copyFile(String userId, String filePath,
                                      String type, String destFileOrDir) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpPost httpPost = new HttpPost(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_file/" + userId + "/" + filePath);
        httpPost.setHeader("Host", "192.168.3.132");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        //���ò���
        JSONObject typeDir = new JSONObject();
        try {
            typeDir.put("type", type);
            typeDir.put("destFileOrDir", destFileOrDir);
            httpPost.setEntity(new StringEntity(typeDir.toString()));
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        CloseableHttpResponse response;
        OperationResponse cpFile = new OperationResponse();

        try {
            response = httpclient.execute(httpPost);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");
            JSONObject jsonobj = new JSONObject(resString);

            String errMsg = jsonobj.getString("errMsg");
            cpFile.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            cpFile.setIsOK(isOK);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return cpFile;
    }

    public OperationResponse moveFile(String userId, String filePath,
                                      String type, String destFileOrDir) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpPost httpPost = new HttpPost(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_file/" + userId + "/" + filePath);
        httpPost.setHeader("Host", "192.168.3.132");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        //���ò���
        JSONObject typeDir = new JSONObject();
        try {
            typeDir.put("type", type);
            typeDir.put("destFileOrDir", destFileOrDir);
            httpPost.setEntity(new StringEntity(typeDir.toString()));
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        CloseableHttpResponse response;
        OperationResponse mvFile = new OperationResponse();

        try {
            response = httpclient.execute(httpPost);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");
            JSONObject jsonobj = new JSONObject(resString);

            String errMsg = jsonobj.getString("errMsg");
            mvFile.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            mvFile.setIsOK(isOK);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return mvFile;
    }

    public OperationResponse renameFile(String userId, String filePath,
                                        String type, String destFileOrDir) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpPut httpPut = new HttpPut(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_file/" + userId + "/" + filePath);
        httpPut.setHeader("Host", "192.168.3.132");
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-Type", "application/json");

        //���ò���
        JSONObject typeDir = new JSONObject();
        try {
            typeDir.put("operationType", type);
            typeDir.put("newName", destFileOrDir);
            httpPut.setEntity(new StringEntity(typeDir.toString()));
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        CloseableHttpResponse response;
        OperationResponse rnFile = new OperationResponse();

        try {
            response = httpclient.execute(httpPut);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");
            JSONObject jsonobj = new JSONObject(resString);

            String errMsg = jsonobj.getString("errMsg");
            rnFile.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            rnFile.setIsOK(isOK);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return rnFile;
    }

    public OperationResponse deleteFile(String userId, String filePath) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpDelete httpDelete = new HttpDelete(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_file/" + userId + "/" + filePath);
        httpDelete.setHeader("Host", "192.168.3.132");
        httpDelete.setHeader("Accept", "application/json");

        CloseableHttpResponse response;
        OperationResponse delFile = new OperationResponse();

        try {
            response = httpclient.execute(httpDelete);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");
            JSONObject jsonobj = new JSONObject(resString);
            String errMsg = jsonobj.getString("errMsg");
            delFile.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            delFile.setIsOK(isOK);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return delFile;
    }

    public OperationResponse insertAccessInfo(String account, String name, String domain, String ip, String dateTime, String service, String url, String describe) {
        // TODO Auto-generated method stub
        CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
        HttpPost httpPost = new HttpPost(AppPlatConstant.CLOUD_PLATFORM_HOST + "/_visitor/" + AppPlatConstant.CLOUD_STORAGE_USER_ID);
        httpPost.setHeader("Host", "192.168.3.132");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        JSONObject accessInfo = new JSONObject();
        try {
            accessInfo.put("account", account);
            accessInfo.put("name", name);
            accessInfo.put("domain", domain);
            accessInfo.put("ip", ip);
            accessInfo.put("dateTime", dateTime);
            accessInfo.put("service", service);
            accessInfo.put("url", url);
            accessInfo.put("describe", describe);
            httpPost.setEntity(new StringEntity(accessInfo.toString(),"UTF-8"));
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        CloseableHttpResponse response;
        OperationResponse isSuccess = new OperationResponse();

        try {
            response = httpclient.execute(httpPost);
            logger.info(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return OperationResponseBuilder.createNot200Response(response);
            }
            HttpEntity myEntity = response.getEntity();
            byte[] resBytes = EntityUtils.toByteArray(myEntity);
            String resString = new String(resBytes,"UTF-8");
            JSONObject jsonobj = new JSONObject(resString);
            String errMsg = jsonobj.getString("errMsg");
            isSuccess.setErrMsg(errMsg);

            boolean isOK = jsonobj.getBoolean("isOK");
            isSuccess.setIsOK(isOK);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return OperationResponseBuilder.createHttpRequestExceptionResponse(e);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return isSuccess;
    }

}
