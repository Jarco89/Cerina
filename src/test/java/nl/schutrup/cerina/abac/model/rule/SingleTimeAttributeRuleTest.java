package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyConstants;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Library;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.abac.model.subject.SubjectAttribute;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class SingleTimeAttributeRuleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleTimeAttributeRuleTest.class);

    private final static LocalDate LOCAL_DATE = LocalDate.of(2021, 04, 10);

    @InjectMocks
    SingleTimeAttributeRule singleTimeAttributeRule = new SingleTimeAttributeRule(PolicyConstants.CORONA_SNELTEST_DATUM, PolicyConstants.CORONA_ANTILICHAMEN_DAGEN_IN_VERLEDEN, Operation.READ_REQUEST);

    @Mock
    private Clock clock;

    private Clock fixedClock;

    @Test
    public void testTimeRuleValid() {
        fixedClock = Clock.fixed(LOCAL_DATE.atTime(11, 15, 00).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.CORONA_SNELTEST_DATUM, "2021-04-10 11:30:00");
        subject.addAttribute(subjectAttribute);
        Resource resource = new Library();
        Operation operation = Operation.READ_REQUEST;

        PolicyResult policyResult = singleTimeAttributeRule.evaluate(subject, resource, operation);
        assertEquals(PolicyResult.PERMIT, policyResult);
    }

    @Test
    public void testTimeRuleInvalidValid() {
        fixedClock = Clock.fixed(LOCAL_DATE.atTime(12, 01, 00).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.CORONA_SNELTEST_DATUM, "2021-04-10 11:30:00");
        subject.addAttribute(subjectAttribute);
        Resource resource = new Library();
        Operation operation = Operation.READ_REQUEST;

        PolicyResult policyResult = singleTimeAttributeRule.evaluate(subject, resource, operation);
        assertEquals(PolicyResult.DENY, policyResult);
    }

}