package it.bonavita.entity;

import it.bonavita.security.SecurityUtils;
import it.bonavita.utils.ASCostants;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.content.ContentValues;
import android.database.Cursor;

@Root(name="item")
public class User {
	private String id;
	@Element(name="name")
	private String nome;
	@Element(name="password")
	private String password;
	private String data;
		
	public class UserMetaData {
		public static final String USER_TABLE = "item";		
		public static final String ID = "_id";
		public static final String NOME = "nome";
		public static final String PASSWORD = "password";
		public static final String DATA = "data";
	}

	public static final String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " 
													+ UserMetaData.USER_TABLE + " ("
													+ UserMetaData.ID + " integer primary key autoincrement, "
													+ UserMetaData.NOME + " text not null, "
													+ UserMetaData.PASSWORD + " text not null, "
													+ UserMetaData.DATA + " text);";
	
	public User() { }
	
	/**
	 * Funzione per ottenere il ContentValues dall'oggetto User
	 * @param encrypt specifica se la password deve essere cifrata
	 * @return
	 */
	public ContentValues getContentValues(Boolean encrypt) {
		ContentValues cv = new ContentValues();
		cv.put(UserMetaData.NOME, this.nome);
		cv.put(UserMetaData.ID, this.id);
		String psw = this.password;
		if(encrypt) {
			try {
				SecurityUtils security = new SecurityUtils(ASCostants.cipherPassw);
				psw = security.encrypt(password);
			} catch (Exception e) { }
		}
		cv.put(UserMetaData.PASSWORD, psw);
		cv.put(UserMetaData.DATA, this.data);
        return cv;
	}
	
	/**
	 * Funzione per ottenere l'oggetto User dal Cursor
	 * @param cursor
	 * @param decrypt specifica se la password deve essere decifrata
	 * @return
	 */
	public static User getUser(Cursor cursor, Boolean decrypt) {
		User user = new User();
		user.setNome(cursor.getString(cursor.getColumnIndexOrThrow(UserMetaData.NOME)));
		user.setId(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(UserMetaData.ID))));
		String psw = new String();
		try {
			if(decrypt)
				psw = cursor.getString(cursor.getColumnIndexOrThrow(UserMetaData.PASSWORD));
			else {
				SecurityUtils security = new SecurityUtils(ASCostants.cipherPassw);
				String b64 = cursor.getString(cursor.getColumnIndexOrThrow(UserMetaData.PASSWORD));
				psw = security.decrypt(b64);
			}
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		user.setPassword(psw);
		user.setData(cursor.getString(cursor.getColumnIndexOrThrow(UserMetaData.DATA)));
        return user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
