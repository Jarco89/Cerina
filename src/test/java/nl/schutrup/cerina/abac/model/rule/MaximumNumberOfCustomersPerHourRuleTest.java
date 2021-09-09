package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.entities.Statistic;
import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.abac.repository.StatisticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.StandardEnvironment;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MaximumNumberOfCustomersPerHourRuleTest {

    private final static LocalDate LOCAL_DATE = LocalDate.of(2021, 01, 01).atStartOfDay().toLocalDate();

    @InjectMocks
    private MaximumNumberOfCustomersPerHourRule maximumNumberOfCustomersPerHourRule;

    @Mock
    private StatisticRepository statisticRepository;

    @Mock
    private StandardEnvironment standardEnvironment;

    @Mock
    private Clock clock;

    private Clock fixedClock;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationRuleTest.class);

    @BeforeEach
    void beforeTest() {
        fixedClock = Clock.fixed(LOCAL_DATE.atTime(03, 01, 01).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

    }

    @DisplayName("Test toegestane gast")
    @Test
    public void testTwoAllowedCustomers() {
        Subject subject = new Subject();
        Resource resource = new Resource();
        Operation operation = Operation.READ_REQUEST;

        List<Statistic> statisticList = new ArrayList<>();
        statisticList.add(new Statistic());

        LocalDateTime start = LOCAL_DATE.atTime(LocalTime.now().getHour(), 00, 00);
        LocalDateTime end = LOCAL_DATE.atTime(LocalTime.now().getHour(), 59, 59);

        when(statisticRepository.findAllByVisitDateTimeBetween(start, end)).thenReturn(statisticList);
        when(standardEnvironment.getProperty("max.visitors.per.hour", Integer.class)).thenReturn(2);

        PolicyResult policyResult = maximumNumberOfCustomersPerHourRule.evaluate(subject, resource, operation);
        assertEquals(PolicyResult.PERMIT, policyResult);
    }

    @DisplayName("Test geweigerde gast")
    @Test
    public void testOneAllowedCustomers() {
        Subject subject = new Subject();
        Resource resource = new Resource();
        Operation operation = Operation.READ_REQUEST;

        List<Statistic> statisticList = new ArrayList<>();
        statisticList.add(new Statistic());

        LocalDateTime start = LOCAL_DATE.atTime(LocalTime.now().getHour(), 00, 00);
        LocalDateTime end = LOCAL_DATE.atTime(LocalTime.now().getHour(), 59, 59);

        when(statisticRepository.findAllByVisitDateTimeBetween(start, end)).thenReturn(statisticList);
        when(standardEnvironment.getProperty("max.visitors.per.hour", Integer.class)).thenReturn(1);

        PolicyResult policyResult = maximumNumberOfCustomersPerHourRule.evaluate(subject, resource, operation);
        assertEquals(PolicyResult.DENY, policyResult);
    }

}