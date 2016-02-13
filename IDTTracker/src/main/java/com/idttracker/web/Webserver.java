package com.idttracker.web;


import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;



public class Webserver{
	private final int port;
	private final String host;
	public Webserver(int port){
		this.port = port;
		host = null;
	}
	public Webserver(int port, String adress){
//		TODO: Add Adress Binding
		this.port = port;
		host = adress;
	}
	
	public void start(){
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        if(host != null){
        	connector.setHost(host);
        }
        server.addConnector(connector);
        
        ContextHandlerCollection contexts = new ContextHandlerCollection();

        ContextHandler context0 = new ContextHandler();
        context0.setContextPath("/");
        
        ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(false);
		resourceHandler.setWelcomeFiles(new String[] {"Tracker.html"});
		resourceHandler.setResourceBase("./WebContent");
		
		

		HandlerList handlelist = new HandlerList();
		handlelist.setHandlers(new Handler[] {new WebHandler(), resourceHandler,new RedirectHandler()});
		
		context0.setHandler(handlelist);

		ServletContextHandler wscontext = new ServletContextHandler(ServletContextHandler.SESSIONS);
		wscontext.setContextPath("/ws");
		contexts.addHandler(wscontext);
		
		contexts.addHandler(context0);


        
        
		

		try {
			
			   ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(wscontext);

//	             Add WebSocket endpoint to javax.websocket layer
	            wscontainer.addEndpoint(EventSocket.class);
	            server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}