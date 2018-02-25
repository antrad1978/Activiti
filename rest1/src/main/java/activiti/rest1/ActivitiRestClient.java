package activiti.rest1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ActivitiRestClient {
	public static String get(String httpUrl) throws Exception
	{
			URL url = new URL(httpUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String userPassword =  "kermit:kermit";
			String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
			conn.setRequestProperty("Authorization", "Basic " + encoding);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			StringBuilder builder=new StringBuilder();
			while ((output = br.readLine()) != null) {
				builder.append(output);
			}

			conn.disconnect();

			return builder.toString();
		}
	
	public static String post(String httpUrl, String input,boolean multipart) throws Exception
	{
		String attachmentName = "MyProcess";
		String attachmentFileName = "MyProcess.bar";
		String crlf = "\r\n";
		String twoHyphens = "--";
		String boundary =  "*****";
		
			URL url = new URL(httpUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			String userPassword =  "kermit:kermit";
			String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
			conn.setRequestProperty("Authorization", "Basic " + encoding);
			conn.setRequestMethod("POST");
			conn.addRequestProperty("Content-Type", "application/json");
			if(multipart)
			{
				conn.setRequestProperty(
					    "Content-Type", "multipart/form-data;boundary=" + boundary);
			}

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			StringBuilder builder=new StringBuilder();
			while ((output = br.readLine()) != null) {
				builder.append(output);
			}

			conn.disconnect();
			return builder.toString();
	}
	
}

