package br.com.ams.backcatalogo.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableAutoConfiguration
public class WebMvcConfigurerAbs implements WebMvcConfigurer {

	@Value("${diretorio.catalogos}")
	String sistemaDiretorioCatalogos;

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		log.info("addCorsMappings");

		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE",
				"OPTIONS");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/catalogos/**").addResourceLocations("file:///" + sistemaDiretorioCatalogos + "/")
				.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS)).resourceChain(false)
				.addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));

		registry.addResourceHandler("/*.js", "/*.css", "/*.ttf", "/*.woff", "/*.woff2", "/*.eot", "/*.svg", "/*.jpg")
				.addResourceLocations("classpath:/static/")
				.setCacheControl(CacheControl.maxAge(15, TimeUnit.DAYS).cachePrivate().mustRevalidate())
				.resourceChain(true).addResolver(new PathResourceResolver());

		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/")
				.setCacheControl(CacheControl.maxAge(15, TimeUnit.DAYS).cachePrivate().mustRevalidate())
				.resourceChain(true).addResolver(new PathResourceResolver());

	}

}