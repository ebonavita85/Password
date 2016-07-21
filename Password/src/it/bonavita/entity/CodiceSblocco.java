package it.bonavita.entity;

import android.content.ContentValues;
import android.database.Cursor;

public class CodiceSblocco {
	private String id;
	private String codice;
	private String data;
	
	public static int MAX_LEN_CODICE_SBLOCCO = 5;
		
	public class CodiceSbloccoMetaData {
		public static final String CODICE_SBLOCCO_TABLE = "codice";		
		public static final String ID = "_id";
		public static final String CODICE = "codice";
		public static final String DATA = "data";
	}

	public static final String CODICE_SBLOCCO_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " 
													+ CodiceSbloccoMetaData.CODICE_SBLOCCO_TABLE + " ("
													+ CodiceSbloccoMetaData.ID + " integer primary key autoincrement, "
													+ CodiceSbloccoMetaData.CODICE + " text not null, "
													+ CodiceSbloccoMetaData.DATA + " text);";
	
	public CodiceSblocco() { }
		
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(CodiceSbloccoMetaData.CODICE, this.codice);
		cv.put(CodiceSbloccoMetaData.ID, this.id);
		cv.put(CodiceSbloccoMetaData.DATA, this.data);
        return cv;
	}
	
	public static CodiceSblocco getCodiceSblocco(Cursor cursor) {
		CodiceSblocco user = new CodiceSblocco();
		user.setCodice(cursor.getString(cursor.getColumnIndexOrThrow(CodiceSbloccoMetaData.CODICE)));
		user.setId(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(CodiceSbloccoMetaData.ID))));
		user.setData(cursor.getString(cursor.getColumnIndexOrThrow(CodiceSbloccoMetaData.DATA)));
        return user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
