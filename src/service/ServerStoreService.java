package service;

import buaa.edu.global.CFileOperate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by winter on 2014/8/19.
 */
public class ServerStoreService {

    public String getRootFilepath() {
//        return this.getClass().getClassLoader().getResource("/").getPath() + "/AppPlatFile" + "/";
        String rootFilepath  = "";
        try {
            rootFilepath = this.getClass().getClassLoader().getResource("/").toURI().getPath() + "/AppPlatFile" + "/";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return rootFilepath;
    }
    public void createRootDir(){
        //如果文件不存在，则创建一个放所有任务的文件夹
        File file = new File(getRootFilepath());
        if(!file.exists()){
            file.mkdirs();
        }
    }
    // 在指定目录下创建文件夹
    public boolean createFolder(String path){
        boolean flag = true;
        File dir = new File(getRootFilepath() + path);
        try {
            if (!dir.exists()) dir.mkdirs();
        }catch(Exception e) {
            flag = false;
        }
        return flag;
    }
    // 创建文件
    public File createFile(String parentPath, String fileName, String data){
        if (!createFolder(parentPath))
            return null;
        File file =  new File(getRootFilepath() + parentPath +  "/" + fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fos = null;
        try{
            //创建流对象
            fos = new FileOutputStream(getRootFilepath() + parentPath  +  "/" + fileName);
            //转换为byte数组
            byte[] bs = data.getBytes();
            fos.write(bs);
        }catch (Exception e) {
            e.printStackTrace();

        }finally{
            try{
                fos.close();
            }catch(Exception e){

            }
        }
        return file;
    }
    // 创建文件,覆盖原有的文件
    public File createFile(String filePath, String data){
        File file =  new File(getRootFilepath() + filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fos = null;
        try{
            //创建流对象
            fos = new FileOutputStream(getRootFilepath() + filePath);
            //转换为byte数组
            byte[] bs = data.getBytes();
            fos.write(bs);
        }catch (Exception e) {
            e.printStackTrace();

        }finally{
            try{
                fos.close();
            }catch(Exception e){

            }
        }
        return file;
    }
    //获得文件夹的孩子目录
    public File[] getChildFileList(String path){
        File file = new File(getRootFilepath() + path);
        File[] childFiles = file.listFiles();
        return childFiles;
    }
    //获取指定目录位置
    public File getDirFile(String path){
        return new File(getRootFilepath() + path);
    }
    public  boolean deleteDir(String filePath){
        File file = new File(getRootFilepath() + filePath);
        Boolean isValid =file.exists(), flag = true;
        if(isValid){
            CFileOperate deleteDir = new CFileOperate();
            deleteDir.delDir(getRootFilepath() + filePath);
        }
        return flag;
    }

    //获取文件内容
    public String getFileByString(String path){
        FileInputStream fis = null;
        String ans = null;
        try{
            fis = new FileInputStream(getFile(path));
            byte[]data = new byte[102400];
            int i = 0;
            int n = fis.read();
            while(n!=-1){
                data[i]=(byte)n;
                i++;
                n=fis.read();
            }
            ans = new String(data, 0, i);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                fis.close();
            }catch(Exception e){}
        }
        return ans;
    };

    public File getFile(String filePath){
        return new File(getRootFilepath() + filePath);
    }
    public boolean deleteFile(String filePath){
        File file = new File(getRootFilepath() + filePath);
        Boolean flag = false;
        Boolean isValid =file.exists();
        if(isValid)
            flag = file.delete();
        else
            flag = true;
        return flag;
    }
}
