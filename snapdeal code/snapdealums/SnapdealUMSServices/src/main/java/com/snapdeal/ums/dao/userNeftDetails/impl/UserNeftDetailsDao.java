/**
 * 
 */
package com.snapdeal.ums.dao.userNeftDetails.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.core.entity.UserNeftDetailsDO;
import com.snapdeal.ums.dao.userNeftDetails.IuserNeftDetailsDao;

/**
 * @author ashish
 * 
 */

@Repository
public class UserNeftDetailsDao implements IuserNeftDetailsDao
{

    @Autowired
    private SessionFactory sessionFactory;

    // @Override
    // public LoyaltyUserDetailDO saveOrUpdateStatusChange(LoyaltyUserDetailDO
    // loyaltyUserDetail)
    // {
    //
    // LoyaltyUserDetailDO persistedLoyaltyUserDetail = null;
    // // if (loyaltyUserDetail == null ||
    // // !UMSStringUtils.isNotNullNotEmpty(loyaltyUserDetail.getEmail())) {
    // // return null;
    // // }
    //
    // int loyaltyUserID = loyaltyUserDetail.getId();
    //
    // if (loyaltyUserID == 0) {
    //
    // // implies a new record.
    // // Persist this obj
    // loyaltyUserDetail.setLastUpdated(DateUtils.getCurrentTime());
    // sessionFactory.getCurrentSession().save(loyaltyUserDetail);
    // persistedLoyaltyUserDetail = loyaltyUserDetail;
    //
    // }
    // else {
    // // is already associated with the table. Update the program and
    // // status of the original record
    // LoyaltyUserDetailDO existingLoyaltyUserDetail = (LoyaltyUserDetailDO)
    // sessionFactory.getCurrentSession()
    // .get(
    // LoyaltyUserDetailDO.class, loyaltyUserID);
    //
    // // Now update the loyalty program and the status ONLY!
    //
    // existingLoyaltyUserDetail.setLoyaltyProgram(loyaltyUserDetail.getLoyaltyProgram());
    // existingLoyaltyUserDetail.setLoyaltyProgramStatus(loyaltyUserDetail.getLoyaltyProgramStatus());
    // existingLoyaltyUserDetail.setLastUpdated(DateUtils.getCurrentTime());
    // persistedLoyaltyUserDetail = existingLoyaltyUserDetail;
    //
    // }
    //
    // return persistedLoyaltyUserDetail;
    // }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.snapdeal.ums.dao.snapBox.ISnapBoxBuyerDtlsDao#getSnapBoxLoyaltyStatus
     * (java.lang.String)
     */
    // @Override
    // public String getSnapBoxLoyaltyStatus(String emailID) {
    // return sessionFactory
    // .getCurrentSession()
    // .createQuery(
    // "from LoyaltyUserDetail where email = :emailIDs").setParameter("emailIDs",
    // emailID).uniqueResult();
    // }

    // @Override
    public List<String> getExistingEmails(Collection<String> emailIDs)
    {

        return sessionFactory
            .getCurrentSession()
            .createQuery(
                "select email from LoyaltyUserDetail where email in (:emailIDs)")
            .setParameterList("emailIDs", emailIDs).list();

    }

    // @Override
    // public LoyaltyUserDetail updateStatus(LoyaltyUserDetail
    // loyaltyUserDetail)
    // {
    //
    // return null;
    // }
    //
    // @Override
    // public LoyaltyUserDetailDO getLoyaltyUserDtl(String emailID, int
    // loyaltyProgramID, int statusID)
    // {
    //
    // LoyaltyUserDetailDO loyaltyUserDetail = (LoyaltyUserDetailDO)
    // sessionFactory
    // .getCurrentSession()
    // .createQuery(
    // "from LoyaltyUserDetailDO where email=:emailID and loyaltyProgram.id =:loyaltyProgramID and loyaltyProgramStatus.id = :statusID")
    // .setParameter("emailID", emailID)
    // .setParameter("loyaltyProgramID", loyaltyProgramID)
    // .setParameter("statusID", statusID).uniqueResult();
    //
    // return loyaltyUserDetail;
    // }
    //
    // @Override
    // public LoyaltyUserDetailDO getLoyaltyUserDtl(String emailID, int
    // loyaltyProgramID)
    // {
    //
    // return (LoyaltyUserDetailDO) sessionFactory
    // .getCurrentSession()
    // .createQuery(
    // "from LoyaltyUserDetailDO where email=:emailID and loyaltyProgram.id =:loyaltyProgramID")
    // .setParameter("emailID", emailID)
    // .setParameter("loyaltyProgramID", loyaltyProgramID).uniqueResult();
    //
    // }
    //
    // @Override
    // public List<String> getExistingLoyaltyEmailIDs(List<String> emailIDs,
    // LoyaltyProgramDO loyaltyProgram)
    // {
    //
    // Query query = sessionFactory
    // .getCurrentSession()
    // .createQuery(
    // "select email from LoyaltyUserDetailDO where loyaltyProgram.id = " +
    // loyaltyProgram.getId()
    // + " and email in (:emailIDs)").setParameterList("emailIDs", emailIDs);
    //
    // return query.list();
    // }
    //
    //
    // @Override
    // public void persist(List<LoyaltyUserDetailDO> LoyaltyUserDetailDOList)
    // {
    //
    // StatelessSession statelessSession = sessionFactory
    // .openStatelessSession();
    // Transaction tx = statelessSession.beginTransaction();
    // for ( int i=0; i<LoyaltyUserDetailDOList.size(); i++ ) {
    // statelessSession.insert(LoyaltyUserDetailDOList.get(i));
    // }
    // tx.commit();
    // statelessSession.close();
    //
    // }

