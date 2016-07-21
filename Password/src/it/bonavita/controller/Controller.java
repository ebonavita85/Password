package it.bonavita.controller;

import it.bonavita.dao.CodiceSbloccoDAO;
import it.bonavita.dao.UserDAO;
import it.bonavita.entity.CodiceSblocco;
import it.bonavita.entity.User;
import it.bonavita.password.R;
import it.bonavita.utils.ASCostants;
import it.bonavita.utils.ASDataFornat;
import it.bonavita.utils.Utility;

import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Controller {

	public void gestPassword(final Context ctx, final User user, final Runnable run, Boolean edit) {
		LayoutInflater li = LayoutInflater.from(ctx);
		View promptsView = li.inflate((edit) ? R.layout.prompts : R.layout.prompts_view, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
		//alertDialogBuilder.setTitle(R.string.txt_password);
		alertDialogBuilder.setView(promptsView);
		Utility.hideTastiera(ctx, promptsView);
		final TextView nomeInput;
		final TextView pswInput; 
		if(edit) {
			nomeInput = (EditText) promptsView.findViewById(R.id.editTextDialogNomeInput);
			pswInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
			alertDialogBuilder.setPositiveButton(R.string.btn_salva, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					String nome = nomeInput.getText().toString();
					String psw = pswInput.getText().toString();
					boolean success = false;
					if(!nome.isEmpty() && !psw.isEmpty()) {
						UserDAO dao = new UserDAO();
						if(user != null) {
							user.setNome(nome);
							user.setPassword(psw);
							user.setData(ASDataFornat.dateToString(new Date()));
							success = dao.updateUser(user, true);
							Toast.makeText(ctx, (success) ? ctx.getString(R.string.toast_update_ok) : ctx.getString(R.string.toast_update_failed), Toast.LENGTH_LONG).show();
						}
						else {
							User tmp = null;
							tmp = new User();
							tmp.setNome(nome);
							tmp.setPassword(psw);
							tmp.setData(ASDataFornat.dateToString(new Date()));
							success = dao.insertUser(tmp, true);
							Toast.makeText(ctx, (success) ? ctx.getString(R.string.toast_insert_ok) : ctx.getString(R.string.toast_insert_failed), Toast.LENGTH_LONG).show();
						}
						if(success && run != null)
							run.run();
					}
				}
			});
			alertDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
		}
		else {
			nomeInput = (TextView) promptsView.findViewById(R.id.editTextDialogNomeInput);
			pswInput = (TextView) promptsView.findViewById(R.id.editTextDialogUserInput);
			alertDialogBuilder.setNegativeButton(android.R.string.ok, null);
		}
		if(user != null) {
			nomeInput.setText(user.getNome());
			pswInput.setText(user.getPassword());
		}
		alertDialogBuilder.create().show();
	}
	
	public void gestCodiceSblocco(final Context ctx, final CodiceSblocco codice) {
		LayoutInflater li = LayoutInflater.from(ctx);
		View promptsView = li.inflate(R.layout.prompts, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
		alertDialogBuilder.setTitle(R.string.txt_codice_sblocco);
		alertDialogBuilder.setView(promptsView);
		final TextView textView2 = (TextView) promptsView.findViewById(R.id.textView2);
		final EditText nomeInput = (EditText) promptsView.findViewById(R.id.editTextDialogNomeInput);
		final TextView textView1 = (TextView) promptsView.findViewById(R.id.textView1);
		final EditText codiceInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
		textView1.setText(String.format(ctx.getString(R.string.message_codice_sblocco), String.valueOf(CodiceSblocco.MAX_LEN_CODICE_SBLOCCO)));
		textView1.setTextSize(11);
		textView2.setVisibility(View.GONE);
		nomeInput.setVisibility(View.GONE);
		codiceInput.setFilters(new InputFilter[] {new InputFilter.LengthFilter(CodiceSblocco.MAX_LEN_CODICE_SBLOCCO)});
		if(codice != null) {
			codiceInput.setText(codice.getCodice());
		}
		alertDialogBuilder.setPositiveButton(R.string.btn_salva, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				String cod = codiceInput.getText().toString();
				boolean success = false;
				if(!cod.isEmpty()) {
					CodiceSbloccoDAO dao = new CodiceSbloccoDAO();
					if(codice != null) {
						codice.setCodice(cod);
						codice.setData(ASDataFornat.dateToString(new Date()));
						success = dao.updateCode(codice);
						Toast.makeText(ctx, (success) ? ctx.getString(R.string.toast_update_ok) : ctx.getString(R.string.toast_update_failed), Toast.LENGTH_LONG).show();
						ASCostants.codice = codice;
					}
					else {
						CodiceSblocco tmp = null;
						tmp = new CodiceSblocco();
						tmp.setCodice(cod);
						tmp.setData(ASDataFornat.dateToString(new Date()));
						success = dao.insertCode(tmp);
						ASCostants.codice = tmp;
						Toast.makeText(ctx, (success) ? ctx.getString(R.string.toast_insert_ok) : ctx.getString(R.string.toast_insert_failed), Toast.LENGTH_LONG).show();
					}
					
				}
			}
		});
		final AlertDialog d = alertDialogBuilder.create();
		codiceInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				final Button okButton = d.getButton(AlertDialog.BUTTON_POSITIVE);
		        if(s.length() == CodiceSblocco.MAX_LEN_CODICE_SBLOCCO) {
		            okButton.setEnabled(true);
		        } else {
		            okButton.setEnabled(false);
		        }
			}
		});
		d.show();
		d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
	}
}
