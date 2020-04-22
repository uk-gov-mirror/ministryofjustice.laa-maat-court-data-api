package gov.uk.courtdata.model.hearing;


import lombok.*;

@ToString
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HearingDetails   {

    private int maatId;
    private String caseUrn;
    private String ccooOutcome;
    private String ccooOutcomeDate;
    private String crownCourtCode;
    private String jurisdictionType;
    private String benchWarrantIssuedYn;
    private String ccImprisioned;
    private String appealType;;

}