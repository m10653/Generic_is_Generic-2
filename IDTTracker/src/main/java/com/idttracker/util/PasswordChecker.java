package com.idttracker.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;

import com.idttracker.console.Console;

public class PasswordChecker {
	private String hash;
	private String user;
	public PasswordChecker (String pass, String user){
		try{
			this.user = user;
			byte[] bytesOfMessage = pass.getBytes(StandardCharsets.UTF_8);
		
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			String hash = new String(thedigest, StandardCharsets.UTF_8);
			System.out.println(hash);
		}catch(Exception e){
			
			Console.sendError("Unable to Check password");
		}
	}
	public boolean isCorrect(){
		return false;
		
	}
}
