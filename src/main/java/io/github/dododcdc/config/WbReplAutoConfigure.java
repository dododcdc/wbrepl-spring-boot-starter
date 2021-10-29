package io.github.dododcdc.config;


import io.github.dododcdc.entity.WbParam;
import io.github.dododcdc.service.WbReplService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ConditionalOnClass(WbReplService.class)
@EnableConfigurationProperties(WbReplProperties.class)
public class WbReplAutoConfigure {

    private WbReplProperties wbReplProperties;

    private ApplicationContext applicationContext;

    private Environment env;

    public WbReplAutoConfigure(WbReplProperties wbReplProperties, ApplicationContext applicationContext, Environment env) {
        this.wbReplProperties = wbReplProperties;
        this.applicationContext = applicationContext;
        this.env = env;
    }

    @Bean(initMethod="start")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "wbrepl", value = "enabled", havingValue = "true")
    WbReplService wbReplService() {
        WbParam wbParam = new WbParam.Builder()
                .host(this.wbReplProperties.getHost())
                .port(this.wbReplProperties.getPort())
                .applicationContext(this.applicationContext)
                .env(this.env)
                .build();
        return new WbReplService(wbParam);
    }
}
