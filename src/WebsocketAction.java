import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import buaa.edu.global.AppPlatConstant;
import buaa.edu.global.AppPlatHttpClientBuilder;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

@ServerEndpoint(value = "/WebsocketAction")
public class WebsocketAction extends Endpoint {
    private Session session;
    private String UserId;
    public static List<String> lockLists = new ArrayList<String>();

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        this.session = null;
        System.out.println("onClose");
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        System.out.println("onError");
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        RemoteEndpoint.Basic remote = session.getBasicRemote();
        session.addMessageHandler(new MyMessageHandle(remote));
    }

    private class MyMessageHandle implements MessageHandler.Whole<String> {
        RemoteEndpoint.Basic remote = null;

        public MyMessageHandle(RemoteEndpoint.Basic remote) {
            this.remote = remote;
        }

        @Override
        public void onMessage(String str) {
            //防止锁冲突
//            try {
//                JSONObject json = new JSONObject(str);
//                String command = json.getString("command");
//                if (command.equals("LockControl")) {
//                    if (((new JSONObject(json.getString("data"))).getString("flag")).equals("true")) {
//                        String id = json.getString("id");
//                        if (lockLists.contains(id)) return;
//                        else lockLists.add(id);
//                    }
//                }
//                if (command.equals("LockControl") && (new JSONObject(json.getString("data"))).getString("flag").equals("false")) {
//                    lockLists.remove(json.getString("id"));
//                }
//            } catch (org.json.JSONException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
            //场景数据更新
            try {
                JSONObject json = new JSONObject(str);
                //判断协同信息是场景协同还是树协同
                if(json.has("sceneInfo")){
//                String userId = json.getString("userID");
//                JSONObject ttt = new JSONObject(json.getString("sceneInfo"));
                    String data = (new JSONObject(json.getString("sceneInfo"))).getString("data");
                    String taskId = (new JSONObject(json.getString("sceneInfo"))).getString("taskId");
                    String solutionId = (new JSONObject(json.getString("sceneInfo"))).getString("solutionId");
                    String nodeId = (new JSONObject(json.getString("sceneInfo"))).getString("nodeId");
                    String lockId = (new JSONObject(json.getString("sceneInfo"))).getString("lockId");

                    CloseableHttpClient httpclient = AppPlatHttpClientBuilder.createHttpClientWith120SecsTimeOut();
                    HttpPost httpPost = new HttpPost(AppPlatConstant.REFRESH_SCENE_DATA_URL);
                    httpPost.setHeader("Host", "192.168.3.1");
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-Type", "application/json");
                    org.json.JSONObject typeDir = new org.json.JSONObject();
                    try {
                        typeDir.put("solutionId", solutionId);
                        typeDir.put("taskId", taskId);
                        typeDir.put("nodeId", nodeId);
                        typeDir.put("data", data);
                        typeDir.put("lockId", lockId);
                        httpPost.setEntity(new StringEntity(typeDir.toString(), HTTP.UTF_8));
                    } catch (org.json.JSONException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    httpclient.execute(httpPost);
                }
            } catch (Throwable e) {
                System.out.printf(e.toString());
            }

            //向所有用户转发指令
            try {
                Set<Session> set = session.getOpenSessions();
                for (Session s : set) {
                    remote = s.getBasicRemote();
                    remote.sendText(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}