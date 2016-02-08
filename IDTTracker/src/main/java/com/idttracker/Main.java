package com.idttracker;

import java.awt.Desktop;
import java.net.URI;

import com.idttracker.console.ComandHandler;
import com.idttracker.console.Console;
import com.idttracker.console.ConsoleWindow;
import com.idttracker.util.Config;
import com.idttracker.web.Webserver;

public class Main {
	public static void main(String[] args) {
		Webserver wserver = new Webserver(Integer.parseInt(Config.read("port")));
		ConsoleWindow window = new ConsoleWindow();
		Console.addConsoleWindow(window);
		ComandHandler.addConsoleWindow(window);
		wserver.start();
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
