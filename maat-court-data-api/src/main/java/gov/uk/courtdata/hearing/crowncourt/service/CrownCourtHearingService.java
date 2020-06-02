package gov.uk.courtdata.hearing.crowncourt.service;

import gov.uk.courtdata.hearing.crowncourt.impl.CrownCourtHearingResultedImpl;
import gov.uk.courtdata.hearing.crowncourt.impl.CrownCourtProcessingImpl;
import gov.uk.courtdata.hearing.crowncourt.validator.CrownCourtValidationProcessor;
import gov.uk.courtdata.model.hearing.CCOutComeData;
import gov.uk.courtdata.model.hearing.HearingResulted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CrownCourtHearingService {

    private final CrownCourtValidationProcessor crownCourtValidationProcessor;
    private final CrownCourtProcessingImpl crownCourtProcessingImpl;
    private final CrownCourtHearingResultedImpl crownCourtHearingResultedImpl;


    public void execute(final HearingResulted hearingResulted) {

        CCOutComeData CCOutComeData = hearingResulted.getCcOutComeData();
        if (isCrownCourtOutCome(CCOutComeData)) {
            executeCrownCourtOutCome(hearingResulted);
        }
        crownCourtHearingResultedImpl.execute(hearingResulted);

    }


    private void executeCrownCourtOutCome(HearingResulted hearingResulted) {

        crownCourtValidationProcessor.validate(hearingResulted);
        crownCourtProcessingImpl.execute(hearingResulted);
        log.info("Crown Court Outcome Processing has been Completed for MAAT ID: {}", hearingResulted.getMaatId());
    }

    private boolean isCrownCourtOutCome(CCOutComeData CCOutComeData) {
        return CCOutComeData != null
                && CCOutComeData.getCcooOutcome() != null
                && !CCOutComeData.getCcooOutcome().isEmpty();
    }
}