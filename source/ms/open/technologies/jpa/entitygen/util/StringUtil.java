package ms.open.technologies.jpa.entitygen.util;
/**
 * 2014-11-12
 * @author Bruce Li
 */
public class StringUtil {
	public static int getStartIndex(String str,String targStr){
		return str.indexOf(targStr);
	}
	public static int getEndIndex(String str,String targStr){
		return str.indexOf(targStr) + targStr.length() - 1;
	}
	public static String toUpperCaseFirst(String str){
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}
	public static String[] splitTrime(String str,String ajx){
		str = str.replaceAll(" ","");
		return str.split(ajx);
	}
}
