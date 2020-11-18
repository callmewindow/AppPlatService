package service;
/**
 * 管理文件在本地的存取
 * Created by winter on 2014/7/9.
 */

import buaa.edu.global.CFileOperate;
import com.sun.org.apache.xpath.internal.operations.Bool;
import entity.DirInfo;
import entity.OperationResponse;
import serviceInterface.ILocalStoreOperate;

import java.io.*;
import java.net.URISyntaxException;

public class LocalStoreService implements ILocalStoreOperate{
	/*
	 * ILocalStoreOperate test = new LocalStoreOperateImpl();
	   OperationResponse fileInfo = test.getDirInfo("dd", "e:/�½�");
       for(int i=0;i<fileInfo.getData().length;i++){
          System.out.println(fileInfo.getData()[i].getCreateTime());
          System.out.println(fileInfo.getData()[i].getFilePath());
          System.out.println(fileInfo.getData()[i].getIsDirectory());
          System.out.println(fileInfo.getData()[i].getObjectName());
          System.out.println(fileInfo.getData()[i].getSize()+"\n\n");        	
      }
      获取目录信息
	 */
    private String userId = "";

    @Override
    public OperationResponse insertAccessInfo(String account, String name, String domain, String ip, String dateTime, String service, String url, String describe) {
        return null;
    }

