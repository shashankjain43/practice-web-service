package com.snapdeal.ums.services.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.stereotype.Component;

import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.job.JobDO;
import com.snapdeal.ums.dao.users.IUsersDao;
import com.snapdeal.ums.event.SnapBoxActivationEvent;
import com.snapdeal.ums.loyalty.SnapBoxConstants;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.services.event.EventsPublishingService;
// import com.snapdeal.ums.dao.users.IUsersDao;
// import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.utils.UMSStringUtils;

@Component
public class JobExecuter

{

    @Autowired
    private IEmailServiceInternal emailServiceInternal;

    @Autowired
    private IUsersDao usersDao;

    @Autowired
    private EventsPublishingService eventsPublishingService;

    private static final Logger LOG = LoggerFactory.getLogger(JobExecuter.class);

    /**
     * Executes the jobDO in request
     * 
     * @param jobDO
     */
    boolean execute(JobDO jobDO)
    {

        boolean isSucess = true;

        if (jobDO == null) {
            LOG.error("Null job recieved for execution!");
            return false;
        }
        if (UMSStringUtils.isNullOrEmpty(jobDO.getContextID()) || jobDO.getJobType() == null) {
            LOG.error("BAD REQUEST! invalid praramaters for " + jobDO.toString());

        }

        switch (jobDO.getJobType()) {
        case SEND_SNAPBOX_ACTIVATION_EMAIL:
            LOG.info("Starting execution of : " + jobDO);
            isSucess = sendSnapBoxActivationEmail(jobDO.getContextID());
            break;

        case REQUEST_PROMO_CODE_ASSIGNMENT:
            try {
                eventsPublishingService.publishSnapBoxActivationEvent(new SnapBoxActivationEvent(
                    System.currentTimeMillis(), jobDO.getContextID()));
            }
            catch (JmsException jmsException) {
                LOG.warn("Failed to publish SnapBoxActivationEvent for " + jobDO.getContextID()
                    );
                isSucess = false;
            }
            break;

        default:
            break;
        }

        return isSucess;
    }

    private boolean sendSnapBoxActivationEmail(String emailID)
    {

        User user = usersDao.getUserByEmailWithoutRoles(emailID);
        emailServiceInternal.sendSnapBoxActivationConfirmation(emailID, user.getFirstName(),
            SnapBoxConstants.SNAPDEAL_MY_SNAPBOX_PAGE_LINK);
        return true;

    }

}
