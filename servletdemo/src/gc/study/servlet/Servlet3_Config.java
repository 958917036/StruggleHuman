package gc.study.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//从这个口进来的请求不会传递web.xml中的初始化参数，注解自成一派
@WebServlet("/Servlet3")
public class Servlet3_Config extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config = null;
       
    public Servlet3_Config() {
        super();
        System.out.println("new servlet3");
    }

	public void init(ServletConfig config) throws ServletException {
		System.out.println("init3");
		this.config = config;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = this.config.getInitParameter("name");
		String age = this.config.getInitParameter("age");
		String param = "name:"+name+" ,age:"+age;
		System.out.println("param:"+param);
		response.getWriter().print("<hr>"+param+"</hr>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
