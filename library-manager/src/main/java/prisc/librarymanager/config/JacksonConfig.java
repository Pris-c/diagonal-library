package prisc.librarymanager.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.Optional;

/**
 * Configuration class for Jackson ObjectMapper settings.
 * It ensures that Jackson fails on unknown properties during deserialization.
 * This is important for catching unintended data issues early in the development process.
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
        Optional<MappingJackson2HttpMessageConverter> httpMessageConverterOptional = converter.getMessageConverters().stream()
                .filter(mc -> mc.getClass().equals(MappingJackson2HttpMessageConverter.class))
                .map(mc -> (MappingJackson2HttpMessageConverter) mc)
                .findFirst();
    }

}