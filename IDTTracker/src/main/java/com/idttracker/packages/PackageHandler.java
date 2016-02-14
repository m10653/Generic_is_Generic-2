package com.idttracker.packages;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.idttracker.console.Console;
import com.idttracker.util.Config;
import com.idttracker.util.Parser;

public class PackageHandler {
	static private Map <String , Package> packages = new HashMap<String, Package>();
	private static Timestamp lastCheck = new Timestamp(System.currentTimeMillis());
	public static boolean isValid(String UUID){ // returns if UUID is Taken
		return packages.get(UUID) == null;
	}
	public static void add(String UUID,Package p){
		packages.put(UUID, p);
		if(lastCheck.getTime() + Long.parseLong(Config.read("cleanTime")) < System.currentTimeMillis()){ // Cleans Packages That have not been updated(clean happens every 30 min) removes if not updated for an hour.
			Console.sendInfo("Starting Package Cleaning.......");
			String[] uuids = getUUIDs();
			for(String str:uuids){
				if(packages.get(str).getLastUpdate().getTime() + Long.parseLong(Config.read("removepackages")) < System.currentTimeMillis()){
					close(str);
					Console.sendWarning("Package: " + str + "Removed for not being updated");
				}
			}
		}
		
	}
	public static void updatePackage(String uuid, double lat, double lon, double ele, String time) {
		packages.get(uuid).update(lat, lon, Parser.parseTimeStamp(time));
		
	}
	public static Package getPackage(String uuid){
		return packages.get(uuid);
	}
	public static String[] getUUIDs(){
		String[] uuids =new String[packages.keySet().size()];
		uuids = packages.keySet().toArray(uuids);
		return uuids;
	}
	public static void close (String uuid){ // Cleans Memory When packages arrive
		packages.remove(uuid);
	}
	
	

}
