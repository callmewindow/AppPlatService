package buaa.edu.global;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Created by xpzsoft on 14-8-9.
 */
public class SocketClient {
    private static String url = null;
    private static int port = 0;
    private static Socket mysocket = null;
    private static BufferedReader sin = null;//由系统标准输入设备构造BufferedReader对象
    private static PrintWriter os = null;//由Socket对象得到输出流，并构造PrintWriter对象
    private static BufferedReader is = null;
    public static boolean free = true;

    public static String getSocketData(String jsonstr){
        String response_jsonstr = "";

        try{
            if(mysocket != null){
                try{
                    mysocket.sendUrgentData(0xFF);
                }
                catch (Exception e){
                    if(os!=null){
                        os.close();
                    }
                    if(is!=null){
                        is.close();
                    }
                    mysocket.close();
                    mysocket = null;
                    os = null;
                    is = null;
                }
            }

            if(mysocket == null){
                url = "127.0.0.1";
                port = 1989;
                mysocket=new Socket(url,port);//向本机的1989端口发出客户请求
                mysocket.setSoTimeout(600000);
            }

            if(os == null){
                os=new PrintWriter(mysocket.getOutputStream());//由Socket对象得到输出流，并构造PrintWriter对象
            }

            if(is == null){
                is=new BufferedReader(new InputStreamReader(mysocket.getInputStream()));
            }

            jsonstr = "COVER" + jsonstr;

            os.write(jsonstr);

            os.flush();

            String item_str;

            while (true){
                item_str = is.readLine();
                if(item_str.equals("EOF")){
                    break;
                }
                response_jsonstr += item_str;
            }
//            if(!getConfig(filepath)){
//                url = "127.0.0.1";
//                port = 1989;
//            }
//            Socket socket=new Socket(url,port);//向本机的1989端口发出客户请求
//
//            socket.setSoTimeout(600000);
//
//            BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));//由系统标准输入设备构造BufferedReader对象
//
//            PrintWriter os=new PrintWriter(socket.getOutputStream());//由Socket对象得到输出流，并构造PrintWriter对象
//
//            BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));//由Socket对象得到输入流，并构造相应的BufferedReader对象
//
//            os.write(jsonstr);
//
//            os.flush();
//
//            response_jsonstr = "";
//
//            String item_str;
//
//            while (true){
//                item_str = is.readLine();
//                if(item_str.equals("EOF")){
//                    break;
//                }
//                response_jsonstr += item_str;
//            }
//
//            os.close(); //关闭Socket输出流
//
//            is.close(); //关闭Socket输入流
//
//            socket.close(); //关闭Socket

        }catch(Exception e) {
            response_jsonstr = "200";
        }
        return response_jsonstr;
    }

    public static synchronized String getSocketData(String jsonstr, String filepath){

        String response_jsonstr = "";

        try{
            if(mysocket != null){
                try{
                    mysocket.sendUrgentData(0xFF);
                }
                catch (Exception e){
                    if(os!=null){
                        os.close();
                    }
                    if(is!=null){
                        is.close();
                    }
                    mysocket.close();
                    mysocket = null;
                    os = null;
                    is = null;
                }
            }

            if(mysocket == null){
                url = "127.0.0.1";
                port = 1989;
                mysocket=new Socket(url,port);//向本机的1989端口发出客户请求
                mysocket.setSoTimeout(600000);
            }

            if(os == null){
                os=new PrintWriter(mysocket.getOutputStream());//由Socket对象得到输出流，并构造PrintWriter对象
            }

            if(is == null){
                is=new BufferedReader(new InputStreamReader(mysocket.getInputStream()));
            }

            os.write(jsonstr);

            os.flush();

            String item_str;

            while (true){
                item_str = is.readLine();
                if(item_str.equals("EOF")){
                    break;
                }
                response_jsonstr += item_str;
            }
//            if(!getConfig(filepath)){
//                url = "127.0.0.1";
//                port = 1989;
//            }
//            Socket socket=new Socket(url,port);//向本机的1989端口发出客户请求
//
//            socket.setSoTimeout(600000);
//
//            BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));//由系统标准输入设备构造BufferedReader对象
//
//            PrintWriter os=new PrintWriter(socket.getOutputStream());//由Socket对象得到输出流，并构造PrintWriter对象
//
//            BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));//由Socket对象得到输入流，并构造相应的BufferedReader对象
//
//            os.write(jsonstr);
//
//            os.flush();
//
//            response_jsonstr = "";
//
//            String item_str;
//
//            while (true){
//                item_str = is.readLine();
//                if(item_str.equals("EOF")){
//                    break;
//                }
//                response_jsonstr += item_str;
//            }
//
//            os.close(); //关闭Socket输出流
//
//            is.close(); //关闭Socket输入流
//
//            socket.close(); //关闭Socket

        }catch(Exception e) {
            response_jsonstr = "200";
        }
        return response_jsonstr;
    }

    private static boolean getConfig(String filepath){
        if(url == null || port == 0){
            FileInputStream fis;
            try {
                String path = filepath;
                path += "../resources/data/config/server.txt";
                fis = new FileInputStream(path);
                byte[] byteInData = new byte[128];
                int length = 0;
                String text = "";
                while ((length = fis.read(byteInData)) != -1) {
                    text += new String(byteInData, 0, length);
                }
                fis.close();
                fis = null;
                if(text!=null){
                    url = text.split(":")[0];
                    port = Integer.parseInt(text.split(":")[1]);
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return false;
        }
        return true;
    }
}
