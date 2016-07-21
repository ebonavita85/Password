package it.bonavita.dao;

import it.bonavita.entity.CodiceSblocco;
import it.bonavita.entity.CodiceSblocco.CodiceSbloccoMetaData;
import it.bonavita.utils.ASCostants;

import android.content.ContentValues;
import android.database.Cursor;

public class CodiceSbloccoDAO {

	public CodiceSbloccoDAO() { }
	
	public boolean insertCode(CodiceSblocco cod) {
		boolean success = false;
		ASCostants.db.open();
		ContentValues cv = cod.getContentValues();
		success = ASCostants.db.getDB().insert(CodiceSbloccoMetaData.CODICE_SBLOCCO_TABLE, null, cv) > 0;
		ASCostants.db.close();
		return success;
	}
	
	public boolean updateCode(CodiceSblocco cod) {
		boolean success = false;
		ASCostants.db.open();
		ContentValues cv = cod.getContentValues();
		success = ASCostants.db.getDB().update(CodiceSbloccoMetaData.CODICE_SBLOCCO_TABLE, cv, CodiceSbloccoMetaData.ID + "=?", new String [] { String.valueOf(cod.getId())}) > 0;
		ASCostants.db.close();
		return success;
	}
	
	public CodiceSblocco getCodice() {
		ASCostants.db.open();
		CodiceSblocco cod = null;
		Cursor c = ASCostants.db.getDB().query(CodiceSbloccoMetaData.CODICE_SBLOCCO_TABLE, null, null, null, null, null, null);
		if(c.moveToFirst() && c.getCount() > 0) {
			cod = CodiceSblocco.getCodiceSblocco(c);
		}
		c.close();
		ASCostants.db.close();
		return cod;
	}
	
}
