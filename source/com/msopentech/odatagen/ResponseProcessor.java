package com.msopentech.odatagen;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.msopentech.odatagen.core.processor.MetadataToInfoProcessor;
import com.msopentech.odatagen.exception.EntitygenException;
import com.msopentech.odatagen.infos.PathInfo;
import com.msopentech.odatagen.infos.ResponseInfo;
import com.msopentech.odatagen.util.StringUtil;


/**
 * 2015-1-7
 * @author Bruce Li
 */
public class ResponseProcessor {
	private String COM_MSOPENTECH_ODATA_EXPOSETABLE = "COM_MSOpenTech_OData_ExposeTable";
	private static String JPA_ENTITYGEN_CONTROLLER_HTML_FILE = "JpaEntitygenControllerHtml.model";
	private static final String PERSISTENCEUNITNAME_PEREMNAME = "persistence.UnitName";
	private static final String PERSISTENCECLASSPATH_PEREMNAME = "persistence.ClassPath";
	private String NO_TABLES_KEY = "no_exposeTables";
	private String EXPOSE_TABLES_KEY = "exposeTables";
	private String URL_KEY = "url";
	private String RESPONSESTATE_OK = "OK";
	private String RESPONSESTATE_ERROR = "ERROR";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletConfig servletConfig;
	private ResponseInfo responseInfo;
	private PathInfo pathInfo;
	public ResponseProcessor(HttpServletRequest request,HttpServletResponse response,ServletConfig servletConfig){
		this.responseInfo = new ResponseInfo();
		this.request = request;
		this.response = response;
		this.servletConfig = servletConfig;
		pathInfo = createPathInfo(request);
	}
	
	public void entityGenerate(String exposeTableNames) throws IOException{
		try {
			EntityProcessor entityProcessor = new EntityProcessor(pathInfo);
			/* Update Open_Technologies_JPA_ExposeTable */
			entityProcessor.upOpen_Technologies_JPA_ExposeTable(exposeTableNames);
			/* Entity generate */
			entityProcessor.entityGenerate();
			setResponseInfoSuccessfully();
		} catch (Exception e) {
			setResponseInfoError(e.getMessage());
		}
		ptintJSON(getResponseInfoAsJson());
	}
	
	protected void setResponseInfo(String responseState,String responseMessage){
		this.responseInfo.setResponseState(responseState).setResponseMessage(responseMessage);
	}
	
	protected PathInfo createPathInfo(HttpServletRequest request) {
		@SuppressWarnings("deprecation")
		String realPath = request.getRealPath("");
		String sourcePath = this.getClass().getResource("/").getPath();
		String applicationToSourcePath = sourcePath
				.substring(
						StringUtil.getEndIndex(sourcePath, realPath
								.substring(realPath.lastIndexOf("\\") + 1)) + 2,
						sourcePath.length() - 1);

		/* Init PERSISTENCEUNITNAME_PEREMNAME and PERSISTENCECLASSPATH_PEREMNAME */
		String persistenceUnitName = servletConfig
				.getInitParameter(PERSISTENCEUNITNAME_PEREMNAME);
		String persistenceClassPath = servletConfig
				.getInitParameter(PERSISTENCECLASSPATH_PEREMNAME);
		if (persistenceUnitName == null) {
			throw new EntitygenException(" ServletConfig parameter name "
					+ PERSISTENCEUNITNAME_PEREMNAME +" is empty");
		}
		
		PathInfo pathInfo = new PathInfo();
		pathInfo.setRealPath(realPath);
		pathInfo.setApplicationToSourcePath(applicationToSourcePath);
		pathInfo.setPersistenceUnitName(persistenceUnitName);
		if(persistenceClassPath!=null){
			pathInfo.setPersistenceClassPath(persistenceClassPath);
		}
		return pathInfo;
	}

	public void printJpaEntitygenControllerHtml() throws UnsupportedEncodingException, IOException {
		/* Select Open_Technologies_JPA_ExposeTable */
		Map<String,String> optionMap = getOptionMap();
		/* Get url */
		String url = request.getRequestURL().toString();
		StringBuilder htmlSB = new ResourceProcessor().getResourceAsStringBuilder(JPA_ENTITYGEN_CONTROLLER_HTML_FILE);
		/* Fill JpaEntitygenControllerHtml */
		HashMap<String,String> fillMap = new HashMap<String,String>();
		fillMap.put(NO_TABLES_KEY, optionMap.get(NO_TABLES_KEY));
		fillMap.put(EXPOSE_TABLES_KEY, optionMap.get(EXPOSE_TABLES_KEY));
		fillMap.put(URL_KEY, url);
		String html = new FillCodeProcessor().fillCodeToString(htmlSB, fillMap);
		ptintHTML(html);
	}
	private Map<String,String> getOptionMap() {
		StringBuilder allTableOptionSB = new StringBuilder();
		Map<String, List<String>> tableMap;
		try {
			tableMap = new MetadataToInfoProcessor().getAlltablesAndExposeTables(pathInfo.getPersistenceUnitName());
		} catch (SQLException e) {
			throw new EntitygenException(e.getMessage());
		}
		Map<String,String> optionMap = new HashMap<String,String>();
		
		StringBuilder exposeTableOptionSB = new StringBuilder();
		List<String> allTables = tableMap.get(NO_TABLES_KEY);
		List<String> exposeTables = tableMap.get(EXPOSE_TABLES_KEY);
		for(int i=0;exposeTables!=null&&i<exposeTables.size();i++){
			String tableName = exposeTables.get(i);
			allTables.remove(tableName);//All table names do not need have exposes table name
			exposeTableOptionSB.append("<option value='"+tableName+"'>"+tableName+"</option>");
		}
		optionMap.put(EXPOSE_TABLES_KEY, exposeTableOptionSB.toString());
		for(int i=0;allTables!=null&&i<allTables.size();i++){
			String tableName = allTables.get(i);
			/* If is table Open_Technologies_JPA_ExposeTable?so do not add this name */
			if(!COM_MSOPENTECH_ODATA_EXPOSETABLE.toLowerCase().equals(tableName.toLowerCase())){
			    allTableOptionSB.append("<option value='"+tableName+"'>"+tableName+"</option>");
			}
		}
		optionMap.put(NO_TABLES_KEY, allTableOptionSB.toString());
		return optionMap;
	}
	
	protected String getResponseInfoAsJson(){
		return "{\"responseState\":\""+responseInfo.getResponseState()+"\",\"responseMessage\":\""+responseInfo.getResponseMessage()+"\"}";
	}
	
	protected void setResponseInfoSuccessfully(){
		String RESPONSEMESSAGE_OK = "OData generate successfully!Please restart the service ["+request.getServletContext().getContextPath()+"] now!";
		setResponseInfo(RESPONSESTATE_OK,RESPONSEMESSAGE_OK);
	}
	protected void setResponseInfoError(String errorMsg){
		setResponseInfo(RESPONSESTATE_ERROR,errorMsg);
	}
	protected void ptintHTML(String html) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(html);
	}
	protected void ptintJSON(String json) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(json);
	}
}
