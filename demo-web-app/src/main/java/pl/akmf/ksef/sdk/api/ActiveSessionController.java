package pl.akmf.ksef.sdk.api;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.session.AuthenticationListItem;
import pl.akmf.ksef.sdk.client.model.session.AuthenticationListResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.session.SessionsQueryResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionsQueryResponseItem;

import java.util.List;

import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;
import static pl.akmf.ksef.sdk.client.Headers.CONTINUATION_TOKEN;

@RequiredArgsConstructor
@RestController
public class ActiveSessionController {
    private final DefaultKsefClient ksefClient;

    @PostMapping("/session/query/{pageSize}")
    public List<SessionsQueryResponseItem> getSessions(@RequestBody SessionsQueryRequest request,
                                                       @PathVariable Integer pageSize,
                                                       @RequestHeader(name = AUTHORIZATION) String authToken,
                                                       @RequestHeader(name = CONTINUATION_TOKEN, required = false, defaultValue = "") String continuationToken) throws ApiException {
        SessionsQueryResponse response = ksefClient.getSessions(request, pageSize, continuationToken, authToken);
        List<SessionsQueryResponseItem> sessionsList = response.getSessions();

        while (Strings.isNotBlank(response.getContinuationToken())) {
            response = ksefClient.getSessions(request, pageSize, response.getContinuationToken(), authToken);
            sessionsList.addAll(response.getSessions());
        }
        return sessionsList;
    }

    @PostMapping("/session/active/{pageSize}")
    public List<AuthenticationListItem> getActiveSessions(@PathVariable Integer pageSize,
                                                          @RequestHeader(name = AUTHORIZATION) String authToken,
                                                          @RequestHeader(name = CONTINUATION_TOKEN, required = false, defaultValue = "") String continuationToken) throws ApiException {
        AuthenticationListResponse response = ksefClient.getActiveSessions(pageSize, continuationToken, authToken);
        List<AuthenticationListItem> authenticationList = response.getItems();

        while (Strings.isNotBlank(response.getContinuationToken())) {
            response = ksefClient.getActiveSessions(pageSize, response.getContinuationToken(), authToken);
            authenticationList.addAll(response.getItems());
        }

        return authenticationList;
    }

    @DeleteMapping("/session/revoke/current")
    public void revokeCurrentSession(@RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        ksefClient.revokeCurrentSession(authToken);
    }

    @DeleteMapping("/session/revoke/{sessionReferenceNumber}")
    public void revokeSession(@PathVariable String sessionReferenceNumber,
                              @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        ksefClient.revokeSession(sessionReferenceNumber, authToken);
    }
}
