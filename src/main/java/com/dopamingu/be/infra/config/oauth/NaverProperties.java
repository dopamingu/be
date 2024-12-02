package com.dopamingu.be.infra.config.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.naver.client")
@Getter
@Setter
public class NaverProperties {
    private String id;
    private String secret;
    private String redirectUri;
    private String grantType;
    //    private String admin;
}
