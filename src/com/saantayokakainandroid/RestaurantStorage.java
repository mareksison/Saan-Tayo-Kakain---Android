package com.saantayokakainandroid;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author Aron Asor
 *
 *	Class that's supposed to handle persistent storage of
 *	restaurants.
 *
 *	Version 1: Use a file implementation, written in an SD Card
 */
public class RestaurantStorage {
	
	private SQLiteDatabase db;
	private RestaurantSQLHelper helper;
	
	private static final String[] ALL_COLUMNS =
		{ RestaurantSQLHelper.ID
		, RestaurantSQLHelper.LOCATION
		, RestaurantSQLHelper.CUISINE
		, RestaurantSQLHelper.MAXPEOPLE
		, RestaurantSQLHelper.WORTH
		, RestaurantSQLHelper.LAST_EATING_DATE
		, RestaurantSQLHelper.APPROVAL_RATE
		, RestaurantSQLHelper.DECLINE_RATE
		, RestaurantSQLHelper.SCORE
		, RestaurantSQLHelper.COST
		};
	
	
	public RestaurantStorage(Context c) {
		helper = new RestaurantSQLHelper(c);
		open();
	}
	
	public void open() throws SQLException {
		db = helper.getWritableDatabase();
	}
	
	public void close() {
		helper.close();
	}
	
	public void writeRestaurant(Restaurant r) {
		ContentValues ct = new ContentValues();
		ct.put(RestaurantSQLHelper.ID, r.getName());
		ct.put(RestaurantSQLHelper.LOCATION, r.getLocation());
		ct.put(RestaurantSQLHelper.CUISINE, r.getCuisine());
		ct.put(RestaurantSQLHelper.MAXPEOPLE, r.getMaxPeople());
		ct.put(RestaurantSQLHelper.WORTH, r.getWorth());
		ct.put(RestaurantSQLHelper.LAST_EATING_DATE, r.getLastDate());
		ct.put(RestaurantSQLHelper.APPROVAL_RATE, r.getKainC());
		ct.put(RestaurantSQLHelper.DECLINE_RATE, r.getDeclinedC());
		ct.put(RestaurantSQLHelper.SCORE, r.getScore());
		ct.put(RestaurantSQLHelper.COST, r.getCost());
		
		long rowID = db.insertOrThrow(RestaurantSQLHelper.RESTAURANT_TABLE, null, ct);
	}
	
	public List<Restaurant> getAllRestaurants() {
		List<Restaurant> restaurants = new LinkedList<Restaurant>();
		Cursor cursor = db.query(RestaurantSQLHelper.RESTAURANT_TABLE, ALL_COLUMNS, null
				, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String name = cursor.getString(0);
			float loc = cursor.getFloat(1);
			String cuisine = cursor.getString(2);
			int maxpeople = cursor.getInt(3);
			float worth = cursor.getFloat(4);
			String last_eating_date = cursor.getString(5);
			int approval_rate = cursor.getInt(6);
			int decline_rate = cursor.getInt(7);
			float score = cursor.getFloat(8);
			float cost = cursor.getFloat(9);
			
			Restaurant r = new Restaurant(name, loc);
			r.cuisine = cuisine;
			r.maxPeople = maxpeople;
			r.worth = worth;
			r.last_eating_date = last_eating_date;
			r.approval_rate = approval_rate;
			r.decline_rate = decline_rate;
			r.score = score;
			r.cost = cost;
			
			restaurants.add(r);
			cursor.moveToNext();
		}
		cursor.close();
		
		return restaurants;
	}
}
