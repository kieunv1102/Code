package vn.ce.sale.data;

public class DataManager {
	public static IDataManager factoryData(DataType dt) {
		if (dt == DataType.NETWORK)
			return new HttpNetWorkManager();
		if (dt == DataType.DUMMY)
			return new DummyManager();
		if (dt == DataType.CACHE)
			return new CacheManager();
		return new HttpNetWorkManager();

	}

	// default to test
	public static IDataManager<?> factoryData() {
		// if(1==1) return new DummyManager<T>();
		return new DummyManager();

	}
}
