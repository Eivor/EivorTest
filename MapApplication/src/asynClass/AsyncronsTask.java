package asynClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

public class AsyncronsTask {

	public String downloadData(String... params) {
		// TODO Auto-generated method stub

		String data = null;
		InputStream inputStream;

		try {
			// TODO make separate class
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

		return data;
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

		return builder.toString();
	}

}
