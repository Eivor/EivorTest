package com.example.mapapplication;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import database.MetroDatabase;

public class MainActivity extends Activity implements OnClickListener,
		TextWatcher, LocationListener {

	GoogleMap googleMap;
	Button goButton;
	LinearLayout layout;
	// AutoCompleteTextView autoCompleteTextView1, autoCompleteTextView2;

	ArrayList<String> shortestDistance;
	ArrayList<Long> distance_in_meter;

	ArrayList<String> shortestDuration;
	ArrayList<Long> duration_in_second;

	ArrayList<LatLng> pathsLatLng;

	ArrayList<LatLng> data = new ArrayList<LatLng>();

	private boolean servicesConnected() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		if (ConnectionResult.SUCCESS == resultCode) {
			Log.e("1", "Google play services is Available");
			return true;
		} else {
			Log.e("1", "Google play services is Not-Available");
			return false;
		}
	}

	public void showToast(String toShow) {
		Toast.makeText(getApplicationContext(), toShow, Toast.LENGTH_LONG)
				.show();
	}

	/** SET MARKERS ON MAP OF SOURCE AND DESTINATION POSITION **/
	public void setMarkerOnPoint(LatLng source, LatLng dest) {

		// googleMap.clear();

		// googleMap.addMarker(new MarkerOptions().position(source).title("A"));
		googleMap.addMarker(new MarkerOptions()
				.position(dest)
				.title("B")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

		// String url = GetAPIUrl.getUrlForDirectionAPI(source, dest); // Get
		// Direction
		// API
		// URL.....

		// new GetRoutes(this).execute(url); // Get Json Data From Async Task
		// Class...

	}

	/** Draw Polyline on Map for Route **/
	public void drawPolyLineInMap(PolylineOptions options) {

		googleMap.addPolyline(options);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		servicesConnected();

		LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		MapFragment fragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		googleMap = fragment.getMap();
		googleMap.setMyLocationEnabled(true);

		Criteria criteria = new Criteria();

		String provider = manager.getBestProvider(criteria, true);
		manager.requestLocationUpdates(provider, 0, 0, this);

		/*
		 * autoCompleteTextView1 = (AutoCompleteTextView)
		 * findViewById(R.id.autoCompleteTextView1); autoCompleteTextView2 =
		 * (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
		 * 
		 * autoCompleteTextView1.addTextChangedListener(this);
		 * autoCompleteTextView2.addTextChangedListener(this);
		 */

		goButton = (Button) findViewById(R.id.go_button);
		goButton.setOnClickListener(this);
		/*
		 * autoCompleteTextView1.setThreshold(3);
		 * autoCompleteTextView2.setThreshold(3);
		 */

		layout = (LinearLayout) findViewById(R.id.linearLayout);

		shortestDistance = new ArrayList<String>();
		shortestDuration = new ArrayList<String>();

		distance_in_meter = new ArrayList<Long>();
		duration_in_second = new ArrayList<Long>();

		data.add(new LatLng(28.574659, 77.356075));
		data.add(new LatLng(28.567139, 77.345974));
		data.add(new LatLng(28.564087, 77.334205));
		data.add(new LatLng(28.570816, 77.326117));
		data.add(new LatLng(28.578577, 77.317271));
		data.add(new LatLng(28.585118, 77.311554));
		data.add(new LatLng(28.589157, 77.302044));
		data.add(new LatLng(28.594287, 77.294567));
		data.add(new LatLng(28.604449, 77.289365));
		data.add(new LatLng(28.617988, 77.279371));
		data.add(new LatLng(28.623232, 77.267722));
		data.add(new LatLng(28.620508, 77.249926));
		data.add(new LatLng(28.623419, 77.242513));
		data.add(new LatLng(28.625736, 77.234242));
		data.add(new LatLng(28.629662, 77.224876));
		data.add(new LatLng(28.632891, 77.219556));

		MetroDatabase database = new MetroDatabase(this);
		database.open();

		LatLng latLng;
		double lat, lng;
		for (int i = 0; i < data.size(); i++) {

			latLng = data.get(i);

			lat = latLng.latitude;
			lng = latLng.longitude;

			Log.e("lat and lng", "" + lat + " " + lng);

			database.insertData(i + 1, lat, lng, "sdasd");

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		/*
		 * googleMap.clear();
		 * 
		 * if (autoCompleteTextView1.getText().toString().length() > 3 &&
		 * autoCompleteTextView2.getText().toString().length() > 3) {
		 * 
		 * new GetLatLang(getApplicationContext(), this).execute(
		 * autoCompleteTextView1.getText().toString(),
		 * autoCompleteTextView2.getText().toString()); } else {
		 * Toast.makeText(getApplicationContext(),
		 * "Please enter, Valid city name...", Toast.LENGTH_LONG) .show(); }
		 */

		goButton.setVisibility(View.GONE);
		layout.setVisibility(View.VISIBLE);

		Location location = googleMap.getMyLocation();

		LatLng currentLatLng = new LatLng(location.getLatitude(),
				location.getLongitude());

		MetroDatabase database = new MetroDatabase(this);

		database.open();
		Log.e("1", "" + currentLatLng);
		pathsLatLng = database.getLatLng(currentLatLng);
		Log.e("2", "" + pathsLatLng);
		database.Close();

		for (int i = 0; i < pathsLatLng.size(); i++) {
			LatLng where = pathsLatLng.get(i);
			setMarkerOnPoint(null, where);
			String url = GetAPIUrl.getUrlForDirectionAPI(currentLatLng, where);
			Log.e("URL", "" + url);

			new GetRoutes(MainActivity.this).execute(url);

		}

	}

	public void setListOnAdapter(ArrayList<String> list) {
		// TODO Auto-generated method stub

		/*
		 * autoCompleteTextView1.setAdapter(new ArrayAdapter<String>(this,
		 * android.R.layout.simple_expandable_list_item_1, list));
		 * 
		 * autoCompleteTextView2.setAdapter(new ArrayAdapter<String>(this,
		 * android.R.layout.simple_expandable_list_item_1, list));
		 */

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

		/*
		 * if (autoCompleteTextView1.getText() == s) {
		 * 
		 * GetAddressNames task = new GetAddressNames(MainActivity.this);
		 * task.execute(autoCompleteTextView1.getText().toString());
		 * 
		 * }
		 * 
		 * if (autoCompleteTextView2.getText() == s) { GetAddressNames task =
		 * new GetAddressNames(MainActivity.this);
		 * task.execute(autoCompleteTextView2.getText().toString());
		 * 
		 * }
		 */

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

		// googleMap.clear();

		MarkerOptions options = new MarkerOptions();
		options.position(new LatLng(location.getLatitude(), location
				.getLongitude()));
		options.title("ME...");

		googleMap.addMarker(options);

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void drawShortestPath(PolylineOptions options) {
		googleMap.addPolyline(options);
	}

	public void setData() {
		// TODO Auto-generated method stub

	}

	public void backButtonPress(View view) {

		googleMap.clear();
		layout.setVisibility(View.GONE);
		goButton.setVisibility(View.VISIBLE);

	}

}
