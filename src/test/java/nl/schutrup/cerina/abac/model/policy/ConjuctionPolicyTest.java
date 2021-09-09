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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ConjuctionPolicyTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConjuctionPolicyTest.class);

    @InjectMocks
    private ConjuctionPolicy conjuctionPolicy;

    @Mock
    private SingleStringAttributeRule firstRule;

    @Mock
    private SingleStringAttributeRule secondRule;

    @Test
    public void testSingleRulePermit() {
        when(firstRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);

        List<Rule> rules = new ArrayList<>();
        rules.add(firstRule);

        conjuctionPolicy = new ConjuctionPolicy(rules);

        PolicyResult result = conjuctionPolicy.doAutorisationValidation(any(), any(), any());

        assertEquals(PolicyResult.PERMIT, result);
    }

    @Test
    public void testTwoRulesPermit() {
        when(firstRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);
        when(secondRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);

        List<Rule> rules = new ArrayList<>();
        rules.add(firstRule);
        rules.add(secondRule);

        conjuctionPolicy = new ConjuctionPolicy(rules);

        PolicyResult result = conjuctionPolicy.doAutorisationValidation(any(), any(), any());

        assertEquals(PolicyResult.PERMIT, result);
    }

    @Test
    public void testTwoRulesPermitDeny() {
        when(firstRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.PERMIT);
        when(secondRule.evaluate(any(), any(), any())).thenReturn(PolicyResult.DENY);

        List<Rule> rules = new ArrayList<>();
        rules.add(firstRule);
        rules.add(secondRule);

        conjuctionPolicy = new ConjuctionPolicy(rules);

        PolicyResult result = conjuctionPolicy.doAutorisationValidation(any(), any(), any());

        assertEquals(PolicyResult.DENY, result);
    }
}
