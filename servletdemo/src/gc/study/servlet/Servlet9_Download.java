package gc.study.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet9")
public class Servlet9_Download extends HttpServlet {
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	//使用HttpServletResponse实现文件下载的功能
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		downloadFileByOutputStream(resp);
	}
	private void downloadFileByOutputStream(HttpServletResponse resp)  throws ServletException, IOException {
		String filePath = this.getServletContext().getRealPath("/main.html");
		String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
		resp.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
		
		InputStream in = new FileInputStream(filePath);
		int len = 0;
		byte[] buffer = new byte[1024];
		OutputStream output = resp.getOutputStream();
		while((len=(in.read(buffer))) > 0) {
			output.write(buffer,0,len);
		}
		in.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
