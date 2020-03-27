package gov.uk.courtdata.laaStatus.service;

import feign.Param;
import feign.RequestLine;
import gov.uk.courtdata.model.Token;
import gov.uk.courtdata.model.laastatus.LaaStatusUpdate;
import reactor.core.publisher.Mono;


public interface CourtDataApiService {

    @RequestLine("POST /oauth/token?grant_type=client_credentials&client_id={client_id}&client_secret={client_secret}")
    Mono<Token> getOAuthToken(@Param("client_id") final String clientId, @Param("client_secret") final String clientSecret);

    @RequestLine("POST /api/internal/v1/laa_references")
    Mono<Void> postLaaStatusUpdate(LaaStatusUpdate laaStatusUpdate);

}