
package com.snapdeal.ums.admin.client.services;

import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerByIdRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerByIdResponse;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerListRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerListRequest2;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerListResponse;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerRequest2;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerRequest3;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerRequest4;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerResponse;
import com.snapdeal.ums.admin.ext.smsscheduler.PersistRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.PersistResponse;
import com.snapdeal.ums.admin.ext.smsscheduler.UpdateRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.UpdateResponse;

public interface ISmsSchedulerClientService {

@Deprecated
    public UpdateResponse update(UpdateRequest request);
@Deprecated
    public PersistResponse persist(PersistRequest request);
@Deprecated
    public GetSmsSchedulerByIdResponse getSmsSchedulerById(GetSmsSchedulerByIdRequest request);
@Deprecated
    public GetSmsSchedulerListResponse getSmsSchedulerList(GetSmsSchedulerListRequest request);
@Deprecated
    public GetSmsSchedulerListResponse getSmsSchedulerList(GetSmsSchedulerListRequest2 request);
@Deprecated
    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest request);
@Deprecated
    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest2 request);
@Deprecated
    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest3 request);
@Deprecated
    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest4 request);

}
