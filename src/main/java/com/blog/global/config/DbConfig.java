package com.blog.global.config;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DbConfig {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUser;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Bean
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(driverClassName);
		config.setJdbcUrl(dbUrl);
		config.setUsername(dbUser);
		config.setPassword(dbPassword);

		// 커넥션 풀 설정
		config.setMaximumPoolSize(10); // 최대 커넥션 개수
		config.setMinimumIdle(2); // 최소 유휴 커넥션 개수
		config.setIdleTimeout(180000); // 최소 유휴 시간 (3분)
		config.setMaxLifetime(600000); // 커넥션 최대 생존 시간 (10분)
		config.setConnectionTimeout(30000); // 커넥션 획득 대기 시간 (30초)
		config.setValidationTimeout(5000); // 검증 쿼리 타임아웃 (5초)
		config.setLeakDetectionThreshold(30000); // 커넥션 누수 감지 (30초)
		config.setPoolName("HikariPool");

		// 유휴 커넥션 검증
		config.setConnectionTestQuery("SELECT 1");
		config.setAutoCommit(true);

		return new HikariDataSource(config);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
