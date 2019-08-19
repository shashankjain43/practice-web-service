package com.snapdeal.ums.services.loyalty;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ums.core.entity.LoyaltyProgramDO;
import com.snapdeal.ums.core.entity.LoyaltyProgramStatusDO;
import com.snapdeal.ums.dao.loyalty.ILoyaltyProgramDao;
import com.snapdeal.ums.dao.loyalty.ILoyaltyProgramStatusDao;
import com.snapdeal.ums.loyalty.LoyaltyConstants;

/**
 * Manager class assists in using enums(LOYALTY_PROGRAM and LOYALTY_STATUS) and
 * actual LoyaltyProgram and LoyaltyProgramStatus entity objects. The objective
 * is to effectively re-use loyalty program and status entity objects and
 * standardise the approach to accommodate future programs and status.
 * 
 * @author ashish
 * 
 */
@Service
public class LoyaltyProgramStatusManager
{

    @Autowired
    private ILoyaltyProgramDao loyaltyProgramDao;

    @Autowired
    private ILoyaltyProgramStatusDao loyaltyProgramStatusDao;

    private static Map<LoyaltyConstants.LoyaltyProgram, LoyaltyProgramDO> loyaltyProgramMap;

    private static Map<LoyaltyConstants.LoyaltyStatus, LoyaltyProgramStatusDO> loyaltyStatusMap;

    public LoyaltyProgramStatusManager()
    {

    }

    /**
     * Returns LoyaltyProgram object with the same state as
     * LOYALTY_PROGRAM#$program in the request
     * 
     * @param program
     * @return
     */
    public LoyaltyProgramDO getLoyaltyProgram(LoyaltyConstants.LoyaltyProgram program)
    {

        LoyaltyProgramDO loyaltyProgram = null;
        if (loyaltyProgramMap == null) {
            // It is not loaded!! So let us do that!
            loadLoyaltyProgramMap();
        }
        loyaltyProgram = loyaltyProgramMap.get(program);
        return loyaltyProgram;

    }

    /**
     * Returns LoyaltyProgramStatus object with the same status as of
     * LOYALTY_PROGRAM#$program in the request
     * 
     * @param program
     * @return
     */
    public LoyaltyProgramStatusDO getLoyaltyProgramStatus(LoyaltyConstants.LoyaltyStatus status)
    {

        LoyaltyProgramStatusDO loyaltyProgramStatus = null;
        if (loyaltyStatusMap == null) {
            // Map not loaded, let us do that!

            loadLoyaltyStatusMap();
        }
        loyaltyProgramStatus = loyaltyStatusMap.get(status);

        return loyaltyProgramStatus;

    }

    /**
     * Loads loyaltyProgramMap
     */
    private void loadLoyaltyProgramMap()
    {

        for (LoyaltyConstants.LoyaltyProgram program : LoyaltyConstants.LoyaltyProgram.values()) {

            LoyaltyProgramDO loyaltyProgram = loyaltyProgramDao.getLoyaltyProgram(program.toString());
            if (loyaltyProgramMap == null) {
                loyaltyProgramMap = new HashMap<LoyaltyConstants.LoyaltyProgram, LoyaltyProgramDO>();
            }
            loyaltyProgramMap.put(program, loyaltyProgram);

        }
    }

    /**
     * Loads
     */
    private void loadLoyaltyStatusMap()
    {

        for (LoyaltyConstants.LoyaltyStatus status : LoyaltyConstants.LoyaltyStatus.values()) {

            LoyaltyProgramStatusDO loyaltyProgramStatus = loyaltyProgramStatusDao.getLoyaltyStatus(status.toString());
            if (loyaltyStatusMap == null) {
                loyaltyStatusMap = new HashMap<LoyaltyConstants.LoyaltyStatus, LoyaltyProgramStatusDO>();
            }
            loyaltyStatusMap.put(status, loyaltyProgramStatus);

        }

    }

}
