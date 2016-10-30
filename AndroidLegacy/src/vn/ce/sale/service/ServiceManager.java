package vn.ce.sale.service;

import java.util.HashMap;

import android.util.Log;
import vn.ce.sale.data.DataManager;
import vn.ce.sale.data.DataOrder;
import vn.ce.sale.data.DataType;
import vn.ce.sale.data.ICallBack;
import vn.ce.sale.data.TransformDataManager;
import vn.ce.sale.ui.ICallBackUI;

public class ServiceManager {

	public static ServiceManager factoryData() {
		// TODO Auto-generated method stub
		return new ServiceManager();
	}

	public void getDataRaw(final String urlData, final String params, final DataOrder orderPriorityData,
			final ICallBackUI iCallBackUI) {
		// string
		// sCache=DataManager.facxcctoryData(DataType.CACHE).fetDataRaw(urlData,
		// params, iCallBackUI);
		DataType typeDataType;
		if (orderPriorityData == DataOrder.ONLY_NETWORK)
			typeDataType = DataType.NETWORK;
		else if (orderPriorityData == DataOrder.ONLY_CACHE)
			typeDataType = DataType.CACHE;

		else if (orderPriorityData == DataOrder.CACHE_THEN_NETWORK) {
			typeDataType = DataType.CACHE;
		} else if (orderPriorityData == DataOrder.NETWORK_THEN_CACHE) {
			typeDataType = DataType.NETWORK;
		} else {
			typeDataType = DataType.NETWORK;
		}

		final DataType typeDataWillCall = typeDataType;
		new Thread(new Runnable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void run() {
				try {
					// Thread.sleep(5000);
					// TODO Auto-generated method stub
					DataManager.factoryData(typeDataWillCall).fetDataRaw(urlData, params, new ICallBack() {
						public void postExecuteData(int status, String result) {
							// TODO Auto-generated method stub
							if (status == vn.ce.sale.util.Util.ERROR_NETWORK)// error
							{
								if (orderPriorityData == DataOrder.ONLY_NETWORK
										|| orderPriorityData == DataOrder.ONLY_CACHE) {
									transformDataToUIRaw(urlData, status, result, iCallBackUI);
								} else if (orderPriorityData == DataOrder.CACHE_THEN_NETWORK) {
									getDataRaw(urlData, params, DataOrder.NETWORK_THEN_CACHE, iCallBackUI);
								} else if (orderPriorityData == DataOrder.NETWORK_THEN_CACHE) {
									getDataRaw(urlData, params, DataOrder.ONLY_CACHE, iCallBackUI);
								}
							} else {

								if (orderPriorityData == DataOrder.ONLY_CACHE
										|| orderPriorityData == DataOrder.CACHE_THEN_NETWORK
										|| orderPriorityData == DataOrder.NETWORK_THEN_CACHE) {
									// ContextLocalManager.getInstance().saveToDB(null,
									// urlData,result);
									HashMap<String, String> hashmap = new HashMap<String, String>();
									hashmap.put(urlData, result);
									DataManager.factoryData(DataType.CACHE).sendDataRaw(urlData, hashmap, null);
								}
								transformDataToUIRaw(urlData, status, result, iCallBackUI);
							}
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void getDataRaw(final String urlData, final String params, final ICallBackUI iCallBackUI) {
		getDataRaw(urlData, params, DataOrder.ONLY_NETWORK, iCallBackUI);
	}

	public void postDataRaw(final String urlData, final Object object, final ICallBackUI iCallBackUI) {

		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void run() {
				try {
					// Thread.sleep(5000);
					// TODO Auto-generated method stub
					DataManager.factoryData(DataType.NETWORK).sendDataRaw(urlData, (HashMap<String, String>) object, new ICallBack() {
						public void postExecuteData(int status, String result) {
							// TODO Auto-generated method stub
							transformDataToUIRaw(urlData, status, result, iCallBackUI);

						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void postFileRaw(final String urlData, final String file, final ICallBackUI iCallBackUI) {

		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@SuppressWarnings({ "rawtypes" })
			@Override
			public void run() {
				try {
					// Thread.sleep(5000);
					// TODO Auto-generated method stub
					DataManager.factoryData(DataType.NETWORK).sendFileRaw(urlData, file, new ICallBack() {
						public void postExecuteData(int status, String result) {
							// TODO Auto-generated method stub
							transformDataToUIRaw(urlData, status, result, iCallBackUI);

						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	// transform to UI
	public void transformDataToUI(String key, int status, String result, final ICallBackUI iCallBackUI) {
		iCallBackUI.process(key, status, TransformDataManager.getListJsonByXPath(result, "data"));
		/*
		 * if(key.indexOf("catalog")!=-1) { //iCallBackUI.process(key, status,
		 * TransformDataManager.convertStringToListJSON(result));
		 * iCallBackUI.process(key, status,
		 * TransformDataManager.getListJsonByXPath(result,"data")); } else {
		 * iCallBackUI.process(key, status,
		 * TransformDataManager.convertStringToListJSON2Level(result)); }
		 */
	}

	public void transformDataToUIRaw(String key, int status, String result, final ICallBackUI iCallBackUI) {
		if (iCallBackUI != null)
			iCallBackUI.processRaw(key, status, result);
		/*
		 * if(key.indexOf("catalog")!=-1) { //iCallBackUI.process(key, status,
		 * TransformDataManager.convertStringToListJSON(result));
		 * iCallBackUI.process(key, status,
		 * TransformDataManager.getListJsonByXPath(result,"data")); } else {
		 * iCallBackUI.process(key, status,
		 * TransformDataManager.convertStringToListJSON2Level(result)); }
		 */
	}
}
