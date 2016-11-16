/**
 * Created by wunan on 2015/4/21.
 */
package com.etong.pt.provider.customer;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.etong.pt.api.customer.PtCustomerService;
import com.etong.pt.dao.PtCustomerDao;
import com.etong.pt.data.customer.PtCustomer;
import com.etong.pt.data.customer.PtCustomerExample;
import com.etong.pt.utility.PtResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("cust")
@Produces({ContentType.APPLICATION_JSON_UTF_8})
public class PtCustomerServiceImpl implements PtCustomerService {
    private PtCustomerDao ptCustomerDao;

    public void setPtCustomerDao(PtCustomerDao ptCustomerDao) {
        this.ptCustomerDao = ptCustomerDao;
    }

    @Override
    public PtResult add(PtCustomer record) {
        return ptCustomerDao.add(record);
    }

    @Override
    public PtResult update(PtCustomer record) {
        return ptCustomerDao.update(record);
    }

    @Override
    public PtResult getById(Long id) {
        return ptCustomerDao.getById(id);
    }

    @Override
    public PtResult getByUserId(Integer userId) {
        return ptCustomerDao.getOneByUserId(userId);
    }

    @Override
    public PtResult setCustByUserId(PtCustomer record) {
        return ptCustomerDao.update(record);
    }

    @Override
    public PtResult setCustomer(PtCustomer record) {
        return ptCustomerDao.update(record);
    }

    @Override
    public PtResult getListByPhone(String phone) {
        PtCustomer param = new PtCustomer();
        param.setF_phone(phone);
        return ptCustomerDao.findByParam(param);
    }

    @GET
    @Path("list/{start}/{limit}")
    @Override
    public PtResult getCustomer(@PathParam("start") Integer start
            , @PathParam("limit") Integer limit) {
        PtCustomerExample example = new PtCustomerExample();
        example.setLimitClause(start, limit);
        return ptCustomerDao.getCustomer(example);
    }
}
