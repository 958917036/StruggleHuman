package gc.study.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet4")
public class Servlet4_Context extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config = null;
       
    public Servlet4_Context() {
        super();
        System.out.println("new servlet4");
    }

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("init4");
		this.config = config;
	}
	//通过servletContext传递变量data，先访问：
	//http://127.0.0.1:8080/servlet/Servlet4,再访问：http://127.0.0.1:8080/servlet/Servlet5
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		context.setAttribute("data", "helloworld");
		response.getWriter().print("<hr>"+"set data"+"</hr>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
