package com.iceloof;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import com.iceloof.Setting;

@Component
public class AuthFilter implements Filter {

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
          throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    HttpSession session = req.getSession();
    Setting setting = new Setting();
    System.out.println("Logging Request " + req.getMethod() + ":" + req.getRequestURI());
    System.out.println("User: " + session.getAttribute("UserName"));
    if(!req.getRequestURI().matches(String.join("|", setting.public_access)) && session.getAttribute("UserName") == null) {
      res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      res.setContentType("application/json;charset=UTF-8");
      PrintWriter responseWriter = response.getWriter();
      responseWriter.write("{\"error\": {\"code\": \"AUTH_REQUIRED\", \"description\": \"User has not been authenticated\"}}");
    } else {
      chain.doFilter(request, response);
    }
    System.out.println("Logging Response :" + res.getContentType());
  }

}