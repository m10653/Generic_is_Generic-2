package com.idttracker.web;

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
        Console.sendInfo("Socket Connected: " + sess); // Comment out to prevent spam under high load.
        client = new WebClient(sess);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        if(message.startsWith("uuid")){
        	String uuid = message.substring(5);
        	boolean alreadyused = false;
        	for(String ids:client.getUUIDS()){
        		if(ids.equals(uuid)){
        			alreadyused = true;
        			break;
        		}
        	}
        	if(alreadyused){
        		session.getBasicRemote().sendText("Invalid Already Entered");
        	}else if(!PackageHandler.isValid(uuid)){
        		session.getBasicRemote().sendText("Valid UUID");
        		client.addUUID(uuid);
        		
        	}else{
        		session.getBasicRemote().sendText("Invalid UUID");
        	}
        	  
        }else if(message.startsWith("update")){
        	String[] uuids;
        	if(client.isAdmin()){
        		uuids = PackageHandler.getUUIDs();
        	}else{
        		uuids =  client.getUUIDS();
        	}
        	if(uuids.length > 0){
        		for(int i = 0; i < uuids.length; i++){
        			Package temp = PackageHandler.getPackage(uuids[i]);
        			double[] curlatlon= temp.getLocation();
        			double[] deslatlon = temp.getDestination();
        			session.getBasicRemote().sendText(uuids[i]+ " "+ temp.getName() +" " + temp.getDis() + " " + temp.getETA() + " " + curlatlon[0] + " " + curlatlon[1] + " " + deslatlon[0] + " " + deslatlon[1] + " ");
        		}
        	}else{
        		session.getBasicRemote().sendText("NoPackages");
        	}

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
        Console.sendInfo("Socket Closed: " + reason); // Comment out to prevent spam under high load.
    }
    
    @OnError
    public void onWebSocketError(Throwable cause, Session sess)
    {
    	Console.sendWarning("Websocket Error: "+cause.toString());
//        cause.printStackTrace(System.err);
    }
}