package it.bonavita.utils;

import java.util.Random;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utility {
	public int getRandomIndex(int min, int max) {
		Random r = new Random();
		int i1 = r.nextInt(max - min + 1) + min;
		return i1;
	}
	
	/**
	 * Costruisci una stringa di tanti '?' quanto la dimensione indicata.
	 * Con size pari a 3 costruisce una stringa "?,?,?".
	 * Da usare per definire query con clausula IN
	 */
	public static String makePlaceholder(int size) {
		StringBuffer sb = new StringBuffer();
		for(int i=1; i<=size;i++) {
			sb.append("?");
			if(i!=size) sb.append(",");
		}
		return sb.toString();
	}

	/**
	 * Funzione di utilità per nascondere la tastiera programmaticamente
	 * @param ctx
	 * @param view
	 */
	public static void hideTastiera(Context ctx, View view) {
		InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
	}
}
