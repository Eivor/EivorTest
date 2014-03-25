package com.example.mapapplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class GetLatLang extends AsyncTask<String, Void, ArrayList<LatLng>> {

	Context context;
	MainActivity activity;

	public GetLatLang(Context context, MainActivity activity) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.activity = activity;
	}

	@Override
	protected ArrayList<LatLng> doInBackground(String... params) {
		// TODO Auto-generated method stub

		Geocoder geocoder = new Geocoder(context);
		List<Address> from_address = null;
		List<Address> to_address = null;

		LatLng from_LatLng = null, to_LatLng = null;

		try {
			//TODO More Search on second Param
			
			from_address = geocoder.getFromLocationName(params[0], 5);
			to_address = geocoder.getFromLocationName(params[1], 5);

			if (from_address == null && to_address == null) {
				Log.e("TAG", "NULL");
				return null;
			}

			Address from = from_address.get(0);
			Address to = to_address.get(0);

			from_LatLng = new LatLng(from.getLatitude(), from.getLongitude());
			to_LatLng = new LatLng(to.getLatitude(), to.getLongitude());

			Log.e("LatLng", "FROM " + from_LatLng + "   TO" + to_LatLng);

			ArrayList<LatLng> latLngs = new ArrayList<LatLng>();
			latLngs.add(from_LatLng);
			latLngs.add(to_LatLng);

			return latLngs;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("error", "" + e);

		}

		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<LatLng> latLngs) {
		// TODO Auto-generated method stub
		activity.setMarkerOnPoint(latLngs.get(0), latLngs.get(1));
	}

}
