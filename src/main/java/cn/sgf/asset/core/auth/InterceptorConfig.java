package cn.sgf.asset.core.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/***
 * @Desciption: 拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Bean
	public AuthInterceptor getAuthInterceptor() {
		return new AuthInterceptor();
	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getAuthInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/api/login");
	}

}
