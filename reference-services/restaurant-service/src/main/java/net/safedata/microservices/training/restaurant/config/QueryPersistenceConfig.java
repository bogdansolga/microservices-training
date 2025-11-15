package net.safedata.microservices.training.restaurant.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "net.safedata.microservices.training.restaurant.domain.repository.query",
        entityManagerFactoryRef = "queryEntityManager",
        transactionManagerRef = "queryTransactionManager"
)
@EntityScan(basePackages = "net.safedata.microservices.training.restaurant.domain.model.query")
public class QueryPersistenceConfig {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    @Value("${spring.datasource.query.url}")
    private String url;

    @Value("${spring.datasource.query.username}")
    private String userName;

    @Value("${spring.datasource.query.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public javax.sql.DataSource queryDataSource() {
        final HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setPoolName("query-connection-pool");
        hikariConfig.setMaximumPoolSize(AVAILABLE_PROCESSORS * 2);
        hikariConfig.setMinimumIdle(AVAILABLE_PROCESSORS / 2);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setIdleTimeout(60000);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);

        return getQueryDataSource(hikariConfig);
    }

    @Bean(destroyMethod = "close")
    public HikariDataSource getQueryDataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean queryEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(queryDataSource());
        em.setPackagesToScan("net.safedata.microservices.training.restaurant.domain.model.query");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Bean
    public PlatformTransactionManager queryTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(queryEntityManager().getObject());
        return transactionManager;
    }
}
