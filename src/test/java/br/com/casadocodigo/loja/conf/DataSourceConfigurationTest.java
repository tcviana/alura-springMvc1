package br.com.casadocodigo.loja.conf;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import javax.sql.DataSource;

public class DataSourceConfigurationTest {
	
	@Bean
	@Profile("test")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc://mysql://localhost:3306/casadocodigo_test");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		return dataSource;
	}
}
