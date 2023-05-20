/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.conf;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Elio Raymundo
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "pesoCabalEntityManagerFactory",
        transactionManagerRef = "pesoCabalTransactionManager", basePackages = {
            "gt.umg.beneficiocafe.repository.pesocabal"
        })
public class PesoCabalDb {
    @Autowired
    private Environment environment;


    @Bean(name = "pesoCabalDataSource")
    //@ConfigurationProperties(prefix ="pesocabal.datasource")
    public DataSource pesoCabalDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("pesocabal.datasource.url")); 
        dataSource.setUsername(environment.getProperty("pesocabal.datasource.username")); 
        dataSource.setPassword(environment.getProperty("pesocabal.datasource.password")); 
        dataSource.setDriverClassName(environment.getProperty("pesocabal.datasource.driver-class-name"));
        //return DataSourceBuilder.create().build();
        /*return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/pesodb")
                .username("postgres")
                .password("1234")
                .build();*/
        return dataSource;
    }

    @Bean(name = "pesoCabalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            /*@Qualifier("pesoCabalDataSource") DataSource dataSource*/) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        //factoryBean.setDataSource(dataSource);
        factoryBean.setDataSource(pesoCabalDataSource());
        factoryBean.setPackagesToScan("gt.umg.beneficiocafe.models.pesocabal");
        HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendor);
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", environment.getProperty("pesocabal.jpa.database-platform"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("pesocabal.jpa.hibernate.ddl-auto"));
        //factoryBean.setJpaPropertyMap(properties);
        return factoryBean;
    }

    @Bean("pesoCabalTransactionManager")
    public PlatformTransactionManager platformTransactionManager(
           /* @Qualifier("pesoCabalEntityManagerFactory") EntityManagerFactory entityManagerFactory*/) {
        JpaTransactionManager jpaTranscactionManger = new JpaTransactionManager();
        jpaTranscactionManger.setEntityManagerFactory(entityManagerFactory().getObject());
        return jpaTranscactionManger;
    }
}
