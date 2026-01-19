package pl.akmf.ksef.sdk.api;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationToken;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationTokenStatus;
import pl.akmf.ksef.sdk.client.model.auth.GenerateTokenResponse;
import pl.akmf.ksef.sdk.client.model.auth.KsefTokenRequest;
import pl.akmf.ksef.sdk.client.model.auth.QueryTokensResponse;

import java.util.ArrayList;
import java.util.List;

import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class TokensController {
    private final DefaultKsefClient ksefClient;

    @PostMapping("/token/generate")
    public GenerateTokenResponse generateKsefToken(@RequestBody KsefTokenRequest ksefTokenRequest,
                                                   @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.generateKsefToken(ksefTokenRequest, authToken);
    }


    @PostMapping("/token/query")
    public List<AuthenticationToken> queryKsefTokens(@RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        List<AuthenticationTokenStatus> status = List.of(AuthenticationTokenStatus.ACTIVE);
        Integer pageSize = 10;

        QueryTokensResponse response = ksefClient.queryKsefTokens(status, null, null, null, null, pageSize, authToken);
        List<AuthenticationToken> authenticationTokens = new ArrayList<>(response.getTokens());
        while (Strings.isNotBlank(response.getContinuationToken())) {
            response = ksefClient.queryKsefTokens(status, null, null, null, response.getContinuationToken(), pageSize, authToken);
            authenticationTokens.addAll(response.getTokens());
        }

        return authenticationTokens;
    }

    @GetMapping("/token/retrieve")
    public AuthenticationToken getKsefToken(@RequestParam String referenceNumber, @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.getKsefToken(referenceNumber, authToken);

    }

    @PostMapping("token/revoke")
    public void revokeKsefToken(@RequestParam String referenceNumber, @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        ksefClient.revokeKsefToken(referenceNumber, authToken);
    }
}
