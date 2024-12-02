package com.dopamingu.be.domain.auth.application;

import com.dopamingu.be.domain.auth.dto.NaverUserInfoDto;
import com.dopamingu.be.domain.member.dao.MemberRepository;
import com.dopamingu.be.infra.config.oauth.NaverProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import lombok.RequiredArgsConstructor;
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
public class NaverService {

    private final NaverProperties properties;
    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;

    public String getIdToken(String code, String state) throws JsonProcessingException {
        URI uri =
                UriComponentsBuilder.fromUriString("https://nid.naver.com")
                        .path("/oauth2.0/token")
                        .queryParam("grant_type", properties.getGrantType())
                        .queryParam("client_id", properties.getId())
                        .queryParam("client_secret", properties.getSecret())
                        .queryParam("redirect_uri", properties.getRedirectUri())
                        .queryParam("code", code)
                        .queryParam("state", state)
                        .encode()
                        .build()
                        .toUri();

        /* HTTP Header 생성 */
        HttpHeaders headers = new HttpHeaders();

        /* HTTP Body 생성 */
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        RequestEntity<MultiValueMap<String, String>> requestEntity =
                RequestEntity.post(uri).headers(headers).body(body);

        /* HTTP request 보내기 */
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        String accessToken = jsonNode.get("access_token").asText();

        return accessToken;
    }

    public NaverUserInfoDto getNaverUserInfo(String accessToken) throws JsonProcessingException {
        URI uri =
                UriComponentsBuilder.fromUriString("https://openapi.naver.com")
                        .path("/v1/nid/me")
                        .encode()
                        .build()
                        .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        RequestEntity<MultiValueMap<String, String>> requestEntity =
                RequestEntity.post(uri).headers(headers).body(new LinkedMultiValueMap<>());

        /* HTTP 요청 보내기 */
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        String id = jsonNode.get("response").get("id").asText();
        String email = jsonNode.get("response").get("email").asText();

        return new NaverUserInfoDto(id, email);
    }
}
