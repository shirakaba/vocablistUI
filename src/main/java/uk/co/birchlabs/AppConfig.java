package uk.co.birchlabs;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by jamiebirch on 21/06/2016.
 */
@Configuration
public class AppConfig {
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
