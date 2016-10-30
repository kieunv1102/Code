package vn.ce.sale.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import vn.ce.sale.ui.ICallBackUI;

public class DBManager extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "productDB.db";
	public static final String TABLE_PURCHASE = "TABLE_PURCHASE";
	public static final String TABLE_ORDER = "TABLE_ORDER";

	// order
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATA = "code";
	public static final String COLUMN_SERVER = "server";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_TIMESTAMP = "timestamp";

	//orderProduct
	public static final String ORDER_ID = "_id";
	public static final String ORDER_ProductID = "ProductID";
	public static final String ORDER_ProductName = "ProductName";
	public static final String ORDER_ProductPrice = "ProductPrice";
	public static final String ORDER_UnitName = "UnitName";
	public static final String ORDER_UnitID = "UnitID";
	public static final String ORDER_TT = "TT";
	public static final String ORDER_Quantity = "Quantity";

	// product
	public static final String PRODUCT_ID = "_id";
	public static final String PRODUCT_ORDERID = "orderid";
	public static final String PRODUCT_CODE = "code";
	public static final String PRODUCT_NAME = "name";
	public static final String PRODUCT_PRICE = "price";
	public static final String PRODUCT_QUANTITY = "quantity";
	public static final String PRODUCT_STATUS = "status";
	public static final String PRODUCT_PROMOTION = "promotion";

	private static DBManager sInstance;

	public static synchronized DBManager getInstance(Context context) {
		// super(context, DATABASE_NAME, factory, DATABASE_VERSION);
		// if (sInstance == null)
		{
			sInstance = new DBManager(context.getApplicationContext());
		}
		return sInstance;
	}

	public DBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
//		String CREATE_ORDER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER + " (" + COLUMN_ID
//				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DATA + " TEXT," + COLUMN_SERVER + " TEXT,"
//				+ COLUMN_TIMESTAMP + " INTEGER," + COLUMN_STATUS + " INTEGER" + ")";
//		db.execSQL(CREATE_ORDER_TABLE);
		
		String CREATE_ORDER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER + " (" + COLUMN_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ ORDER_ProductID + " INTEGER," 
				+ ORDER_ProductName + " TEXT,"
				+ ORDER_ProductPrice + " INTEGER," 
				+ ORDER_UnitName + " TEXT," 
				+ ORDER_UnitID + " TEXT,"
				+ORDER_TT + " TEXT,"
				+ORDER_Quantity + " TEXT"+")";
		db.execSQL(CREATE_ORDER_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASE);
		onCreate(db);
	}

	public void saveOrder(JSONObject o) {
		SQLiteDatabase db = this.getWritableDatabase();
		// java.lang.IllegalStateException: attempt to re-open an already-closed
		// object
		try {

			db.beginTransaction();
			String sql = "Insert or Replace into " + TABLE_ORDER + "(" + COLUMN_DATA + "," + COLUMN_STATUS + ","
					+ COLUMN_TIMESTAMP + ") values(?,?,?)";
			SQLiteStatement insert = db.compileStatement(sql);

			insert.bindString(1, o.toString());
			insert.bindLong(2, o.getInt("status"));
			insert.bindLong(3, o.getLong("timestamp"));
			insert.execute();

			db.setTransactionSuccessful();
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.v("SQLiteDatabase1", ex.toString());
		} finally {
			db.endTransaction();
			// db.close();
		}
	}

	public JSONObject getOrder(long timestamp) throws JSONException {
		String query = "Select * FROM " + TABLE_ORDER + " WHERE " + COLUMN_TIMESTAMP + " =  " + timestamp + "";

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		String jsonString = "";

		if (cursor.moveToFirst()) {
			cursor.moveToFirst();

			jsonString = (cursor.getString(1));// id,data,server,status,timestamp
			cursor.close();
		} else {
			jsonString = null;
		}
		db.close();
		if (jsonString == null)
			return null;
		return new JSONObject(jsonString);
	}

	public List<JSONObject> findProduct(int status) {

		ArrayList<JSONObject> lstItems = new ArrayList<JSONObject>();

		String query = "Select * FROM " + TABLE_ORDER + " WHERE " + COLUMN_STATUS + " =  " + status
				+ " ORDER BY timestamp DESC";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		while (cursor.moveToNext()) {
			try {
				lstItems.add(new JSONObject(cursor.getString(1)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cursor.close();
		Log.v("SQLiteDatabase1", "Count=" + String.valueOf(lstItems.size()));
		return lstItems;
	}

	public List<JSONObject> findProduct2(int i) {

		ArrayList<JSONObject> lstItems = new ArrayList<JSONObject>();

		String query = "Select * FROM " + TABLE_PURCHASE + " WHERE " + COLUMN_SERVER + " =  " + i + "";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		while (cursor.moveToNext()) {
			try {
				lstItems.add(new JSONObject(cursor.getString(1)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cursor.close();
		Log.v("SQLiteDatabase1", "Count=" + String.valueOf(lstItems.size()));
		return lstItems;
	}

	public void removeAllData() throws SQLException {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("drop table " + TABLE_ORDER);
		// db.close ();
	}

	public void createIfNeed() throws SQLException {

		SQLiteDatabase db = getWritableDatabase();
		// TODO Auto-generated method stub
		String CREATE_ORDER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER + "(" + COLUMN_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DATA + " TEXT," + COLUMN_SERVER + " TEXT,"
				+ COLUMN_STATUS + " INTEGER," + COLUMN_TIMESTAMP + " INTEGER" + ")";
		db.execSQL(CREATE_ORDER_TABLE);

		String CREATE_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PURCHASE + "(" + PRODUCT_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + PRODUCT_ORDERID + " TEXT," + PRODUCT_CODE + " TEXT,"
				+ PRODUCT_NAME + " TEXT," + PRODUCT_PRICE + " TEXT," + PRODUCT_QUANTITY + " TEXT," + PRODUCT_STATUS
				+ " TEXT," + PRODUCT_PROMOTION + " TEXT" + ")";
		db.execSQL(CREATE_PRODUCT_TABLE);
		db.close();
	}

	public boolean updateOrder(JSONObject oOrder) throws JSONException {

		long timestamp = oOrder.getLong("timestamp");

		boolean result = false;

		String query = "Select * FROM " + TABLE_ORDER + " WHERE " + COLUMN_TIMESTAMP + " =  " + timestamp + " ";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			ContentValues args = new ContentValues();
			args.put(COLUMN_DATA, oOrder.toString());
			args.put(COLUMN_SERVER, oOrder.has("server") ? oOrder.getString("server") : "");
			args.put(COLUMN_STATUS, oOrder.getInt("status"));
			// args.put(COLUMN_TIMESTAMP, oOrder.getLong("timestamp"));
			db.update(TABLE_ORDER, args, COLUMN_TIMESTAMP + " =  " + timestamp, null);
			cursor.close();
			result = true;
		} else
			result = false;
		return result;
	}

	public boolean deleteOrder(long timestamp) throws JSONException {

		// int timestamp=oOrder.getInt("timestamp");
		boolean result = false;

		String query = "Select * FROM " + TABLE_ORDER + " WHERE " + COLUMN_TIMESTAMP + " =  " + timestamp + "";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			db.delete(TABLE_ORDER, COLUMN_TIMESTAMP + " = ?", new String[] { String.valueOf(timestamp) });
			cursor.close();
			result = true;
		} else
			result = false;
		return result;
	}
}