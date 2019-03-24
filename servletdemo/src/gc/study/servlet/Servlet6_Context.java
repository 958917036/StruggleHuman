package gc.study.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet6")
public class Servlet6_Context extends HttpServlet {
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	//获取web.xml中配置的servletContext的参数
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doGet6");
		ServletContext context = this.getServletContext();
		String url = context.getInitParameter("url");
		resp.getWriter().print(url);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
