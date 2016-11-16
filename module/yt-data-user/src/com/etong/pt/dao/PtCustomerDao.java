package com.etong.pt.dao;

import com.etong.pt.data.customer.PtCustomer;
import com.etong.pt.data.customer.PtCustomerExample;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/11/3.
 */
public interface PtCustomerDao {
    PtResult add(PtCustomer record);
    PtResult update(PtCustomer record);
    PtResult getById(Long id);
    PtResult getOneByUserId(Integer userId);
    PtResult findByParam(PtCustomer param);

    PtResult getCustomer(PtCustomerExample example);
}
