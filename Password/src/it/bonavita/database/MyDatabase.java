package it.bonavita.database;

import it.bonavita.entity.CodiceSblocco;
import it.bonavita.entity.User;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase {

	private SQLiteDatabase mDb;
	private DbHelper mDbHelper;
	Context mContext;
	private static final String DB_NAME = "PasswordDB";
	private static final int DB_VERSION = 2;
	
	public MyDatabase(Context ctx) {
		mContext = ctx;
		mDbHelper = new DbHelper(ctx, DB_NAME, null, DB_VERSION);
	}

	public SQLiteDatabase getDB() {
		if(mDb != null)
			return mDb;
		else
			return mDb = mDbHelper.getWritableDatabase();
	}

	public void open() {
		try {
			if(mDb == null || !mDb.isOpen())
				mDb = mDbHelper.getWritableDatabase();
		} catch(Exception e) {
			
		}
	}
	
	public void openRead() {
		try {
			if(mDb == null || !mDb.isOpen())
				mDb = mDbHelper.getReadableDatabase();
		} catch(Exception e) {
			
		}
	}
	
	public void close() {
		try {
			if(mDb != null && mDb.isOpen())
				mDb.close();
		} catch(Exception e) {
			
		}
	}
	
	private class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context, String name, CursorFactory factory,int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(CodiceSblocco.CODICE_SBLOCCO_TABLE_CREATE);
			_db.execSQL(User.USER_TABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			if(oldVersion < 2) {
				_db.execSQL(CodiceSblocco.CODICE_SBLOCCO_TABLE_CREATE);
			}
		}
		
	}
}
