var CarEditView = Class.extend({
    init: function () {
    },

    getFormData: function (old) {
        var data = {};

        if (old != undefined) {
            data = old;
        }

        data.subject = $('#title').val();
        data.image = $('#image').val();
        data.prices = $('#price').val();
        data.outputVol = $('#vol').val();
        //data.salestatus = $('#salestatus').val();
        data.salestatusid = $('input[name="salesRadios"]:checked').val();
        return data;
    },

    setFormData: function (car, carParam) {
        $('#title').val(car.subject);
        $('#image').val(car.image);
        $('#image_url').attr('src', car.image);
        $('#price').val(car.prices);
        $('#vol').val(car.outputVol);
        //$('#salestatus').val(car.salestatus);

        if (car.salestatusid != undefined) {
            $('input[name="salesRadios"][value="' + car.salestatusid + '"]').attr('checked', 'checked');
        }

        $('#warranty').val(carParam.warranty);
        $('#max_speed').val(carParam.max_speed);
        $('#drive_system').val(carParam.drive_system);
        $('#curb_weight').val(carParam.curb_weight);
        $('#door_num').val(carParam.door_num);
        $('#category').val(carParam.category);
        $('#structure').val(carParam.structure);
        $('#length').val(carParam.length);
        $('#width').val(carParam.width);
        $('#height').val(carParam.height);
        $('#wheelbase').val(carParam.wheelbase);
        $('#front_gauge').val(carParam.front_gauge);
        $('#rear_track').val(carParam.rear_track);
        $('#trunk_vol').val(carParam.trunk_vol);

        $('#fuel_type').val(carParam.fuel_type);
        $('#fuel_grade').val(carParam.fuel_grade);
        $('#engine').val(carParam.engine);
        $('#max_power').val(carParam.max_power);
        $('#max_power_rev').val(carParam.max_power_rev);
        $('#max_torque').val(carParam.max_torque);
        $('#max_torque_rev').val(carParam.max_torque_rev);
        $('#zylinderreihe').val(carParam.zylinderreihe);
        $('#ground_clearance').val(carParam.ground_clearance);
        $('#special_technology').val(carParam.special_technology);
        $('#cylinder_num').val(carParam.cylinder_num);
        $('#valve_num').val(carParam.valve_num);

        $('#ratio').val(carParam.ratio);
        $('#cylinder_material').val(carParam.cylinder_material);
        $('#env_standards').val(carParam.env_standards);
        $('#front_brake_type').val(carParam.front_brake_type);
        $('#rear_brake_type').val(carParam.rear_brake_type);
        $('#parking_brake').val(carParam.parking_brake);
        $('#front_tire').val(carParam.front_tire);
        $('#rear_tire').val(carParam.rear_tire);
        $('#spare_tire').val(carParam.spare_tire);
        $('#abat_vent').val(carParam.abat_vent);
        $('#rear_wiper').val(carParam.rear_wiper);

        $('#wiper_sensor').val(carParam.wiper_sensor);
        $('#rearview_electric').val(carParam.rearview_electric);
        $('#rearview_fold').val(carParam.rearview_fold);
        $('#rearview_heat').val(carParam.rearview_heat);
        $('#rearview_remember').val(carParam.rearview_remember);
        $('#front_foglight').val(carParam.front_foglight);
        $('#headlight_adjust').val(carParam.headlight_adjust);
        $('#headlight_clean').val(carParam.headlight_clean);
        $('#steering_shift').val(carParam.steering_shift);
        $('#hud').val(carParam.hud);
        $('#ecu').val(carParam.ecu);
        $('#sport_seat').val(carParam.sport_seat);
        $('#massage_seat').val(carParam.massage_seat);
        $('#lumbar_support').val(carParam.lumbar_support);

        $('#front_armrest').val(carParam.front_armrest);
        $('#rear_armrest').val(carParam.rear_armrest);
        $('#cd').val(carParam.cd);
        $('#dvd').val(carParam.dvd);
        $('#cartv').val(carParam.cartv);
        $('#center_screen').val(carParam.center_screen);
        $('#rear_screen').val(carParam.rear_screen);
        $('#manual_acc').val(carParam.manual_acc);
        $('#automatic_acc').val(carParam.automatic_acc);
        $('#rear_outlet').val(carParam.rear_outlet);
        $('#gra').val(carParam.gra);
        $('#gps').val(carParam.gps);
        $('#bluetooth').val(carParam.bluetooth);
        $('#cooler').val(carParam.cooler);

        $('#mfl').val(carParam.mfl);
        $('#keyless').val(carParam.keyless);
        $('#rear_cuphold').val(carParam.rear_cuphold);
        $('#cosmetic_mirror').val(carParam.cosmetic_mirror);
        $('#abs').val(carParam.abs);
        $('#ebd').val(carParam.ebd);
        $('#tcs').val(carParam.tcs);
        $('#drivers_airbag').val(carParam.drivers_airbag);
        $('#assistant_airbag').val(carParam.assistant_airbag);
        $('#front_headairbag').val(carParam.front_headairbag);
        $('#rear_headairbag').val(carParam.rear_headairbag);

        $('#front_sideairbag').val(carParam.front_sideairbag);
        $('#rear_sideairbag').val(carParam.rear_sideairbag);
        $('#launch_time').val(carParam.launch_time);
        $('#oilwear_1').val(carParam.oilwear_1);
        $('#oilwear_2').val(carParam.oilwear_2);
        $('#oilwear_3').val(carParam.oilwear_3);
        $('#oilwear_4').val(carParam.oilwear_4);
        $('#oilwear_5').val(carParam.oilwear_5);
        $('#avg_oilwear').val(carParam.avg_oilwear);
    },

    resetFormData: function () {
        this.setFormData({});
    },

});