    public String getRootFilepath() {

        String path = "";
        try {
            path = this.getClass().getClassLoader().getResource("/").toURI().getPath()+ "/AppPlatFile";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return path;
    }
     public void createRootDir(){
         //如果文件不存在，则创建一个放所有任务的文件夹
         File file = new File(getRootFilepath());
         if(!file.exists()){
             file.mkdirs();
         }
     }

	public OperationResponse getDirInfo(String userId, String directory) {
		// TODO Auto-generated method stub
		 OperationResponse dirInfo = new OperationResponse();
		 File dir = new File(getRootFilepath()+"/"+directory);
		 File[] files = dir.listFiles();
		 DirInfo data[]=new DirInfo[files.length];
		 for(int i = 0;i < files.length;i++){
			 data[i] = new DirInfo();
			 data[i].setCreateTime(CFileOperate.getCreateTime(files[i].lastModified()));
		     int endIndex = files[i].getPath().lastIndexOf("/");
		     String path = files[i].getPath().substring(0,endIndex);
			 data[i].setFilePath(path);
			 if(files[i].isDirectory()){
				 data[i].setIsDirectory(1);
				 data[i].setSize(0);
			}
			 else{
				 data[i].setIsDirectory(2);
				 data[i].setSize(Integer.parseInt(String.valueOf(files[i].length()))/(1024*1024));
			 }
			 data[i].setObjectName(files[i].getName());
		 }
		 dirInfo.setData(data);
		 return dirInfo;
	}
	/*
	 * ILocalStoreOperate test = new LocalStoreOperateImpl();
	   test.getDirInfo("dd", "e:/�½�");
	   创建目录
	 */
	public OperationResponse createDir(String userId, String directory) {
		// TODO Auto-generated method stub
        boolean flag = true;
        createRootDir();
		File file = new File(getRootFilepath()+"/"+directory);
        try{
            file.mkdirs();
        }
        catch(Exception e){
            flag = false;
        }
		OperationResponse creDir = new OperationResponse();
		creDir.setIsOK(flag);
        creDir.setErrMsg("");
		return creDir;
	}
	/*
	 * ILocalStoreOperate test = new LocalStoreOperateImpl();
	   test.copyDir("dd", "e:/�½�","copy","g:/�½�");
	   拷贝目录
	 */
	public OperationResponse copyDir(String userId, String directory,
			String type, String destDirectory) {
		// TODO Auto-generated method stub
        createRootDir();
        File fromfile = new File(getRootFilepath()+"/"+directory);// Դ�ļ���
        File tofile = new File(getRootFilepath()+"/"+destDirectory);// Ŀ��
        
       // ������Դȥ��   
        CFileOperate copyFile = new CFileOperate();
        CFileOperate.dirFrom = fromfile;  
        String dirfrom = fromfile.getAbsolutePath();
        int endindex = dirfrom.lastIndexOf("/");// �ҵ�"/"���ڵ�λ��   
        String mkdirPath = tofile.getAbsolutePath() + dirfrom.substring(endindex, dirfrom.length());   
        File tempFile = new File(mkdirPath);   
        tempFile.mkdirs();// �������ļ���   
        tofile = new File(mkdirPath);
        CFileOperate.dirTo = tofile;   
        copyFile.listFileInDir(fromfile);   

		OperationResponse cpyDir=new OperationResponse();
		cpyDir.setIsOK(true);
		return cpyDir;
	}
	/*
	 * ILocalStoreOperate test = new LocalStoreOperateImpl();
	   test.moveDir("dd", "e:/�½�","move","g:/�½�");
	   移动目录
	 */
	public OperationResponse moveDir(String userId, String directory,
			String type, String destDirectory) {
		// TODO Auto-generated method stub
        createRootDir();
        File fromfile = new File(getRootFilepath()+"/"+directory);// Դ�ļ���
        File tofile = new File(getRootFilepath()+"/"+destDirectory);// Ŀ��
        
       // ������Դȥ��   
        CFileOperate.dirFrom = fromfile;  
        CFileOperate moveFIle = new CFileOperate();
        String dirfrom = fromfile.getAbsolutePath();
        int endindex = dirfrom.lastIndexOf("/");// �ҵ�"/"���ڵ�λ��   
        String mkdirPath = tofile.getAbsolutePath() + dirfrom.substring(endindex, dirfrom.length());   
        File tempFile = new File(mkdirPath);   
        tempFile.mkdirs();// �������ļ���   
        tofile = new File(mkdirPath);
        CFileOperate.dirTo = tofile;   
        moveFIle.listFileInDir(fromfile);   
        moveFIle.delDir(directory);
		OperationResponse mvDir=new OperationResponse();
		mvDir.setIsOK(true);
		return mvDir;
	}
	/*
	 * ILocalStoreOperate test = new LocalStoreOperateImpl();
	   test.renameDir("dd", "e:/�½�","rename","e:/�½��ļ���");//ע������·������һ�£�ֻ�������Ҫ�ĵ��ļ��е����ֲ�һ��
	   重命名目录
	 */
	public OperationResponse renameDir(String userId, String directory,
			String type, String destDirectory) {
		// TODO Auto-generated method stub
        createRootDir();
		File reFile = new File(getRootFilepath()+"/"+directory);
		reFile.renameTo(new File(getRootFilepath()+"/"+destDirectory));
		OperationResponse renDir=new OperationResponse();
		renDir.setIsOK(true);
		return renDir;
	}
	/*
	 * ILocalStoreOperate test = new LocalStoreOperateImpl();
	   test.deletDir("dd", "e:/�½�");
	   删除目录
	 */
	public OperationResponse deletDir(String userId, String directory) {
		// TODO Auto-generated method stub
        createRootDir();
		CFileOperate deleteDir = new CFileOperate();
		deleteDir.delDir(getRootFilepath()+"/"+directory);
		OperationResponse delDir=new OperationResponse();
		delDir.setIsOK(true);
		return delDir;
	}

    /*
  		OperationResponse dlFile = test.downloadFile("dd", "e:/�½�/qc.log");//filePathָԶ���ļ�·��
  		InputStream in = dlFile.getIn();
  		CFileOperate.download(in, "g:/qcc.log");//���·��ָ����·��
  		下载文件
	 */

	public OperationResponse downloadFile(String userId, String filePath) {
		// TODO Auto-generated method stub
        createRootDir();
		OperationResponse dlFile = new OperationResponse();
		try {
			dlFile.setIn(new  FileInputStream(getRootFilepath()+"/"+filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dlFile;
	}
	/*
	 * ILocalStoreOperate test = new LocalStoreOperateImpl();
	   OperationResponse fileInfo = test.findFileMetadata("dd", "e:/�½�/qc.log",true);
       System.out.println(fileInfo.getData()[0].getCreateTime());
       System.out.println(fileInfo.getData()[0].getFilePath());
       System.out.println(fileInfo.getData()[0].getIsDirectory());
       System.out.println(fileInfo.getData()[0].getObjectName());
       System.out.println(fileInfo.getData()[0].getSize()+"\n\n");
       查询文件元数据
	 */
	public OperationResponse findFileMetadata(String userId, String filePath,
			boolean getFileInfo) {
		// TODO Auto-generated method stub
         createRootDir();
		 File file = new File(getRootFilepath()+"/"+filePath);
		 DirInfo data[]=new DirInfo[1];
		 data[0] = new DirInfo();
		 data[0].setCreateTime(CFileOperate.getCreateTime(file.lastModified()));
	     int endIndex = filePath.lastIndexOf("/");
	     String path = filePath.substring(0,endIndex);
		 data[0].setFilePath(path);
		 data[0].setIsDirectory(0);
		 data[0].setObjectName(file.getName());
		 data[0].setSize(Integer.parseInt(String.valueOf(file.length()))/(1024*1024));
		 OperationResponse findFile=new OperationResponse();
		 findFile.setData(data);
		 findFile.setIsOK(true);
		 return findFile;
	}
	/*
	    OperationResponse upFile = test.uploadFile("dd", "g:/qcc.log");//fileNameָԶ�˵��ļ�·��
        OutputStream out = upFile.getOut();
        CFileOperate.upload(out, "e:/�½�/qc.log");//�����Ǳ����ļ�·��
        上传文件
	 */
	public OperationResponse uploadFile(String userId, String localFilePath, String fileName) {
		// TODO Auto-generated method stub
        createRootDir();
		OperationResponse ulFile = new OperationResponse();
        File oldFile = new File(localFilePath);
        File newFile = new File(getRootFilepath()+"/"+fileName);
        Boolean flag = oldFile.renameTo(newFile);
        ulFile.setIsOK(flag);
		return ulFile;
	}
	/*
	 * ILocalStoreOperate test = new LocalStoreOperateImpl();
	   test.copyFile("dd", "e:/�½�/qc.log","copy","g:/�½�");
	   拷贝文件
	 */
	public OperationResponse copyFile(String userId, String filePath,
			String type, String destFileOrDir) {
		// TODO Auto-generated method stub
         createRootDir();
		 int endIndex = filePath.lastIndexOf("/");
		 destFileOrDir = destFileOrDir + filePath.substring(endIndex,filePath.length());
		 CFileOperate copyFile = new CFileOperate();
		 copyFile.copy(getRootFilepath()+"/"+filePath,getRootFilepath()+"/"+destFileOrDir);
		 OperationResponse cpyFile=new OperationResponse();
		 cpyFile.setIsOK(true);
		 return cpyFile;
	}
	/*
	 * ILocalStoreOperate test = new LocalStoreOperateImpl();
	   test.moveFile("dd", "e:/�½�/qc.log","move","g:/�½�");
	   移动文件
	 */
	public OperationResponse moveFile(String userId, String filePath,
			String type, String destFileOrDir) {
		// TODO Auto-generated method stub
         createRootDir();
		 int endIndex = filePath.lastIndexOf("/");
		 destFileOrDir = destFileOrDir + filePath.substring(endIndex,filePath.length());
		 CFileOperate moveFile = new CFileOperate();
		 moveFile.copy(getRootFilepath()+"/"+filePath,getRootFilepath()+"/"+destFileOrDir);
	     File delFile = new File(getRootFilepath()+"/"+filePath);
	     delFile.delete();
		 OperationResponse mvDir=new OperationResponse();
		 mvDir.setIsOK(true);
		 return mvDir;
	}
	/*
	 * ILocalStoreOperate test = new LocalStoreOperateImpl();
	   test.renameFile("dd", "e:/�½�/qc.log","rename","e:/�½�/qcc.log");//ע������·������һ�£�ֻ�������Ҫ�ĵ��ļ������ֲ�һ��
	   文件重命名
	 */
	public OperationResponse renameFile(String userId, String filePath,
			String type, String destFileOrDir) {
		// TODO Auto-generated method stub
         createRootDir();
		 File reFile = new File(getRootFilepath()+"/"+filePath);
		 reFile.renameTo(new File(getRootFilepath()+"/"+destFileOrDir));
		 OperationResponse remDir=new OperationResponse();
		 remDir.setIsOK(true);
		 return remDir;
	}
	/*
	 * ILocalStoreOperate test = new LocalStoreOperateImpl();
	   test.deleteFile("dd", "e:/�½�/qc.log");
	   文件删除
	 */
	public OperationResponse deleteFile(String userId, String filePath) {
		// TODO Auto-generated method stub
         createRootDir();
	     File delFile = new File(getRootFilepath()+"/"+filePath);
	     delFile.delete();
		 OperationResponse delDir=new OperationResponse();
		 delDir.setIsOK(true);
		 return delDir;
	}
}
