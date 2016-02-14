package com.idttracker.util;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;

import com.idttracker.console.Console;

public class PasswordChecker {
	private String hash;
	private String user;
	private String myUser;
	private String myHash;
	public PasswordChecker (String pass, String user){
		try{
			this.user = user;
			hash = pass;
			myUser = Config.read("username");
			
			byte[] bytesOfMessage = Config.read("password").getBytes("UTF-8");
		
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			String hash = new String(Hex.encodeHex(thedigest));
			myHash = hash;
		}catch(Exception e){
			
			Console.sendError("Unable to Check password");
		}
	}
	public boolean isCorrect(){
		return hash.equals(myHash) && user.equals(myUser);
		
	}
}
