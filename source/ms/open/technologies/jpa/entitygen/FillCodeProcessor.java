package ms.open.technologies.jpa.entitygen;
import java.util.HashMap;
import java.util.Iterator;
/**
 * 2015-1-14
 * @author Bruce Li
 */
public class FillCodeProcessor {
	public String fillCodeToString(StringBuilder resourceCode,HashMap<String,String> fillMap){
		String resourceCodeStr = resourceCode.toString();
		Iterator<String> keyIterator = fillMap.keySet().iterator();
		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			resourceCodeStr = resourceCodeStr.replaceAll("\\$\\{"+key+"\\}", fillMap.get(key));
		}
		return resourceCodeStr;
	}
	public StringBuilder fillCodeToStringBuilder (StringBuilder resourceCode,HashMap<String,String> fillMap){
		return new StringBuilder(fillCodeToString(resourceCode,fillMap));
	}
}
