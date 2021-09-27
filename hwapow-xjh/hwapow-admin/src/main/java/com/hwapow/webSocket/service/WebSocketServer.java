package com.hwapow.webSocket.service;

import com.alibaba.fastjson.JSONObject;
import com.hwapow.common.utils.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/webSocket/{sid}")
@Component
public class WebSocketServer{
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    //发送消息
    public static void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    //给指定用户发送信息
    public static void sendInfo(String userId,String code, String message) {
        Session session = sessionPools.get(userId);
        try {
            JSONObject msgJson=new JSONObject();
            msgJson.put("code",code);//传code的目的是为了前端可以根据code去识别通知类型，以判断如何处理数据
            msgJson.put("data",message);
            sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //给所有用户发送消息，建立此方法的目的是为了避免获取不到用户id的时候
    public static void sendInfo(String code,String message) {
        for (Map.Entry<String, Session> sessionItem: sessionPools.entrySet()){
            Session session=sessionItem.getValue();
            try {
                JSONObject msgJson=new JSONObject();
                msgJson.put("code",code);//传code的目的是为了前端可以根据code去识别通知类型，以判断如何处理数据
                msgJson.put("data",message);
                sendMessage(session,msgJson.toJSONString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String userId) {
        sessionPools.put(userId, session);
        addOnlineCount();
        System.out.println(userId + "加入webSocket！当前人数为" + onlineNum);
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "sid") String userId) {
        sessionPools.remove(userId);
        subOnlineCount();
        System.out.println(userId + "断开webSocket连接！当前人数为" + onlineNum);
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message) throws IOException {
        message = "客户端：" + message + ",已收到";
        System.out.println(message);
        for (Session session : sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

}
