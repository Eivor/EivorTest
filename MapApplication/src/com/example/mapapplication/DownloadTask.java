package com.example.mapapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class DownloadTask extends
		AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

	MainActivity activity;

	public DownloadTask(MainActivity activity) {
		// TODO Auto-generated constructor stub

		this.activity = activity;
	}

	@Override
	protected List<List<HashMap<String, String>>> doInBackground(
			String... params) {
		// TODO Auto-generated method stub

		String data = null;
		InputStream inputStream;

		try {
			URL url = new URL(params[0]);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

			inputStream = httpURLConnection.getInputStream();

			data = converStringToInputStream(inputStream);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<List<HashMap<String, String>>> result = new JSONParser()
				.parseFromMap(data);

		return result;
	}

	@Override
	protected void onPostExecute(List<List<HashMap<String, String>>> result) {
		// TODO Auto-generated method stub

		ArrayList<LatLng> points = null;
		PolylineOptions lineOptions = null;
		String distance = "";
		String duration = "";

		for (int i = 0; i < result.size(); i++) {

			points = new ArrayList<LatLng>();
			lineOptions = new PolylineOptions();

			List<HashMap<String, String>> path = result.get(i);

			for (int j = 0; j < path.size(); j++) {

				HashMap<String, String> map = path.get(j);

				if (j == 0) {
					distance = (String) map.get("distance");
					continue;
				} else if (j == 1) {
					duration = map.get("duration");
					continue;
				}

				Log.e("MAP", "" + map.size());
				Log.e("MAP", "" + map + "null" + (map.get("lat") != null));
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
		activity.drawPolyLineInMap(lineOptions);

	}

	public String converStringToInputStream(InputStream inputStream)
			throws IOException {

		InputStreamReader streamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(streamReader, 8);

		String line = "";
		StringBuilder builder = new StringBuilder();

		while ((line = reader.readLine()) != null) {

			builder.append(line);
		}

		Log.e("JSON", builder.toString());
		return builder.toString();
	}
}
