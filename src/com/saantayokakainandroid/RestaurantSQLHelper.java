package com.saantayokakainandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RestaurantSQLHelper extends SQLiteOpenHelper {
	
	public static final String DB_NAME = "restaurant.db";
	public static final String RESTAURANT_TABLE = "restaurants";
	public static final int DB_VERSION = 1;
	public static final String ID = "_id";
	
	public static final String NAME = "name";
	public static final String LOCATION = "location";
	public static final String CUISINE = "cuisine";
	public static final String MAXPEOPLE = "maxpeople";
	public static final String WORTH = "worth";
	public static final String LAST_EATING_DATE = "lastdate";
	public static final String APPROVAL_RATE = "approval_rate";
	public static final String DECLINE_RATE = "decline_rate";
	public static final String SCORE = "score";
	public static final String COST = "cost";
	
	public static final String DB_CREATE = "create table "
			+ RESTAURANT_TABLE + " ( "
			+ ID + " text primary key, "
			+ LOCATION + " float, "
			+ CUISINE + " Text, "
			+ MAXPEOPLE + " integer, "
			+ WORTH + " float, "
			+ LAST_EATING_DATE + " Date, "
			+ APPROVAL_RATE + " integer, "
			+ DECLINE_RATE + " integer, "
			+ SCORE + " float, "
			+ COST + " float "
			+ " ) ";
	

	public RestaurantSQLHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public RestaurantSQLHelper(Context c) {
		super(c, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
