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
import android.os.Environment;
import android.widget.Toast;

public class ExportTask extends AsyncTask<Boolean, String, Boolean> {
	
	private File allegato;
	private ProgressDialog pd;
	private Context ctx;
	
	public ExportTask(Context ctx) {
		this.allegato = null;
		this.ctx = ctx;
		pd = ProgressDialog.show(ctx, ctx.getString(R.string.menu_txt_export), ctx.getString(R.string.progress_dialog_avvio), true, false);
	}
	
	@Override
	protected Boolean doInBackground(Boolean... params) {
		Boolean result = Boolean.FALSE;
		Serializer serializer = new Persister();
		Export export = new Export();
		UserDAO dao = new UserDAO();
		Vector<User> list = dao.getListUsers(true);
		export.setUsers(list);
		if(list.size() > 0) {
			allegato = new File(Environment.getExternalStorageDirectory(), "export.xml");
			try {
				serializer.write(export, allegato);
				result = Boolean.TRUE;
			} catch (Exception e) { }
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		try {
			pd.dismiss();
		} catch (Exception e) { }
		Toast.makeText(ctx, (result) ? ctx.getString(R.string.msg_export_ok) : ctx.getString(R.string.msg_export_failed), Toast.LENGTH_SHORT).show();
	}

}
