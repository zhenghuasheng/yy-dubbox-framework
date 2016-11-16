var UserModel = Class.extend({
    cacheUser: [],
    selUser: null,

    init: function () {
    },

    setUser: function(user) {
        this.cacheUser[user.mid] = user;
    },

    getUser: function(userId) {
        return this.cacheUser[userId];
    }
});
