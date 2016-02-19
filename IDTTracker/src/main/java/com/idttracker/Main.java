package com.idttracker;

import java.awt.Desktop;
import java.io.PrintWriter;
import java.net.URI;

import com.idttracker.console.ComandHandler;
import com.idttracker.console.Console;
import com.idttracker.console.ConsoleWindow;
import com.idttracker.util.Config;
import com.idttracker.web.Webserver;

public class Main {
	public static void main(String[] args) {
		
		
		ConsoleWindow window = new ConsoleWindow();
		Console.addConsoleWindow(window);
		ComandHandler.addConsoleWindow(window);
		
		Console.sendInfo("Starting server.......");
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("WebContent/JS/uri.js");
			writer.println("var wsUri=\"ws://"+ Config.read("remoteaddress")+":" + Config.read("port")+ "/ws/\";");
			writer.close();
		} catch (Exception e) {
			Console.sendError("Error Seting Remote Adress File");
		
		}
		Webserver webserver;
		if(Boolean.parseBoolean(Config.read("bindip"))){
			webserver = new Webserver(Integer.parseInt(Config.read("port")), Config.read("address"));
			Console.sendInfo("Server Bound to "+ Config.read("address")+":"+ Config.read("port"));
		}else{
			webserver = new Webserver(Integer.parseInt(Config.read("port")));
			Console.sendInfo("Server Bound to Port:" + Config.read("port"));
		}
		webserver.start();
		Console.sendInfo("Server Started");
		if (Desktop.isDesktopSupported() && Boolean.parseBoolean(Config.read("openWebUIOnStartup"))) { 
			try {
				Desktop.getDesktop().browse(new URI("Http://" + Config.read("address") + ":" + Config.read("port"))); 
				Console.sendInfo("Web UI Opened");
			} catch (Exception e) {
				Console.sendWarning("Unable to open web GUI, Error: " + e);
			}
		}

	}

}
