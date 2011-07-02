/*
 * Copyright (c) 2011
 * Andrew V. Pogrebnyak
 * All rights reserved.
 *
 * This software is distributed under GNU General Public License Version 2.0
 * You shall use it and distribute only in accordance with the terms of the 
 * License Agreement.
  */
package org.forumj.db.dao.tool;

import java.io.*;

import org.forumj.web.servlet.tool.FJServletTools;

/**
 *
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
public class QueryBuilder {
   
   private static String loadConfigQuery = null;

   private static String createThreadQuery = null;

   private static String updateThreadQuery = null;
   
   private static String updatePostQuery = null;

   private static String updatePostBodyQuery = null;

   private static String updatePostHeadQuery = null;

   private static String readThreadQuery = null;
   
   private static String firstPostIdInThreadQuery = null;

   private static String createPostQuery = null;
   
   private static String readPostQuery = null;

   private static String readPostHeadQuery = null;

   private static String readPostBodyQuery = null;
   
   private static String createPostBodyQuery = null;
   
   private static String createPostHeadQuery = null;
   
   public static String getLoadConfigQuery() throws IOException{
      if (loadConfigQuery == null){
         loadConfigQuery = loadQuery("/sql/load_config.sql");
      }
      return loadConfigQuery;
   }
   
   public static String getCreateThreadQuery() throws IOException{
      if (createThreadQuery == null){
         createThreadQuery = loadQuery("/sql/create_thread.sql");
      }
      return createThreadQuery;
   }
   
   public static String getUpdateThreadQuery() throws IOException{
      if (updateThreadQuery == null){
         updateThreadQuery = loadQuery("/sql/update_thread.sql");
      }
      return updateThreadQuery;
   }
   
   public static String getUpdatePostQuery() throws IOException{
      if (updatePostQuery == null){
         updatePostQuery = loadQuery("/sql/update_post.sql");
      }
      return updatePostQuery;
   }
   
   public static String getUpdatePostBodyQuery(String bodyTable) throws IOException{
      if (updatePostBodyQuery == null){
         updatePostBodyQuery = loadQuery("/sql/update_post_body.sql");
      }
      return updatePostBodyQuery.replace("@@TABLE@@", bodyTable);
   }
   
   public static String getUpdatePostHeadQuery(String headTable) throws IOException{
      if (updatePostHeadQuery == null){
         updatePostHeadQuery = loadQuery("/sql/update_post_head.sql");
      }
      return updatePostHeadQuery.replace("@@TABLE@@", headTable);
   }
   
   public static String getReadThreadQuery() throws IOException{
      if (readThreadQuery == null){
         readThreadQuery = loadQuery("/sql/read_thread.sql");
      }
      return readThreadQuery;
   }
   
   public static String getFirstPostIdInThreadQuery() throws IOException{
      if (firstPostIdInThreadQuery == null){
         firstPostIdInThreadQuery = loadQuery("/sql/first_post_id_in_thread.sql");
      }
      return firstPostIdInThreadQuery;
   }
   
   public static String getCreatePostQuery() throws IOException{
      if (createPostQuery == null){
         createPostQuery = loadQuery("/sql/create_post.sql");
      }
      return createPostQuery;
   }
   
   public static String getReadPostQuery() throws IOException{
      if (readPostQuery == null){
         readPostQuery = loadQuery("/sql/read_post.sql");
      }
      return readPostQuery;
   }
   
   public static String getReadPostHeadQuery(String headTable) throws IOException{
      if (readPostHeadQuery == null){
         readPostHeadQuery = loadQuery("/sql/read_post_head.sql");
      }
      return readPostHeadQuery.replace("@@TABLE@@", headTable);
   }
   
   public static String getReadPostBodyQuery(String bodyTable) throws IOException{
      if (readPostBodyQuery == null){
         readPostBodyQuery = loadQuery("/sql/read_post_body.sql");
      }
      return readPostBodyQuery.replace("@@TABLE@@", bodyTable);
   }
   
   public static String getCreatePostBodyQuery(String bodyTableName) throws IOException{
      if (createPostBodyQuery == null){
         createPostBodyQuery = loadQuery("/sql/create_post_body.sql");
      }
      return createPostBodyQuery.replace("@@currentBodyTable@@", bodyTableName);
   }
   
   public static String getCreatePostHeadQuery(String headTableName) throws IOException{
      if (createPostHeadQuery == null){
         createPostHeadQuery = loadQuery("/sql/create_post_head.sql");
      }
      return createPostHeadQuery.replace("@@currentHeadTable@@", headTableName);
   }
   
   private static String loadQuery(String path) throws IOException{
      ClassLoader classLoader = FJServletTools.class.getClassLoader();
      InputStream stream = classLoader.getResourceAsStream(path);
      BufferedReader br = new BufferedReader(new InputStreamReader(stream));
      StringBuffer result = new StringBuffer();
      while(br.ready()){
         result.append(br.readLine() + "\n");
      }
      return result.toString();
   }
}
