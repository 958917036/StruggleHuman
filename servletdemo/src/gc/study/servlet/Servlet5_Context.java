package gc.study.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet5")
public class Servlet5_Context extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config = null;
       
    public Servlet5_Context() {
        super();
        System.out.println("new servlet5");
    }

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("init5");
		this.config = config;
	}
	//获取servlet4传递到servletContext的数据
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		String data = (String) context.getAttribute("data");
		response.getWriter().print("<hr>"+data+"</hr>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
