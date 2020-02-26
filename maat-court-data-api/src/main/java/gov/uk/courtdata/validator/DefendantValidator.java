package gov.uk.courtdata.validator;

import gov.uk.courtdata.entity.DefendantMAATDataEntity;
import gov.uk.courtdata.exception.ValidationException;
import gov.uk.courtdata.repository.DefendantMAATDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <code>DefendantValidator</code> to check existence of MAAT defendant details.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DefendantValidator implements IValidator<DefendantMAATDataEntity, Integer> {


    private final DefendantMAATDataRepository defendantMAATDataRepository;

    /**
     * @param maatId
     * @return
     * @throws ValidationException
     */
    @Override
    public Optional<DefendantMAATDataEntity> validate(Integer maatId) throws ValidationException {

        final Optional<DefendantMAATDataEntity> defendantViewEntity =
                defendantMAATDataRepository.findBymaatId(maatId);
        System.out.println(defendantViewEntity);
        defendantViewEntity.orElseThrow(() -> new ValidationException("MAAT Defendant details not available."));

        return defendantViewEntity;
    }
}
