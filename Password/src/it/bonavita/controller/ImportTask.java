package it.bonavita.controller;

import it.bonavita.dao.UserDAO;
import it.bonavita.entity.Export;
import it.bonavita.entity.User;
import it.bonavita.password.R;

import java.io.File;
import java.util.Vector;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ImportTask  extends AsyncTask<Void, String, Boolean> {
	
	private File allegato;
	private Context ctx;
	private ProgressDialog pd;
	private Runnable run;
	
	public ImportTask(File allegato, Context ctx, Runnable run) {
		this.allegato = allegato;
		this.ctx = ctx;
		this.run = run;
		pd = ProgressDialog.show(ctx, ctx.getString(R.string.menu_txt_import), ctx.getString(R.string.progress_dialog_avvio), true, false);
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		Boolean result = Boolean.FALSE;
		try {
			Serializer serializer = new Persister();
			Export export = serializer.read(Export.class, allegato);
			if(export.getUsers().size() > 0) {
				UserDAO dao = new UserDAO();
				dao.insertUsers((Vector<User>) export.getUsers(), false);
				result = Boolean.TRUE;
			}
		} catch (Exception e) { }
		return result;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		try {
			pd.dismiss();
		} catch (Exception e) { }
		run.run();
		Toast.makeText(ctx, (result) ? ctx.getString(R.string.msg_import_ok) : ctx.getString(R.string.msg_import_failed), Toast.LENGTH_SHORT).show();
	}
}
