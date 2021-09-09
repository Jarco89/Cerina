package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class DayTimeRuleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationRuleTest.class);

     private final static LocalDate LOCAL_DATE = LocalDate.of(2021, 01, 01).atStartOfDay().toLocalDate();

    @InjectMocks
    private DayTimeRule dayTimeRule;

    @Mock
    private Clock clock;

    private Clock fixedClock;

    @Test
    public void testDayTimeRuleValid() {
        fixedClock = Clock.fixed(LOCAL_DATE.atTime(9, 01, 01).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        Subject subject = new Subject();
        Resource resource = new Resource();
        Operation operation = Operation.READ_REQUEST;

        PolicyResult policyResult = dayTimeRule.evaluate(subject, resource, operation);
        assertEquals(PolicyResult.PERMIT, policyResult);
    }

    @Test
    public void testDayTimeRuleInValid() {
        fixedClock = Clock.fixed(LOCAL_DATE.atTime(19, 01, 01).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        Subject subject = new Subject();
        Resource resource = new Resource();
        Operation operation = Operation.READ_REQUEST;

        PolicyResult policyResult = dayTimeRule.evaluate(subject, resource, operation);
        assertEquals(PolicyResult.DENY, policyResult);
    }
}