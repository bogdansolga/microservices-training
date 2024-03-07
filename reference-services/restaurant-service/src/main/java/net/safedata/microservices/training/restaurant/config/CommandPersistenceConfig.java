package net.safedata.microservices.training.restaurant.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "net.safedata.microservices.training.restaurant.domain.repository.command",
        transactionManagerRef = "commandEntityManager"
)
@EntityScan(basePackages = "net.safedata.microservices.training.restaurant.domain.model.command")
public class CommandPersistenceConfig {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    @Value("${spring.datasource.command.url}")
    private String url;

    @Value("${spring.datasource.command.username}")
    private String userName;

    @Value("${spring.datasource.command.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Primary
    @Bean
    public javax.sql.DataSource commandDataSource() {
        final HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setPoolName("command-connection-pool");
        hikariConfig.setMaximumPoolSize(AVAILABLE_PROCESSORS * 2);
        hikariConfig.setMinimumIdle(AVAILABLE_PROCESSORS / 2);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setIdleTimeout(60000);
        hikariConfig.setMaxLifetime(120000);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);

        return getHikariDataSource(hikariConfig);
    }

    @Bean(destroyMethod = "close")
    public HikariDataSource getHikariDataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean commandEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(commandDataSource());
        em.setPackagesToScan("net.safedata.microservices.training.restaurant.domain.model.command");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }
}
