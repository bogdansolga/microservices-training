package net.safedata.spring.training.complete.project.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * A simple {@link javax.sql.DataSource} configuration, which:
 * <ul>
 *     <li>configures the JPA repositories, using the {@link EnableJpaRepositories} annotation</li>
 *     <li>configures a custom {@link javax.sql.DataSource}, using the {@link HikariDataSource} class</li>
 * </ul>
 *
 * @author bogdan.solga
 */
@Configuration
@EnableJpaRepositories(basePackages = "net.safedata.spring.training.complete.project.repository")
@EnableTransactionManagement
@EntityScan(basePackages = "net.safedata.spring.training.complete.project.model")
public class DataSourceConfig {
}
