/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.conf;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
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
@EnableJpaRepositories(entityManagerFactoryRef = "beneficioagricultorEntityManagerFactory",
        transactionManagerRef = "beneficioagricultorTransactionManager", basePackages={
            "gt.umg.beneficiocafe.repository.beneficioagricultor"
        })
public class BeneficioAgricultorDb {
    @Autowired
    private Environment environment;
        
    
    @Bean(name = "beneficioDataSource")
    //@ConfigurationProperties(prefix ="spring.datasource")
    public DataSource beneficioDataSource(){
       DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("spring.datasource.url")); 
        dataSource.setUsername(environment.getProperty("spring.datasource.username")); 
        dataSource.setPassword(environment.getProperty("spring.datasource.password")); 
        dataSource.setDriverClassName(environment.getProperty("spring.jpa.database-platform"));
        //return DataSourceBuilder.create().build();
        /*return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/pesodb")
                .username("postgres")
                .password("1234")
                .build();*/
        return dataSource;
    }
    
    @Bean(name = "beneficioagricultorEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
    ){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        //factoryBean.setDataSource(dataSource);
        factoryBean.setDataSource(beneficioDataSource());
        factoryBean.setPackagesToScan("gt.umg.beneficiocafe.models.beneficioagricultor");
        HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendor);
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", environment.getProperty("spring.jpa.database-platform"));
        //factoryBean.setJpaPropertyMap(properties);
        return factoryBean;
    }
    
    @Bean("beneficioagricultorTransactionManager")
    @Primary
    public PlatformTransactionManager platformTransactionManager(
         /* @Qualifier("pesoCabalEntityManagerFactory") EntityManagerFactory entityManagerFactory*/) {
        JpaTransactionManager jpaTranscactionManger = new JpaTransactionManager();
        jpaTranscactionManger.setEntityManagerFactory(entityManagerFactory().getObject());
        return jpaTranscactionManger;
    }
}
