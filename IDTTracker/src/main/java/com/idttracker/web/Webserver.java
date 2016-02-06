package com.idttracker.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlets.gzip.GzipHandler;
import org.eclipse.jetty.server.handler.;

public class Webserver{
	private final int port;
	public Webserver(int port){
		this.port = port;
	}
	public Webserver(int port, String adress){
//		TODO: Add Adress Binding
		this.port = port;
	}
	
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}
	public void start(){
		Server server = new Server(port);
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setWelcomeFiles(new String[] {"tacker.html"});
		resourceHandler.setResourceBase(".");
		
		
		GzipHandler gzip = new GzipHandler();
		server.setHandler(gzip);
		HandlerList handlelist = new HandlerList();
		handlelist.setHandlers(new Handler[] {new WebHandler(), resourceHandler, new DefaultHandler()}); // TODO:remove 404 and rederect to root
		gzip.setHandler(handlelist);
		
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args){
		Webserver server = new Webserver(8080);
		server.start();
	}

}
