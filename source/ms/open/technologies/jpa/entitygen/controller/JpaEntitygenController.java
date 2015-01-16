package ms.open.technologies.jpa.entitygen.controller;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ms.open.technologies.jpa.entitygen.ResponseProcessor;
/**
 * 
 * 2015-1-14
 * @author Bruce Li
 */
public class JpaEntitygenController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig servletConfig;
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		this.servletConfig = servletConfig;
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ResponseProcessor responseProcessor = new ResponseProcessor(request,response,this.servletConfig);
		String exposeTableNames = request.getParameter("exposeTableNames");
		if(exposeTableNames != null){
			/* Response create jpa entities result  */
			responseProcessor.entityGenerate(exposeTableNames);
		}else {
			/* Response JpaEntitygenControllerHtml  */
			responseProcessor.printJpaEntitygenControllerHtml();
		}
		
	}
}
