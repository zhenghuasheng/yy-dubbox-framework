/**
 * Created by wunan on 15-11-13.
 */
package com.etong.dc.auto.service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.etong.container.utility.CacheKey;
import com.etong.data.auto.car.Car;
import com.etong.data.auto.car.CarExample;
import com.etong.data.auto.carparam.CarParam;
import com.etong.data.auto.carparam.CarParamExample;
import com.etong.data.auto.carphoto.CarPhotoExample;
import com.etong.data.auto.expand.vehicle.ExpandVehicle;
import com.etong.data.auto.expand.vehicle.ExpandVehicleExample;
import com.etong.data.auto.service.AutoData;
import com.etong.data.auto.vehicle.Vehicle;
import com.etong.data.auto.vehicle.VehicleExample;
import com.etong.filter.AuthContext;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("auto")
@Produces({ContentType.APPLICATION_JSON_UTF_8})
public class AutoServiceImpl implements AutoService {
    public static final String CATEGORY_BRAND = "BRAND";
    public static final String CATEGORY_BRAND_EXPAND = "BRAND:EXPAND";
    public static final String CATEGORY_MANU = "manu";
    public static final String CATEGORY_MANU_EXPAND = "manu:expand";
    public static final String CATEGORY_CARSET = "carset";
    public static final String CATEGORY_CAR = "car";
    private static Logger logger = LoggerFactory.getLogger(AutoServiceImpl.class);
    private AutoData autoData;
    private CacheKey cacheKey;

    public void setAutoData(AutoData autoData) {
        this.autoData = autoData;
    }

    public void setCacheKey(CacheKey cacheKey) {
        this.cacheKey = cacheKey;
    }

    @GET
    @Path("brand")
    @Override
    public PtResult getBrands() {
        return getBrands(null, null, null);
    }

    @GET
    @Path("brand/{start}/{limit}")
    @Override
    public PtResult getBrands(@PathParam("start") Integer start
            , @PathParam("limit") Integer limit
            , @QueryParam("condi") String condition) {
        String system = AuthContext.getInstance().getSystem();
        VehicleExample example = new VehicleExample();
        VehicleExample.Criteria criteria = example.or().andLevelEqualTo((byte) 0);
        example.setLimitClause(start, limit);

        if ((condition != null) && !condition.isEmpty()) {
            criteria.andTitleLike("%" + condition + "%");
        }

        PtResult ptResult = autoData.getVehicleByExample(example
                , cacheKey.getKeyByPage(system, CATEGORY_BRAND
                        , start, limit, condition));

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> brandList = ptResult.getObject();
        //获取应用扩展品牌数据，覆盖基础品牌数据
        ExpandVehicleExample expandExample = new ExpandVehicleExample();
        expandExample.or().andStidEqualTo(system).andLevelEqualTo((byte) 0);
        expandExample.setLimitClause(start, limit);
        ptResult = autoData.getExpandVehicleByExample(expandExample
                , cacheKey.getKeyByPage(system, CATEGORY_BRAND_EXPAND, start, limit));
        return expandVehicle(brandList, ptResult);
    }

