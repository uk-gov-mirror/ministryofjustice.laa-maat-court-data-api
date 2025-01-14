package gov.uk.courtdata.laastatus.service;

import com.google.gson.Gson;
import gov.uk.courtdata.dto.CourtDataDTO;
import gov.uk.courtdata.enums.MessageType;
import gov.uk.courtdata.laastatus.builder.CourtDataDTOBuilder;
import gov.uk.courtdata.model.CaseDetails;
import gov.uk.courtdata.service.QueueMessageLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LaaStatusServiceUpdate {

    private final LaaStatusService laaStatusService;

    private final LaaStatusPostCDAService laaStatusPostCDAService;

    private final CourtDataDTOBuilder courtDataDTOBuilder;

    private final QueueMessageLogService queueMessageLogService;
    private Gson gson;

    public void updateMlaAndCDA(CaseDetails caseDetails) {

        queueMessageLogService.createLog(MessageType.LAA_STATUS_REST_CALL, gson.toJson(caseDetails));
        CourtDataDTO courtDataDTO = courtDataDTOBuilder.build(caseDetails);

        processLaaStatusServiceForCDA(courtDataDTO);
        processLaaStatusServiceForMLA(courtDataDTO);
    }

    private void processLaaStatusServiceForCDA(CourtDataDTO courtDataDTO) {
        log.info("Start - POST Rep Order update to CDA");
        laaStatusPostCDAService.process(courtDataDTO);
        log.info("Ends - POST Rep Order update to CDA");
    }

    private void processLaaStatusServiceForMLA(CourtDataDTO courtDataDTO) {

        if (!courtDataDTO.getCaseDetails().isOnlyForCDAService()) {
            log.info("Start - Update LAA status to MLA");
            laaStatusService.execute(courtDataDTO);
            log.info("Ends - After laa update to MLA");
        }
    }
}