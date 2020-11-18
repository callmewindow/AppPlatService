package buaa.edu.global;

import entity.OperationResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.AllServices;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by winter on 2014/8/25.
 */
public class CloudStore implements Runnable {
    private List<String> folderPathList = new ArrayList<String>();
    private List<String> localFilePathList = new ArrayList<String>();
    private List<String> cloudFilePathList = new ArrayList<String>();
//    private ApplicationContext apc =  new ClassPathXmlApplicationContext("applicationContext-server.xml");
//    private AllServices allServices = (AllServices)apc.getBean("allServices");

    public void createFolder(String folderPath){
        folderPathList.add(folderPath);
    }
    public void  uploadFile(String localFilePath,String cloudFilePath){
        localFilePathList.add(localFilePath);
        cloudFilePathList.add(cloudFilePath);
    }

//    public AllServices getAllServices() {
//        return allServices;
//    }
//
//    public void setAllServices(AllServices allServices) {
//        this.allServices = allServices;
//    }

    @Override
    public void run() {
        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if(folderPathList!=null&&folderPathList.size()>0){
//                    for(int i = 0;i<folderPathList.size();i++){
//                        OperationResponse operationResponse = allServices.getEnCloudStoreService().createAllPathDirs(AppPlatConstant.CLOUD_STORAGE_USER_ID, folderPathList.get(i));
//                        if(operationResponse.getIsOK()){
//                            folderPathList.remove(i);
//                        }
//                    }
//                }
//                if(localFilePathList!=null&&localFilePathList.size()>0){
//                    for(int i = 0;i<localFilePathList.size();i++){
//                        OperationResponse operationResponse =allServices.getiLocalStoreService().uploadFile(AppPlatConstant.CLOUD_STORAGE_USER_ID,localFilePathList.get(i),cloudFilePathList.get(i));
//                        if(operationResponse.getIsOK()){
//                            localFilePathList.remove(i);
//                            cloudFilePathList.remove(i);
//                        }
//                    }
//                }
//            }
//        },0,AppPlatConstant.CLOUD_STORAGE_TIME_INTERVAL);
        TimerTask sceneObjectSaveTask = new TimerTask() {
            @Override
            public void run() {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                HttpGet httpGet = new HttpGet(AppPlatConstant.SAVE_SCENE_DATA_BY_TIMER_URL);
                httpGet.setHeader("Host", "localhost");
                httpGet.setHeader("Accept","application/json");
                httpGet.setHeader("Content-Type","application/json");
                try {
                    CloseableHttpResponse response;
                    response = httpclient.execute(httpGet);
                    int code = response.getStatusLine().getStatusCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(sceneObjectSaveTask,0,AppPlatConstant.LOCAL_STORAGE_TIME_INTERVAL);
        }

}
