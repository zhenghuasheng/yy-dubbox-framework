/**
 * Created by wunan on 16-2-1.
 */
package com.etong.pt.data.service;

import com.etong.pt.data.customer.PtCustomer;
import com.etong.pt.data.user.PtUser;
import com.etong.pt.data.vehicle.PtVehicle;

import java.util.List;

public class UserInfo {
    private PtUser user;
    private PtCustomer cust;
    private List<PtVehicle> vehicles;

    public PtUser getUser() {
        return user;
    }

    public void setUser(PtUser user) {
        this.user = user;
    }

    public PtCustomer getCust() {
        return cust;
    }

    public void setCust(PtCustomer cust) {
        this.cust = cust;
    }

    public List<PtVehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<PtVehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
