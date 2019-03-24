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

@WebServlet("/Servlet11")
public class Servlet11_Request_Forward extends HttpServlet {
	@Override
	public void init() throws ServletException {
		super.init();
	}
	//转发请求，即一个web资源收到客户端请求时，通知服务器去调用另外一个web资源进行处理，307
	//请求重定向：一个web资源收到客户端请求时，通知浏览器去访问另外一个web资源进行处理，302
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//请求转发
		req.getRequestDispatcher("/Servlet2").forward(req, resp);
	}
	private void getParam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
