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
package org.forumj.web.servlet.post;

import static org.forumj.tool.Diletant.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.forumj.common.*;
import org.forumj.common.db.entity.IUser;
import org.forumj.common.db.service.*;
import org.forumj.web.servlet.FJServlet;

/**
 * 
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/" + FJUrl.DELETE_MAIL}, name = FJServletName.DELETE_MAIL)
public class DelMail extends FJServlet {

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      StringBuffer buffer = new StringBuffer();
      try {
         HttpSession session = request.getSession();
         String idParameter = request.getParameter("id");
         String actionParameter = request.getParameter("ACT");
         IUser user = (IUser) session.getAttribute("user");
         MailService mailService = FJServiceHolder.getMailService();
         if (user != null && !user.isBanned() && user.isLogined()){
            if (actionParameter != null && !"".equals(actionParameter)){
               String nrwParameter = request.getParameter("NRW");
               Integer nrw = Integer.valueOf(nrwParameter);
               if ("del".equalsIgnoreCase(actionParameter)){
                  Integer id = Integer.valueOf(idParameter);
                  for (int nrwIndex = 0; nrwIndex < nrw; nrwIndex++) {
                     String mailIdParameter = request.getParameter(String.valueOf(nrwIndex));
                     if (mailIdParameter != null){
                        Long mailId = Long.valueOf(mailIdParameter);
                        switch(id) {
                        case 2:
                           mailService.deleteMailFromInbox(mailId, user);
                           break;
                        case 4:
                           mailService.deleteMailFromOutbox(mailId, user);
                           break;
                        }      
                     }
                  }
               }
            }
            buffer.append(successPostOut("0", FJUrl.SETTINGS + "?id=" + idParameter));
         }else{
            // Вошли незарегистрировавшись
            buffer.append(unRegisteredPostOut());
         }
      } catch (Throwable e) {
         buffer = new StringBuffer();
         buffer.append(errorOut(e));
         e.printStackTrace();
      }
      response.setContentType("text/html; charset=UTF-8");
      response.getWriter().write(buffer.toString());
   }
}
