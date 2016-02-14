package com.idttracker;

import java.awt.Desktop;
import java.net.URI;
import java.sql.Timestamp;
import java.util.TimeZone;

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
		Webserver webserver;
		if(Boolean.parseBoolean(Config.read("bindip"))){
			webserver = new Webserver(Integer.parseInt(Config.read("port")), Config.read("address"));
		}else{
			webserver = new Webserver(Integer.parseInt(Config.read("port")));
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
