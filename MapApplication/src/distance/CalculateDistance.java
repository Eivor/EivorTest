package distance;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mapapplication.MainActivity;
import com.google.android.gms.maps.model.LatLng;

public class CalculateDistance extends AsyncTask<LatLng, Void, Float> {

	MainActivity activity;

	public CalculateDistance(MainActivity activity) {
		// TODO Auto-generated constructor stub

		this.activity = activity;

	}

	public float totalDistance(LatLng A, LatLng B) {

		Log.e("2", "OK");

		Location locationA = new Location("POINT A");
		locationA.setLatitude(A.latitude);
		locationA.setLatitude(A.longitude);

		Location locationB = new Location("POINT B");
		locationB.setLatitude(B.latitude);
		locationB.setLatitude(B.longitude);

		return locationA.distanceTo(locationB);

	}

	@Override
	protected Float doInBackground(LatLng... params) {
		// TODO Auto-generated method stub

		return totalDistance(params[0], params[1]);

	}

	@Override
	protected void onPostExecute(Float result) {
		// TODO Auto-generated method stub

		Float distance = result / 1000;
		// activity.showDistance(distance.toString());

	}
}
