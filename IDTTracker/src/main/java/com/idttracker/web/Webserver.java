package com.idttracker.web;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlets.gzip.GzipHandler;


public class Webserver{
	private final int port;
	public Webserver(int port){
		this.port = port;
	}
	public Webserver(int port, String adress){
//		TODO: Add Adress Binding
		this.port = port;
	}
	
	public void start(){
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(false);
		resourceHandler.setWelcomeFiles(new String[] {"Tracker.html"});
		resourceHandler.setResourceBase("./WebContent");
		
		
		GzipHandler gzip = new GzipHandler();
		server.setHandler(gzip);
		HandlerList handlelist = new HandlerList();
		handlelist.setHandlers(new Handler[] {new WebHandler(), resourceHandler,new RedirectHandler()});
		gzip.setHandler(handlelist);
		
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}