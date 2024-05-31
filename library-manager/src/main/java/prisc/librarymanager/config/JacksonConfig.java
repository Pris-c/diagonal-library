package prisc.librarymanager.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Configuration class for Jackson ObjectMapper settings.
 * It ensures that Jackson fails on unknown properties during deserialization.
 */
@Configuration
public class JacksonConfig implements InitializingBean {
    @Autowired
    private RequestMappingHandlerAdapter converter;

    /**
     * Configures Jackson ObjectMapper to fail on unknown properties after properties are set.
     *
     * @throws Exception If an error occurs during configuration.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        configureJacksonToFailOnUnknownProperties();
    }

    /**
     * Configures Jackson ObjectMapper to fail on unknown properties.
     */
    private void configureJacksonToFailOnUnknownProperties() {
        MappingJackson2HttpMessageConverter httpMessageConverter = converter.getMessageConverters().stream()
                .filter(mc -> mc.getClass().equals(MappingJackson2HttpMessageConverter.class))
                .map(mc -> (MappingJackson2HttpMessageConverter) mc)
                .findFirst()
                .get();

        httpMessageConverter.getObjectMapper().enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}