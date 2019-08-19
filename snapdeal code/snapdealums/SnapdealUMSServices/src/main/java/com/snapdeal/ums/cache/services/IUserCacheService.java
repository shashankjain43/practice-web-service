/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.ums.cache.services;

import com.snapdeal.ums.core.sro.user.UserSRO;


/**
 *  @version   1.0, Dec 26, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

public interface IUserCacheService {

    public UserSRO getUserSROByEmail(final String email);
    
    public boolean putUserSROByEmail(final String email, final UserSRO userSRO);
    
    public boolean deleteUserSROByEmail(final String email);
    
    public UserSRO getUserSROById(final int userId);
    
    public boolean putEmailByIdMapping(final int userId, final String email);
    
    public String getEmailByIdMapping(final int userId);
    
    public boolean putUserSROById(final int userId, final UserSRO userSRO);
}
