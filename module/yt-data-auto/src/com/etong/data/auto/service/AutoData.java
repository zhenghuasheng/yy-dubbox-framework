/**
 * Created by wunan on 15-11-11.
 */
package com.etong.data.auto.service;

import com.etong.data.auto.car.Car;
import com.etong.data.auto.car.CarExample;
import com.etong.data.auto.carparam.CarParamExample;
import com.etong.data.auto.carphoto.CarPhotoExample;
import com.etong.data.auto.expand.vehicle.ExpandVehicle;
import com.etong.data.auto.expand.vehicle.ExpandVehicleExample;
import com.etong.data.auto.vehicle.Vehicle;
import com.etong.data.auto.vehicle.VehicleExample;
import com.etong.pt.utility.PtResult;

public interface AutoData {
    PtResult getVehicleByExample(VehicleExample example, String cacheKey);
    PtResult setVehicle(Vehicle vehicle);

    PtResult getCarByExample(CarExample example, String cacheKey);

    PtResult setCar(Car car);

    PtResult getCarParamByExample(CarParamExample example, String cacheKey);

    PtResult getCarPhotoByExample(CarPhotoExample example, String cacheKey);

    PtResult getExpandVehicleByExample(ExpandVehicleExample example, String cacheKey);

    PtResult setExpandVehicle(ExpandVehicle vehicle);
}
