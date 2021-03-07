package it.course.piadac4.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "it.course.piadac4.repository", transactionManagerRef = "jpaTransactionManager")
@PropertySource(value = { "classpath:application.properties" })
@EnableTransactionManagement
public class JpaConfig {

	private static final String[] ENTITYMANAGER_PACKAGES_TO_SCAN = { "it.course.piadac4.entity" };

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		
		System.out.println("Datasource creation");

		String username = env.getProperty("spring.datasource.username");
		String password = env.getProperty("spring.datasource.password");
		String driverClass = env.getProperty("spring.datasource.driver-class-name");
		String url = env.getProperty("spring.datasource.url");

		return DataSourceBuilder.create()
				.username(username)
				.password(password)
				.url(url)
				.driverClassName(driverClass)
				.build();
	}

	@Bean
	public JpaTransactionManager jpaTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
	
	private HibernateJpaVendorAdapter vendorAdaptor() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		return vendorAdapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
		entityManagerFactoryBean.setJpaProperties(addProperties());

		return entityManagerFactoryBean;
	}

	private Properties addProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
		properties.setProperty("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
		properties.setProperty("spring.datasource.initialization-mode", env.getProperty("spring.datasource.initialization-mode"));
		properties.setProperty("spring.datasource.data", env.getProperty("spring.datasource.data"));
		//properties.setProperty("hibernate.globally_quoted_identifiers", env.getProperty("hibernate.globally_quoted_identifiers"));
		
		return properties; 
	}

}
