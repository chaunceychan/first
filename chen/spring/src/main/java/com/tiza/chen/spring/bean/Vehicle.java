package com.tiza.chen.spring.bean;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Descriptions
 * Created by Chauncey on 2014/7/15.
 */
public class Vehicle implements Serializable {
    private int id;
    private String vinCode;
    private int model;
    private String terminalCode;
    private int tonnageType;
    private int ownType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public int getTonnageType() {
        return tonnageType;
    }

    public void setTonnageType(int tonnageType) {
        this.tonnageType = tonnageType;
    }

    public int getOwnType() {
        return ownType;
    }

    public void setOwnType(int ownType) {
        this.ownType = ownType;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",id).add("vinCode",vinCode).add("model",model).add("terminalCode",terminalCode).add("tonnageType",tonnageType).add("ownType",ownType).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;

        Vehicle vehicle = (Vehicle) o;

        if (!terminalCode.equals(vehicle.terminalCode)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return terminalCode.hashCode();
    }
}
