package util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;


public class DateUtil {
	public static boolean ifTime(String timeStart,String timeEnd){
		String nowData = new SimpleDateFormat("HH-mm").format(new Date());
		return nowData.equals(timeStart)?true:nowData.equals(timeEnd);
	}
	public static Timestamp getTimestamp(){
		return new Timestamp(new Date().getTime());
	}
	
	public static void main(String[] args) {
		
		System.out.println(new DateUtil().getTimestamp());
	}
}
