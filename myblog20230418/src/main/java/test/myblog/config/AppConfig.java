package test.myblog.config;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;


@Configuration
public class AppConfig implements WebMvcConfigurer{

    @Autowired
    private ServletContext servletContext;

    @Bean
    public ServletContext getServletContext() {
        return servletContext;
    }
    	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/article/**")
                .addResourceLocations("/upload/article/");
    }
}

