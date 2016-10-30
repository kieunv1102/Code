package vn.ce.sale.data;

import java.util.HashMap;

import vn.ce.sale.util.ShareMemManager;

public class CacheManager implements IDataManager {

	public CacheManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fetDataRaw(String urlData, String params, ICallBack iCallBack) {
		// TODO Auto-generated method stub
		String cache = ShareMemManager.getInstance().readFromDB(null, extractUrlToKey(urlData));
		iCallBack.postExecuteData((cache.equalsIgnoreCase("") ? vn.ce.sale.util.Util.ERROR_NETWORK : 200), cache);
	}

	@Override
	public void sendDataRaw(String urlData, HashMap params, ICallBack responseHandler) {
		// TODO Auto-generated method stub
		ShareMemManager.getInstance().saveToDB(null, extractUrlToKey(urlData), params.get(urlData).toString());
	}

	@Override
	public void sendFileRaw(String key, String uploadToFile, ICallBack responseHandler) {
		// TODO Auto-generated method stub

	}

	public String extractUrlToKey(String urlData) {
		if (urlData.indexOf("\"PRODUCT\"") != -1)
			return "product_store";
		if (urlData.indexOf("\"STORE\"") != -1)
			return "product";
		if (urlData.indexOf("&") == -1)
			return urlData;
		return urlData.substring(0, urlData.indexOf("&"));
	}
}
