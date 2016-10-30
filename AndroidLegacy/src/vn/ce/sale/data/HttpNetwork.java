package vn.ce.sale.data;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class HttpNetwork<T> implements IHttpNetwork<T> {

	@Override
	public void getData(String urlx, ICallBack<T> responseHandler) throws Exception {
		/*
		 * Log.v("lamlt", "Result call url  "+urlx); // TODO Auto-generated
		 * method stub System.setProperty("http.keepAlive", "false");
		 * 
		 * URL url = new URL(urlx); HttpURLConnection conn = (HttpURLConnection)
		 * url.openConnection(); conn.setConnectTimeout(10000); // Get the
		 * response BufferedReader rd = new BufferedReader(new
		 * InputStreamReader(conn.getInputStream())); String line = ""; String
		 * result = ""; while ((line = rd.readLine()) != null) { result += line;
		 * }
		 * 
		 * Log.v("lamlt", "Result call to "+result);
		 * responseHandler.postExecuteData(conn.getResponseCode(),result);
		 * //free memory rd=null; conn=null; url=null;
		 */
		URL url = new URL(urlx);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(10000);
		conn.setInstanceFollowRedirects(false);
		int lenghtOfFile = conn.getContentLength();

		// Get the response
		// conn.setDoOutput(false);
		int statusDebug = conn.getResponseCode();
		DataInputStream rd = (new DataInputStream(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		byte[] buffer = new byte[512];
		int total = 0;
		int count;
		while ((count = rd.read(buffer)) != -1) {
			total += count;
			// int progress_temp = (int) total * 100 / lenghtOfFile;
			// publishProgress("" + progress_temp);
			// if(progress_temp<100)
			// responseHandler.postExecuteData(vn.zopost.sale.util.Util.NOTIFY_NETWORK,""
			// + progress_temp);
			// sb.append(new String(buffer, "UTF-8"));

			if (count < buffer.length) {
				byte[] bufferX = new byte[count];
				for (int jx = 0; jx <= bufferX.length - 1; jx++) {
					bufferX[jx] = buffer[jx];
				}
				sb.append(new String(bufferX, "UTF-8"));
			} else {
				sb.append(new String(buffer, "UTF-8"));
			}
		}
		String sResult = sb.toString();
		responseHandler.postExecuteData(conn.getResponseCode(), sResult);
		rd = null;
		conn = null;
		url = null;

	}

	@Override
	public void postData(String url, HashMap<String, String> params, ICallBack<T> responseHandler) throws Exception {
		// TODO Auto-generated method stub

		HttpClient httpClient = new DefaultHttpClient();
		// replace with your url
		HttpPost httpPost = new HttpPost(url);

		// Post Data
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(params.size());

		for (String key : params.keySet()) {
			nameValuePair.add(new BasicNameValuePair(key, params.get(key)));
		}

		// Encoding POST data
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// log exception
			e.printStackTrace();
		}

		// making POST request.
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();

		InputStreamReader is = new InputStreamReader(in);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(is);
		String read = br.readLine();

		while (read != null) {
			// System.out.println(read);
			sb.append(read);
			read = br.readLine();

		}
		// write response to log
		Log.d("Http Post Response:", response.toString());
		responseHandler.postExecuteData(response.getStatusLine().getStatusCode(), sb.toString());

	}

	@Override
	public void uploadFile(String URL, String fileToUpload, ICallBack<T> responseHandler) throws Exception {
		/*
		 * // TODO Auto-generated method stub //Log.v("lamdaica1",
		 * "Call In httpPostFile "); //notifyMessage("Streaming file");
		 * HttpURLConnection connection = null; DataOutputStream outputStream =
		 * null; DataInputStream inputStream = null;
		 * 
		 * //String pathToOurFile = "/data/file_to_send.mp3";
		 * //if(!URL.startsWith("http")) // URL=prefixUrl+URL; String urlServer
		 * =URL;// "http://192.168.1.1/handle_upload.php"; String lineEnd =
		 * "\r\n"; String twoHyphens = "--"; String boundary = "*****";
		 * 
		 * int serverResponseCode=-99;
		 * 
		 * int bytesRead, bytesAvailable, bufferSize; byte[] buffer; int
		 * maxBufferSize = 1 * 1024 * 1024;
		 * 
		 * 
		 * //Log.v("ErrorFile",fileToUpload+"-"+FILE);
		 * System.setProperty("http.keepAlive", "false");
		 * 
		 * FileInputStream fileInputStream = new FileInputStream(new
		 * File(fileToUpload)); ByteArrayOutputStream bos = new
		 * ByteArrayOutputStream(); int totalByte=fileInputStream.available();
		 * 
		 * String
		 * extFile=fileToUpload.substring(0,fileToUpload.lastIndexOf(".")+1);
		 * String[] arrParts=fileToUpload.split("/");
		 * extFile=arrParts[arrParts.length-1];
		 * 
		 * //if(totalByte-indexStreaming<=0) return; //notifyMessage(
		 * "Streaming file:"+(totalByte-indexStreaming)); String result = "";
		 * //Log.v("ErrorFile",""+totalByte); boolean
		 * avoidOutOfMemory=false;//totalByte>15016025;
		 * 
		 * if(!avoidOutOfMemory) {
		 * 
		 * int indexStreaming=0;
		 * 
		 * byte[] imgData = new byte[totalByte-indexStreaming];
		 * fileInputStream.read(imgData, indexStreaming, imgData.length);
		 * indexStreaming=totalByte-1;
		 * 
		 * //FileInputStream fileInputStream = new FileInputStream(new
		 * File(pathToOurFile)); String
		 * debugQuery="";//"indexStreaming="+String.valueOf(indexStreaming)
		 * //+"&f="+String.valueOf(totalByte)+"&img="+String.valueOf(imgData.
		 * length);
		 * 
		 * URL url = new URL(urlServer+debugQuery); connection =
		 * (HttpURLConnection) url.openConnection();
		 * 
		 * connection.setConnectTimeout(10*1000); // Allow Inputs & Outputs
		 * connection.setDoInput(true); connection.setDoOutput(true);
		 * connection.setUseCaches(false);
		 * 
		 * // Enable POST method connection.setRequestMethod("POST");
		 * 
		 * //connection.setRequestProperty("Connection", "Keep-Alive");
		 * connection.setRequestProperty("Content-Type",
		 * "multipart/form-data;boundary=" + boundary); //indexFile=indexFile+1;
		 * outputStream = new DataOutputStream(connection.getOutputStream());
		 * outputStream.writeBytes(twoHyphens + boundary + lineEnd);
		 * outputStream .writeBytes(
		 * "Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" +
		 * extFile + "\"" + lineEnd); outputStream.writeBytes(lineEnd);
		 * 
		 * 
		 * //old version //outputStream.write(imgData, 0, imgData.length); //new
		 * version byte[] bytes=imgData; int bufferLength = 256;
		 * 
		 * for (int i = 0; i < bytes.length; i += bufferLength) { if
		 * (bytes.length - i >= bufferLength) { outputStream.write(bytes, i,
		 * bufferLength);//error here .txt-9769767 } else {
		 * outputStream.write(bytes, i, bytes.length - i);//error here-9769767 }
		 * } //end of new version
		 * 
		 * outputStream.writeBytes(lineEnd); outputStream.writeBytes(twoHyphens
		 * + boundary + twoHyphens + lineEnd);
		 * 
		 * // Responses from the server (code and message) serverResponseCode =
		 * connection.getResponseCode(); String serverResponseMessage =
		 * connection.getResponseMessage();
		 * 
		 * //fileInputStream.close(); outputStream.flush();
		 * 
		 * 
		 * BufferedReader rd = new BufferedReader(new
		 * InputStreamReader(connection.getInputStream())); String line = "";
		 * 
		 * while ((line = rd.readLine()) != null) { result += line; }
		 * 
		 * outputStream.close();
		 * 
		 * } else { fileInputStream.close(); //bos.close(); }
		 * responseHandler.postExecuteData(serverResponseCode, result);
		 */
		String sResult = "Upload Ok";
		try {
			HttpURLConnection connection = null;
			DataOutputStream outputStream = null;
			DataInputStream inputStream = null;

			String fileToUploadCurrent = fileToUpload;
			// String pathToOurFile = "/data/file_to_send.mp3";
			String urlServer = URL;// "http://192.168.1.1/handle_upload.php";
			String lineEnd = "\r\n";
			String twoHyphens = "--";
			String boundary = "*****";

			// FileInputStream fileInputStream = new FileInputStream(new
			// File(pathToOurFile));

			URL url = new URL(urlServer);
			connection = (HttpURLConnection) url.openConnection();

			// Allow Inputs & Outputs
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			// Enable POST method
			connection.setRequestMethod("POST");
			// connection.setReadTimeout(300000);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			File fiTou = new File(fileToUploadCurrent);
			String fileToX = fiTou.getName();
			String fileCurrent = fileToX;

			outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			outputStream.writeBytes(
					"Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileToX + "\"" + lineEnd);
			outputStream.writeBytes(lineEnd);

			// Read file to array byte

			FileInputStream fileInputStreamx = new FileInputStream(fiTou);
			ByteArrayOutputStream bosx = new ByteArrayOutputStream();
			int totalByte = fileInputStreamx.available();
			byte[] imgDataNew = new byte[totalByte];
			fileInputStreamx.read(imgDataNew, 0, imgDataNew.length);
			// outputStream.write(imgDataNew, 0, imgData.length);

			byte[] bytes = imgDataNew;
			int bufferLength = 256;

			for (int i = 0; i < bytes.length; i += bufferLength) {
				int progress = (int) ((i / (float) bytes.length) * 100);
				// publishProgress(""+progress,(progress==99?"�?ang lưu trên
				// server...":"Uploading..."));
				if (progress < 100)
					responseHandler.postExecuteData(vn.ce.sale.util.Util.NOTIFY_NETWORK, ("" + progress));
				if (bytes.length - i >= bufferLength) {
					outputStream.write(bytes, i, bufferLength);
				} else {
					outputStream.write(bytes, i, bytes.length - i);
				}
			}

			/*
			 * while (bytesRead > 0) { outputStream.write(buffer, 0,
			 * bufferSize); bytesAvailable = fileInputStream.available();
			 * bufferSize = Math.min(bytesAvailable, maxBufferSize); bytesRead =
			 * fileInputStream.read(buffer, 0, bufferSize); }
			 */

			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			int serverResponseCode = connection.getResponseCode();
			String serverResponseMessage = connection.getResponseMessage();

			outputStream.flush();
			outputStream.close();

			fileInputStreamx.close();

			fiTou.delete();

			responseHandler.postExecuteData(serverResponseCode, serverResponseMessage);

		} catch (Exception e) {
			sResult = "Error:" + e.toString();
			responseHandler.postExecuteData(vn.ce.sale.util.Util.ERROR_NETWORK, "");
		}

	}

}
