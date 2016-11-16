/**
 * Created by wunan on 2015/4/21.
 */
package com.etong.pt.api.vehicle;

import com.etong.pt.data.vehicle.PtVehicle;
import com.etong.pt.data.vehicle.PtVehicleExample;
import com.etong.pt.utility.PtResult;

import java.util.List;

public interface PtVehicleService {
    PtResult add(PtVehicle record);

    PtResult update(PtVehicle record);

    PtResult delete(Long id);

    PtResult getListByCustId(Long custId);

    PtResult deleteByCustId(Long custId);

    PtResult batch(List<PtVehicle> list);

    PtResult getVehileList(Long custId, Integer start, Integer limit);
}
