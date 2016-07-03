package uk.co.birchlabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by jamiebirch on 03/07/2016.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = EmbeddedServletContainerFactory.class)
@Profile("test")
public class TestConfig {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TestConfig.class);
        app.setWebEnvironment(false);

        Properties properties = new Properties();
        properties.put("spring.jpa.database-platform", "uk.co.birchlabs.SQLiteDialect");
        properties.put("hibernate.dialect", "uk.co.birchlabs.SQLiteDialect");

        app.setDefaultProperties(properties);
        ConfigurableApplicationContext ctx = app.run(args);
    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
//        dataSourceBuilder.type(org.sqlite.SQLiteDataSource.class);
//        dataSourceBuilder.url("jdbc:sqlite:pokedex.sqlite");
        dataSourceBuilder.url("jdbc:sqlite:normalised_jmdict_e_and_kanjidic.sqlite");
        return dataSourceBuilder.build();
    }
}
