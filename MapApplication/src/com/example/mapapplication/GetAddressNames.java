package com.example.mapapplication;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import asynClass.AsyncronsTask;

public class GetAddressNames extends AsyncTask<String, Void, ArrayList<String>> {

	MainActivity object;

	public GetAddressNames(MainActivity obj) {
		// TODO Auto-generated constructor stub
		this.object = obj;
	}

	@Override
	protected ArrayList<String> doInBackground(String... params) {

		ArrayList<String> resultList = null;
		String data;

		data = new AsyncronsTask().downloadData(params[0]);

		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(data);
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
