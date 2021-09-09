package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.entities.Policy;
import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.abac.repository.PolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.StandardEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ConfirmationRuleTest {

    @InjectMocks
    private ConfirmationRule confirmationRule;

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private StandardEnvironment standardEnvironment;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationRuleTest.class);

    @BeforeEach
    void beforeTest() {
        Policy policy = new Policy();
        policy.setCurrentValue(String.valueOf(0));
        when(standardEnvironment.getProperty("confirmation.users")).thenReturn("1");
        when(policyRepository.findPolicyByName("ConfirmationPolicy")).thenReturn(policy);
    }

    @DisplayName("Test confirmationrule")
    @Test
    public void testConfirmation() {
        Subject subject = new Subject();
        Resource resource = new Resource();
        Operation operation = Operation.READ_REQUEST;

        PolicyResult policyResult = confirmationRule.evaluate(subject, resource, operation);
        assertEquals(PolicyResult.PERMIT, policyResult);

        Policy firstVisit = policyRepository.findPolicyByName("ConfirmationPolicy");

        assertEquals("1", firstVisit.getCurrentValue());

        PolicyResult second = confirmationRule.evaluate(subject, resource, operation);

        Policy secondVisit = policyRepository.findPolicyByName("ConfirmationPolicy");

        assertEquals(PolicyResult.PERMIT, second);
        assertEquals("2", secondVisit.getCurrentValue());
    }

}