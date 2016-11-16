package com.etong.pt.dao;

import com.etong.pt.data.vehicle.PtVehicle;
import com.etong.pt.data.vehicle.PtVehicleExample;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/11/3.
 */
public interface PtVehicleDao {
    PtResult add(PtVehicle record);
    PtResult update(PtVehicle record);
    PtResult delete(Long id);
    PtResult getListByCustId(Long custId);
    PtResult deleteByCustId(Long custId);

    PtResult getVehicle(PtVehicleExample example);
}
