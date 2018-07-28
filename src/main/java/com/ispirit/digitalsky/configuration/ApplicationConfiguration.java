package com.ispirit.digitalsky.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispirit.digitalsky.document.ImportDroneApplication;
import com.ispirit.digitalsky.document.LocalDroneAcquisitionApplication;
import com.ispirit.digitalsky.repository.*;
import com.ispirit.digitalsky.repository.storage.FileSystemStorageService;
import com.ispirit.digitalsky.repository.storage.StorageService;
import com.ispirit.digitalsky.service.*;
import com.ispirit.digitalsky.service.api.*;
import com.sendgrid.SendGrid;
import freemarker.template.TemplateExceptionHandler;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration {

    @Value("${server.port}")
    private int port;

    @Value("${server.http-port}")
    private int httpPort;

    @Value("${DEFAULT_FROM_EMAIL_ID:no-reply@digitalsky.com}")
    private String defaultFromEmailId;

    @Value("${SEND_GRID_API_KEY:default}")
    private String sendGridApiKey;

    @Value("${RESET_PASSWORD_PATH:http://192.168.33.10:3000/resetPassword}")
    private String resetPasswordBasePath;

    @Value("${JWT_KEYSTORE_PATH:classpath:keystore.jks}")
    private String jwtKeyStorePath;

    @Value("${JWT_KEYSTORE_PASSWORD:cacms789}")
    private String jwtKeyStorePassword;

    @Value("${JWT_KEYSTORE_TYPE:jks}")
    private String jwtKeyStoreType;

    @Value("${JWT_KEY_ALIAS:tomcat-localhost}")
    private String jwtKeyAlias;

    @Value("${JWT_KEY_PASSWORD:cacms789}")
    private String jwtKeyPassword;

    @Value("${JWT_EXPIRY_TIME_IN_DAYS:30}")
    private String jwtExpiryInDays;

    @Value("${FILE_STORAGE_LOCATION:uploads}")
    private String storageLocation;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userService;
    }

    @Bean
    public UserService userService(
            UserRepository userRepository,
            EmailService emailService,
            DroneAcquisitionApplicationService<ImportDroneApplication> importDroneService,
            UAOPApplicationService uaopApplicationService,
            UINApplicationService uinApplicationService,
            DroneAcquisitionApplicationService<LocalDroneAcquisitionApplication> localDroneService) {
        return new CustomUserDetailService(userRepository,
                emailService,
                localDroneService,
                importDroneService,
                uaopApplicationService,
                uinApplicationService,
                resetPasswordBasePath);
    }

    @Bean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public SecurityTokenService securityTokenService(ResourceLoader resourceLoader) {
        return new JwtTokenService(resourceLoader, Integer.parseInt(jwtExpiryInDays), jwtKeyStorePath, jwtKeyStorePassword, jwtKeyStoreType, jwtKeyAlias, jwtKeyPassword);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(SecurityTokenService securityTokenService, UserService userService) {
        return new JwtAuthenticationFilter(userService, securityTokenService);
    }

    @Bean
    StorageService storageService() {
        return new FileSystemStorageService(storageLocation);
    }

    @Bean
    public freemarker.template.Configuration freemarkerConfiguration() {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_20);
        cfg.setClassForTemplateLoading(this.getClass(), "/emailTemplates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocalizedLookup(false);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }

    @Bean
    EmailService sendGridEmailService(freemarker.template.Configuration freemarkerConfiguration) {
        SendGrid sendGrid = new SendGrid(sendGridApiKey);
        return new SendGridEmailService(sendGrid, freemarkerConfiguration, defaultFromEmailId);
    }

    @Bean
    PilotService pilotService(PilotRepository pilotRepository) {
        return new PilotServiceImpl(pilotRepository);
    }

    @Bean
    IndividualOperatorService individualOperatorService(IndividualOperatorRepository individualOperatorRepository, OrganizationOperatorRepository organizationOperatorRepository) {
        return new IndividualOperatorServiceImpl(individualOperatorRepository, organizationOperatorRepository);
    }

    @Bean
    OrganizationOperatorService organizationOperatorService(OrganizationOperatorRepository organizationOperatorRepository, IndividualOperatorRepository individualOperatorRepository) {
        return new OrganizationOperatorServiceImpl(organizationOperatorRepository, individualOperatorRepository);
    }

    @Bean
    DirectorService directorService(DirectorRepository directorRepository) {
        return new DirectorServiceImpl(directorRepository);
    }

    @Bean
    DroneAcquisitionApplicationService<LocalDroneAcquisitionApplication> localDroneAcquisitionService(DroneAcquisitionApplicationRepository<LocalDroneAcquisitionApplication> droneAcquisitionRepository, StorageService documentRepository, EntityRepository entityRepository) {
        return new DroneAcquisitionApplicationServiceImpl<LocalDroneAcquisitionApplication>(droneAcquisitionRepository, documentRepository, entityRepository);
    }

    @Bean
    DroneAcquisitionApplicationService<ImportDroneApplication> importedDroneAcquisitionService(DroneAcquisitionApplicationRepository<ImportDroneApplication> droneAcquisitionRepository, StorageService documentRepository, EntityRepository entityRepository) {
        return new DroneAcquisitionApplicationServiceImpl<ImportDroneApplication>(droneAcquisitionRepository, documentRepository, entityRepository);
    }

    @Bean
    UAOPApplicationService uaopApplicationService(StorageService storageService, UAOPApplicationRepository uaopApplicationRepository) {
        return new UAOPApplicationServiceImpl(uaopApplicationRepository, storageService);
    }

    @Bean
    UINApplicationService uinApplicationService(StorageService storageService, UINApplicationRepository uinApplicationRepository) {
        return new UINApplicationServiceImpl(uinApplicationRepository, storageService);
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
        return tomcat;
    }

    @Bean
    public ObjectMapper myObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper;
    }

    private Connector getHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(httpPort);
        connector.setSecure(false);
        connector.setRedirectPort(port);
        return connector;
    }

}
