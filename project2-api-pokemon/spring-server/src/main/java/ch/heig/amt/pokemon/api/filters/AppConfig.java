package ch.heig.amt.pokemon.api.filters;

import ch.heig.amt.pokemon.api.filters.isLoggedFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    @Bean
    public FilterRegistrationBean <isLoggedFilter> filterLoggedRegistrationBean() {
        FilterRegistrationBean < isLoggedFilter > registrationBean = new FilterRegistrationBean();
        isLoggedFilter isLoggedFilter = new isLoggedFilter();

        registrationBean.setFilter(isLoggedFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }


}