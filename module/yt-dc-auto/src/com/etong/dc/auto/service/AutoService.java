/**
 * Created by wunan on 15-11-13.
 */
package com.etong.dc.auto.service;

import com.etong.data.auto.car.Car;
import com.etong.pt.utility.PtResult;

public interface AutoService {
    PtResult getBrands();
    PtResult getBrands(Integer start, Integer limit, String condition);

    PtResult getBrandInfo(int id);

    PtResult getBrandByCountry(String country);

    PtResult getManus();
    PtResult getManuByBrandId(int id);

    PtResult getManuInfo(int id);

    PtResult getCarsets();
    PtResult getCarsetByManuId(int id);

    PtResult getCarsetByLevel(int level);

    PtResult getCarsetByPrice(double min, double max);

    PtResult getCarsetInfo(int id);

    PtResult findCarset(String title);

    PtResult getCarByCarsetId(int id);
    PtResult getCarByCarsetId(int id, Integer start
            , Integer limit, String condition);

    PtResult getCarBySaleStatus(int carset, int status);

    PtResult getCarParam(int id);

    PtResult setCarInfo(Car car);

    PtResult getCarPhoto(int id, Integer type);

//    PtResult exportCar();
}
