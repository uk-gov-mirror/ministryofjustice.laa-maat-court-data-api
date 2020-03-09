package gov.uk.courtdata.laaStatus.validator;

import com.google.gson.Gson;
import gov.uk.courtdata.builder.TestEntityDataBuilder;
import gov.uk.courtdata.builder.TestModelDataBuilder;
import gov.uk.courtdata.model.CaseDetails;
import gov.uk.courtdata.model.MessageCollection;
import gov.uk.courtdata.model.Offence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LaaStatusValidatorTest {

    private LaaStatusValidator laaStatusValidator;
    private CaseDetails caseDetails;

    @Before
    public void build() {
        TestModelDataBuilder testModelDataBuilder = new TestModelDataBuilder(new TestEntityDataBuilder(),new Gson());
        laaStatusValidator = new LaaStatusValidator();
        caseDetails =  testModelDataBuilder.getCaseDetails();
    }

    @Test
    public void givenFailIOJDecisionAndGrantedLAAStatus_whenLaaStatusValidatorIsInvoked_thenValidationFailed() {

        //given
        Offence offence = caseDetails.getDefendant().getOffences().get(0);
        offence.setIojDecision(0);
        offence.setLegalAidStatus("GR");

        //when
        MessageCollection messageCollection = laaStatusValidator.validate(caseDetails);

        //then
        assertThat(messageCollection.getMessages().get(0)).isEqualTo("Cannot Grant Legal Aid on a Failed or Pending IOJ - See offence 001");

    }

    @Test
    public void givenPassIOJDecisionAndFailLAAStatus_whenLaaStatusValidatorIsInvoked_thenValidationFailed() {

        //given
        Offence offence = caseDetails.getDefendant().getOffences().get(0);
        offence.setIojDecision(1);
        offence.setLegalAidStatus("FB");

        //when
        MessageCollection messageCollection = laaStatusValidator.validate(caseDetails);

        //then
        assertThat(messageCollection.getMessages().get(0)).isEqualTo("Cannot Pass IOJ and Fail Legal Aid on IOJ - See offence 001");

    }

    @Test
    public void givenNAIOJDecisionAndGrantedLAAStatus_whenLaaStatusValidatorIsInvoked_thenValidationFailed() {

        //given
        Offence offence = caseDetails.getDefendant().getOffences().get(0);
        offence.setIojDecision(3);
        offence.setLegalAidStatus("GQ");

        //when
        MessageCollection messageCollection = laaStatusValidator.validate(caseDetails);

        //then
        assertThat(messageCollection.getMessages().get(0)).isEqualTo("Cannot Grant Legal Aid on a n/a IOJ - See offence 001");

    }

    @Test
    public void givenPassIOJDecisionAndGrantedLAAStatus_whenLaaStatusValidatorIsInvoked_thenValidationPassed() {

        //given
        Offence offence = caseDetails.getDefendant().getOffences().get(0);
        offence.setIojDecision(1);
        offence.setLegalAidStatus("G2");

        //when
        MessageCollection messageCollection = laaStatusValidator.validate(caseDetails);

        //then
        assertThat(messageCollection.getMessages().isEmpty()).isEqualTo(true);

    }

    @Test
    public void givenFailIOJDecisionAndFailLAAStatus_whenLaaStatusValidatorIsInvoked_thenValidationPassed() {

        //given
        Offence offence = caseDetails.getDefendant().getOffences().get(0);
        offence.setIojDecision(0);
        offence.setLegalAidStatus("FJ");

        //when
        MessageCollection messageCollection = laaStatusValidator.validate(caseDetails);

        //then
        assertThat(messageCollection.getMessages().isEmpty()).isEqualTo(true);

    }
}
