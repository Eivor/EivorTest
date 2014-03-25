package com.example.mapapplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.android.gms.maps.model.LatLng;

public class GetAPIUrl {

	public static String getUrlForDirectionAPI(LatLng from, LatLng where) {

		String origin = "origin=" + from.latitude + "," + from.longitude;
		String dest = "destination=" + where.latitude + "," + where.longitude;

		String sensor = "sensor=false";
		String parameters = origin + "&" + dest + "&" + sensor;

		String url = "https://maps.googleapis.com/maps/api/directions/json?"
				+ parameters;

		return url;
	}

	public static String getUrlForAutoComplete(String... params) {

		String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
		String TYPE_AUTOCOMPLETE = "/autocomplete";
		String OUT_JSON = "/json";

		String API_KEY = "AIzaSyAUNwUy1_ee1_4z_TxWrJe8LgfKZA2JRQM";

		StringBuilder url = new StringBuilder(PLACES_API_BASE
				+ TYPE_AUTOCOMPLETE + OUT_JSON);
		url.append("?sensor=false&key=" + API_KEY);
		url.append("&types=geocode");
		try {
			url.append("&input=" + URLEncoder.encode(params[0], "utf8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return url.toString();
	}

}
