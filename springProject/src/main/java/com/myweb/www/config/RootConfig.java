package com.myweb.www.config;





import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@MapperScan(basePackages = {"com.myweb.www.repository"}) // mapper 스캔 설정
@ComponentScan(basePackages = {"com.myweb.www.service","com.myweb.www.handler","com.myweb.www.exception"}) // 서비스 객체 스캔 설정
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableScheduling

public class RootConfig {
   // DB 설정
   // 전과 달라진 부분 log4jdbc-log4j2 사용
   // hikariCP 사용
   
   @Autowired
   ApplicationContext applicationContext;
   
   @Bean
   public DataSource dataSource() {
      HikariConfig hikariCongig = new HikariConfig();
      // log4jdbc-log4j2의 드라이버 클래스 url 사용
      hikariCongig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
      hikariCongig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/springdb");
      hikariCongig.setUsername("springUser");
      hikariCongig.setPassword("mysql");
      
      hikariCongig.setMinimumIdle(5); // 최소 유휴 커넥션 개수
      hikariCongig.setMaximumPoolSize(5); // 최대 커넥션 개수(일반적으로 유휴 커넥션 개수와 동일하게 설정)
      
      hikariCongig.setConnectionTestQuery("SELECT now()"); // test 쿼리 용도
      hikariCongig.setPoolName("springHikariCP");
      
      // config 추가 설정
      // cache 사용 여부 설정 true
      hikariCongig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
      
      // mysql 드라이버가 연결당 cache statement의 수에 관한 설정 default 25 (250 ~ 500사이 권장)
      
      hikariCongig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
      // mysql connection당 캐싱할 preparedStatement의 개수 지정 옵션 default 256(true 설정하면 기본값 256)
      hikariCongig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "true");
      
      // mysql 서버에서 최신 이슈가 있을경우 지원받는 설정
      hikariCongig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
      
      HikariDataSource hikariDataSource = new HikariDataSource(hikariCongig);
      return hikariDataSource;
   }
   
   @Bean
   public SqlSessionFactory sqlSessionFactory() throws Exception {
      
      SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
      
      sqlSessionFactoryBean.setDataSource(dataSource());
      
      sqlSessionFactoryBean.setMapperLocations(
            applicationContext.getResources("classpath:/mappers/*.xml"));
      
      sqlSessionFactoryBean.setConfigLocation(
            applicationContext.getResource("classpath:/MybatisConfig.xml"));
      
      return (SqlSessionFactory)sqlSessionFactoryBean.getObject();
   }
   
   //트렌젝션 매니저 빈 설정
   @Bean
   public DataSourceTransactionManager transactionManager() {
	   return new DataSourceTransactionManager(dataSource());
   }
   
}