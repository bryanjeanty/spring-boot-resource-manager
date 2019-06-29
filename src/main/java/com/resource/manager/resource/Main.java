package com.resource.manager.resource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableJpaRepositories(basePackages = "com.example.demo")
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.example.demo" })
@EntityScan("com.example.demo")
public class Main {
	private static HikariDataSource hds;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig();

		config.setJdbcUrl(System.getenv("MSSQL_AWS_URL"));
		config.setUsername(System.getenv("MSSQL_AWS_USER"));
		config.setPassword(System.getenv("MSSQL_AWS_PASS"));
		config.setDriverClassName(System.getenv("MSSQL_DRIVER"));
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		hds = new HikariDataSource(config);

		return hds;
	}

	public static Connection getConnection() throws SQLException {
		return hds.getConnection();
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.SQL_SERVER);
		adapter.setGenerateDdl(true);
		adapter.setShowSql(true);
		return adapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource,
			JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean emBean = new LocalContainerEntityManagerFactoryBean();
		emBean.setDataSource(dataSource);
		emBean.setJpaVendorAdapter(jpaVendorAdapter);
		emBean.setPackagesToScan("com.resource.manager.resource");
		return emBean;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}
