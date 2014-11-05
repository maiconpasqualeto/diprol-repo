/**
 * 
 */
package br.com.sixinf.diprol;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author maicon
 *
 */
public class DiprolHelper {
	private static final NumberFormat moneyFormatter;
	static {
	    moneyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	    moneyFormatter.setRoundingMode(RoundingMode.HALF_EVEN);
	}
	
	public static NumberFormat getMoneyFormatterInstance(){
		return moneyFormatter;
	}
}
