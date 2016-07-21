package it.bonavita.password;

import it.bonavita.adapter.UserAdapter;
import it.bonavita.controller.Controller;
import it.bonavita.controller.ExportTask;
import it.bonavita.controller.ImportTask;
import it.bonavita.dao.UserDAO;
import it.bonavita.entity.User;
import it.bonavita.utils.ASCostants;
import it.bonavita.utils.FileUtils;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ElencoPasswordActivity extends Activity {

	private ListView listView;
	private TextView txt_no_password;
	private UserAdapter adapter;
	private Vector<User> list;
	private static final int IMPORT_FROM_FILE = 6384;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elenco_activity);
		txt_no_password = (TextView) findViewById(R.id.txt_no_password);
		UserDAO userDAO = new UserDAO();
		this.list = userDAO.getListUsers(false);
		adapter = new UserAdapter(ElencoPasswordActivity.this, this.list);
        listView = (ListView) findViewById(R.id.ListPassword);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
        	@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int pos, long id) {
        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ElencoPasswordActivity.this);
        		alertDialogBuilder.setTitle(R.string.title_remove_password)
        		.setMessage(R.string.msg_remove_password)
        		.setPositiveButton(android.R.string.ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						User user = adapter.getItem(pos);
						UserDAO userDAO = new UserDAO();
						userDAO.deleteUser(user);
						updateListView();
					}
				})
				.setNegativeButton(android.R.string.cancel, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
        		alertDialogBuilder.create().show();
        		return false;
			}
		});
        listView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> parent, View view, final int pos, long id) {
        		//new Controller().gestPassword(ElencoPasswordActivity.this, adapter.getItem(pos));
			}
		});
        manageTxtView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.elenco_activity, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add:
			new Controller().gestPassword(ElencoPasswordActivity.this, null, new Runnable() {
				@Override
				public void run() {
					updateListView();
				}
			}, true);
			break;
		case R.id.menu_codice_sblocco:
			new Controller().gestCodiceSblocco(ElencoPasswordActivity.this, ASCostants.codice);
			break;
		case R.id.menu_export:
			doExport();
			break;
		case R.id.menu_import:
			showChooser();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		UserDAO userDAO = new UserDAO();
		this.list = userDAO.getListUsers(false);
		adapter = ((UserAdapter) listView.getAdapter());
		adapter.clear();
		adapter.addAll(list);
		manageTxtView();
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(ASCostants.db != null)
			ASCostants.db.close();
	}
	
	private void updateListView() {
		UserDAO userDAO = new UserDAO();
		this.list = userDAO.getListUsers(false);
		adapter.clear();
		adapter.addAll(list);
		manageTxtView();
		adapter.notifyDataSetChanged();
	}

	private void manageTxtView() {
		if(this.list.size() == 0)
			txt_no_password.setVisibility(View.VISIBLE);
		else
			txt_no_password.setVisibility(View.GONE);
	}
	
	private void doExport() {
		new ExportTask(ElencoPasswordActivity.this).execute();
	}
	
	private void showChooser() {
		Intent target = FileUtils.createGetContentIntent();
		Intent intent = Intent.createChooser(target, getString(R.string.chooser_title));
		try {
			startActivityForResult(intent, IMPORT_FROM_FILE);
		} catch (ActivityNotFoundException e) { }				
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case IMPORT_FROM_FILE:	
			if (resultCode == RESULT_OK) {
				if (data != null) {
					final Uri uri = data.getData();
					new ImportTask(FileUtils.getFile(uri), ElencoPasswordActivity.this, new Runnable() {
						@Override
						public void run() {
							updateListView();
						}
					}).execute();
				}
			} 
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
