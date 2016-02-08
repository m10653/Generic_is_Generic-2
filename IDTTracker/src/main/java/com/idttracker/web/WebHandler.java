package com.idttracker.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.idttracker.console.Console;
import com.idttracker.packages.PackageHandler;
import com.idttracker.util.Config;
import com.idttracker.util.Parser;

public class WebHandler extends AbstractHandler{
	private String encoding = Config.read("encoding");

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("handle");
		if(target.startsWith("/tracknewpackage")){
			
			
			if(baseRequest.getQueryString() != null){
					System.out.println(baseRequest.getQueryString());
					String query = baseRequest.getQueryString();
					Map<String,String> values = Parser.parseUrl(query);
				if(!PackageHandler.isValid(values.get("uuid"))){
					response.getWriter().println("UUID: "+ values.get("uuid")+ " Is already in Use");
					Console.sendWarning("Package Cannot be Added Package with UUID: " + values.get("uuid") + " already Exists");
				}else if(values.get("name") != null && values.get("uuid") != null && values.get("destinationLon") != null && values.get("destinationLat") !=null){
					PackageHandler.add(values.get("uuid"), new com.idttracker.packages.Package(Double.parseDouble(values.get("destinationLat")) , Double.parseDouble(values.get("destinationLon")), values.get("name")));
					response.addHeader("Content-Type", "application/json;charset="+encoding);
					response.getWriter().print("{ \"ackUUID\":\"["+ values.get("uuid") +"]\" }");
				}
			}else{
				response.getWriter().print("Malformed Url: " + baseRequest.getRequestURI());
				Console.sendWarning("Malformed Url for Track new package. URl " + baseRequest.getRequestURI());
			}
			
			
			
			baseRequest.setHandled(true);
		}else if(target.startsWith("/packagetrackupdate/")){
			String uuid = target.substring(20);
			String body =request.getReader().readLine();
			Map<String, String> values = Parser.parseBody(body);
			if(values.get("uuid")!= null && values.get("lat") != null && values.get("lon") != null && values.get("ele") != null && values.get("time") != null){
				PackageHandler.updatePackage(values.get("uuid"), Double.parseDouble(values.get("lat")),  Double.parseDouble(values.get("lat")), 0, ""); //FIXME
			}
			response.setContentLength(0);
			System.out.println(uuid);
			Console.sendInfo(body);
			baseRequest.setHandled(true);
		}else{
			baseRequest.setHandled(false);
		}
		
	}
	

}
