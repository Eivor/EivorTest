package com.example.mapapplication;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

public class GetAddressTask extends AsyncTask<LatLng, Void, String> {

	Context context;
	MainActivity activity;

	public GetAddressTask(Context context, MainActivity activity) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.activity = activity;

	}

	/* Find-out Address from Lat and Lang of Address */
	@Override
	protected String doInBackground(LatLng... params) {
		// TODO Auto-generated method stub

		Geocoder geocoder = new Geocoder(context, Locale.getDefault());

		List<Address> addresses = null;

		LatLng location = params[0];

		try {
			addresses = geocoder.getFromLocation(location.latitude,
					location.longitude, 1);

			if (addresses == null)
				return "";

			Log.e("Address", "" + addresses.get(0));

		} catch (IllegalArgumentException exception) {
			exception.printStackTrace();
			Log.e("Address", "Error1");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("Address", "Error2");
			return null;
		}

		if (addresses != null && addresses.size() > 0) {

			Address address = addresses.get(0);

			String addressText = String.format("%s,%s,%s", address
					.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0)
					: "", address.getLocality(), address.getCountryName());

			Log.e("Text", addressText);
			return addressText;
		} else {
			return "No Address Found";
		}
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub

		activity.showToast(result);

	}

}
