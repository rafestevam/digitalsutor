package br.com.intelliapps.digitalsutor.conf;

import java.util.Locale;
import java.util.Properties;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//		registry.addViewController("/dashboard").setViewName("dashboard");
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
		return messageSource;
	}
	
	@Bean
	public MailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost("smtp.gmail.com");
		mailSender.setUsername("digitalsutor@gmail.com");
		mailSender.setPassword("c0c0m0l3!@3");
		mailSender.setPort(587);
		
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
		
		mailSender.setJavaMailProperties(mailProperties);
		
		return mailSender;
	}
	
	@Bean
	public CacheManager cacheManager() {
		
		return new ConcurrentMapCacheManager();
		
//		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
//				.maximumSize(100)
//				.expireAfterAccess(5, TimeUnit.MINUTES);
//		
//		GuavaCacheManager manager = new GuavaCacheManager();
//		manager.setCacheBuilder(builder);
//		
//		return manager;
		
	}
	
	@Bean
	public Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}
	
//	@Override
//	public void addFormatters(FormatterRegistry registry) {
//		registry.addConverter(new StringUsuarioConverter());
//	}
	
}
