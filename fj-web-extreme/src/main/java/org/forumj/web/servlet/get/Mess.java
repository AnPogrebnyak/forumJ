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
package org.forumj.web.servlet.get;

import static org.forumj.tool.Diletant.*;
import static org.forumj.tool.FJServletTools.*;
import static org.forumj.web.servlet.tool.FJServletTools.*;

import java.io.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.forumj.common.*;
import org.forumj.common.db.entity.IUser;
import org.forumj.common.db.entity.Image;
import org.forumj.common.db.entity.ImageType;
import org.forumj.common.db.service.FJServiceHolder;
import org.forumj.common.db.service.ImageService;
import org.forumj.tool.LocaleString;
import org.forumj.web.servlet.FJServlet;

/**
 * 
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/" + FJUrl.NEW_THREAD}, name = FJServletName.NEW_THREAD)
public class Mess extends FJServlet {

   /**
    * {@inheritDoc}
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      StringBuffer buffer = new StringBuffer();
      try{
         HttpSession session = request.getSession();
         IUser user = (IUser) session.getAttribute("user");
         ImageService imageService = FJServiceHolder.getImageService();
         LocaleString locale = (LocaleString) session.getAttribute("locale");
         cache(response);
         buffer.append("<!doctype html public \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
         buffer.append("<html>");
         buffer.append("<head>");
         buffer.append("<meta http-equiv='content-type' content='text/html; charset=UTF-8'>");
         /*Стили*/
         buffer.append(loadCSS("/css/style.css"));
         // Скрипты (смайлики)
         buffer.append(loadJavaScript("/js/smile_.js"));
         // Скрипты (автовставка тегов)
         buffer.append(loadJavaScript("/js/jstags.js"));
         // Скрипты (submit поста)
         buffer.append(new_submit(locale.getString("mess128")));
         buffer.append("<title>");
         buffer.append(locale.getString("mess4"));
         buffer.append("</title>");
         buffer.append("</head>");
         /*Цвет фона страницы*/
         buffer.append("<body bgcolor=#EFEFEF>");
         /*Главная таблица*/
         buffer.append("<table border='0' style='border-collapse: collapse' width='100%'>");
         /*Таблица с лого и верхним баннером*/
         buffer.append(logo(request));
         // Главные ссылки
         // Главное "меню"
         buffer.append(menu(request, user, locale, false));
         // Форма новой ветки
         buffer.append("<tr>");
         buffer.append("<td>");
         buffer.append("<table width=100%>");
         buffer.append("<tr>");
         buffer.append("<td>");
         buffer.append("<form name='post' action='" + FJUrl.ADD_THREAD + "' method='post'>");
         buffer.append("<table width='100%'>");
         /*Тема*/
         buffer.append("<tr>");
         buffer.append("<td colspan='3' align='left'>");
         buffer.append(locale.getString("mess4") + "&nbsp");
         buffer.append("<input class='mnuforumSm' type=text name='NHEAD' size='120' maxlength='120'>");
         buffer.append("</td>");
         buffer.append("</tr>");
         buffer.append("<tr>");
         /*Смайлики заголовок*/
         buffer.append("<td align=center>");
         buffer.append("<p>");
         buffer.append(locale.getString("mess21") + ":");
         buffer.append("</p>");
         buffer.append("</td>");
         /*Приглашение*/
         buffer.append("<td align='CENTER'>");
         buffer.append("<p>");
         buffer.append(locale.getString("mess12"));
         buffer.append("</p>");
         buffer.append("</td>");
         /*Photoalbum header*/
         buffer.append("<td align=left>");
         buffer.append("<p>");
         buffer.append(locale.getString("MSG_PHOTOALBUM") + ":");
         buffer.append("</p>");
         buffer.append("</td>");
         buffer.append("</tr>");
         /*Пост*/
         buffer.append("<tr>");
         buffer.append("<td valign='TOP'>");
         /*Смайлики*/
         buffer.append(smiles_add(locale.getString("mess11")));
         buffer.append("</td>");
         buffer.append("<td align='CENTER' valign='top'>");
         /*Автотеги*/
         buffer.append(autotags_add());
         /*текстарий*/
         buffer.append("<p>");
         buffer.append("<textarea class='mnuforumSm' rows='30' id='ed1' name='A2' cols='55'></textarea>");
         buffer.append("</p>");
         /*Кнопки*/
         buffer.append("<table>");
         buffer.append("<tr>");
         buffer.append("<td>");
         buffer.append(fd_button(locale.getString("mess13"),"new_submit(\"write\");","B1", "1"));
         buffer.append("</td>");
         buffer.append("<td>");
         buffer.append(fd_button(locale.getString("mess63"),"new_submit(\"view\");","B3", "1"));
         buffer.append("</td>");
         buffer.append("</tr>");
         buffer.append("</table>");
         /*Прередаем нужные пераметры...*/
         buffer.append(fd_form_add(user));
         buffer.append("</td>");
         //Photoalbum
         buffer.append("<td align='LEFT' valign='top'>");

         List<Image> imageThumbs = imageService.getImages(user.getId(), 0, ImageType.POST_THUMBNAIL);
         buffer.append("<div style='float: left;width: 330px;overflow-y: auto;overflow-x: hidden;height:600px;'>");
         buffer.append("<div style='float: left;width: 160px;'>");

         for (int thumbIndex = 0; thumbIndex < imageThumbs.size(); thumbIndex += 2){ //just for start
            Image thumb = imageThumbs.get(thumbIndex);
            buffer.append("<div style='width: 150px;margin-bottom:10px;'>");
            buffer.append("<img border='0' src='photo/");
            buffer.append(thumb.getId());
            buffer.append("?id=");
            buffer.append(thumb.getId());
            buffer.append("' onclick=\"InsertTags('[img]photo/");
            buffer.append(thumb.getParentId());
            buffer.append("?id=");
            buffer.append(thumb.getParentId());
            buffer.append("','[/img]')\" alt='Вставить картинку'>");
            buffer.append("</div>");
         }
            buffer.append("</div>");
         buffer.append("<div style='margin-left: 160px;width: 160px;'>");
         for (int thumbIndex = 1; thumbIndex < imageThumbs.size(); thumbIndex += 2){ //just for start
            Image thumb = imageThumbs.get(thumbIndex);
            buffer.append("<div style='width: 150px;margin-bottom:10px;'>");
            buffer.append("<img border='0' src='photo/");
            buffer.append(thumb.getId());
            buffer.append("?id=");
            buffer.append(thumb.getId());
            buffer.append("' onclick=\"InsertTags('[img]photo/");
            buffer.append(thumb.getParentId());
            buffer.append("?id=");
            buffer.append(thumb.getParentId());
            buffer.append("','[/img]')\" alt='Вставить картинку'>");
            buffer.append("</div>");
         }
            buffer.append("</div>");
         buffer.append("</div>");

         buffer.append("</td>");
         buffer.append("</tr>");
         buffer.append("</table>");
         buffer.append("</form>");
         buffer.append("</td>");
         buffer.append("</tr>");
         buffer.append("</table>");
         buffer.append("</td>");
         buffer.append("</tr>");
         // Главное "меню"
         buffer.append(menu(request, user, locale, false));
         // Баннер внизу, счетчики и копирайт.
         buffer.append(footer(request));
         buffer.append("</body>");
         buffer.append("</html>");
      } catch (Throwable e) {
         buffer = new StringBuffer();
         buffer.append(errorOut(e));
         e.printStackTrace();
      }
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter writer = response.getWriter();
      String out = buffer.toString();
      writer.write(out);
   }

}
