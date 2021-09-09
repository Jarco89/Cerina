package nl.schutrup.cerina.abac.model.policy;

import nl.schutrup.cerina.abac.model.rule.Rule;
import nl.schutrup.cerina.abac.model.rule.SingleStringAttributeRule;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DisjunctionPolicyTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisjunctionPolicyTest.class);

    @InjectMocks
    private DisjunctionPolicy disjunctionPolicy;

    @Mock
    private SingleStringAttributeRule firstRule;

    @Mock
    private SingleStringAttributeRule secondRule;

    @Test
    public void testSingleRulePermit() {
        when(firstRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);

        List<Rule> rules = new ArrayList<>();
        rules.add(firstRule);

        disjunctionPolicy = new DisjunctionPolicy(rules);

        PolicyResult result = disjunctionPolicy.doAutorisationValidation(any(), any(), any());

        assertEquals(PolicyResult.PERMIT, result);
    }

    @Test
    public void testTwoRulesPermit() {
        when(firstRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);
        when(secondRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);

        List<Rule> rules = new ArrayList<>();
        rules.add(firstRule);
        rules.add(secondRule);

        disjunctionPolicy = new DisjunctionPolicy(rules);

        PolicyResult result = disjunctionPolicy.doAutorisationValidation(any(), any(), any());

        assertEquals(PolicyResult.PERMIT, result);
    }

    @Test
    public void testTwoRulesPermitDeny() {
        when(firstRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);
        when(secondRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.DENY);

        List<Rule> rules = new ArrayList<>();
        rules.add(firstRule);
        rules.add(secondRule);

        disjunctionPolicy = new DisjunctionPolicy(rules);

        PolicyResult result = disjunctionPolicy.doAutorisationValidation(any(), any(), any());

        assertEquals(PolicyResult.PERMIT, result);
    }

}