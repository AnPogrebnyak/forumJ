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
package org.forumj.dbextreme.db.service;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.configuration.ConfigurationException;
import org.forumj.common.db.service.CountService;

/**
 * 
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
public class CountServiceImpl extends FJService implements CountService {

   public long getAddedPostsAmount(long lastPostId, long userId) throws SQLException, ConfigurationException, IOException{
      return getPostDao().getAddedPostsAmount(lastPostId, userId);
   }
   
   public long getAddedThreadsAmount(long lastThreadId, long userId) throws SQLException, ConfigurationException, IOException{
      return getThreadDao().getAddedThreadsAmount(lastThreadId, userId);
   }
   
   public long getAddedPostsAmount(long threadId, long lastPostId, long userId) throws SQLException, ConfigurationException, IOException{
      return getPostDao().getAddedPostsAmount(threadId, lastPostId, userId);
   }
}
