package buaa.edu.global;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */

/**
 * @author Administrator
 *
 */
public class CFileOperate {
	public static File dirFrom;   
    public static File dirTo;


    public static String getFileName(String filePath){
        int index = 0;
        String  fileName = null;
        if(filePath != null){
            index = filePath.lastIndexOf('/');
            if(index == -1){
                fileName =filePath;
            }
            else{
                fileName = filePath.substring(index+1,filePath.length());

            }
        }
        return fileName;
    }

	 public static String getCreateTime(long time){
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = new Date(time);		 
		 String createTime = format.format(date);
		 return createTime;
	 }
	    // Ŀ��·�������ļ���   
	    public void listFileInDir(File file) {   
	         File[] files = file.listFiles();   
	        for (File f : files) {   
	             String tempfrom = f.getAbsolutePath();   
	             String tempto = tempfrom.replace(dirFrom.getAbsolutePath(),   
	                     dirTo.getAbsolutePath()); // �����·�� �滻ǰ���·����   
	            if (f.isDirectory()) {   
	                 File tempFile = new File(tempto);   
	                 tempFile.mkdirs();   
	                 listFileInDir(f);   
	             } else {   
	                 System.out.println("Դ�ļ�:" + f.getAbsolutePath());   
	                //   
	                 System.out.println("Ŀ���:" + tempto);   
	                 copy(tempfrom, tempto);   
	             }   
	         }   
	     }   
	    /**
	      * ��װ�õ��ļ���������
	      */  
	    public void copy(String from, String to) {   
	        try {   
	             InputStream in = new FileInputStream(from);   
	             OutputStream out = new FileOutputStream(to);   
	  
	            byte[] buff = new byte[1024];   
	            int len = 0;   
	            while ((len = in.read(buff)) != -1) {   
	                 out.write(buff, 0, len);   
	             }   
	             in.close();   
	             out.close();   
	         } catch (FileNotFoundException e) {   
	             e.printStackTrace();   
	         } catch (IOException e) {   
	             e.printStackTrace();   
	         }   
	     }  
	    
	    /*
	     * ɾ��Ŀ¼
	     */
	    public void delDir(String filePath){
	    	File f = new File(filePath);
	    	if(f.isDirectory()){
	    		if(f.listFiles().length==0){
	    			f.delete();
	    		}else{
	    			File files[] = f.listFiles();
	    			for(int i = 0;i<files.length;i++){
	    				delDir(files[i].getAbsolutePath());
	    			}
	    			f.delete();
	    		}
	    	}
	    	else{
	    		f.delete();
	    	}
	    }   
	    
	    public static void upload(OutputStream out,String filepath){
	    	InputStream in;
	        try {
	        	in = new FileInputStream(filepath);
	            byte[] buff = new byte[1024];   
	            int len = 0;   
	            while ((len = in.read(buff)) != -1) {   
	                 out.write(buff, 0, len);   
	             }   
	             in.close();   
	             out.close(); 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e){
				e.printStackTrace();
			}
	    }
	    public static void download(InputStream in,String filepath){
	        OutputStream out;
	        try {
	        	out = new FileOutputStream(filepath);
	            byte[] buff = new byte[1024];   
	            int len = 0;   
	            while ((len = in.read(buff)) != -1) {   
	                 out.write(buff, 0, len);   
	             }   
	             in.close();   
	             out.close(); 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e){
				e.printStackTrace();
			}
	    }
    public static String subStringBySlash( String  directory){
        int index = 0;
        String subString = null;
        if(directory != null){
            index = directory.indexOf('/');
            if(index == -1){
                subString = directory;
            }
            else{
                subString = directory.substring(0,index);
            }
        }
        return subString;
    }
	    
}
