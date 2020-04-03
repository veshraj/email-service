package com.test.emailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class EmailserviceApplication {
	private static ApplicationContext context;

	public static void main(String[] args) {
		/**
		 * Set current date so that log file can be generated as per required
		 */
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		System.setProperty("currentDate", formatter.format(date));
		SpringApplication.run(EmailserviceApplication.class, args);
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public static DataSource getDataSource() {
		return (DataSource) context.getBean("dataSource");
	}


	@Autowired
	public void context(ApplicationContext appContext) {
		context = appContext;
	}

	@Bean
	WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {

			}
		};
	}

}
