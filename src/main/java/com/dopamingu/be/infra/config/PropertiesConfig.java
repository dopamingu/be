package com.dopamingu.be.infra.config;

import com.dopamingu.be.infra.config.jwt.JwtProperties;
import com.dopamingu.be.infra.config.oauth.KakaoProperties;
import com.dopamingu.be.infra.config.oauth.NaverProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({JwtProperties.class, KakaoProperties.class, NaverProperties.class})
@Configuration
public class PropertiesConfig {}
