/**
 * 
 */
package com.snapdeal.ums.dao.loyalty.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.LoyaltyProgramDO;
import com.snapdeal.ums.core.entity.LoyaltyUserDetailDO;
import com.snapdeal.ums.dao.loyalty.ILoyaltyUserDtlsDao;
import com.snapdeal.ums.loyalty.LoyaltyConstants;
import com.snapdeal.ums.utils.UMSStringUtils;

/**
 * @author ashish
 * 
 */

@Repository
// @Transactional
public class LoyaltyUserDtlsDao implements ILoyaltyUserDtlsDao
{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public LoyaltyUserDetailDO saveOrUpdateStatusChange(LoyaltyUserDetailDO loyaltyUserDetail)
    {

        LoyaltyUserDetailDO persistedLoyaltyUserDetail = null;
        if (loyaltyUserDetail == null || !UMSStringUtils.isNotNullNotEmpty(loyaltyUserDetail.getEmail())) {
            return null;
        }

        int loyaltyUserID = loyaltyUserDetail.getId();

        if (loyaltyUserID == 0) {

            // implies a new record.
            // Persist this obj

            sessionFactory.getCurrentSession().save(loyaltyUserDetail);
            persistedLoyaltyUserDetail = loyaltyUserDetail;

        }
        else {
            // is already associated with the table. Update the program and
            // status of the original record
            LoyaltyUserDetailDO existingLoyaltyUserDetail = (LoyaltyUserDetailDO) sessionFactory.getCurrentSession()
                .get(
                    LoyaltyUserDetailDO.class, loyaltyUserID);

            // Now update the loyalty program and the status ONLY!

            existingLoyaltyUserDetail.setLoyaltyProgram(loyaltyUserDetail.getLoyaltyProgram());
            existingLoyaltyUserDetail.setLoyaltyProgramStatus(loyaltyUserDetail.getLoyaltyProgramStatus());
            existingLoyaltyUserDetail.setLastUpdated(loyaltyUserDetail.getLastUpdated());
            persistedLoyaltyUserDetail = existingLoyaltyUserDetail;

        }

        return persistedLoyaltyUserDetail;
    }

    public void grantLoyaltyEligibility(LoyaltyConstants.LoyaltyProgram loyaltyProgram, Set<String> emaiIDs)
    {

        Session session = sessionFactory.openSession();
        // Transaction tx = session.beginTransaction();
        Iterator<String> emailIterator = emaiIDs.iterator();

        // while(emailIterator.hasNext()){
        // SnapBoxBuyerDtls snapBoxBuyerDtls = new
        // SnapBoxBuyerDtls(emailIterator.next(),, util);
        // session.save(customer);
        // if ( i % 20 == 0 ) { //20, same as the JDBC batch size
        // //flush a batch of inserts and release memory:
        // session.flush();
        // session.clear();
        //
        //
        // }
        //
        // for ( int i=0; i<emaiIDs.size(); i++ ) {
        // SnapBoxBuyerDtls snapBoxBuyerDtls = new SnapBoxBuyerDtls(emaiIDs.,
        // status, util);
        // session.save(customer);
        // if ( i % 20 == 0 ) { //20, same as the JDBC batch size
        // //flush a batch of inserts and release memory:
        // session.flush();
        // session.clear();
        // }
        // }
        //
        // tx.commit();
        // session.close();
        // }
    }

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

    @Override
    public LoyaltyUserDetailDO getLoyaltyUserDtl(String emailID, int loyaltyProgramID, int statusID)
    {

        LoyaltyUserDetailDO loyaltyUserDetail = (LoyaltyUserDetailDO) sessionFactory
            .getCurrentSession()
            .createQuery(
                "from LoyaltyUserDetailDO where email=:emailID and loyaltyProgram.id =:loyaltyProgramID and loyaltyProgramStatus.id = :statusID")
            .setParameter("emailID", emailID)
            .setParameter("loyaltyProgramID", loyaltyProgramID)
            .setParameter("statusID", statusID).uniqueResult();

        return loyaltyUserDetail;
    }

    @Override
    public LoyaltyUserDetailDO getLoyaltyUserDtl(String emailID, int loyaltyProgramID)
    {

        return (LoyaltyUserDetailDO) sessionFactory
            .getCurrentSession()
            .createQuery(
                "from LoyaltyUserDetailDO where email=:emailID and loyaltyProgram.id =:loyaltyProgramID")
            .setParameter("emailID", emailID)
            .setParameter("loyaltyProgramID", loyaltyProgramID).uniqueResult();

    }

    @Override
    public List<String> getExistingLoyaltyEmailIDs(List<String> emailIDs, LoyaltyProgramDO loyaltyProgram)
    {

        Query query = sessionFactory
            .getCurrentSession()
            .createQuery(
                "select email from LoyaltyUserDetailDO where loyaltyProgram.id = " + loyaltyProgram.getId()
                    + " and email in (:emailIDs)").setParameterList("emailIDs", emailIDs);

        return query.list();
    }
    
    
    @Override
    public void persist(List<LoyaltyUserDetailDO> LoyaltyUserDetailDOList)
    {
    
        StatelessSession statelessSession = sessionFactory
            .openStatelessSession();
        Transaction tx = statelessSession.beginTransaction();
        for ( int i=0; i<LoyaltyUserDetailDOList.size(); i++ ) {
            statelessSession.insert(LoyaltyUserDetailDOList.get(i));
        }
        tx.commit();
        statelessSession.close();
        
    }

}
