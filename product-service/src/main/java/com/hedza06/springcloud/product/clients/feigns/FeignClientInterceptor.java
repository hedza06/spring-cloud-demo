package com.hedza06.springcloud.product.clients.feigns;

import com.hedza06.springcloud.product.config.KeycloakCredentialsConfig;
import com.hedza06.springcloud.product.dto.KeycloakToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

    private final RestTemplate restTemplate;
    private final KeycloakCredentialsConfig credentialsConfig;


    @Override
    public void apply(RequestTemplate requestTemplate)
    {
        String accessToken = getAuthToken();
        requestTemplate.header("Authorization", "Bearer " + accessToken);
    }

    /**
     * Getting access token from KeyCloak Auth Server
     *
     * @return String value of access token
     */
    private String getAuthToken()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", credentialsConfig.getClientId());
        params.add("username", credentialsConfig.getClientUsername());
        params.add("password", credentialsConfig.getClientPassword());
        params.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        try
        {
            String url = credentialsConfig.getUrl();
            ResponseEntity<KeycloakToken> responseEntity = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, KeycloakToken.class
            );
            String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();

            Authentication authentication = new UsernamePasswordAuthenticationToken(accessToken, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return accessToken;
        }
        catch (RestClientException e) {
            log.error("Error occur while authenticating to KeyCloak. Message: {}", e.getMessage());
        }
        return null;
    }
}
