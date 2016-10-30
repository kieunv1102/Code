package vn.ce.sale.ui;

import java.util.List;

import org.json.JSONObject;

public interface ICallBackUI {
	void process(String key, int status, List<JSONObject> lst);

	void processRaw(String key, int status, String json);
}
