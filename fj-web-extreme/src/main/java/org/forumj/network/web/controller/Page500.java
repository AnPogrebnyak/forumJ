/*
 * Copyright Andrew V. Pogrebnyak
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.forumj.network.web.controller;

import org.forumj.common.exception.InvalidKeyException;
import org.forumj.network.web.FJServletTools;
import org.forumj.network.web.resources.LocaleString;
import org.forumj.network.web.resources.ResourcesBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
public class Page500 {

   public void doGet(HttpServletRequest request, HttpServletResponse response, String webapp, Throwable exception) throws ServletException {
      try {
         StringBuffer buffer = new StringBuffer();
         HttpSession session = request.getSession();
         LocaleString locale = (LocaleString) session.getAttribute("locale");
         FJServletTools.cache(response);
         buffer.append("<!doctype html public \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
         buffer.append("<html>");
         buffer.append("<head>");
         buffer.append("<meta http-equiv='content-type' content='text/html; charset=UTF-8'>");
         /*Стили*/
         buffer.append("<title>");
            buffer.append(ResourcesBuilder.getStyleCSS(webapp));
         try {
            buffer.append(locale.getString("MSG_ERROR_PAGE_TITLE"));
         } catch (InvalidKeyException e) {
            buffer.append("Error");
         }
         buffer.append("</title>");
         buffer.append("</head>");
         /*Цвет фона страницы*/
         buffer.append("<body bgcolor=#EFEFEF>");
         /*Главная таблица*/
         buffer.append("<table border='0' style='border-collapse: collapse' width='100%'>");
         /*Таблица с лого и верхним баннером*/
         buffer.append(FJServletTools.logo(webapp));
         // Error
         buffer.append("<tr>");
         buffer.append("<td><div class='messageDiv'>");
         buffer.append("<b>").append(exception.getMessage()).append("</b><br />");
         StackTraceElement[] elements = exception.getStackTrace();
         for (int i = 0; i < elements.length; i++) {
            StackTraceElement element = elements[i];
            buffer.append(element.toString()).append("<br />");
         }
         Throwable causedBy = exception.getCause();
         while (causedBy != null && causedBy != exception){
            buffer.append("<br /><br />");
            buffer.append("<b>caused by</b><br />");
            buffer.append("<b>").append(causedBy.getMessage()).append("</b><br />");
            elements = causedBy.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
               StackTraceElement element = elements[i];
               buffer.append(element.toString()).append("<br />");
            }
            exception = causedBy;
            causedBy = exception.getCause();
         }
         buffer.append("</div></td>");
         buffer.append("</tr>");
         buffer.append("</body>");
         buffer.append("</html>");
         response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         response.setContentType("text/html; charset=UTF-8");
         PrintWriter writer = response.getWriter();
         String out = buffer.toString();
         writer.write(out);
      } catch (IOException e) {
         throw new ServletException(e);
      }
   }

}
