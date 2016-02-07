package com.idttracker.console;

import java.awt.Color;

import com.idttracker.web.WebHandler;

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
	public static void help(){
		
	}
	private static void exit(){
		System.exit(0);
	}
	
	

}
