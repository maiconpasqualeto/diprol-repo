/**
 * 
 */
package br.com.sixinf.diprol;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author maicon
 *
 */
public class DiprolHelper {
	private static final NumberFormat moneyFormatter;
	private static final NumberFormat numFormat;
	static {
	    moneyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	    moneyFormatter.setRoundingMode(RoundingMode.HALF_EVEN);
	    
	    numFormat = NumberFormat.getIntegerInstance(new Locale("pt", "BR"));	    
	}
	
	public static NumberFormat getMoneyFormatterInstance(){
		return moneyFormatter;
	}
	
	public static NumberFormat getIntegerFormatterInstance(){
		return numFormat;
	}
	
	public static Connection getConnection() throws SQLException, NamingException {
		
		InitialContext initialContext = new InitialContext();
		DataSource ds = (DataSource)initialContext.lookup(
		    "java:comp/env/jdbc/DiprolDS");
		Connection conn = ds.getConnection();
		
		/*Class.forName( "org.postgresql.Driver" );
		String url = "jdbc:postgresql:abordagem";
		Properties props = new Properties( );
		props.setProperty( "user", "postgres" );
		props.setProperty( "password", "rs232c" );
		return DriverManager.getConnection( url, props );*/
		
		return conn;
	}
}
