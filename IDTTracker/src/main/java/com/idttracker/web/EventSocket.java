package com.idttracker.web;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.idttracker.console.Console;
import com.idttracker.packages.Package;
import com.idttracker.packages.PackageHandler;
import com.idttracker.util.PasswordChecker;

@ClientEndpoint
@ServerEndpoint(value="/", encoders = {JsonEncoder.class})
public class EventSocket{
	private WebClient client;
    @OnOpen
    public void onWebSocketConnect(Session sess){
        System.out.println("Socket Connected: " + sess);
        client = new WebClient(sess);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        if(message.startsWith("uuid")){
        	String uuid = message.substring(5);
        	if(!PackageHandler.isValid(uuid)){
        		session.getBasicRemote().sendText("Valid UUID");
        		client.addUUID(uuid);
        		
        	}else{
        		session.getBasicRemote().sendText("Invalid UUID");
        	}
        	  
        }else if(message.startsWith("update")){
//        	List<String> UUIDS = new ArrayList<String>();
        	String[] uuids;
        	if(client.isAdmin()){
        		uuids = PackageHandler.getUUIDs();
        	}else{
        		uuids =  client.getUUIDS();
        	}
        	
        	JsonArrayBuilder arraybuilder = Json.createArrayBuilder();
        	
        	for(int i = 0; i < uuids.length;i++){
        		Package temp = PackageHandler.getPackage(uuids[i]);
                JsonObject packageupdate = Json.createObjectBuilder().add("name",temp.getName()).add("dist", temp.getDis()).add("curLoc", Json.createArrayBuilder().add(temp.getLocation()[0]).add(temp.getLocation()[1])).add("desloc", Json.createArrayBuilder().add(temp.getDestination()[0]).add(temp.getDestination()[1])).build();
                arraybuilder.add(packageupdate);
        	}
        	JsonObject packages =Json.createObjectBuilder().add("packages",arraybuilder.build()).build();
        	System.out.println("Object Sent");
        	
            session.getBasicRemote().sendObject(packages);
            
        }else if(message.startsWith("login")){
        	String[] temp = message.split(" ");
        	client.setisAdmin(new PasswordChecker(temp[2], temp[1]).isCorrect());
        	if(client.isAdmin()){
        		Console.sendWarning("Admin Logged in");
        		session.getBasicRemote().sendText("Valid Login");;
        	}else{
        		Console.sendWarning("Auth Failure: Admin login");
        		session.getBasicRemote().sendText("Invalid Login");
        	}
        }else{
        	session.close(new CloseReason(CloseCodes.UNEXPECTED_CONDITION, "Invalid Comand"));
        }
      
        		
    }
    
    @OnClose
    public void onWebSocketClose(CloseReason reason, Session sess)
    {
        System.out.println("Socket Closed: " + reason);
    }
    
    @OnError
    public void onWebSocketError(Throwable cause, Session sess)
    {
//        cause.printStackTrace(System.err);
    }
}