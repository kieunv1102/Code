package vn.ce.sale.data;

import java.util.HashMap;

public interface IHttpNetwork<T> {
	void getData(String url, ICallBack<T> responseHandler) throws Exception;

	void postData(String url, HashMap<String, String> params, ICallBack<T> responseHandler) throws Exception;

	void uploadFile(String url, String fileToUpload, ICallBack<T> responseHandler) throws Exception;

}
