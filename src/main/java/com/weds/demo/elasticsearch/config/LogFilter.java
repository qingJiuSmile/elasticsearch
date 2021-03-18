package com.weds.demo.elasticsearch.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 日志过滤器
 */
@WebFilter(urlPatterns = "/22", filterName = "logFilter")
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI().substring(request.getContextPath().length());
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }
        System.out.println("过滤器2 ========" + url);

        filterChain.doFilter(servletRequest, servletResponse);

        /*if (IncludePath.includePath(url) || request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            // swagger用，不需要校验token
            filterChain.doFilter(request, response);
        } else {
            // 防止流读取一次后就没有了, 所以需要将流继续写出去
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            ServletRequest requestWrapper = new MyRequestWrapper(httpServletRequest);
            filterChain.doFilter(requestWrapper, servletResponse);
        }*/
    }

    @Override
    public void destroy() {

    }
}
