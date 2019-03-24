package gc.study.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class Servlet12_filter_after implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
		System.out.println("filter_after process init");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("filter_after process doFilter");
		//将请求传回过滤链
		chain.doFilter(req, resp);
	}
	@Override
	public void destroy() {
		Filter.super.destroy();
		System.out.println("filter_after process destroy");
	}

}
