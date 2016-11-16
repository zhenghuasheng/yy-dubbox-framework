var LoginModel = Class.extend({
    data: {remeber : false},

    init: function () {
        var param = global.getLocalParam('remeber');

        if (param != undefined) {
            this.data = param;
        }
    },

    isRemeber: function() {
        if (this.data == undefined) {
            return false;
        }

        return this.data.remeber;
    }
});
