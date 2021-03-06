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
package org.forumj.common.db.entity;

import java.util.Date;

/**
 * 
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
public interface IIgnor {
   
   public static final String ID_FIELD_NAME = "id";
   public static final String TYPE_FIELD_NAME = "type";
   public static final String USER_ID_FIELD_NAME = "user";
   public static final String IGNORED_USER_ID_FIELD_NAME = "ignor";
   public static final String IGNOR_START_FIELD_NAME = "begin";
   public static final String IGNOR_END_FIELD_NAME = "end";
   public abstract void setEnd(Date end);
   public abstract Date getEnd();
   public abstract void setStart(Date start);
   public abstract Date getStart();
   public abstract void setUser(IUser user);
   public abstract IUser getUser();
   public abstract void setType(Integer type);
   public abstract Integer getType();
   public abstract void setId(Long id);
   public abstract Long getId();
   public abstract void setUserId(Long userId);
   public abstract Long getUserId();

}