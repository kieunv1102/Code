package vn.ce.sale.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.bool;
import android.R.integer;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class FileUtil {
	public static String fILE_METADATA = "metadata";
	public static String DIR_IN_SDCARD = "/sdcard/zopost/";
	public static String NAME_FILE_OFFLINE = DIR_IN_SDCARD + fILE_METADATA + ".txt";

	public static boolean saveTextToMetaData(String nameFile, String time, String[] data) {
		try {

			File myFile = new File(DIR_IN_SDCARD + fILE_METADATA + ".txt");
			if (!myFile.exists())
				myFile.createNewFile();
			FileWriter fw = new FileWriter(myFile, true);
			String text = nameFile + "," + time;// +","+note+","+data;
			for (int j = 0; j <= data.length - 1; j++) {
				String note = data[j];
				note = note.replace(",", "");
				text = text + "," + note;
			}
			fw.write(text + "\n");
			fw.flush();
			fw.close();
			return true;
		} catch (Exception e) {
			// Toast.makeText(ContextManager.getInstance().getCurrentContext(),
			// e.toString(),
			// Toast.LENGTH_LONG).show();
			return false;
		}
	}

	public static boolean saveJsonToMetaData(String nameFile, JSONObject oJson, boolean isAppend) {
		try {

			File myFile = new File(DIR_IN_SDCARD + nameFile + ".txt");
			if (!myFile.exists())
				myFile.createNewFile();
			FileWriter fw = new FileWriter(myFile, isAppend);
			fw.write(oJson.toString() + "\n");
			fw.flush();
			fw.close();
			return true;
		} catch (Exception e) {
			// Toast.makeText(ContextManager.getInstance().getCurrentContext(),
			// e.toString(),
			// Toast.LENGTH_LONG).show();
			return false;
		}
	}

	public static String saveJsonToActionUser(String nameFile, JSONObject oJson) {
		try {
			createDir(DIR_IN_SDCARD);
			// String nameFile="ActionUser";
			File myFile = new File(DIR_IN_SDCARD + nameFile + ".txt");
			if (!myFile.exists())
				myFile.createNewFile();
			FileWriter fw = new FileWriter(myFile, true);
			fw.write(oJson.toString() + "\n");
			fw.flush();
			fw.close();
			return DIR_IN_SDCARD + nameFile + ".txt";
		} catch (Exception e) {
			// Toast.makeText(ContextManager.getInstance().getCurrentContext(),
			// e.toString(),
			// Toast.LENGTH_LONG).show();
			return "";
		}
	}

	public static List<JSONObject> readJsonFromMetaData(String nameFile) {
		List<JSONObject> data = new ArrayList<JSONObject>();
		try {

			File myFile = new File(DIR_IN_SDCARD + nameFile + ".txt");

			if (!myFile.exists())
				return data;

			FileReader fw = new FileReader(myFile);
			BufferedReader br = new BufferedReader(fw);
			String line;
			while ((line = br.readLine()) != null) {
				data.add(new JSONObject(line));
			}
			fw.close();

		} catch (Exception e) {
			// Toast.makeText(ContextManager.getInstance().getCurrentContext(),
			// e.toString(),
			// Toast.LENGTH_LONG).show();

		}
		return data;
	}

	public static boolean existMetaData() {
		File myFile = new File(DIR_IN_SDCARD + fILE_METADATA + ".txt");
		return (myFile.exists());
	}

	public static HashMap<String, DataMetaData> loadTextFromMetaData() {
		try {
			File myFile = new File(DIR_IN_SDCARD + fILE_METADATA + ".txt");
			HashMap<String, DataMetaData> map = new HashMap<String, DataMetaData>();
			FileReader fw = new FileReader(myFile);

			BufferedReader br = new BufferedReader(fw);
			String line;
			while ((line = br.readLine()) != null) {
				String[] arrLine = line.split(",");
				DataMetaData m = new DataMetaData(arrLine[0], arrLine[2], arrLine[1], arrLine[3]);
				map.put(arrLine[0], m);
			}
			fw.close();
			return map;

		} catch (Exception e) {

			Log.v("lamdaica1", "Expcetion when loadTextFromMetaData:" + e.toString());
			return new HashMap<String, DataMetaData>();
		}
	}

	public static File getTempDir() {
		File dir = new File(DIR_IN_SDCARD);// Environment.getExternalStorageDirectory(),
											// "temp" );
		// dir.listFiles()
		if (dir.exists() == false && dir.mkdirs() == false) {
			// Log.e( TAG, "failed to get/create temp directory" );
			return null;
		}
		return dir;
	}

	public static File[] getListFileOffline() {
		File dir = new File(DIR_IN_SDCARD);
		if (dir.exists() && dir.isDirectory()) {
			File[] list = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File directory, String fileName) {
					// TODO Auto-generated method stub
					return fileName.endsWith(".jpg") || fileName.endsWith(".mp4")
							|| (fileName.startsWith("offline") && fileName.endsWith(".txt"));
				}
			});
			return list;
		}
		return new File[] {};
	}

	public static void deleteFileMetaData() {
		File dir = new File(DIR_IN_SDCARD + fILE_METADATA + ".txt");// Environment.getExternalStorageDirectory(),
																	// "temp" );
		if (dir.exists()) {
			dir.delete();
		}
	}

	public static File createDir(String pathDir) {
		File dir = new File(pathDir);// Environment.getExternalStorageDirectory(),
										// "temp" );
		if (dir.exists() == false && dir.mkdirs() == false) {
			// Log.e( TAG, "failed to get/create temp directory" );
			return null;
		}
		return dir;
	}

	/**
	 * Helper method to close given output stream ignoring any exceptions.
	 */
	public static void close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
			}
		}
	}

	public static void encryptFile(String aOutputFileName) {

		try {

			byte[] arrLast = new byte[10];
			byte[] arrFirst = new byte[10];

			// Open the file for both reading and writing
			RandomAccessFile rand = new RandomAccessFile(aOutputFileName, "rw");

			// tracking first
			rand.seek(0); // Seek to start point of file
			rand.read(arrLast);

			// tracking last
			rand.seek(rand.length() - arrFirst.length - 1);
			rand.read(arrFirst);

			rand.seek(0);
			rand.write(arrFirst);

			rand.seek(rand.length() - arrLast.length - 1);
			rand.write(arrLast);

			rand.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public static String encryptString(String sString) {
		return sString;
		/*
		 * if(!UtilGame.modeEncrypt) return sString; return
		 * SecBase64.encode(sString);
		 */
	}

	public static void saveTextToMetaData(String nameFile, String typeTable, String stringTimeNow,
			HashMap<String, String> data) {
		// TODO Auto-generated method stub
		try {

			File myFile = new File(DIR_IN_SDCARD + nameFile + ".txt");
			if (!myFile.exists())
				myFile.createNewFile();
			FileWriter fw = new FileWriter(myFile, true);
			String text = "table=" + typeTable + ",dateadded=" + stringTimeNow;// +","+note+","+data;
			for (String key : data.keySet()) {
				String note = data.get(key);
				note = note.replace(",", "");
				text = text + "," + key + "=" + note;
			}
			fw.write(text + "\r\n");
			fw.flush();
			fw.close();
			// return true;
		} catch (Exception e) {
			// Toast.makeText(ContextManager.getInstance().getCurrentContext(),
			// e.toString(),
			// Toast.LENGTH_LONG).show();
			// return false;
		}
	}

	public static void deleteFile(String fileToUploadx) {
		// TODO Auto-generated method stub

	}
}
