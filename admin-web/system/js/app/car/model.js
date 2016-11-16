var CarModel = Class.extend({
    carset: null,
    cars: null,

    init: function () {
        this.carset = global.getParameter('carset');
    },

    cacheCar: function (data) {
        this.cars = [];

        if (data == undefined) {
            return;
        }

        for (var i = 0; i < data.length; ++i) {
            var car = data[i];
            this.cars[car.vid] = car;
        }
    },

    getCacheCar: function(id) {
        return this.cars[id];
    }
});
