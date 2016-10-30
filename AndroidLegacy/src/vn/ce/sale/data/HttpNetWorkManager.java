package vn.ce.sale.data;

import java.util.HashMap;

import vn.ce.sale.util.Util;

public class HttpNetWorkManager<T> implements IDataManager<T> {
	@Override
	public void fetDataRaw(String urlData, String params, ICallBack responseHandler) {
		// TODO Auto-generated method stub
		try {
			(new HttpNetwork<T>()).getData(urlData + params, responseHandler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (responseHandler != null)
				responseHandler.postExecuteData(vn.ce.sale.util.Util.ERROR_NETWORK, "");
		}
	}

	@Override
	public void sendDataRaw(String urlData, HashMap<String, String> params, ICallBack responseHandler) {
		// TODO Auto-generated method stub
		String paramRequests = Util.fromHashToQueryString(params);

		// if nocache then fetch from server...
		// TODO Auto-generated method stub
		try {
			(new HttpNetwork<T>()).postData(urlData, params, responseHandler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (responseHandler != null)
				responseHandler.postExecuteData(vn.ce.sale.util.Util.ERROR_NETWORK, "");
		}
	}

	@Override
	public void sendFileRaw(String urlData, String uploadToFile, ICallBack responseHandler) {
		// TODO Auto-generated method stub
		try {
			(new HttpNetwork<T>()).uploadFile(urlData, uploadToFile, responseHandler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (responseHandler != null)
				responseHandler.postExecuteData(vn.ce.sale.util.Util.ERROR_NETWORK, "");
		}
	}
}
