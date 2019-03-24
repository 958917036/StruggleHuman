package gc.study.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet8")
public class Servlet8_Encode extends HttpServlet {
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	//发送中文时在响应头中指明中文编码，方便浏览器解析
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String data = "我最帅，我用UTF-8，you too";
		OutputStream output = resp.getOutputStream();
		resp.setHeader("content-type", "text/html;charset=UTF-8");
		byte[] datas = data.getBytes("UTF-8");
		output.write(datas);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
