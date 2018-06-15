package br.com.intelliapps.digitalsutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.intelliapps.digitalsutor.conf.SecurityConfiguration;
import br.com.intelliapps.digitalsutor.controllers.HomeController;
import br.com.intelliapps.digitalsutor.daos.UsuarioDAO;

@SpringBootApplication(scanBasePackageClasses= {HomeController.class, SecurityConfiguration.class, UsuarioDAO.class})
public class BasicConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(BasicConfiguration.class, args);
	}	
	
//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://localhost:3306/digitalsutor");
//		dataSource.setUsername("root");
//		dataSource.setPassword("1234");
//		return dataSource;
//	}
	
}
