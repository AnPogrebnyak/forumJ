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
package org.forumj.common.db.service;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.configuration.ConfigurationException;
import org.forumj.common.db.entity.*;
import org.forumj.common.exception.DBException;
import org.forumj.common.web.Pin;

/**
 * 
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
public interface ThreadService {
   
   public boolean checkThreadExist(Long id) throws IOException, SQLException, ConfigurationException;

   
   public IFJThread getThreadObject();

   public IFJQuestionThread getQuestionThreadObject();

   public void create(IFJThread thread, IFJPost post) throws IOException, DBException, SQLException, ConfigurationException;
   
   /**
    * 
    * @param id
    * @return
    * @throws ConfigurationException
    * @throws SQLException
    * @throws IOException
    */
   public IFJThread readThread(Long id) throws ConfigurationException, SQLException, IOException;


   /**
    * 
    * @param user
    * @param threadId
    * @throws ConfigurationException
    * @throws SQLException
    * @throws IOException
    */
   public void setSeen(IUser user, Long threadId) throws ConfigurationException, SQLException, IOException;
   
   public void pin(Long threadId, Pin pin) throws ConfigurationException, IOException, SQLException;

   public void close(Long threadId, boolean closed) throws ConfigurationException, IOException, SQLException;
}
