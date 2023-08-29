package net.safedata.microservices.training.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class GraphQLDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphQLDemoApplication.class, args);
    }
}