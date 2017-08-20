package com.development.backend.ng.springmvc;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.development.backend.ng.springmvc.repository.CommentRepository;
import com.development.backend.ng.springmvc.repository.PostRepository;
import com.development.backend.ng.springmvc.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;



@ComponentScan(basePackages = { "com.app.*", "com.app.api.*", "com.app.api.customer.*","com.app.api.employee.*","com.app.api.product.*","com.app.identity.*" } )
@Configuration
@EnableJpaRepositories(basePackageClasses = {CommentRepository.class , PostRepository.class , UserRepository.class})
public class RootConfig {

	
	
	@Bean
	  public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("org.postgresql.Driver");
	    dataSource.setUrl("jdbc:postgresql://localhost:5433/protei_");
	    dataSource.setUsername("protei");
	    dataSource.setPassword("protei");
	    return dataSource;
	  }
	
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPersistenceXmlLocation("classpath*:/META-INF/persistence.xml");
		entityManagerFactory.setPersistenceUnitName("localEntity");

		entityManagerFactory.setPackagesToScan("com.development.backend.ng.springmvc.domain");

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

		Properties additionalProperties = new Properties();
		additionalProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		additionalProperties.put("hibernate.show_sql", true);
		additionalProperties.put("hibernate.hbm2ddl.auto", "update");
		entityManagerFactory.setJpaProperties(additionalProperties);

		return entityManagerFactory;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}


	@Autowired
	private DataSource dataSource;

	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate template = new JdbcTemplate(dataSource());
		return template;
	}
	@Bean
	public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder(){
		org.springframework.security.crypto.password.PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean
	public ClientHttpRequestFactory httpRequestFactory() {
	    return new HttpComponentsClientHttpRequestFactory();
	}
	
	
	@Bean
	public RestTemplate restTemplate() {
	    RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
	    List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
	    for (HttpMessageConverter<?> converter : converters) {
	        if (converter instanceof Jaxb2RootElementHttpMessageConverter) {
	        	Jaxb2RootElementHttpMessageConverter xmlConverter = (Jaxb2RootElementHttpMessageConverter) converter;
	        	
	        	xmlConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("*", "xml", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
	        }
	    }
	    return restTemplate;
	}
}