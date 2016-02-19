package com.idttracker.console;

import java.awt.Color;

import com.idttracker.packages.PackageHandler;
import com.idttracker.util.Config;


public class ComandHandler {
	private static ConsoleWindow window;
//	public  ComandHandler(ConsoleWindow window){
//		window= this.window;
//		
//	}
	public static void addConsoleWindow(ConsoleWindow w){
		window = w;
	}
	public static void processComand(String comand){
		String[] words = comand.split(" ");
		String cmd = words[0];
		if(cmd.equalsIgnoreCase("Color")){
			color(words);
		}else if(cmd.equalsIgnoreCase("Exit")){
			exit();
		}else if(cmd.equalsIgnoreCase("get")){
			get(words);
		}else if(cmd.equalsIgnoreCase("help")){
			help();
		}else if(cmd.equalsIgnoreCase("list")){
			list();
		}else if(cmd.equalsIgnoreCase("set")){
			set(words);
		}
	}
	private static void color(String[] args){
		try{
			if(args.length == 5){
				if(args[1].equalsIgnoreCase("input")){
					window.setInputTextColor(new Color(Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4])));
				}else if(args[1].equalsIgnoreCase("info")){
					window.setInfoTextColor(new Color(Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4])));
				}else if(args[1].equalsIgnoreCase("warning")){
					window.setWarningTextColor(new Color(Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4])));
				}else if(args[1].equalsIgnoreCase("Error")){
					window.setErrorTextColor(new Color(Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4])));
				}else{
					Console.sendError("Text tag not found");
				}
			}else{
				Console.sendError("Invalid number of arguments");
			}
		}catch(Exception e){
			Console.sendError("Syntax Error");
		}
	}
	private static void help(){
		Console.sendInfo("Comands\n"
				+ "Color <info/error/warning> <red value> <green value> <blue value> ---- sets Text Color in console window\n"
				+ "get <uuid> ----Returns Package info\n"
				+ "Exit  -------- exits server\n"
				+ "list ---- lists all active packages\n"
				+ "set <setting name> <value> --- changes a config setting\n"
				+ "help -- shows this text");
		
	}
	private static void list(){
		String[] uuids = PackageHandler.getUUIDs();
		if(uuids.length >0){
			for(String uuid:uuids){
				Console.sendInfo(uuid);
			}
		}
		Console.sendInfo("Active Packages: " + PackageHandler.getUUIDs().length);
	}
	private static void get(String[] args){
		Console.sendInfo(PackageHandler.getPackage(args[1]).toString());
	}
	private static void set(String[] args){
		Config.write(args[1], args[2]);
		if(Config.read(args[1]).equals(args[2])){
			Console.sendInfo("Setting Saved");
		}else{
			Console.sendError("Unable to save setting");
		}
	}
	private static void exit(){
		System.exit(0);
	}
	
	

}