    @Override
    public UserNeftDetailsDO addActiveNeftDetails(UserNeftDetailsDO userNeftDetails)
    {

        userNeftDetails.setLastUpdated(DateUtils.getCurrentTime());
        sessionFactory.getCurrentSession().save(userNeftDetails);

        return userNeftDetails;
    }

    @Override
    public void deactivateUserNeftDetails(int neftDetailsID) throws ObjectNotFoundException
    {

        UserNeftDetailsDO neftDetailsDO = (UserNeftDetailsDO) sessionFactory.getCurrentSession().get(
            UserNeftDetailsDO.class, neftDetailsID);

        if (neftDetailsDO == null) {
            throw new ObjectNotFoundException((Serializable)new Integer(neftDetailsID),UserNeftDetailsDO.class.toString() );
        }

        else
        {
            neftDetailsDO.setActive(false);
            neftDetailsDO.setLastUpdated(DateUtils.getCurrentTime());
        }
    }

    @Override
    public UserNeftDetailsDO getActiveNeftDetails(String email)
    {

        return (UserNeftDetailsDO) sessionFactory
            .getCurrentSession()
            .createQuery(
                "from UserNeftDetailsDO where isActive=:TRUE and email=:email")
            .setParameter("TRUE", true).setParameter("email", email).uniqueResult();
    }

    @Override
    public void verifyActivateExistingUserNEFTDetails(int neftDetailsID, Date lastVerifiedTime)
    {

        Session session = sessionFactory.getCurrentSession();
        UserNeftDetailsDO neftDetailsDO = (UserNeftDetailsDO) session.get(
            UserNeftDetailsDO.class, neftDetailsID);
        neftDetailsDO.setActive(true);
        neftDetailsDO.setLastUpdated(lastVerifiedTime);
        neftDetailsDO.setLastVerified(lastVerifiedTime);
        session.update(neftDetailsDO);

        //
        //
        // return sessionFactory
        // .getCurrentSession()
        // .createQuery(
        // "update UserNeftDetailsDO set isActive=:TRUE, lastVerified=:lastVerified where id=:neftDetailsID")
        // .setParameter("TRUE", true).setParameter("neftDetailsID",
        // neftDetailsID)
        // .setParameter("lastVerified", lastVerifiedTime)
        // .executeUpdate();
    }

    @Override
    public UserNeftDetailsDO fetchUserNeftDetails(String accHolderName, String email, String ifscCode, String branch,
        String bankName,
        String accountNo)
    {

        Query query = sessionFactory
            .getCurrentSession()
            .createQuery(
                "from UserNeftDetailsDO where accHolderName=:accHolderName and email=:email and ifscCode=:ifscCode and branch=:branch and bankName=:bankName "
                    + "and accountNo=:accountNo")
            .setParameter("email", email).setParameter("ifscCode", ifscCode).setParameter("branch", branch)
            .setParameter("bankName", bankName).setParameter("accountNo", accountNo)
            .setParameter("accHolderName", accHolderName);

        return (UserNeftDetailsDO) query.uniqueResult();

    }

    @Override
    public UserNeftDetailsDO fetchUserNeftDetailsByID(int userNEFTDetailsID)
    {

        //
        // Query query = sessionFactory
        // .getCurrentSession()
        // .createQuery(
        // "from UserNeftDetailsDO where id=:id")
        // .setParameter("id", userNEFTDetailsID);

        UserNeftDetailsDO neftDetailsDO = (UserNeftDetailsDO) sessionFactory.getCurrentSession().get(
            UserNeftDetailsDO.class, userNEFTDetailsID);

        // return (UserNeftDetailsDO) query.uniqueResult();

        return neftDetailsDO;

    }

    @Override
    public int deactivateAllActiveNeftDetails(String email)
    {

        return sessionFactory
            .getCurrentSession()
            .createQuery(
                "update UserNeftDetailsDO set isActive=:isActiveStatusFalse, lastUpdated =:lastUpdated where email=:email and isActive=:TRUE")
            .setParameter("isActiveStatusFalse", false).setParameter("email", email)
            .setTimestamp("lastUpdated", DateUtils.getCurrentTime()).setParameter("TRUE", true).executeUpdate();
    }

}
