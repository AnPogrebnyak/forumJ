/*
 * Copyright (c) 2011
 * Andrew V. Pogrebnyak
 * All rights reserved.
 *
 * This software is distributed under GNU General Public License Version 2.0
 * You shall use it and distribute only in accordance with the terms of the 
 * License Agreement.
 */
package org.forumj.common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.forumj.common.config.FJConfiguration;

/**
 * @author <a href = "mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
public interface FJUrl {

   public static final String INDEX = "index";
   public static final String VIEW_THREAD = "tema";
   public static final String VIEW_THREAD_OLD = "tema.php";
   public static final String NEW_THREAD = "mess";
   public static final String MESSAGE = "message";
   public static final String ADD_THREAD = "new";
   public static final String NEW_QUESTION = "opr";
   public static final String ADD_QUESTION = "quest";
   public static final String ADD_POST = "write";
   public static final String SETTINGS = "control";
   public static final String ADMIN = "admin";
   public static final String LOGIN = "auth";
   public static final String DO_LOGIN = "submit";
   public static final String REGISTRATION = "reg";
   public static final String DO_REGISTRATION = "insnew";
   public static final String UPDATE_IGNORING = "amn";
   public static final String DELETE_MAIL = "delmail";
   public static final String MOVE_THREAD_TO_RECYCLE = "delone";
   public static final String ACTIVATE_USER = "activate";
   public static final String APPROVE_USER = "approve";
   public static final String PIN_THREAD = "pin";
   public static final String CLOSE_THREAD = "close";
   public static final String DELETE_SUBSCRIBE = "delonesubs";
   public static final String DELETE_SUBSCRIBES = "delsubs";
   public static final String DELETE_ONE_SUBSCRIBE_BY_EMAIL = "delonesubsbymail";
   public static final String DELETE_FOLDER_FROM_VIEW = "delvfolder";
   public static final String DELETE_VIEW = "delview";
   public static final String SET_DEFAULT_VIEW = "defview";
   public static final String DELETE_VOICE = "delvoice";
   public static final String VOICE = "voice";
   public static final String ADD_VOTE = "uservoice";
   public static final String ADD_IGNOR = "ignor";
   public static final String MOVE_TITLE = "movetitle";
   public static final String NEW_FOLDER = "newfolder";
   public static final String FOLDER_TOOLS = "delfolder";
   public static final String NEW_VIEW = "newview";
   public static final String SET_AVATAR = "setavatar";
   public static final String POST_IMAGE = "postimage";
   public static final String SET_FOOTER = "setfooter";
   public static final String POST = "post";
   public static final String SET_LOCATION = "setlocation";
   public static final String V_AVATAR = "vavatar";
   public static final String SELECT_VIEW = "slctview";
   public static final String SEND_PIVATE_MESSAGE = "send";
   public static final String ADD_SUBSCRIBE = "addsubs";
   public static final String PING = "ping";
   public static final String BAN = "ban";
   public static final String CSS = "css";
   public static final String PICTS = "picts";

   // a lot of pictures paths
    // "/css/picts/*","/picts/*", "/images/*", "/skin/*", "/banner/*", "/smiles/*", "/avatars/*",  "/photo/*"
   public static final String CSS_PICTURES = CSS + "/" + PICTS;
   public static final String PICTURES = "picts";
   public static final String IMAGES = "images";
   public static final String SKIN = "skin";
   public static final String BANNER = "banner";
   public static final String SMILES = "smiles";
   public static final String AVATARS = "avatars";
   public static final String PHOTO = "photo";
   // end pictures
   public static final String STATIC = "00";
   public static final String DYNAMIC = "01";
}
