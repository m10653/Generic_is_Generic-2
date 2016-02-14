package com.idttracker.web;


import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

public class WebClient {
	private final Session session;
	private List<String> UUIDS = new ArrayList<String>();
	private boolean isAdmin = false;
	public WebClient(Session sess){
		session = sess;
	}
	public void addUUID(String u){
		UUIDS.add(u);
	}
	public boolean isAdmin(){
		return isAdmin;
	}
	public void setisAdmin(boolean admin){
		isAdmin = admin;
	}
	public List<String> getUUIDS(){
		return UUIDS;
	}
	public Session getSession(){
		return session;
	}

	
	

}
