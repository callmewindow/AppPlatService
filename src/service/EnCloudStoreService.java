package service;

import buaa.edu.global.CFileOperate;
import entity.OperationResponse;


/**
 * Created by winter on 2014/8/22.
 */
public class EnCloudStoreService {
    private AllServices allServices;

    public AllServices getAllServices() {
        return allServices;
    }

    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }

    public OperationResponse createAllPathDirs(String userId, String directory){
        String subDirectory = CFileOperate.subStringBySlash(directory);
        String remainingPath = directory.substring(subDirectory.length()+1,directory.length());
        OperationResponse operationResponse = new OperationResponse();
        operationResponse = allServices.getiLocalStoreService().createDir(userId,subDirectory);
        while(true){
            subDirectory = subDirectory + "/" + CFileOperate.subStringBySlash(remainingPath);
            operationResponse = allServices.getiLocalStoreService().createDir(userId,subDirectory);
            if(!subDirectory.equals(directory)){
                 remainingPath = directory.substring(subDirectory.length()+1,directory.length());
             }
             else{
                 return  operationResponse;
             }
         }
    }
}
