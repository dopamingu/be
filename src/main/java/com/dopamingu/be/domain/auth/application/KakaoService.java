package com.dopamingu.be.domain.auth.application;

import com.dopamingu.be.infra.config.oauth.KakaoProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final KakaoProperties properties;
    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KakaoService.class);

    public String getIdToken(String code) throws JsonProcessingException {
        /* 요청 URL 만들기 */
        URI uri =
                UriComponentsBuilder.fromUriString("https://kauth.kakao.com")
                        .path("/oauth/token")
                        .encode()
                        .build()
                        .toUri();

        /* HTTP Header 생성 */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        /* HTTP Body 생성 */
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", properties.getId());
        body.add("client_secret", properties.getSecret());
        body.add("redirect_uri", properties.getRedirectUri());
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity =
                RequestEntity.post(uri).headers(headers).body(body);

        /* HTTP 요청 보내기 */
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        /* HTTP 응답 (JSON) -> 액세스 토큰 파싱 */
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());

        /*  access token 반환 */
        // return KakaoTokenLoginResponse
        return jsonNode.get("id_token").asText();
    }
}
