/*
 * Copyright (c) 2011
 * Andrew V. Pogrebnyak
 * All rights reserved.
 *
 * This software is distributed under GNU General Public License Version 2.0
 * You shall use it and distribute only in accordance with the terms of the 
 * License Agreement.
 */
package org.forumj.network.web.controller;

import java.io.*;

import javax.servlet.http.*;

import org.forumj.common.db.entity.IUser;
import org.forumj.network.web.FJUrl;
import org.forumj.network.web.resources.ResourcesBuilder;
import org.forumj.network.web.resources.LocaleString;
import org.forumj.network.web.FJServletTools;

public class Auth{

   public void doGet(HttpServletRequest request, HttpServletResponse response, String webapp, String userURI) throws Exception {
      StringBuffer buffer = new StringBuffer();
      FJServletTools.cache(response);
      HttpSession session = request.getSession();
      String gid = request.getParameter("id");
      gid = (gid == null || "".equalsIgnoreCase(gid.trim())) ? "1" : gid;
      LocaleString locale = (LocaleString) session.getAttribute("locale");
      IUser user = (IUser) session.getAttribute("user");
      buffer.append("<!doctype html public \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
      buffer.append("<html>");
      buffer.append("<head>");
      buffer.append("<meta http-equiv='content-type' content='text/html; charset=UTF-8'>");
      // Стили
      buffer.append(ResourcesBuilder.getStyleCSS(webapp));
      buffer.append("<title>");
      buffer.append("Авторизация");
      buffer.append("</title>");
      buffer.append("</head>");
      // Цвет фона страницы
      buffer.append("<body bgcolor=#EFEFEF>");
      // Главная таблица
      buffer.append("<table border='0' style='border-collapse: collapse' width='100%'>");
      // Таблица с лого и верхним баннером
      buffer.append(FJServletTools.logo(webapp));
      // Главные ссылки
      buffer.append(FJServletTools.menu(request, user, locale, false, webapp, userURI));
      // Форма авторизации
      buffer.append("<tr><td width='100%' align='center'><table width='100%'><tr><td>");
      buffer.append("<form  action='").append("/").append(userURI).append("/").append(FJUrl.DO_LOGIN).append("' method='post'>");
      buffer.append("<table><tr><td><p>");
      // Определяем, откуда мы сюда попали?
      switch (Integer.valueOf(gid)){
         // Нажали на ссылку Вход
         case 1:
            buffer.append("Авторизуйтесь, пожалуйста");
            break;
         case 4:
            buffer.append("<b>Авторизуйтесь, пожалуйста</b>");;
            break;
            // Попытались предложить тему незарегистрировавшись
         case 2:
            buffer.append("Предлагать темы для обсуждения могут только зарегистрированные посетители!");
            break;
            // Попытались ответить незарегистрировавшись
         case 3:
            buffer.append("Добавлять свое мнение могут только зарегистрированные посетители!");
            break;
         case 5:
            buffer.append("<b>Пардон, мы таких не знаем! :)</b>");;
            break;
         case 6:
            buffer.append("<b>Вы не угадали пароль! :)</b>");;
            break;
         case 7:
            buffer.append("<b>В связи с усложнением системы идентификации участников форума прошу вас пройти процедуру дооформления :) необходимо добавить еще один идентификатор, который вы введете только ОДИН раз в, дальнейшем это не позволит хацкерам подделать вашу куку.</b>");;
            break;
         case 8:
            buffer.append("В связи с усложнением системы идентификации участников форума прошу вас пройти процедуру дооформления :) необходимо добавить еще один идентификатор, который вы введете только ОДИН раз в, дальнейшем это не позволит хацкерам подделать вашу куку.<br> <b>Идентификаторы не совпадают</b>");;
            break;
         case 9:
            buffer.append("Проводить опросы могут только зарегистрированные пользователи");
            break;
         case 10:
            buffer.append("Ваш аккаунт не активирован");
            break;
      }
      buffer.append("</p></td></tr>");
      // С любезностями закончили
      // Запрашиваем Ник
      buffer.append("<tr><td><table><tr><td>");
      buffer.append("Ник</td>");
      buffer.append("<td><input type='text' name='T1' size='20'></td>");
      buffer.append("</tr>");
      // Пароль
      buffer.append("<tr>");
      buffer.append("<td>Пароль</td>");
      buffer.append("<td><input type=password name='T2' size='20'>");
      buffer.append("</td>");
      buffer.append("</tr>");
      // Кнопки
      buffer.append("<tr>");
      buffer.append("<td>");
      buffer.append("<input type='submit' value='Отправить' name='B1'>");
      buffer.append("<input type='reset' value='Отменить' name='B2'>");
      buffer.append("</td></tr></table></td></tr></table></form></td></tr></table></td></tr>");
      // Форма закончилась
      // Главные ссылки
      buffer.append(FJServletTools.menu(request, user, locale, false, webapp, userURI));
      buffer.append(FJServletTools.footer(webapp));
      buffer.append("</body>");
      buffer.append("</html>");
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter writer = response.getWriter();
      writer.write(buffer.toString());
   }

}
