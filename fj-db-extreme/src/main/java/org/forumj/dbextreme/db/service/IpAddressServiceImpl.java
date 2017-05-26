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

import org.apache.commons.configuration.ConfigurationException;
import org.forumj.common.db.entity.IpAddress;
import org.forumj.common.db.service.IpAddressService;
import org.forumj.dbextreme.db.entity.IpAddressImpl;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 
 * @author <a href="mailto:an.pogrebnyak@gmail.com">Andrew V. Pogrebnyak</a>
 */
public class IpAddressServiceImpl extends FJService implements IpAddressService {

   public void createIpAddress(IpAddress ipAddress) throws SQLException, ConfigurationException, IOException{
      getIpAddressDao().create(ipAddress);
   }

   public Boolean isSpammer(String ip) throws IOException, SQLException, ConfigurationException{
      return getIpAddressDao().isSpammer(ip);
   }

   public IpAddress getObject() {
      return new IpAddressImpl();
   }
}
