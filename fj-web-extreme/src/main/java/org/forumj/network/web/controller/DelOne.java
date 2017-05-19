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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.forumj.common.db.entity.IUser;
import org.forumj.common.db.service.*;
import org.forumj.network.web.FJServletTools;
import org.forumj.network.web.FJUrl;
import org.forumj.network.web.HttpParameters;

/**
 * перенос темы в корзину 
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
public class DelOne{

   public void doGet(HttpServletRequest request, HttpServletResponse response, String userURI) throws Exception {
      StringBuffer buffer = new StringBuffer();
      HttpSession session = request.getSession();
      String idThreadParameter = request.getParameter(HttpParameters.ID);
      String pageParameter = request.getParameter(HttpParameters.PAGE);
      String usrParameter = request.getParameter("usr");
      IUser user = (IUser) session.getAttribute(HttpParameters.USER);
      FolderService service = FJServiceHolder.getFolderService();
      if (user != null && !user.isBanned() && user.isLogined()){
         if (idThreadParameter != null && !"".equals(idThreadParameter)){
            Long idThread = Long.valueOf(idThreadParameter);
            if (user.isModerator() && usrParameter != null && usrParameter.equals("0")){
               UserService userService = FJServiceHolder.getUserService();
               user = userService.readUser(0l);
               service.moveToRecyclebin(idThread, user);
            }else{
               service.moveToRecyclebin(idThread, user);
            }
         }
         StringBuffer buffer1 = new StringBuffer("/").append(userURI).append("/").append(FJUrl.INDEX);
         if (pageParameter != null && !pageParameter.isEmpty()){
            buffer1.append("?").append(HttpParameters.PAGE).append("=").append(pageParameter);
         }
         response.sendRedirect(buffer1.toString());
      }else{
         // unlogined
         response.sendRedirect(new StringBuffer("/").append(userURI).append("/").append(FJUrl.INDEX).toString());
      }
      response.setContentType("text/html; charset=UTF-8");
      response.getWriter().write(buffer.toString());
   }
}