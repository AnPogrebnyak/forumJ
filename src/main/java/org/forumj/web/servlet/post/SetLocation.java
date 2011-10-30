/*
 * Copyright (c) 2011
 * Andrew V. Pogrebnyak
 * All rights reserved.
 *
 * This software is distributed under GNU General Public License Version 2.0
 * You shall use it and distribute only in accordance with the terms of the 
 * License Agreement.
 */
package org.forumj.web.servlet.post;

import static org.forumj.tool.Diletant.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.forumj.common.*;
import org.forumj.db.dao.UserDao;
import org.forumj.db.entity.User;
import org.forumj.web.servlet.FJServlet;

/**
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = {FJUrl.SET_LOCATION}, name=FJServletName.SET_LOCATION)
public class SetLocation extends FJServlet {

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
         StringBuffer buffer = new StringBuffer();
         boolean scity = request.getParameter("scity") != null;;
         boolean scountry = request.getParameter("scountry") != null;;
         String timezoneParameter = request.getParameter("timezone");
         String cityParameter = request.getParameter("city");
         String countryParameter = request.getParameter("country");
         int timeZone = Integer.valueOf(timezoneParameter); 
         HttpSession session = request.getSession();
         User user = (User) session.getAttribute("user");
         UserDao dao = new UserDao();
         if (user != null && !user.isBanned() && user.isLogined()){
            user.setShowCity(scity);
            user.setShowCountry(scountry);
            user.setCity(cityParameter);
            user.setCountry(countryParameter);
            user.setTimeZone(timeZone);
            dao.update(user);
            //TODO Magic integer!
            buffer.append(successPostOut("0", "control.php?id=10"));
         }else{
            // Вошли незарегистрировавшись
            buffer.append(unRegisteredPostOut());
         }
      }catch (Exception e) {
         e.printStackTrace();
      }
   }

}