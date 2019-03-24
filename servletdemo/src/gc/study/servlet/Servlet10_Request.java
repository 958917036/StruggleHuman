package gc.study.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet10")
public class Servlet10_Request extends HttpServlet {
	@Override
	public void init() throws ServletException {
		super.init();
	}
	//访问地址（获取参数）：http://127.0.0.1:8080/servlet/test.jsp
	//或者直接在url中携带参数：http://127.0.0.1:8080/servlet/Servlet10?userid=1111&username=%E8%AF%B7%E8%BE%93%E5%85%A5%E7%94%A8%E6%88%B7%E5%90%8D&sex=%E7%94%B7&inst=%E8%B7%B3%E8%88%9E&inst=%E7%BC%96%E7%A8%8B&note=+++++&hiddenField=hiddenvalue
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getAddr(req);
		getParam(req,resp);
	}
	private void getParam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		String userid = req.getParameter("userid");
		String username = req.getParameter("username");
		String[] interests = req.getParameterValues("inst");
		String inst = "";
		for (int i = 0; i < interests.length; i++) {
			inst += interests[i] +", ";
		}
		String s = "userid:"+userid+" ,username:"+username+" ,interests:"+inst;
		resp.getOutputStream().write(s.getBytes("UTF-8"));
	}
	private void getAddr(HttpServletRequest req) {
		String url = req.getRequestURL().toString();
		String uri = req.getRequestURI();
		String remoteAddr = req.getRemoteAddr();
		String remoteHost = req.getRemoteHost();
		String remoteUser = req.getRemoteUser();
		int remotePort = req.getRemotePort();
		System.out.println("url:"+url);
		System.out.println("uri:"+uri);
		System.out.println("remoteAddr:"+remoteAddr);
		System.out.println("remoteHost:"+remoteHost);
		System.out.println("remoteUser:"+remoteUser);
		System.out.println("remotePort:"+remotePort);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