    @GET
    @Path("brand/{id}")
    @Override
    public PtResult getBrandInfo(@PathParam("id") int id) {
        String system = AuthContext.getInstance().getSystem();
        VehicleExample example = new VehicleExample();
        example.or().andIdEqualTo((short) id);
        PtResult ptResult = autoData.getVehicleByExample(example, cacheKey.getKey(
                system, CATEGORY_BRAND, "INFO", Integer.toString(id)));

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> vehicleList = ptResult.getObject();
        ExpandVehicleExample expandExample = new ExpandVehicleExample();
        expandExample.or().andStidEqualTo(system).andIdEqualTo((short) id);
        ptResult = autoData.getExpandVehicleByExample(expandExample
                , cacheKey.getKey(system, CATEGORY_BRAND_EXPAND, Integer.toString(id)));

        ptResult = expandVehicle(vehicleList, ptResult);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, vehicleList.get(0));
    }

    @GET
    @Path("brand/country/{country}")
    @Override
    public PtResult getBrandByCountry(@PathParam("country") String country) {
        String system = AuthContext.getInstance().getSystem();
        VehicleExample example = new VehicleExample();
        example.or().andCountryEqualTo(country);
        PtResult ptResult = autoData.getVehicleByExample(example
                , cacheKey.getKey(system, CATEGORY_BRAND, "COUNTRY", country));

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> brandList = ptResult.getObject();
        //获取应用扩展品牌数据，覆盖基础品牌数据
        ExpandVehicleExample expandExample = new ExpandVehicleExample();
        expandExample.or().andStidEqualTo(system).andLevelEqualTo((byte) 0);
        ptResult = autoData.getExpandVehicleByExample(expandExample
                , cacheKey.getKey(system, CATEGORY_BRAND_EXPAND, "COUNTRY", country));
        return expandVehicle(brandList, ptResult);
    }

    @GET
    @Path("manu")
    @Override
    public PtResult getManus() {
        PtResult ptResult = getBrands();

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> brandList = ptResult.getObject();
        Map<Short, Vehicle> brandMap = new HashMap<Short, Vehicle>(brandList.size());

        for (Vehicle brand : brandList) {
            brandMap.put(brand.getId(), brand);
        }

        String system = AuthContext.getInstance().getSystem();
        VehicleExample example = new VehicleExample();
        example.or().andLevelEqualTo((byte) 1);
        ptResult = autoData.getVehicleByExample(example
                , cacheKey.getKey(system, CATEGORY_MANU));

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> manuList = ptResult.getObject();

        for (Vehicle manu : manuList) {
            Vehicle brand = brandMap.get(manu.getPid());

            if (brand != null) {
                manu.setpTitle(brand.getTitle());
            }
        }

        //获取应用扩展品牌数据，覆盖基础品牌数据
        ExpandVehicleExample expandExample = new ExpandVehicleExample();
        expandExample.or().andStidEqualTo(system).andLevelEqualTo((byte) 1);
        ptResult = autoData.getExpandVehicleByExample(expandExample
                , cacheKey.getKey(system, CATEGORY_MANU_EXPAND));
        return expandVehicle(manuList, ptResult);
    }

    @GET
    @Path("brand/{id}/manu")
    @Override
    public PtResult getManuByBrandId(@PathParam("id") int id) {
        PtResult ptResult = getBrandInfo(id);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Vehicle brand = ptResult.getObject();
        String system = AuthContext.getInstance().getSystem();
        VehicleExample example = new VehicleExample();
        example.or().andLevelEqualTo((byte) 1).andPidEqualTo((short) id);
        ptResult = autoData.getVehicleByExample(example, cacheKey.getKey(
                CATEGORY_MANU, CATEGORY_BRAND, Integer.toString(id)));

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> manuList = ptResult.getObject();

        for (Vehicle manu : manuList) {
            manu.setpTitle(brand.getTitle());
        }

        //获取应用扩展品牌数据，覆盖基础品牌数据
        ExpandVehicleExample expandExample = new ExpandVehicleExample();
        expandExample.or().andStidEqualTo(system)
                .andLevelEqualTo((byte) 1).andPidEqualTo((short) id);
        ptResult = autoData.getExpandVehicleByExample(expandExample
                , cacheKey.getKey(system, CATEGORY_MANU_EXPAND
                        , CATEGORY_BRAND_EXPAND, Integer.toString(id)));
        return expandVehicle(manuList, ptResult);
    }

    @GET
    @Path("manu/{id}")
    @Override
    public PtResult getManuInfo(@PathParam("id") int id) {
        String system = AuthContext.getInstance().getSystem();
        VehicleExample example = new VehicleExample();
        example.or().andIdEqualTo((short) id);
        PtResult ptResult = autoData.getVehicleByExample(example
                , cacheKey.getKey(CATEGORY_MANU, "INFO", Integer.toString(id)));

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> manuList = ptResult.getObject();
        Vehicle manu = manuList.get(0);
        ptResult = getBrandInfo(manu.getPid());

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Vehicle brand = ptResult.getObject();
        manu.setpTitle(brand.getTitle());
        //获取应用扩展品牌数据，覆盖基础品牌数据
        ExpandVehicleExample expandExample = new ExpandVehicleExample();
        expandExample.or().andStidEqualTo(system).andIdEqualTo((short) id);
        ptResult = autoData.getExpandVehicleByExample(expandExample
                , cacheKey.getKey(system, CATEGORY_MANU_EXPAND
                        , CATEGORY_BRAND_EXPAND, Integer.toString(id)));
        expandVehicle(manuList, ptResult);
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, manu);
    }

    @GET
    @Path("carset")
    @Override
    public PtResult getCarsets() {
        PtResult ptResult = getManus();

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> manuList = ptResult.getObject();
        Map<Short, Vehicle> manuMap = new HashMap<Short, Vehicle>(manuList.size());

        for (Vehicle manu : manuList) {
            manuMap.put(manu.getId(), manu);
        }

        VehicleExample example = new VehicleExample();
        example.or().andLevelEqualTo((byte) 2);
        ptResult = autoData.getVehicleByExample(example, "carset");

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> carsetList = ptResult.getObject();

        for (Vehicle carset : carsetList) {
            Vehicle manu = manuMap.get(carset.getPid());

            if (manu != null) {
                manu.setpTitle(manu.getTitle());
            }
        }

        return ptResult;
    }

    @GET
    @Path("manu/{id}/carset/")
    @Override
    public PtResult getCarsetByManuId(@PathParam("id") int id) {
        PtResult ptResult = getManuInfo(id);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Vehicle manu = ptResult.getObject();
        String system = AuthContext.getInstance().getSystem();
        VehicleExample example = new VehicleExample();
        example.or().andLevelEqualTo((byte) 2).andPidEqualTo((short) id);
        ptResult = autoData.getVehicleByExample(example, cacheKey.getKey(
                system, CATEGORY_CARSET, "MENU", Integer.toString(id)));

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> carsetList = ptResult.getObject();

        for (Vehicle carset : carsetList) {
            carset.setpTitle(manu.getTitle());
        }

        return ptResult;
    }

    @GET
    @Path("carset/level/{level}")
    @Override
    public PtResult getCarsetByLevel(@PathParam("level") int level) {
        VehicleExample example = new VehicleExample();
        example.or().andModelsLevelEqualTo((short) level);
        PtResult ptResult = autoData.getVehicleByExample(example
                , "carset:level:" + Integer.toString(level));
        return setCarsetParent(ptResult);
    }

    @GET
    @Path("carset/price/{min}/{max}")
    @Override
    public PtResult getCarsetByPrice(@PathParam("min") double min
            , @PathParam("max") double max) {
        VehicleExample example = new VehicleExample();
        example.or().andMinguideBetween((float) min, (float) max);
        example.or().andMaxguideBetween((float) min, (float) max);
        PtResult ptResult = autoData.getVehicleByExample(example
                , "manu:price:" + Double.toString(min) + "_" + Double.toString(max));
        return setCarsetParent(ptResult);
    }

    @GET
    @Path("carset/{id}")
    @Override
    public PtResult getCarsetInfo(@PathParam("id") int id) {
        String system = AuthContext.getInstance().getSystem();
        VehicleExample example = new VehicleExample();
        example.or().andIdEqualTo((short) id);
        PtResult ptResult = autoData.getVehicleByExample(example, cacheKey.getKey(
                system, CATEGORY_CARSET, "INFO", Integer.toString(id)));

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> carsetList = ptResult.getObject();
        Vehicle carset = carsetList.get(0);
        ptResult = getManuInfo(carset.getPid());

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Vehicle manu = ptResult.getObject();
        carset.setpTitle(manu.getTitle());
        ptResult.setObject(carset);
        return ptResult;
    }

    @GET
    @Path("carset/title/{title}")
    @ReadThroughSingleCache(namespace = "mem:auto:carset:find", expiration = 3600)
    @Override
    public PtResult findCarset(@PathParam("title") String title) {
        PtResult ptResult = getCarsets();

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> carsetList = ptResult.getObject();
        String tmpTitle = title.toUpperCase();
        List<Vehicle> findList = new ArrayList<Vehicle>();

        for (Vehicle carset : carsetList) {
            if (carset.getTitle().contains(tmpTitle)
                    || carset.getpTitle().contains(tmpTitle)) {
                findList.add(carset);
            }
        }

        if (findList.isEmpty()) {
            return new PtResult(PtCommonError.PT_ERROR_NODATA, "没有差找到车型数据", null);
        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, findList);
    }

    @GET
    @Path("carset/{id}/car")
    @ReadThroughSingleCache(namespace = "mem:auto:car", expiration = 3600)
    @Override
    public PtResult getCarByCarsetId(@PathParam("id") int id) {
        return getCarByCarsetId(id, null, null, null);
    }

    @GET
    @Path("carset/{id}/car/{start}/{limit}")
    @Override
    public PtResult getCarByCarsetId(@PathParam("id") int id
            , @PathParam("start") Integer start
            , @PathParam("limit") Integer limit
            , @QueryParam("condi") String condition) {
        PtResult ptResult = getCarsetInfo(id);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Vehicle carset = ptResult.getObject();
        ptResult = getManuInfo(carset.getPid());

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Vehicle manu = ptResult.getObject();
        String system = AuthContext.getInstance().getSystem();
        CarExample example = new CarExample();
        CarExample.Criteria criteria = example.or().andCarsetEqualTo((short) id);
        example.setOrderByClause("f_year desc");
        example.setLimitClause(start, limit);

        if ((condition != null) && !condition.isEmpty()) {
            criteria.andSubjectLike("%" + condition + "%");
        }

        ptResult = autoData.getCarByExample(example, cacheKey.
                getKeyByPage(system, CATEGORY_CAR, start, limit,
                CATEGORY_CARSET, Integer.toString(id), condition));

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Car> carList = ptResult.getObject();

        for (Car car : carList) {
            car.setCarsetTitle(carset.getTitle());
            car.setManu(manu.getTitle());
            car.setBrand(manu.getpTitle());
        }

        return ptResult;
    }

    @GET
    @Path("carset/{id}/car/status/{status}")
    @ReadThroughSingleCache(namespace = "mem:auto:car", expiration = 3600)
    @Override
    public PtResult getCarBySaleStatus(@PathParam("id") int carset
            , @PathParam("status") int status) {
        CarExample example = new CarExample();
        example.or().andCarsetEqualTo((short) carset)
                .andSalestatusidEqualTo((short) status);
        return autoData.getCarByExample(example, "carset:" + carset + ":sales:" + status);
    }

    @GET
    @Path("car/{id}")
    @ReadThroughSingleCache(namespace = "mem:auto:car:param", expiration = 3600)
    @Override
    public PtResult getCarParam(@PathParam("id") int id) {
        String system = AuthContext.getInstance().getSystem();
        CarParamExample example = new CarParamExample();
        example.or().andVidEqualTo(id);
        PtResult ptResult = autoData.getCarParamByExample(example
                , cacheKey.getKey(system, CATEGORY_CAR, "PARAM", Integer.toString(id)));

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<CarParam> carParams = ptResult.getObject();
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, carParams.get(0));
    }

    @PUT
    @Path("car")
    @Consumes({MediaType.APPLICATION_JSON})
    @Override
    public PtResult setCarInfo(Car car) {
        if (car.getVid() == null) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "车款标识没有设置", null);
        }

        PtResult ptResult = autoData.setCar(car);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        String system = AuthContext.getInstance().getSystem();
        cacheKey.changeVer(system, CATEGORY_CAR);
        return ptResult;
    }

    @GET
    @Path("car/{id}/photo/{type}")
    @ReadThroughSingleCache(namespace = "mem:auto:car:photo", expiration = 3600)
    @Override
    public PtResult getCarPhoto(@PathParam("id") int id
            , @PathParam("type") Integer type) {
        CarPhotoExample example = new CarPhotoExample();
        CarPhotoExample.Criteria criteria = example.or().andVidEqualTo(id);

        if ((type != null) && (type > 0)) {
            criteria.andImagetypeidEqualTo(type.shortValue());
        }

        PtResult ptResult = autoData.getCarPhotoByExample(example, "car:" + id + ":photo");

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        return ptResult;
    }

