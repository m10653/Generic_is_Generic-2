package com.idttracker.web;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.idttracker.packages.PackageHandler;

@ClientEndpoint
@ServerEndpoint(value="/", encoders = {JsonEncoder.class})
public class EventSocket
{
    @OnOpen
    public void onWebSocketConnect(Session sess){
        System.out.println("Socket Connected: " + sess);
//        sess.getAsyncRemote().sendText("Hello!");
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        System.out.println("Received TEXT message: " + message);
        session.getBasicRemote().sendText("Hello you Entered "+ message);
        
    }
    
    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        System.out.println("Socket Closed: " + reason);
    }
    
    @OnError
    public void onWebSocketError(Throwable cause)
    {
        cause.printStackTrace(System.err);
    }
}