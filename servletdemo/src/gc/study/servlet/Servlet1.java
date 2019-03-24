package gc.study.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * servlet工作流程：
 * 		1.客户端请求servlet
 * 		2.加载servlet到内存
 * 		3.实例化并调用init方法初始化该Servlet（一个servlet对象只会实例化一次，init也只会调用一次）
 * 		4.调用service()方法，service方法在根据请求类型调用doPut，doGet，doPost等
 * 
 */
//这个注解对应的访问url为  http://127.0.0.1:8080/servlet2/Servlet1   (servlet2是工程名， Servlet1为下面注解配置的路径)
@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Servlet1() {
        System.out.println("new servlet1");
    }

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init1");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
