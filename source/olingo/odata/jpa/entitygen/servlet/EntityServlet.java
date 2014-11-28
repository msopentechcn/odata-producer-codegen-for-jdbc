/**
 * 
 */
package olingo.odata.jpa.entitygen.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import olingo.odata.jpa.entitygen.EntityProcessor;
import olingo.odata.jpa.entitygen.infos.PathInfo;
import olingo.odata.jpa.entitygen.util.StringUtil;

/**
 * 2014-11-13
 * 
 * @author Bruce Li
 */
public class EntityServlet extends HttpServlet {
	private static final String PERSISTENCEUNITNAME_PEREMNAME = "persistence.UnitName";
	private static final String PERSISTENCECLASSPATH_PEREMNAME = "persistence.ClassPath";
	private ServletConfig servletConfig;
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		this.servletConfig = servletConfig;
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EntityProcessor modelReadAndWriteProcessor = new EntityProcessor();
		PathInfo pathInfo = createPathInfo(request);
		try {
			modelReadAndWriteProcessor.creatModel(pathInfo);
		} catch (Exception e) {
			errorResponse(response, e.getMessage());
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println("  STATE:OK</br>");
		out.print("  Application["
				+ request.getContextPath()
				+ "] auto create entity is successed.Please manually restart the application!");
		
		out.println("  </BODY>");
		out.println("</HTML>");
        out.flush();
		out.close();
	}
	protected PathInfo createPathInfo(HttpServletRequest request) {
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
			throw new RuntimeException(" ServletConfig parameter name "
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
	protected void errorResponse(HttpServletResponse response,
			String errorMessage) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println("  STATE:ERROR</br>");
		out.println("  " + errorMessage);
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}
