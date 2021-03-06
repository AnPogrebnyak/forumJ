/*
 * Copyright (c) 2011
 * Andrew V. Pogrebnyak
 * All rights reserved.
 *
 * This software is distributed under GNU General Public License Version 2.0
 * You shall use it and distribute only in accordance with the terms of the 
 * License Agreement.
 */
package org.forumj.common.db.entity;

import java.sql.*;

import org.forumj.common.web.Locale;

/**
 *
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
public interface IUser {
   
   public static final String ID_FIELD_NAME = "id";
   public static final String NICK_FIELD_NAME = "nick";
   
   public void setAvatarApproved(Boolean ok_avatar);
   public Boolean getAvatarApproved();
   public void setAvatar(String avatar);
   public String getAvatar();
   public boolean isBanned();
   public void setId(Long id);
   public Long getId();
   public void setNick(String nick);
   public String getNick();
   public void setPass2(String pass2);
   public String getPass2();
   public void setPass(String pass);
   public String getPass();
   public boolean isLogined();
   public void setPostsOnPage(int postsOnPage);
   public int getPostsOnPage();
   public void setThreadsOnPage(int threadsOnPage);
   public int getThreadsOnPage();
   public void setView(long view);
   public long getView();
   public void setBan(int ban);
   public int getBan();
   public void setShowAvatar(Boolean s_avatar);
   public Boolean getShowAvatar();
   public void setIsActive(Boolean isActive);
   public Boolean getIsActive();
   public void setActivateCode(Integer activateCode);
   public Integer getActivateCode();
   public void setFooter(String footer);
   public String getFooter();
   public void setTimeZone(Integer timeZone);
   public Integer getTimeZone();
   public void setLanguge(Locale languge);
   public Locale getLanguge();
   public void setHideIp(Boolean hideIp);
   public Boolean getHideIp();
   public void setShowIcq(Boolean showIcq);
   public Boolean getShowIcq();
   public void setIcq(String icq);
   public String getIcq();
   public void setShowBithday(Boolean showBithday);
   public Boolean getShowBithday();
   public void setShowSex(Boolean showSex);
   public Boolean getShowSex();
   public void setShowCountry(Boolean showCountry);
   public Boolean getShowCountry();
   public void setCountry(String country);
   public String getCountry();
   public void setShowCity(Boolean showCity);
   public Boolean getShowCity();
   public void setCity(String city);
   public String getCity();
   public void setShowName(Boolean showName);
   public Boolean getShowName();
   public void setShowMail(Boolean showMail);
   public Boolean getShowMail();
   public Timestamp getReg();
   public void setBith(Date bith);
   public Date getBith();
   public void setSex(String sex);
   public String getSex();
   public void setFam(String fam);
   public String getFam();
   public void setName(String name);
   public String getName();
   public void setWantSeeAvatars(Boolean wantSeeAvatars);
   public Boolean getWantSeeAvatars();
   public void setEmail(String email);
   public String getEmail();
   void setReg(Timestamp reg);
   public boolean isModerator();
   boolean isApproved();
   void setApproved(boolean approved);

}