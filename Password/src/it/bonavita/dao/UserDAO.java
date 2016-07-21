package it.bonavita.dao;

import it.bonavita.entity.User;
import it.bonavita.entity.User.UserMetaData;
import it.bonavita.utils.ASCostants;

import java.util.Vector;

import android.content.ContentValues;
import android.database.Cursor;

public class UserDAO {

	public UserDAO() { }
	
	public boolean insertUser(User user, Boolean encrypt) {
		boolean success = false;
		ASCostants.db.open();
		ContentValues cv = user.getContentValues(encrypt);
		success = ASCostants.db.getDB().insert(UserMetaData.USER_TABLE, null, cv) > 0;
		ASCostants.db.close();
		return success;
	}
	
	public boolean deleteUser(User user) {
		boolean success = false;
		ASCostants.db.open();
		if(user.getId() != null) {
			success = ASCostants.db.getDB().delete(UserMetaData.USER_TABLE, UserMetaData.ID+"=?", new String[]{ user.getId()}) > 0;
			ASCostants.db.close();
			return success;
		}
		success = ASCostants.db.getDB().delete(UserMetaData.USER_TABLE, UserMetaData.NOME+"=?", new String[]{ user.getNome() }) > 0;
		ASCostants.db.close();
		return success;
	}
	
	public boolean updateUser(User user, Boolean encrypt) {
		boolean success = false;
		ASCostants.db.open();
		ContentValues cv = user.getContentValues(encrypt);
		String whereClause = "";
		String whereArgs [];
		if(user.getId() != null) {
			whereClause = UserMetaData.ID + "=?";
			whereArgs = new String[]{ user.getId() };
		}
		else {
			whereClause = UserMetaData.NOME + "=?";
			whereArgs = new String[]{ user.getNome() };
		}
		success = ASCostants.db.getDB().update(UserMetaData.USER_TABLE, cv, whereClause, whereArgs) > 0;
		ASCostants.db.close();
		return success;
	}
	
	public Vector<User> getListUsers(Boolean decrypt) {
		ASCostants.db.open();
		Vector<User> user = new Vector<User>();
		Cursor c = ASCostants.db.getDB().query(UserMetaData.USER_TABLE, null, null, null, null, null, UserMetaData.NOME + " ASC");
		if(c.moveToFirst() && c.getCount() > 0) {
			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				user.add(User.getUser(c, decrypt));
			}
		}
		c.close();
		ASCostants.db.close();
		return user;
	}
	
	/*public User getUser(String nome) {
		ASCostants.db.open();
		User user = null;
		Cursor c = ASCostants.db.getDB().query(UserMetaData.USER_TABLE, null, UserMetaData.NOME+"=?", new String[] { nome }, null, null, null);
		if(c.moveToFirst() && c.getCount() > 0) {
			user = User.getUser(c);
		}
		c.close();
		ASCostants.db.close();
		return user;
	}*/
	
	/*public Cursor fetchUsers() {
		ASCostants.db.open();
		Cursor c = ASCostants.db.getDB().query(UserMetaData.USER_TABLE, null,null,null,null,null,null);
		c.moveToFirst();
		ASCostants.db.close();
		return c;
	}*/
	
	public boolean insertUsers(Vector<User> list, Boolean encrypt) {
		boolean success = false;
		ASCostants.db.open();
		ASCostants.db.getDB().delete(UserMetaData.USER_TABLE,null, null);
		for (User user : list) {
			ContentValues cv = user.getContentValues(encrypt);
			success = ASCostants.db.getDB().insert(UserMetaData.USER_TABLE, null, cv) > 0;
			if(!success)
				break;
		}
		ASCostants.db.close();
		return success;
	}
	
}
