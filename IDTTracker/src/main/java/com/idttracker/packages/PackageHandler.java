package com.idttracker.packages;

import java.util.HashMap;
import java.util.Map;

import com.idttracker.util.Parser;

public class PackageHandler {
	static private Map <String , Package> packages = new HashMap<String, Package>();
	public static boolean isValid(String UUID){ // returns if UUID is Taken
		return packages.get(UUID) == null;
	}
	public static void add(String UUID,Package p){
		packages.put(UUID, p);
		
	}
	public static void updatePackage(String uuid, double lat, double lon, double ele, String time) {
		packages.get(uuid).update(lat, lon, Parser.parseTimeStamp(time));
		
	}
	public static Package getPackage(String uuid){
		return packages.get(uuid);
	}
	public static String[] getUUIDs(){
		return (String[]) packages.keySet().toArray();
	}
	public static void close (String uuid){ // Cleans Memory When packages arrive
		packages.remove(uuid);
	}
	
	

}
