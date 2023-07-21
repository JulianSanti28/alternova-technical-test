package co.soft.technical.test.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = { "co.soft" })
@EnableTransactionManagement
@ComponentScan(basePackages = "co.soft")
@EntityScan(basePackages = "co.soft")
@Profile("test")
public class DataSourceConfigurationTest {
}
