
package com.snapdeal.ums.admin.server.services;

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

public interface ISmsSchedulerService {


    public UpdateResponse update(UpdateRequest request);

    public PersistResponse persist(PersistRequest request);

    public GetSmsSchedulerByIdResponse getSmsSchedulerById(GetSmsSchedulerByIdRequest request);

    public GetSmsSchedulerListResponse getSmsSchedulerList(GetSmsSchedulerListRequest request);

    public GetSmsSchedulerListResponse getSmsSchedulerList(GetSmsSchedulerListRequest2 request);

    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest request);

    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest2 request);

    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest3 request);

    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest4 request);

}
