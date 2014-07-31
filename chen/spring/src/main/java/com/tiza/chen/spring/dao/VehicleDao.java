package com.tiza.chen.spring.dao;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.tiza.chen.spring.App;
import com.tiza.chen.spring.bean.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Descriptions
 * Created by Chauncey on 2014/7/15.
 */
@Component
public class VehicleDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String sql = "SELECT VI_ID,VI_VIN_CODE,VI_TERMINAL_CODE,VI_OWN_TYPE,VI_TONNAGE_TYPE,VI_MODEL FROM VEHICLE_INFO WHERE VI_IS_DELETED = 0";

    /**
     * 数据库和实体类映射封装私有类
     */
    private class VehicleRowMapper implements RowMapper<Vehicle> {

        @Override
        public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(rs.getInt("VI_ID"));
            vehicle.setVinCode(Strings.nullToEmpty(rs.getString("VI_VIN_CODE")));
            vehicle.setTerminalCode(Strings.nullToEmpty(rs.getString("VI_TERMINAL_CODE")));
            vehicle.setOwnType(rs.getInt("VI_OWN_TYPE"));
            vehicle.setTonnageType(rs.getInt("VI_TONNAGE_TYPE"));
            vehicle.setModel(rs.getInt("VI_MODEL"));
            return vehicle;
        }
    }

    /**
     * 查询所有车辆信息并更新缓存
     */
    public void queryAll() {
        Cache<String, Vehicle> vehicleCache = (Cache<String, Vehicle>) App.cacheManager.getCache("vehicleCache").getNativeCache();
        List<Vehicle> list = jdbcTemplate.query(sql, new VehicleRowMapper());
        for (Vehicle vehicle : list) {
            vehicleCache.put(vehicle.getTerminalCode(), vehicle);
        }
        for (String key : vehicleCache.asMap().keySet()) {
            Vehicle cacheVehicle = vehicleCache.getIfPresent(key);
            if (!list.contains(cacheVehicle)) {
                vehicleCache.invalidate(key);
            }
        }
    }
}
