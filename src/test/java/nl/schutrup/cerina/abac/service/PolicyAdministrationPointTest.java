package nl.schutrup.cerina.abac.service;

import nl.schutrup.cerina.abac.entities.Policy;
import nl.schutrup.cerina.abac.model.policy.AbstractPolicy;
import nl.schutrup.cerina.abac.model.policy.ConjuctionPolicy;
import nl.schutrup.cerina.abac.model.policy.DisjunctionPolicy;
import nl.schutrup.cerina.abac.model.policy.PermitPolicy;
import nl.schutrup.cerina.abac.model.rule.*;
import nl.schutrup.cerina.abac.repository.PolicyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static nl.schutrup.cerina.abac.model.policy.PolicyConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PolicyAdministrationPointTest {


    @InjectMocks
    private PolicyAdministrationPoint policyAdministrationPoint;

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private VisitorCounterRule visitorCounterRule;

    @Mock
    private DayTimeRule singleDayTimeRule;

    @Mock
    private DeleteResourceRule deleteResourceRule;

    @Mock
    private MaximumNumberOfCustomersRule maximumNumberOfCustomersRule;

    @Mock
    private MaximumNumberOfCustomersPerHourRule maximumNumberOfCustomersPerHourRule;

    @Mock
    private OpenClosedRule openClosedRule;

    @Mock
    private ConfirmationRule confirmationRule;

    @Test
    public void testLoadPolicies() {
        Policy woonplaatsPolicy = mock(Policy.class);
        Policy woonplaatsNaamPolicy = mock(Policy.class);
        Policy woonplaatsNaamLeeftijdPolicy = mock(Policy.class);
        Policy visitorCounter = mock(Policy.class);
        Policy maximumNumberOfCustomersPerHour = mock(Policy.class);
        Policy maximumNumberOfCustomers = mock(Policy.class);
        Policy deleteResource = mock(Policy.class);
        Policy openedDuringDayTime = mock(Policy.class);
        Policy openClosed = mock(Policy.class);
        Policy confirmation = mock(Policy.class);

        when(policyRepository.findPolicyByName(CITY_POLICY)).thenReturn(woonplaatsPolicy);
        when(policyRepository.findPolicyByName(CITY_AND_NAME_POLICY)).thenReturn(woonplaatsNaamPolicy);
        when(policyRepository.findPolicyByName(CITY_AND_NAME_AND_AGE_POLICY)).thenReturn(woonplaatsNaamLeeftijdPolicy);
        when(policyRepository.findPolicyByName(VISITOR_COUNTER_POLICY)).thenReturn(visitorCounter);
        when(policyRepository.findPolicyByName(MAXIMUM_NUMBER_OF_CUSTOMERS_PER_HOUR_POLICY)).thenReturn(maximumNumberOfCustomersPerHour);
        when(policyRepository.findPolicyByName(MAXIMUM_NUMBER_OF_CUSTOMERS_PER_DAY_POLICY)).thenReturn(maximumNumberOfCustomers);
        when(policyRepository.findPolicyByName(DELETE_RESOURCE_POLICY)).thenReturn(deleteResource);
        when(policyRepository.findPolicyByName(OPEN_DURING_DAYTIME_POLICY)).thenReturn(openedDuringDayTime);
        when(policyRepository.findPolicyByName(OPEN_CLOSED_POLICY)).thenReturn(openClosed);
        when(policyRepository.findPolicyByName(CONFIRMATION_POLICY)).thenReturn(confirmation);

        List<AbstractPolicy> policies = policyAdministrationPoint.loadPolicies();
        assertEquals(10, policies.size());
    }

    @Test
    public void testCreateConjuctionPolicy() {
        Policy policy = mock(Policy.class);

        ConjuctionPolicy res = (ConjuctionPolicy) policyAdministrationPoint.createConjuctionPolicy(policy, new ArrayList<>());
        assertEquals(ConjuctionPolicy.class, res.getClass());
    }

    @Test
    public void testCreateDisjunctionPolicy() {
        Policy policy = mock(Policy.class);

        DisjunctionPolicy res = (DisjunctionPolicy) policyAdministrationPoint.createDisjunctionPolicy(policy, new ArrayList<>());
        assertEquals(DisjunctionPolicy.class, res.getClass());
    }


    @Test
    public void testCreatePermitPolicy() {
        Policy policy = mock(Policy.class);

        PermitPolicy res = (PermitPolicy) policyAdministrationPoint.createPermitPolicy(policy, new ArrayList<>());
        assertEquals(PermitPolicy.class, res.getClass());
    }


}