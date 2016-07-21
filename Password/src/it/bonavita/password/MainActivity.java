package it.bonavita.password;

import it.bonavita.controller.Controller;
import it.bonavita.dao.CodiceSbloccoDAO;
import it.bonavita.database.MyDatabase;
import it.bonavita.entity.CodiceSblocco;
import it.bonavita.utils.ASCostants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		login = (EditText) findViewById(R.id.login);
		ASCostants.db = new MyDatabase(getApplicationContext());
		init();
		login.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(ASCostants.codice != null && s.length() == ASCostants.codice.getCodice().length() && s.toString().equals(ASCostants.codice.getCodice())) {
					Intent i = new Intent(MainActivity.this, ElencoPasswordActivity.class);
					startActivity(i);
				}
				else if(ASCostants.codice == null) {
					init();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
			
			@Override
			public void afterTextChanged(Editable s) { }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*switch (item.getItemId()) {
		case R.id.menu_add:
			new Controller().gestPassword(MainActivity.this, null, null, true);
			break;
		}*/
		return super.onOptionsItemSelected(item);
	}

	private void init() {
		CodiceSbloccoDAO dao = new CodiceSbloccoDAO();
		CodiceSblocco cs = dao.getCodice();
		if(cs == null || cs.getCodice() == null) {
			Controller control = new Controller();
			control.gestCodiceSblocco(MainActivity.this, null);
		}
		else
			ASCostants.codice = cs;
	}

}
