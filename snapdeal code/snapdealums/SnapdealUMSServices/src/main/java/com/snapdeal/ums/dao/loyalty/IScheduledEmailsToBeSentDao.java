package com.snapdeal.ums.dao.loyalty;

import java.util.Collection;
import java.util.Date;

import com.snapdeal.ums.core.entity.ScheduledEmailToBeSent;

public interface IScheduledEmailsToBeSentDao
{

    public Collection<ScheduledEmailToBeSent> getScheduledEmails(Date lastUpdatedBefore, boolean includeFailedEmails);

    public ScheduledEmailToBeSent getScheduledEmails(int id);

}
