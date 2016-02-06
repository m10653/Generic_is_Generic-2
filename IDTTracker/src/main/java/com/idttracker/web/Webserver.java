package com.idttracker.web;

import org.eclipse.jetty.server.Server;

public class Webserver {
	private final int port;
	public Webserver(int port){
		this.port = port;
	}
	public Webserver(int port, String adress){
//		TODO: Add Adress Binding
		this.port = port;
	}
	public void start(){
		Server server = new Server(port);
		
	}

}
