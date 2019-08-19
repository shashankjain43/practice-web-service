package com.snapdeal.payments.view.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.payments.view.dao.IRequestViewAuditDao;
import com.snapdeal.payments.view.entity.PaymentsViewAuditEntity;
import com.snapdeal.payments.view.entity.RequestViewAuditEntity;
import com.snapdeal.payments.view.merchant.commons.request.RetryPaymentsViewAuditRequest;

@Repository("RequestViewAuditDaoImpl")
public class RequestViewAuditDaoImpl implements IRequestViewAuditDao {

    @Autowired
    private SqlSessionTemplate sqlSession;

    /*
     * (non-Javadoc)
     *
     * @see com.snapdeal.payments.view.dao.IPaymentsViewAuditDao#
     * getPaymentsViewAuditEntity(java.util.Map)
     */
    @Override
    public RequestViewAuditEntity getPaymentsViewAuditEntity(
            Map<String, Object> txnDetails) {
        return sqlSession.selectOne(
                "requestViewAudit.getRequestViewAuditEntity", txnDetails);

    }

    /*
     * (non-Javadoc)
     *
     * @see com.snapdeal.payments.view.dao.IPaymentsViewAuditDao#
     * getPaymentsViewAuditDetailsInChunks
     * (com.snapdeal.payments.view.merchant.commons
     * .request.RetryPaymentsViewAuditRequest)
     */
    @Override
    public List<PaymentsViewAuditEntity> getPaymentsViewAuditDetailsInChunks(
            RetryPaymentsViewAuditRequest request) {

        return sqlSession
                .selectList(
                        "requestViewAudit.getRequestViewAuditDetailsInChunks",
                        request);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.snapdeal.payments.view.dao.IPaymentsViewAuditDao#
     * updatePaymentsViewAuditStatus(java.util.Map)
     */
    @Override
    public int updatePaymentsViewAuditStatus(Map<String, Object> txnDetails) {
        return sqlSession.update(
                "requestViewAudit.updateRequestViewAuditStatus", txnDetails);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.snapdeal.payments.view.dao.IPaymentsViewAuditDao#
     * savePaymentsViewAuditEntity(java.lang.Object)
     */
    @Override
    public void savePaymentsViewAuditEntity(RequestViewAuditEntity entity) {
        sqlSession
                .insert("requestViewAudit.saveRequestViewAuditEntity", entity);

    }

    /*
     * (non-Javadoc)
     *
     * @see com.snapdeal.payments.view.dao.IPaymentsViewAuditDao#
     * updatePaymentsViewAuditEntity(java.lang.Object)
     */
    @Override
    public int updatePaymentsViewAuditEntity(RequestViewAuditEntity entity) {

        return sqlSession.update(
                "requestViewAudit.updateRequestViewAuditStatus", entity);
    }
}
