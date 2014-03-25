package com.example.mapapplication;

import com.google.android.gms.maps.model.LatLng;

public class GetDirectionUrl {

	public static String getUrl(LatLng from, LatLng where) {

		String origin = "origin=" + from.latitude + "," + from.longitude;
		String dest = "destination=" + where.latitude + "," + where.longitude;

		String sensor = "sensor=false";
		String parameters = origin + "&" + dest + "&" + sensor;

		String url = "https://maps.googleapis.com/maps/api/directions/json?"
				+ parameters;

		
		return url;
	}
}
