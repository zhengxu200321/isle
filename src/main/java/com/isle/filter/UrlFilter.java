package com.isle.filter;

import com.isle.pojo.Url;
import com.isle.pojo.User;
import org.apache.http.HttpRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UrlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String uri = request.getRequestURI();
        if(uri.contains("login")){
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            System.out.println(ip+"--->"+uri);
        }



        HttpSession session = request.getSession();
        if(uri.endsWith(".js")||uri.endsWith(".css")||uri.endsWith(".html")||uri.endsWith(".jpg")||uri.endsWith(".png")||uri.endsWith(".gif")||uri.endsWith(".woff")||uri.endsWith(".woff2")||uri.endsWith(".ttf")||uri.endsWith(".svg")||uri.endsWith(".eot")||uri.contains("dist")){
            filterChain.doFilter(request, ((HttpServletResponse)servletResponse));
        }else {
//            System.out.println("===>"+uri);
            if(uri.equals("/manageisle/login")||uri.equals("/manageisle/adminjsp/login.jsp")||uri.equals("/manageisle/loginout")||uri.contains("cmd")){
                filterChain.doFilter(request, servletResponse);
            }else {
                Object obj = session.getAttribute("user");
                if(obj!=null){
                    List<Url> listUrl = (List<Url>) session.getAttribute("allurl");
                    boolean isExists=false;
                    for (Url url : listUrl) {
                        if(url.getUrl().equals(uri)){
                            isExists = true;
                        }
                    }
                    //如果该url需要进行权限控制
                    if(isExists){
                        User users = (User) obj;
                        boolean isRight = false;
                        if(users.getUrl()==null){
                            //清除sessin中内容
                            session.removeAttribute("user");
                            session.removeAttribute("allurl");
                            session.setAttribute("err_msg","您不具有该项功能;请重新登陆哦");
                            ((HttpServletResponse)servletResponse).sendRedirect("/manageisle/adminjsp/login.jsp");
                        }else {
                            for (Url url : users.getUrl()) {
                                if(url.getUrl().equals(uri)){
                                    isRight = true;
                                }
                            }
                            //登录用户对该uri具有访问权限
                            if(isRight){
                                filterChain.doFilter(request, ((HttpServletResponse)servletResponse));
                            }else{
                                //清除sessin中内容
                                session.removeAttribute("user");
                                session.removeAttribute("allurl");
                                session.setAttribute("err_msg","您不具有该项功能;请重新登陆哦");
                                ((HttpServletResponse)servletResponse).sendRedirect("/manageisle/adminjsp/login.jsp");
                            }
                        }

                    }else{
                        filterChain.doFilter(request, ((HttpServletResponse)servletResponse));
                    }
                }else {
                    session.setAttribute("err_msg", "用户未登录");
                    ((HttpServletResponse)servletResponse).sendRedirect("/manageisle/adminjsp/login.jsp");
                }
            }

        }

    }

    @Override
    public void destroy() {

    }
}
