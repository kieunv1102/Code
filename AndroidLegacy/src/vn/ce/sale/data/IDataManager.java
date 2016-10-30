package vn.ce.sale.data;

import java.util.HashMap;

public interface IDataManager<T> {

	void fetDataRaw(String urlData, String params, ICallBack iCallBack);

	void sendDataRaw(String urlData, HashMap<String, String> params, ICallBack responseHandler);

	void sendFileRaw(String key, String uploadToFile, ICallBack responseHandler);
}
