package org.forumj.web.filter;

import static org.forumj.common.FJServletName.*;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
/**
 * Filter for transcoding {@link String} parameters from ISO-8859-1 to UTF-8 in the {@link ServletRequest}
 * 
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
@WebFilter("/*")
public class AAAAAUtf8Filter implements Filter{

   /**
    * {@inheritDoc}
    */
   public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
      req.setCharacterEncoding("UTF-8");
      chain.doFilter(req, resp);
   }

   /**
    * {@inheritDoc}
    */
   public void destroy() {}

   /**
    * {@inheritDoc}
    */
   public void init(FilterConfig filterConfig) throws ServletException {}
}
