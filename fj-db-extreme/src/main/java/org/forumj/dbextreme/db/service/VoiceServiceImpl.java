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
import org.forumj.common.db.entity.*;
import org.forumj.common.db.service.VoiceService;

/**
 * 
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
public class VoiceServiceImpl extends FJService implements VoiceService {

   /**
    * 
    * @param threadId
    * @param user
    * @return
    * @throws SQLException
    * @throws ConfigurationException
    * @throws IOException
    */
   public boolean isUserVoted(long threadId, long userId) throws SQLException, ConfigurationException, IOException{
      return getVoiceDao().isUserVoted(threadId, userId);
   }
   
   public IFJVoice getVoiceObject(){
      return getVoiceDao().getVoiceObject();
   }

   public IFJVoice read(Long threadId, Long userId) throws ConfigurationException, IOException, SQLException{
      return getVoiceDao().read(threadId, userId);
   }
   
   
}
