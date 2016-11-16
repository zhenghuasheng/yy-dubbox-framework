package com.etong.sms.service.impl;

import com.alibaba.dubbo.config.support.Parameter;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.etong.pt.utility.PtResult;
import com.etong.sms.data.ConsumerDao;
import com.etong.sms.model.Consumer;
import com.etong.sms.service.ConsumerServer;

import javax.ws.rs.*;
import java.util.Date;

/**
 * Created by Administrator on 2015/11/13.
 */
@Produces({ContentType.APPLICATION_JSON_UTF_8})
@Path("consumer")
public class ConsumerServerImpl implements ConsumerServer {

    private ConsumerDao consumerDao;

    public void setConsumerDao(ConsumerDao consumerDao) {
        this.consumerDao = consumerDao;
    }

    @POST
    @Path("add")
    @Override
    public PtResult addConsumerRequset(Consumer consumer) {
        consumer.setCreatTime((int) (new Date().getTime() / 1000));
        consumer.setViable(true);
        return consumerDao.addConsumerRequset(consumer);
    }

    @GET
    @Path("name/{memberName}/id/{memberId}")
    @Override
    public PtResult getConsumerRequest(@PathParam("memberName") String memberName, @PathParam("memberId") String memberId) {
        return consumerDao.getConsumerRequest(memberName, memberId);
    }

    @Override
    public PtResult getConsumerBykey(int id) {
        return null;
    }

    @GET
    @Path("list")
    @Override
    public PtResult getConsumers(@QueryParam("start") Integer start, @QueryParam("count") Integer count) {
        return consumerDao.getConsumers(start, count);
    }

    @POST
    @Path("update")
    @Override
    public PtResult modifyConsumerRequest(Consumer consumer) {
        return consumerDao.updateConsumerRequest(consumer);
    }

}
