package com.hedza06.springcloud.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("scope")
    private String tokenScope;

    /**
     * Converting keycloak scopes (String) to List of Strings
     *
     * @param tokenScope token scope (client scopes from keycloak)
     * @return List of Client Scopes (acting as resources/permissions)
     */
    public static List<String> convertScopesToList(String tokenScope)
    {
        return StringUtils.isNotBlank(tokenScope)
            ? Arrays.asList(tokenScope.split(" "))
            : Collections.emptyList();
    }

    /**
     * Converting list of client scopes to granted (simple) authorities
     *
     * @param scopes client scopes from keycloak
     * @return List of Granted Authorities
     */
    public static List<GrantedAuthority> convertScopesToGrantedAuthorities(List<String> scopes)
    {
        return scopes != null && !scopes.isEmpty()
            ? scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            : Collections.emptyList();
    }
}
