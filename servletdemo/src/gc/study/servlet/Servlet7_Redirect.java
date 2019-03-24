package gc.study.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet7")
public class Servlet7_Redirect extends HttpServlet {
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	//请求重定向
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//redirectByContext(req, resp);
		redirectByResp(req, resp);
	}
	//使用ServletContextshi实现请求转发（app内部转发），传入的url为当前context下的相对url，即不需要传入项目名的那一层
	private void redirectByContext(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		RequestDispatcher dsp = context.getRequestDispatcher("/Servlet6");
		System.out.println("将请求转发到Servlet6");
		dsp.forward(req, resp);
	}
	//使用HttpServletResponse重定向（需要浏览器访问新的链接），传入的url为绝对url，需要包含项目名那一层，如下servlet
	//http://127.0.0.1:8080/servlet/Servlet8, 即传入的url为相对根路径127.0.0.1的路径
	private void redirectByResp(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		//request.getContextPath 获取的就是项目名，这样比较灵活
		System.out.println("contextpath:"+req.getContextPath());
		//resp.sendRedirect("/servlet/Servlet8");
		resp.sendRedirect(req.getContextPath()+"/Servlet8");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
