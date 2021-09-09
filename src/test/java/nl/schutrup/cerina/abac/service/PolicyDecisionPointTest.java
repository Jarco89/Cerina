package nl.schutrup.cerina.abac.service;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.MetaPolicy;
import nl.schutrup.cerina.abac.model.policy.PermitPolicy;
import nl.schutrup.cerina.abac.model.policy.PolicyConstants;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PolicyDecisionPointTest {

    @InjectMocks
    private PolicyDecisionPoint policyDecisionPoint;

    @Mock
    private PolicyInformationPoint policyInformationPoint;

    @Mock
    private PolicyAdministrationPoint policyAdministrationPoint;

    @Mock
    private MetaPolicy metaPolicy;


    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of("scenario-1-jwt", PolicyConstants.CITY_POLICY),
                Arguments.of("scenario-2-jwt", PolicyConstants.CITY_AND_NAME_POLICY),
                Arguments.of("scenario-4-jwt", PolicyConstants.CITY_AND_NAME_AND_AGE_POLICY),
                Arguments.of("scenario-5-jwt", PolicyConstants.VISITOR_COUNTER_POLICY),
                Arguments.of("scenario-6-jwt", PolicyConstants.MAXIMUM_NUMBER_OF_CUSTOMERS_PER_HOUR_POLICY),
                Arguments.of("scenario-7-jwt", PolicyConstants.MAXIMUM_NUMBER_OF_CUSTOMERS_PER_DAY_POLICY),
                Arguments.of("scenario-8-jwt", PolicyConstants.DELETE_RESOURCE_POLICY),
                Arguments.of("scenario-9-jwt", PolicyConstants.CONFIRMATION_POLICY),
                Arguments.of("scenario-10-jwt", PolicyConstants.OPEN_DURING_DAYTIME_POLICY),
                Arguments.of("scenario-11-jwt", PolicyConstants.OPEN_CLOSED_POLICY)
        );
    }


    @ParameterizedTest
    @MethodSource("provideParameters")
    public void testScenario(String scenario, String policy) {
        PermitPolicy permitPolicy1 = new PermitPolicy(new ArrayList<>());

        when(metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), policy)).thenReturn(permitPolicy1);
        AccessRequest accessRequest = mock(AccessRequest.class);

        PolicyResult policyResult = policyDecisionPoint.evaluate(accessRequest, scenario);
        verify(metaPolicy).getPolicyFor(anyList(), anyString());
        assertEquals(PolicyResult.PERMIT, policyResult);
    }


    @Test
    public void testException() {
        PermitPolicy permitPolicy = new PermitPolicy(new ArrayList<>());

        when(metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.CITY_POLICY)).thenReturn(permitPolicy);
        AccessRequest accessRequest = mock(AccessRequest.class);
        Assertions.assertThrows(RuntimeException.class, () -> {
            policyDecisionPoint.evaluate(accessRequest, "invalide-scenario");

        });
    }

}