//    @GET
//    @Path("export")
//    @Override
//    public PtResult exportCar() {
//        PtResult ptResult = getManus();
//
//        if (!ptResult.isSucceed()) {
//            return ptResult;
//        }
//
//        List<Vehicle> manuList = ptResult.getObject();
////        HttpServletResponse response = RpcContext.getContext()
////                .getResponse(HttpServletResponse.class);
//        String filename = "车型库.xls";
//        String mimeType = new MimetypesFileTypeMap().getContentType(filename);
////        response.setContentType(mimeType);
////        response.setHeader("access-control-allow-headers"
////                , "Content-Type, Content-Range, Content-Disposition, X-Requested-With, auth-token");
////        response.setHeader("access-control-allow-methods"
////                , "OPTIONS, HEAD, GET, POST, PUT, DELETE");
////        response.setHeader("Access-Control-Allow-Origin", "*");
//
//        try {
////            response.setHeader("Content-Disposition", "attachment;filename="
////                    + URLEncoder.encode(filename, "UTF8"));
//
//            WritableWorkbook writableWorkbook = Workbook.createWorkbook(
//                    new File(filename));
////            WritableWorkbook writableWorkbook = Workbook.createWorkbook(
////                    response.getOutputStream());
//            WritableSheet writableSheet = createSheet(writableWorkbook, 0);
//            int row = 1;
//
//            for (Vehicle manu : manuList) {
//                ptResult = getCarsetByManuId(manu.getId());
//
//                if (!ptResult.isSucceed()) {
//                    break;
//                }
//
//                List<Vehicle> carsetList = ptResult.getObject();
//
//                for (Vehicle carset : carsetList) {
//                    ptResult = getCarByCarsetId(carset.getId());
//
//                    if (!ptResult.isSucceed()) {
//                        break;
//                    }
//
//                    List<Car> carList = ptResult.getObject();
//
//                    for (Car car : carList) {
//                        int col = 0;
//                        writableSheet.addCell(new Label(col++, row, car.getVid().toString()));
//                        writableSheet.addCell(new Label(col++, row, car.getManu()));
//                        writableSheet.addCell(new Label(col++, row, car.getCarsetTitle()));
//                        writableSheet.addCell(new Label(col++, row, car.getSubject()));
//                        writableSheet.addCell(new Label(col++, row, String.format("%.2f", car.getPrices())));
//                        writableSheet.addCell(new Label(col++, row, String.format("%.2f", car.getOutputVol())));
//                        ptResult = getCarParam(car.getVid());
//
//                        if (ptResult.isSucceed()) {
//                            CarParam carParam = ptResult.getObject();
//                            writableSheet.addCell(new Label(col++, row, carParam.getColor()));
//                        }
//
//                        ++row;
//
//                        if ((row / 60000) > 0) {
//                            writableSheet = createSheet(writableWorkbook, row / 60000);
//                        }
//                    }
//                }
//
//                if (!ptResult.isSucceed()) {
//                    break;
//                }
//            }
//
//            writableWorkbook.write();
//            writableWorkbook.close();
//        } catch (IOException | WriteException e) {
//            logger.error("导出车型库文件失败，Exception:{}", e.getMessage());
//            return new PtResult(PtCommonError.PT_ERROR_IO, "生成Excel文件出现异常", null);
//        }
//
//        if (!ptResult.isSucceed()) {
//            return ptResult;
//        }
//
//        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
//    }

    private PtResult setCarsetParent(PtResult carsetListResult) {
        if (!carsetListResult.isSucceed()) {
            return carsetListResult;
        }

        PtResult ptResult = getManus();

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<Vehicle> manuList = ptResult.getObject();
        Map<Short, Vehicle> manuMap = new HashMap<Short, Vehicle>(manuList.size());

        for (Vehicle manu : manuList) {
            manuMap.put(manu.getId(), manu);
        }

        List<Vehicle> carsetList = carsetListResult.getObject();

        for (Vehicle carset : carsetList) {
            Vehicle manu = manuMap.get(carset.getPid());

            if (manu != null) {
                carset.setpTitle(manu.getTitle());
            }
        }

        return carsetListResult;
    }

    private void objectReplace(Object src, Object dest) {
        Field[] fields = dest.getClass().getDeclaredFields();

        for (Field field : fields) {
            Object newValue = getObjectFieldValue(dest, field);
            setObjectFieldValue(src, field, newValue);
        }
    }

    private Object getObjectFieldValue(Object object, Field field) {
        try {
            Method method = object.getClass().getMethod(
                    "get" + upcaseFirstLetter(field.getName()));
            return method.invoke(object);
        } catch (NoSuchMethodException e) {
            logger.error("获取对象函数异常，Field: {}, Exception: {}"
                    , field, e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error("调用对象方法异常，Field: {}, Exception: {}"
                    , field, e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("获取对象属性值异常，Field: {}, Exception: {}"
                    , field, e.getMessage());
        }

        return null;
    }

    private void setObjectFieldValue(Object object, Field field, Object value) {
        if (value == null) {
            return;
        }

        try {
            Method method = object.getClass().getMethod("set"
                    + upcaseFirstLetter(field.getName()), value.getClass());
            method.invoke(object, value);
        } catch (NoSuchMethodException e) {
            logger.error("获取对象函数异常，Field: {}, Exception: {}"
                    , field, e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error("调用对象方法异常，Field: {}, Exception: {}"
                    , field, e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("设置对象属性值异常，Field: {}, Exception: {}"
                    , field, e.getMessage());
        }
    }

    private String upcaseFirstLetter(String str) {
        if (str == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    private PtResult expandVehicle(List<Vehicle> vehicleList, PtResult expandResult) {
        if (!expandResult.isSucceed()) {
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, vehicleList);
        }

        List<ExpandVehicle> expandList = expandResult.getObject();

        for (ExpandVehicle expandBrand : expandList) {
            for (Vehicle brand : vehicleList) {
                if (expandBrand.getId().shortValue() == brand.getId().shortValue()) {
                    objectReplace(brand, expandBrand);
                }
            }
        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, vehicleList);
    }

//    private WritableSheet createSheet(WritableWorkbook writableWorkbook, int index) {
//        WritableSheet writableSheet = writableWorkbook.createSheet(
//                String.format("车型库-%d", index), index);
//              /*设置单元格样式、字体等*/
//        /**
//         * 定义单元格样式
//         */
//        WritableFont writableFont = new WritableFont(WritableFont.ARIAL, 10,
//                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
//                jxl.format.Colour.BLUE); // 定义格式 字体 下划线 斜体 粗体 颜色
//        WritableCellFormat writableCellFormat = new WritableCellFormat(writableFont); // 单元格定义
//
//        try {
//            writableCellFormat.setBackground(Colour.YELLOW); // 设置单元格的背景颜色
//            writableCellFormat.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
//            /**增加表头*/
//            int col = 0, row = 0;
//            writableSheet.addCell(new Label(col++, row, "序号", writableCellFormat));
//            writableSheet.addCell(new Label(col++, row, "品牌", writableCellFormat));
//            writableSheet.addCell(new Label(col++, row, "车系", writableCellFormat));
//            writableSheet.addCell(new Label(col++, row, "车型", writableCellFormat));
//            writableSheet.addCell(new Label(col++, row, "指导价", writableCellFormat));
//            writableSheet.addCell(new Label(col++, row, "排量", writableCellFormat));
//            writableSheet.addCell(new Label(col++, row++, "颜色", writableCellFormat));
//        } catch (WriteException e) {
//            logger.error("生成Excel工作表异常：{}", e.getMessage());
//            return null;
//        }
//
//        /**设置列宽*/
//        for (int i = 0; i < writableSheet.getColumns(); i++) {
//            writableSheet.setColumnView(i, 20); // 设置列的宽度
//        }
//
//        writableSheet.setColumnView(6, 100); // 设置列的宽度
//        return writableSheet;
//    }
}
