/**
 * Created by wunan on 2015/4/21.
 */
package com.etong.pt.api.customer;

import com.etong.pt.data.customer.PtCustomer;
import com.etong.pt.utility.PtResult;

public interface PtCustomerService {
    PtResult add(PtCustomer record);
    PtResult update(PtCustomer record);
    PtResult getById(Long id);
    PtResult getByUserId(Integer userId);
    PtResult setCustByUserId(PtCustomer record);
    PtResult setCustomer(PtCustomer record);
    PtResult getListByPhone(String phone);

    PtResult getCustomer(Integer start, Integer limit);
}
