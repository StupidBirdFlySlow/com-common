package com.cloud.server.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    @DependsOn("argumentsResolver")
    public MethodReturnValueHandler mgMethodReturnValueHandler() {
        MethodReturnValueHandler formatJsonReturnValueHandler = new MethodReturnValueHandler(getMessageConverters());
        return formatJsonReturnValueHandler;
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(mgMethodReturnValueHandler());
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.extendMessageConverters(converters);
        for (HttpMessageConverter converter :
                converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(new Jackson2ObjectMapperBuilder().simpleDateFormat("yyyy-MM-dd HH:mm:ss").serializationInclusion(JsonInclude.Include.NON_NULL).build());
            }
        }
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Configuration
    public class MvcConfig extends WebMvcConfigurerAdapter {

        @Bean("argumentsResolver")
        public ArgumentsResolver mgArgumentsResolver() {
            ArgumentsResolver mgArgumentsResolver = new ArgumentsResolver();
            return mgArgumentsResolver;
        }
        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(mgArgumentsResolver());
            super.addArgumentResolvers(argumentResolvers);
        }

        @Override
        public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
            super.extendMessageConverters(converters);
            for (HttpMessageConverter converter :
                    converters) {
                if (converter instanceof MappingJackson2HttpMessageConverter) {
                    ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(new Jackson2ObjectMapperBuilder().simpleDateFormat("yyyy-MM-dd HH:mm:ss").serializationInclusion(JsonInclude.Include.NON_NULL).build());
                }
            }
        }
    }

    @Configuration
    public class InitializingAdvice implements InitializingBean {

        @Autowired
        private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

        @Override
        public void afterPropertiesSet() throws Exception {
            List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
            List<HandlerMethodReturnValueHandler> handlers = new ArrayList(returnValueHandlers);
//            adapter.getCustomReturnValueHandlers()
            this.decorateHandlers(handlers);
            requestMappingHandlerAdapter.setReturnValueHandlers(handlers);
        }


        private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
            for (HandlerMethodReturnValueHandler handler : handlers) {
                if (handler instanceof RequestResponseBodyMethodProcessor) {
                    MethodReturnValueHandler decorator = mgMethodReturnValueHandler();
                    int index = handlers.indexOf(handler);
                    handlers.set(index, decorator);
                    break;
                }
            }
        }
    }


}