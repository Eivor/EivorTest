package com.example.mapapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import asynClass.AsyncronsTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class GetRoutes extends
		AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

	MainActivity activity;

	public GetRoutes(MainActivity activity) {
		// TODO Auto-generated constructor stub

		this.activity = activity;
	}

	@Override
	protected List<List<HashMap<String, String>>> doInBackground(
			String... params) {
		// TODO Auto-generated method stub

		String data = null;

		data = new AsyncronsTask().downloadData(params[0]);

		List<List<HashMap<String, String>>> result = new RouteParser()
				.parseFromMap(data);

		// setResults(result);

		return result;
	}

	public void setResults(List<List<HashMap<String, String>>> result) {
		ArrayList<LatLng> points = null;
		PolylineOptions lineOptions = null;

		String distance = "";
		long distance_in_value;

		String duration = "";
		long duration_in_value;

		for (int i = 0; i < result.size(); i++) {

			points = new ArrayList<LatLng>();
			lineOptions = new PolylineOptions();

			List<HashMap<String, String>> path = result.get(i);

			for (int j = 0; j < path.size(); j++) {

				HashMap<String, String> map = path.get(j);

				if (j == 0) {

					distance = (String) map.get("distance");
					distance_in_value = Long.parseLong(map
							.get("distance_in_meter"));
					activity.shortestDistance.add(distance);
					activity.distance_in_meter.add(distance_in_value);
					continue;

				} else if (j == 1) {
					duration = map.get("duration");
					duration_in_value = Long.parseLong(map
							.get("duration_in_sec"));

					activity.shortestDuration.add(duration);
					activity.duration_in_second.add(duration_in_value);

					continue;
				}

				double lat = Double.parseDouble(map.get("lat"));
				double lng = Double.parseDouble(map.get("lng"));

				LatLng latLng = new LatLng(lat, lng);
				points.add(latLng);

			}

			lineOptions.addAll(points);
			lineOptions.color(Color.BLUE);
			lineOptions.width(7);
		}

		Log.e("DISTANCE BY JSON", "" + distance + duration);

	}

	@Override
	protected void onPostExecute(List<List<HashMap<String, String>>> result) {
		// TODO Auto-generated method stub

		ArrayList<LatLng> points = null;
		PolylineOptions lineOptions = null;

		String distance = "";
		long distance_in_value;

		String duration = "";
		long duration_in_value;

		for (int i = 0; i < result.size(); i++) {

			points = new ArrayList<LatLng>();
			lineOptions = new PolylineOptions();

			List<HashMap<String, String>> path = result.get(i);

			for (int j = 0; j < path.size(); j++) {

				HashMap<String, String> map = path.get(j);

				if (j == 0) {

					distance = (String) map.get("distance");
					distance_in_value = Long.parseLong(map
							.get("distance_in_meter"));
					activity.shortestDistance.add(distance);
					activity.distance_in_meter.add(distance_in_value);
					continue;

				} else if (j == 1) {
					duration = map.get("duration");
					duration_in_value = Long.parseLong(map
							.get("duration_in_sec"));

					activity.shortestDuration.add(duration);
					activity.duration_in_second.add(duration_in_value);

					continue;
				}

				double lat = Double.parseDouble(map.get("lat"));
				double lng = Double.parseDouble(map.get("lng"));

				LatLng latLng = new LatLng(lat, lng);
				points.add(latLng);

			}

			lineOptions.addAll(points);
			lineOptions.color(Color.BLUE);
			lineOptions.width(7);
		}

		Log.e("DISTANCE BY JSON", "" + distance + duration);

		Log.e("A", "" + activity.shortestDistance);
		Log.e("B", "" + activity.distance_in_meter);
		Log.e("C", "" + activity.shortestDuration);
		Log.e("D", "" + activity.duration_in_second);

		activity.drawPolyLineInMap(lineOptions);
		activity.setData();

	}

}
