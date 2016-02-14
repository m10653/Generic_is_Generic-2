package com.idttracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.idttracker.packages.Package;
import com.idttracker.packages.PackageHandler;
import com.idttracker.util.PasswordChecker;

@ClientEndpoint
@ServerEndpoint(value="/", encoders = {JsonEncoder.class})
public class EventSocket{
	private Map<String,WebClient> sessons = new HashMap<String, WebClient>();
    @OnOpen
    public void onWebSocketConnect(Session sess){
        System.out.println("Socket Connected: " + sess);
        sessons.put(sess.getId(), new WebClient(sess));
//        sess.getAsyncRemote().sendText("Hello!");
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        System.out.println("Received TEXT message: " + message);
//        session.getBasicRemote().sendText("Hello you Entered "+ message);
        if(message.startsWith("uuid")){
        	String uuid = message.substring(5);
        	System.out.println(uuid);
        	if(!PackageHandler.isValid(uuid)){
        		Package temp = PackageHandler.getPackage(uuid);
                JsonObject event = Json.createObjectBuilder().add("name",temp.getName()).add("dist", temp.getDis()).add("curLoc", Json.createArrayBuilder().add(temp.getLocation()[0]).add(temp.getLocation()[1])).add("desloc", Json.createArrayBuilder().add(temp.getDestination()[0]).add(temp.getDestination()[1])).build();
                session.getBasicRemote().sendObject(event);
        	}else{
        		System.out.println("Invalid------------------------");
        	}
        	  
        }else if(message.startsWith("update")){
        	
        }else if(message.startsWith("login")){
        	String test = new String(message.split(" ")[2].getBytes(), "ISO-8859-1");
        	System.out.println(test);
        	new PasswordChecker("Test", "Test");
        }else{
        	
        }
      
        		
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