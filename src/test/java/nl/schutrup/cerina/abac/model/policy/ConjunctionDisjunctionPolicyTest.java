package nl.schutrup.cerina.abac.model.policy;

import nl.schutrup.cerina.abac.model.rule.Rule;
import nl.schutrup.cerina.abac.model.rule.SingleStringAttributeRule;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ConjunctionDisjunctionPolicyTest {

    @Mock
    private SingleStringAttributeRule firstRule;

    @Mock
    private SingleStringAttributeRule secondRule;

    @Mock
    private SingleStringAttributeRule thirdRule;

    @Mock
    private SingleStringAttributeRule fourthRule;

    @Test
    public void testAllDisjunctionPermit() {
        when(firstRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);
        when(secondRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);
        when(thirdRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);
        when(fourthRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);

        List<Rule> a = Arrays.asList(firstRule, secondRule);
        List<Rule> b = Arrays.asList(thirdRule, fourthRule);

        List<List<Rule>> rules = Arrays.asList(a, b);

        ConjunctionDisjunctionPolicy conjunctionDisjunctionPolicy = new ConjunctionDisjunctionPolicy(rules);

        PolicyResult result = conjunctionDisjunctionPolicy.doAutorisationValidation(any(), any(), any());

        assertEquals(PolicyResult.PERMIT, result);
    }

    @Test
    public void testPartialDisjunctionPermit() {
        when(firstRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.DENY);
        when(secondRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.DENY);
        when(thirdRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);
        when(fourthRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);

        List<Rule> a = Arrays.asList(firstRule, secondRule);
        List<Rule> b = Arrays.asList(thirdRule, fourthRule);

        List<List<Rule>> rules = Arrays.asList(a, b);

        ConjunctionDisjunctionPolicy conjunctionDisjunctionPolicy = new ConjunctionDisjunctionPolicy(rules);

        PolicyResult result = conjunctionDisjunctionPolicy.doAutorisationValidation(any(), any(), any());

        assertEquals(PolicyResult.PERMIT, result);
    }

    @Test
    public void testAllDisjunctionDeny() {
        when(firstRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.DENY);
        when(secondRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.DENY);
        when(thirdRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.DENY);
        when(fourthRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.DENY);

        List<Rule> a = Arrays.asList(firstRule, secondRule);
        List<Rule> b = Arrays.asList(thirdRule, fourthRule);

        List<List<Rule>> rules = Arrays.asList(a, b);

        ConjunctionDisjunctionPolicy conjunctionDisjunctionPolicy = new ConjunctionDisjunctionPolicy(rules);

        PolicyResult result = conjunctionDisjunctionPolicy.doAutorisationValidation(any(), any(), any());

        assertEquals(PolicyResult.DENY, result);
    }

}