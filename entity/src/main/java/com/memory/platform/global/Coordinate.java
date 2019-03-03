package com.memory.platform.global;

public class Coordinate {
	//维度
	private float latitude;
   //经度
	private float longitude;
	public Coordinate () {
		
	}
	public Coordinate(float longit, float lat) {
	    this.latitude = lat;
	    this.longitude = longit;
	}
	public Coordinate(double longit, double lat) {
		this.latitude = (float)lat;
	    this.longitude = (float) longit;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public static Coordinate parse(String start) {
	   String[] arr = start.split(",");
	   if(arr == null || arr.length<2)
		return null;
	   return  new  Coordinate(Float.parseFloat( arr[0]),Float.parseFloat(  arr[1])); 
	}
}
