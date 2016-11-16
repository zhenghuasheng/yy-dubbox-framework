/**
 * Created by wunan on 2015/4/21.
 */
package com.etong.pt.provider.vehicle;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.etong.pt.api.vehicle.PtVehicleService;
import com.etong.pt.dao.PtVehicleDao;
import com.etong.pt.data.vehicle.PtVehicle;
import com.etong.pt.data.vehicle.PtVehicleExample;
import com.etong.pt.utility.PtResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("vehicle")
@Produces({ContentType.APPLICATION_JSON_UTF_8})
public class PtVehicleServiceImpl implements PtVehicleService {
    private PtVehicleDao ptVehicleDao;

    public void setPtVehicleDao(PtVehicleDao ptVehicleDao) {
        this.ptVehicleDao = ptVehicleDao;
    }

    @Override
    public PtResult add(PtVehicle record) {
        return ptVehicleDao.add(record);
    }

    @Override
    public PtResult update(PtVehicle record) {
        return ptVehicleDao.update(record);
    }

    @Override
    public PtResult delete(Long id) {
        return ptVehicleDao.delete(id);
    }

    @Override
    public PtResult getListByCustId(Long custId) {
        return ptVehicleDao.getListByCustId(custId);
    }

    @Override
    public PtResult deleteByCustId(Long custId) {
        return ptVehicleDao.deleteByCustId(custId);
    }

    @Override
    public PtResult batch(List<PtVehicle> list) {
        PtResult ptResult = null;
        for (PtVehicle record : list) {
            if(record.getF_dvid() == null) {
                ptResult = this.add(record);
                if (!ptResult.isSucceed()) {
                    return ptResult;
                }
            }
            else {
                if(record.isDel()) {
                    ptResult = this.delete(record.getF_dvid());
                    if (!ptResult.isSucceed()) {
                        return ptResult;
                    }
                }
                else {
                    ptResult = this.update(record);
                    if (!ptResult.isSucceed()) {
                        return ptResult;
                    }
                }
            }
        }
        return ptResult;
    }

    @GET
    @Path("list/{custId}/{start}/{limit}")
    @Override
    public PtResult getVehileList(@PathParam("custId") Long custId
            , @PathParam("start") Integer start
            , @PathParam("limit") Integer limit) {
        PtVehicleExample example = new PtVehicleExample();
        example.or().andF_ciidEqualTo(custId);
        example.setLimitClause(start, limit);
        return ptVehicleDao.getVehicle(example);
    }
}
