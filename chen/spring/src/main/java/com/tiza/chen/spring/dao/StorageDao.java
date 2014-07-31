package com.tiza.chen.spring.dao;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.tiza.chen.spring.App;
import com.tiza.chen.spring.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Descriptions
 * Created by Chauncey on 2014/7/15.
 */
@Component
public class StorageDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String sql = "SELECT STI_ID,STI_NAME,STI_TYPE,STI_AREATYPE,STI_RAILCONTENT FROM STORAGE_INFO WHERE STI_IS_DELETED = 0";

    /**
     * 仓库信息数据库表和实体类映射处理类
     */
    private class StorageRowMapper implements RowMapper<Storage> {

        @Override
        public Storage mapRow(ResultSet rs, int rowNum) throws SQLException {
            Storage storage = new Storage();
            storage.setId(rs.getInt("STI_ID"));
            storage.setName(Strings.nullToEmpty(rs.getString("STI_NAME")));
            storage.setType(rs.getInt("STI_TYPE"));
            int areaType = rs.getInt("STI_AREATYPE");
            String content = rs.getString("STI_RAILCONTENT");
            Area area = null;
            switch (areaType) {
                case 1://圆形
                    List<Circle> circleList = JSON.parseArray(content, Circle.class);
                    if (null != circleList || circleList.size() > 0) {
                        area = circleList.get(0);
                    }
                    break;
                case 2://多边形
                    List<Point> pointList = JSON.parseArray(content, Point.class);
                    Point[] points = new Point[pointList.size()];
                    pointList.toArray(points);
                    Region region = new Region();
                    region.setPts(points);
                    area = region;
                    break;
                case 3://行政区域
                    List<Division> divisionList = JSON.parseArray(content, Division.class);
                    Group group = new Group();
                    group.setList(divisionList);
                    area = group;
                    break;
                default:
                    break;
            }
            if (null != area) {
                storage.setArea(area);
            }
            return storage;
        }
    }

    /**
     * 查询所有仓库信息并更新缓存
     */
    public void queryAll() {
        Cache<Integer, Storage> storageCache = (Cache<Integer, Storage>) App.cacheManager.getCache("storageCache").getNativeCache();
        List<Storage> list = jdbcTemplate.query(sql, new StorageRowMapper());
        for (Storage storage : list) {
            storageCache.put(storage.getId(), storage);
        }
        for (Integer key : storageCache.asMap().keySet()) {
            Storage cacheStorage = storageCache.getIfPresent(key);
            if (!list.contains(cacheStorage)) {
                storageCache.invalidate(key);
            }
        }
    }
}
