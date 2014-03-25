package com.example.mapapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class RouteParser {

	JSONArray routesArray;
	JSONArray legsArray;
	JSONArray stepsArray;
	JSONObject jdistance;
	JSONObject jduration;

	List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();

	public List<List<HashMap<String, String>>> parseFromMap(String JSONData) {

		try {

			JSONObject jsonObject = new JSONObject(JSONData);

			routesArray = jsonObject.getJSONArray("routes");

			// Log.e("ROUTE ARRAY", "" + routesArray.length());

			/** TRAVERSING ALL ROUTES **/
			for (int i = 0; i < routesArray.length(); i++) {

				legsArray = ((JSONObject) routesArray.get(i))
						.getJSONArray("legs");

				List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

				/** TRAVERSING ALL LEGS **/
				for (int j = 0; j < legsArray.length(); j++) {

					stepsArray = ((JSONObject) legsArray.get(j))
							.getJSONArray("steps");

					jdistance = ((JSONObject) legsArray.get(j))
							.getJSONObject("distance");
					HashMap<String, String> distanceMap = new HashMap<String, String>();

					distanceMap.put("distance", jdistance.getString("text"));
					// Long l = jdistance.getLong("value");
					distanceMap.put("distance_in_meter",
							jdistance.getString("value"));

					Log.e("DISTANCE IN METERS", "" + jdistance.getString("value"));
					// Log.e("A1", jdistance.getString("text"));

					jduration = ((JSONObject) legsArray.get(j))
							.getJSONObject("duration");

					HashMap<String, String> durationMap = new HashMap<String, String>();

					durationMap.put("duration", jduration.getString("text"));
					durationMap.put("duration_in_sec",
							jduration.getString("value"));

					Log.e("DURATUON IN SECONDS",
							"" + jduration.getString("value"));
					// Log.e("A2", jduration.getString("text"));

					path.add(distanceMap);
					path.add(durationMap);

					/** TRAVERSING ALL STEPS **/
					for (int k = 0; k < stepsArray.length(); k++) {

						String polyline = "";
						polyline = (String) ((JSONObject) ((JSONObject) stepsArray
								.get(k)).get("polyline")).get("points");

						List<LatLng> list = decodePoly(polyline);

						for (int l = 0; l < list.size(); l++) {

							HashMap<String, String> map = new HashMap<String, String>();
							map.put("lat", Double.toString(((LatLng) list
									.get(l)).latitude));

							map.put("lng", Double.toString(((LatLng) list
									.get(l)).longitude));

							path.add(map);
						}
					}

					routes.add(path);
				}

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return routes;

	}

	/** METHOD FOR DECODE POLYLINES **/
	private List<LatLng> decodePoly(String encoded) {
		// TODO Auto-generated method stub

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;

	}
}
