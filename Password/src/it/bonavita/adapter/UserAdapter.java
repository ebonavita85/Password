package it.bonavita.adapter;

import it.bonavita.controller.Controller;
import it.bonavita.entity.User;
import it.bonavita.password.R;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserAdapter extends ArrayAdapter<User> {

	private Activity context;
	private final List<User> list;
	 
	public UserAdapter(Activity context, List<User> list) {
		super(context, R.layout.elenco_password_item, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected TextView txt_nome, 
						   txt_password;
		protected ImageView btnView, btnEdit;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		final ViewHolder viewHolder; 
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.elenco_password_item, null);
			viewHolder = new ViewHolder();
			viewHolder.txt_nome = (TextView) view.findViewById(R.id.nome);
			viewHolder.txt_password = (TextView) view.findViewById(R.id.password);
			viewHolder.btnView = (ImageView) view.findViewById(R.id.btnView);
			viewHolder.btnEdit = (ImageView) view.findViewById(R.id.btnEdit);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			User c = list.get(position);
			((ViewHolder) view.getTag()).txt_nome.setTag(c);
			((ViewHolder) view.getTag()).txt_password.setTag(c);
			((ViewHolder) view.getTag()).btnView.setTag(c);
			((ViewHolder) view.getTag()).btnEdit.setTag(c);
			viewHolder = (ViewHolder) view.getTag();
		}
		final User c = list.get(position);
		if(c.getNome() != null)
			viewHolder.txt_nome.setText(c.getNome());
		/*if(c.getPassword() != null)
			viewHolder.txt_password.setText(c.getPassword());*/
		viewHolder.btnView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Controller().gestPassword(v.getContext(), c, null, false);
			}
		});
		viewHolder.btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Controller().gestPassword(v.getContext(), c, null, true);
			}
		});
		return view;
	}

}
