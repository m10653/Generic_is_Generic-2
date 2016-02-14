package com.idttracker.packages;


import java.sql.Timestamp;

//2015-12-08T08:42:33.188-05:00
/**
* 
* @author Michael Combs
*
*/
public class Package {
	private double desLat, desLon, curLat, curLon, dist, totalDist, setDist;
	private String name;
	public Timestamp first, last, ETA;

	public Package(double destinationLat, double destinationLon, String n){
		name = n;
		desLat =destinationLat; // Parse Double from Lat lon
		desLon = destinationLon;

	}
	public void update(double lat, double lon, Timestamp time){
		
		curLat = lat;
		curLon = lon;
		double theta = curLon - desLon;
		dist = Math.sin(Math.toRadians(curLat)) * Math.sin(Math.toRadians(desLat)) + Math.cos(Math.toRadians(curLat)) * Math.cos(Math.toRadians(desLat)) * Math.cos(Math.toRadians(theta));
		dist = Math.acos(dist);
		dist = Math.toDegrees(dist);
		dist = dist * 60 * 1.1515;
		first = last;
		if(first==null){
			last = time;
			totalDist = dist;
		}
		else{
			last = time;
			long firstInMillis = first.getTime();
			long lastInMillis = last.getTime();
			long difference = lastInMillis - firstInMillis;
			setDist = totalDist - dist;
			long average = (long) (difference/setDist);
			long distLeft = (long) (dist*average)+lastInMillis;
			totalDist = dist;
			ETA = new Timestamp(distLeft);
		}
	}
	public double[] getLocation(){
		return new double [] {curLat,curLon};
	}
	public double[] getDestination(){
		return new double [] {desLat, desLon};
		
	}
	public String getName(){
		return name;
	}
	public double getDis(){
		return dist;
	}
	public Timestamp getETA(){
		return ETA;
	}
	public String toString(){
		return "Name: " + name + "ETA: " + null + " Distance: " + dist + "CurLat/lon:" + curLat+"/"+curLon + "DesLat/Lon: " + desLat+"/"+desLon;
	}
	

}
