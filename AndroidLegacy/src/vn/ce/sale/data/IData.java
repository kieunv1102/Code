package vn.ce.sale.data;

import org.json.JSONObject;

public interface IData {
	void sendData(JSONObject o);

	JSONObject getData(JSONObject o);
}
