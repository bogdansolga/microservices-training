package net.safedata.microservices.training.customer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "net.safedata.microservices.training.customer.domain.repository")
@EnableTransactionManagement
public class PersistenceConfig {
}
