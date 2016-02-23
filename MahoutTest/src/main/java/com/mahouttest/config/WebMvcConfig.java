package com.mahouttest.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
	    List<MediaType> supportedMediaTypes=new ArrayList<>();
	    supportedMediaTypes.add(MediaType.APPLICATION_JSON);
	    supportedMediaTypes.add(MediaType.TEXT_PLAIN);
	    MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();
	    converter.setObjectMapper(new HibernateAwareObjectMapper());
	    converter.setPrettyPrint(true);
	    converter.setSupportedMediaTypes(supportedMediaTypes);
	    converters.add(converter);
	    super.configureMessageConverters(converters);
	}
}
