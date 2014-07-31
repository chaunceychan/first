package com.tiza.chen.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Descriptions
 * Created by Chauncey on 2014/7/16.
 */
@Component("batchInsert")
public class BatchInsert {
    private Logger logger = LoggerFactory.getLogger(BatchInsert.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void batchInsert() {
        String[] sqls = new String[10];
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                continue;
            }
            sqls[i] = "INSERT INTO A_CHEN (ID,NAME) VALUES (" + i + ",'N" + i + "')";
        }
        sqls[5] = "INSERT INTO A_CHEN (ID,NAME) VALUES (5,'N5',12)";

        try {
            jdbcTemplate.batchUpdate(sqls);
        } catch (DataAccessException e) {
            logger.error("批量执行出错,将进行剔除操作");
            executeBatchFail(sqls);
        }
    }

    /**
     * 批量执行出错的数组进行剔除更新操作
     *
     * @param array
     */
    private void executeBatchFail(String[] array) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.error("获取数据库连接出错,抛掉所有SQL语句,条数约:" + array.length);
            return;
        }
        boolean isGotWrong = false;
        for (int i = 0; i < array.length; i++) {
            String sql = array[i];
            try {
                int res = statement.executeUpdate(sql);
                if (res > 0 && !isGotWrong) {
                    connection.rollback();
                } else {
                    connection.commit();
                }
            } catch (SQLException e) {
                isGotWrong = true;
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    logger.error("wrong roll back");
                }
                logger.error("执行出错的SQL语句-" + sql);
            }
        }
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error("释放资源出错");
        }
    }
}
