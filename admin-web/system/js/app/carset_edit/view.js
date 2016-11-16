var CarsetEditView = Class.extend({
    init: function () {
    },

    getFormData: function (old) {
        var data = {};

        if (old != undefined) {
            data = old;
        }

        data.title = $('#title').val();
        data.image = $('#image').val();
        data.carlevel = $('#carlevel').val();
        data.fullName = $('#fullName').val();
        data.letter = $('#letter').val();
        data.maxOut = $('#maxOut').val();
        data.minOut = $('#minOut').val();
        data.maxguide = $('#maxguide').val();
        data.minguide = $('#minguide').val();
        return data;
    },

    setFormData: function (data) {
        $('#title').val(data.title);
        $('#image').val(data.image);
        $('#image_url').attr('src', data.image);
        $('#carlevel').val(data.carlevel);
        $('#fullName').val(data.fullName);
        $('#letter').val(data.letter);
        $('#maxOut').val(data.maxOut);
        $('#minOut').val(data.minOut);
        $('#maxguide').val(data.maxguide);
        $('#minguide').val(data.minguide);
    },

    resetFormData: function () {
        this.setFormData({});
    }
});