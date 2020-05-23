package deploy;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import deploy.jpaconfig.JskCustomRepositoryFactoryBean;

@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = JskCustomRepositoryFactoryBean.class)
@EnableTransactionManagement
//TODO:NOW Oracle support is skipped
public class SbootOracleConfig {





	@Bean
	public ServletWebServerFactory  tomcatFactory() {



        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/OracleDs");
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", "oracle.jdbc.OracleDriver");
                resource.setProperty("url", "jdbc:oracle:thin:@localhost:1521:orcl");
                resource.setProperty("username", "c##rashmi");
                resource.setProperty("password", "tiger");
                resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");

                context.getNamingResources().addResource(resource);
            }


        };


        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }
        private Connector redirectConnector() {
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setPort(8080);
            connector.setSecure(false);
            connector.setRedirectPort(8443);
            return connector;
        }

        
        private Connector createSslConnector() {
        	Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        	Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        	File keystore=null;
			try {
				keystore = new ClassPathResource("deployMeDev.jks").getFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//File keystore = new FileSystemResource("/certs/deployMeDev.jks").getFile();
			//File truststore = new ClassPathResource("keystore").getFile();
			connector.setScheme("https");
			connector.setSecure(true);
			connector.setPort(8443);
			protocol.setSSLEnabled(true);
			protocol.setKeystoreFile(keystore.getAbsolutePath());
			protocol.setKeystorePass("changeit");
			//protocol.setTruststoreFile(truststore.getAbsolutePath());
			//protocol.setTruststorePass("changeit");
			protocol.setKeyAlias("tomcat");
			return connector;
        }


	@Bean
	public DataSource jndiDataSource() {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:comp/env/jdbc/OracleDs");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		try {
			bean.afterPropertiesSet();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (DataSource) bean.getObject();
	}

	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(

	) {

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(jndiDataSource());
		factoryBean.setPackagesToScan("deploy.jsk.entity");
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setGenerateDdl(true);
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);

		Properties props = new Properties();
		props.put("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
		// props.put("hibernate.hbm2ddl.auto", "update");

		factoryBean.setJpaProperties(props);
		factoryBean.afterPropertiesSet();

		return factoryBean;

	}

	@Primary
	@Bean
	public PlatformTransactionManager transactionManager(

	) throws IllegalArgumentException, NamingException {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		transactionManager.afterPropertiesSet();

		return transactionManager;
	}

}
