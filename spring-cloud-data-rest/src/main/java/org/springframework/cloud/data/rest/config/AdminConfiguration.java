/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.data.rest.config;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.data.module.deployer.ModuleDeployer;
import org.springframework.cloud.data.module.deployer.lattice.ReceptorModuleDeployer;
import org.springframework.cloud.data.module.deployer.local.LocalModuleDeployer;
import org.springframework.cloud.data.rest.controller.StreamController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Mark Fisher
 */
@Configuration
@EnableHypermediaSupport(type = HAL)
@EnableSpringDataWebSupport
@ComponentScan(basePackages = "org.springframework.cloud.data.rest.controller")
public class AdminConfiguration {

	@Bean
	@Profile("!cloud")
	public ModuleDeployer moduleDeployer() {
		return new LocalModuleDeployer();
	}

	@Configuration
	@Profile("cloud")
	protected static class CloudConfig {

		@Bean
		public ModuleDeployer moduleDeployer() {
			return new ReceptorModuleDeployer();
		}
	}

	@Bean
	public WebMvcConfigurer configurer() {
		return new WebMvcConfigurerAdapter() {

//			@Value("${xd.ui.allow_origin:http://localhost:9889}")
//			private String allowedOrigin;

			@Override
			public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//				RestTemplateMessageConverterUtil.installMessageConverters(converters);
//
//				for (HttpMessageConverter<?> httpMessageConverter : converters) {
//					if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
//						final MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) httpMessageConverter;
//
//						final ObjectMapper objectMapper = converter.getObjectMapper();
//						objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//						objectMapper.setDateFormat(new ISO8601DateFormatWithMilliSeconds());
//						objectMapper.addMixInAnnotations(StepExecution.class, StepExecutionJacksonMixIn.class);
//						objectMapper.addMixInAnnotations(ExecutionContext.class, ExecutionContextJacksonMixIn.class);
//					}
//				}

				converters.add(new ResourceHttpMessageConverter());
			}

//			@Override
//			public void addInterceptors(InterceptorRegistry registry) {
//				registry.addInterceptor(new AccessControlInterceptor(allowedOrigin));
//				registry.addInterceptor(webContentInterceptor());
//			}

		};
	}

}
