package vn.ce.sale.data;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class TransformDataManager {

	public static List<JSONObject> fromStringJSONToList(String jsonServer, String hasName) {
		ArrayList<JSONObject> lst = new ArrayList<JSONObject>();
		try {
			JSONArray jsonRoot = new JSONArray(jsonServer);
			for (int j = 0; j <= jsonRoot.length() - 1; j++) {
				Object item = jsonRoot.get(j);
				if (item instanceof JSONObject) {
					lst.add((JSONObject) item);
					if (((JSONObject) item).has(hasName)) {
						JSONArray jsonArray = ((JSONObject) item).getJSONArray(hasName);
						// Log.v("lamlt", "Length of
						// "+j+"===>"+jsonArray.length());
						for (int jx = 0; jx <= jsonArray.length() - 1; jx++) {
							item = jsonArray.get(jx);
							if (item instanceof JSONObject) {
								lst.add((JSONObject) jsonArray.get(jx));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}

	public static List<JSONObject> getListJsonByXPath(String jsonServer, String xPath) {
		String[] arrNames = xPath.split("/");
		ArrayList<JSONObject> lst = new ArrayList<JSONObject>();
		try {
			JSONObject jsonRoot = new JSONObject(jsonServer);
			String name = arrNames[0];
			Object item = jsonRoot.get(name);

			if (item instanceof JSONObject)
				lst.add((JSONObject) item);
			else if (item instanceof JSONArray) {
				JSONArray jsonRoot1 = (JSONArray) item;
				for (int j = 0; j <= jsonRoot1.length() - 1; j++) {
					Object item1 = jsonRoot1.get(j);
					if (item1 instanceof JSONObject)
						lst.add((JSONObject) item1);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}

	public static List<JSONObject> convertStringToListJSON(String jsonServer) {
		ArrayList<JSONObject> lst = new ArrayList<JSONObject>();
		try {
			JSONArray jsonRoot = new JSONArray(jsonServer);
			for (int j = 0; j <= jsonRoot.length() - 1; j++) {
				Object item1 = jsonRoot.get(j);
				lst.add((JSONObject) item1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}

	public static List<JSONObject> convertArrayToListJSON(JSONArray jsonRoot) {
		ArrayList<JSONObject> lst = new ArrayList<JSONObject>();
		try {
			for (int j = 0; j <= jsonRoot.length() - 1; j++) {
				Object item1 = jsonRoot.get(j);
				lst.add((JSONObject) item1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}

	public static List<JSONObject> convertStringToListJSON2Level(String jsonServer) {
		ArrayList<JSONObject> lst = new ArrayList<JSONObject>();
		try {
			JSONArray jsonRoot = new JSONArray(jsonServer);
			for (int j = 0; j <= jsonRoot.length() - 1; j++) {
				JSONObject item1 = (JSONObject) jsonRoot.get(j);

				lst.add(item1);

				JSONArray jsonNames = item1.names();
				// detetec if an object is array or not
				for (int jx = 0; jx <= jsonNames.length() - 1; jx++) {
					Object json1 = item1.get(jsonNames.getString(jx));
					// stop at position where an object is array object
					if (json1 instanceof JSONArray) {

						lst.get(lst.size() - 1).put(jsonNames.getString(jx), new JSONObject());// clear
																								// to
																								// performance
						JSONArray tmpJON = ((JSONArray) json1);
						for (int jx1 = 0; jx1 <= tmpJON.length() - 1; jx1++) {
							lst.add((JSONObject) tmpJON.get(jx1));
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}

	public static List<JSONObject> getListJsonByXPath(JSONObject jsonRoot, String xPath) {
		String[] arrNames = xPath.split("/");
		ArrayList<JSONObject> lst = new ArrayList<JSONObject>();
		try {
			String name = arrNames[0];
			Object item = jsonRoot.get(name);

			if (item instanceof JSONObject)
				lst.add((JSONObject) item);
			else if (item instanceof JSONArray) {
				JSONArray jsonRoot1 = (JSONArray) item;
				for (int j = 0; j <= jsonRoot1.length() - 1; j++) {
					Object item1 = jsonRoot1.get(j);
					if (item1 instanceof JSONObject)
						lst.add((JSONObject) item1);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}
}
