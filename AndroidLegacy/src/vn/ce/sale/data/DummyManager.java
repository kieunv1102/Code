package vn.ce.sale.data;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DummyManager<T> implements IDataManager<T> {

	// dumpy
	private int processDUMMYStatus(String key) {
		// TODO Auto-generated method stub
		return 1;
	}

	private String processDUMMYResult(String key) throws JSONException {
		String json1 = "[{\"productid\":1,\"productcode\":\"0001\",\"productname\":\"san pham 1\",\"price\":10000\"barcode\":\"10000111111\"},{\"productid\":2,\"productcode\":\"0002\",\"productname\":\"san pham 2\",\"price\":10000\"barcode\":\"10000111111\"},{\"productid\":3,\"productcode\":\"0003\",\"productname\":\"san pham 3\",\"price\":10000\"barcode\":\"10000111111\"},{\"productid\":4,\"productcode\":\"0004\",\"productname\":\"san pham 4\",\"price\":10000\"barcode\":\"10000111111\"},{\"productid\":5,\"productcode\":\"0005\",\"productname\":\"san pham 5\",\"price\":10000\"barcode\":\"10000111111\"},{\"productid\":6,\"productcode\":\"0006\",\"productname\":\"san pham 6\",\"price\":10000\"barcode\":\"10000111111\"},{\"productid\":7,\"productcode\":\"0007\",\"productname\":\"san pham 7\",\"price\":10000\"barcode\":\"10000111111\"},{\"productid\":8,\"productcode\":\"0008\",\"productname\":\"san pham 8\",\"price\":10000\"barcode\":\"10000111111\"}]";
		if (key.indexOf("product") != -1)
			return json1;

		if (key.indexOf("catalog") != -1) {
			JSONArray jsonArray = new JSONArray();
			for (int j = 0; j <= 15; j++) {
				// TODO Auto-generated method stub
				JSONObject json = new JSONObject();
				json.put("movieName", "Title " + String.valueOf(j));
				json.put("urlImage", "http://192.168.2.44/img.aspx");
				jsonArray.put(json);
			}
			return jsonArray.toString();
		} else if (key.indexOf("detail") != -1) {
			JSONArray jsonArray = new JSONArray();
			// for(int j=0;j<=15;j++)
			{
				// TODO Auto-generated method stub
				JSONObject json = new JSONObject();
				json.put("title", "Title Demo");
				json.put("urlImage", "http://192.168.2.44/img.aspx");
				json.put("detail",
						"I know this question has been asked several times, but the solutions have been specific to the askers' problems. Consequently, none of those solutions helped me, even though I tried following all of their suggestions.");
				jsonArray.put(json);
			}
			return jsonArray.toString();
		} else {
			// for home key
			JSONArray jsonArray = new JSONArray();
			for (int jx = 0; jx <= 3; jx++) {
				JSONObject json = new JSONObject();
				json.put("ChapterName", "Chapter " + String.valueOf(jx));

				JSONArray jsonArray1 = new JSONArray();
				for (int j = 0; j <= 15; j++) {
					JSONObject jsonx = new JSONObject();
					jsonx.put("movieName", "Title " + String.valueOf(j));
					jsonx.put("urlImage", "http://192.168.2.44/img.aspx");
					jsonArray1.put(jsonx);
				}

				json.put("movies", jsonArray1);

				jsonArray.put(json);
			}
			return jsonArray.toString();
		}

	}

	@Override
	public void fetDataRaw(String urlData, String params, ICallBack iCallBack) {
		// TODO Auto-generated method stub
		// check cache

		String paramRequests = (params);

		// if nocache then fetch from server...
		// TODO Auto-generated method stub
		try {
			int status = processDUMMYStatus(urlData);
			String result = processDUMMYResult(urlData);
			iCallBack.postExecuteData(status, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendDataRaw(String urlData, HashMap<String, String> params, ICallBack responseHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendFileRaw(String key, String uploadToFile, ICallBack responseHandler) {
		// TODO Auto-generated method stub

	}
}
