package com.example.mapapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class BackgroundTask extends AsyncTask<String, Void, ArrayList<String>> {

	MainActivity object;

	public BackgroundTask(MainActivity obj) {
		// TODO Auto-generated constructor stub
		this.object = obj;
	}

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	private static final String API_KEY = "AIzaSyAUNwUy1_ee1_4z_TxWrJe8LgfKZA2JRQM";

	@Override
	protected ArrayList<String> doInBackground(String... params) {

		ArrayList<String> resultList = null;

		Log.e("2", "" + params[0]);

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE
					+ TYPE_AUTOCOMPLETE + OUT_JSON);
			sb.append("?sensor=false&key=" + API_KEY);
			sb.append("&types=geocode");
			sb.append("&input=" + URLEncoder.encode(params[0], "utf8"));

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			Log.e("IS", "" + is.toString());
			InputStreamReader in = new InputStreamReader(is);

			BufferedReader reader = new BufferedReader(in);
			String line;
			while ((line = reader.readLine()) != null) {

				jsonResults.append(line);
			}

			
		} catch (MalformedURLException e) {
			Log.e("LOG_TAG", "Error processing Places API URL", e);
			return resultList;
		} catch (IOException e) {
			Log.e("LOG_TAG", "Error connecting to Places API", e);
			return resultList;
		}

		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

			// Extract the Place descriptions from the results
			resultList = new ArrayList<String>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {
				resultList.add(predsJsonArray.getJSONObject(i).getString(
						"description"));
			}
		} catch (JSONException e) {
			Log.e("LOG_TAG", "Cannot process JSON results", e);
		}

		Log.e("1", "" + resultList.toString());
		return resultList;
	}

	@Override
	protected void onPostExecute(ArrayList<String> result) {
		// TODO Auto-generated method stub

		object.setListOnAdapter(result);
	}

}
