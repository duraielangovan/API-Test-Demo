package com.securonix.at.common.base;

import com.securonix.at.common.util.testreport.ExtentLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class BaseDAO {

    @Autowired
    private ExtentLogger extentLogger;

    public BaseDAO() {
        super();
    }

    /**
     *
     * @param sql
     * @param dataSource
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T> List<T> doSelectAll(String sql, DataSource dataSource, Class<T> clazz) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            extentLogger.logInfo(sql);
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper(clazz));
        } catch (Exception var6) {
            return new ArrayList<T>();
        }
    }

    /**
     *
     * @param sql
     * @param dataSource
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T> T doSelectOne(String sql, DataSource dataSource, Class<T> clazz) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            extentLogger.logInfo(sql);
            Object result = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper(clazz),new Object[0]);
            return clazz.cast(result);

        } catch (Exception var6) {
            return null;
        }
    }

    /**
     *
     * @param sql
     * @param dataSource
     * @return
     */
    protected List<String> doSelectAll(String sql, DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            extentLogger.logInfo(sql);
            return jdbcTemplate.queryForList(sql, String.class);
        } catch (Exception var5) {
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param sql
     * @param dataSource
     * @return
     */
    protected String doSelectOne(String sql, DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            extentLogger.logInfo(sql);
            String result = jdbcTemplate.queryForObject(sql, String.class, new Object[0]);
            return result;
        } catch (Exception var5) {
            return null;
        }
    }

    /**
     *
     * @param sql
     * @param dataSource
     */
    protected void doUpdateQuery(String sql, DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            extentLogger.logInfo(sql);
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            extentLogger.logInfo(e.toString());
        }

    }

    /**
     *
     * @param sql
     * @param dataSource
     * @return
     */
    protected int doInsertRecord(String sql, DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        extentLogger.logInfo(sql);
        return jdbcTemplate.update(sql);

           }

    protected int doInsertEmployeeRecord(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.update("INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?)", 12234, "Bill", "Gates", "USA");
    }

    public DataSource getCustomDataSource(String url, String userName,String password){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        DataSource dataSource = (DataSource) ctx.getBean("customDataSource",url,userName,password);
        ctx.close();
        return dataSource;
    }
}
