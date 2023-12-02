package com.securonix.at;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import oracle.jdbc.pool.OracleDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.sqlite.SQLiteDataSource;
import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Configuration
@ComponentScan
@PropertySource(value={"environment.properties","database.properties"})
@PropertySource(value = "report.properties", ignoreResourceNotFound = true)
public class TestConfig {

    @Value("${report.name:#{null}}")
    private String reportName;

    @Value("${report.log.console:#{null}}")
    private boolean reportLogConsole;

    @Value("${service.env}")
    private String serviceEnv;

    @Value("${db.env}")
    private String dbEnv;

    @Value("${db.soar.postgres.url}")
    private String dbPostgresUrl;

    @Value("${db.oracle.url}")
    private String dbOracleUrl;

    @Value("${db.sqlLite.url}")
    private String dbSQLLiteUrl;

    @Value("${db.read.user}")
    private String dbReadUser;

    @Value("${db.read.password}")
    private String dbReadUserPassword;

    @Value("${db.write.user}")
    private String dbWriteUser;

    @Value("${db.write.password}")
    private String dbWriteUserPassword;

    @Value("${kafka.security.protocol}")
    private String kafkaSecurityProtocol;

    @Value("${kafka.keyPassword}")
    private String kafkaKeyPassword;

    @Value("${kafka.truststoreLocation}")
    private String kafkaTruststorePath;

    @Value("${kafka.truststorePassword}")
    private String kafkaTruststorePassword;

    @Value("${kafka.keystoreLocation}")
    private String kafkaKeystorePath;

    @Value("${kafka.keystorePassword}")
    private String kafkaKeystorePassword;

    public String getKafkaKeyPassword() {
        return kafkaKeyPassword;
    }

    public String getKafkaKeystorePath() {
        return kafkaKeystorePath;
    }

    public String getKafkaTruststorePassword() {
        return kafkaTruststorePassword;
    }

    public String getKafkaKeystorePassword() {
        return kafkaKeystorePassword;
    }

    public String getReportName() {
        return reportName;
    }
    @Bean
    public boolean isReportLogConsole() {
        return reportLogConsole;
    }

    public String getServiceEnv() {
        return serviceEnv;
    }

    public String getDbEnv() {
        return dbEnv;
    }

    public String getOracleDbUrl() {
        return dbOracleUrl;
    }

    public String getDbReadUser() {
        return dbReadUser;
    }

    public String getDbReadUserPassword() {
        return dbReadUserPassword;
    }

    public String getDbWriteUser() {
        return dbWriteUser;
    }

    public String getDbWriteUserPassword() {
        return dbWriteUserPassword;
    }

    public String getKafkaSecurityProtocol() {
        return kafkaSecurityProtocol;
    }

    public String getKafkaTruststorePath() {
        return kafkaTruststorePath;
    }



  @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
  }

    @Bean(name="postgresDataSource")
    public DataSource postgresDataSource(){
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setUrl(dbPostgresUrl);
        return source;
    }

    @Bean(name="readDataSource")
    public DataSource dbReadDataSource(){
        SQLiteDataSource source = new SQLiteDataSource();
        source.setUrl(dbSQLLiteUrl);
        return source;
  }

    @Bean(name="writeDataSource")
    public DataSource dbWriteDataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL(dbSQLLiteUrl);
        dataSource.setUser(dbWriteUser);
        dataSource.setPassword(dbWriteUserPassword);
        return dataSource;
    }

    @Bean(name="customDataSource")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DataSource customDataSource(String url,String user, String password) throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public ExtentReports extentTestFactory(){
        String fs= File.separator;
       ExtentReports reports = new ExtentReports();
       LocalDateTime localDate = LocalDateTime.now();
       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
       String reportDateTime = dtf.format(localDate);
       ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output"+fs+"reports"+fs+
               reportName+"_"+reportDateTime+".html");
        htmlReporter.config().setReportName(getReportName());
        htmlReporter.config().setDocumentTitle(getReportName());
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setTimelineEnabled(false);

        reports.setSystemInfo("Environment",getServiceEnv());
        reports.setSystemInfo("DB",getDbEnv());
        reports.attachReporter(htmlReporter);
        reports.setReportUsesManualConfiguration(true);

        return reports;
    }
}
