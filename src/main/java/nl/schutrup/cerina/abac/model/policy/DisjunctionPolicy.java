package nl.schutrup.cerina.abac.model.policy;


import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.rule.Rule;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DisjunctionPolicy extends AbstractPolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisjunctionPolicy.class);
    private List<Rule> rules;

    public DisjunctionPolicy(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public PolicyResult doAutorisationValidation(Subject requestor, Resource resource, Operation action) {
        LOGGER.info("Policy {} aangeroepen", this.getClass().getName());
        for (Rule rule : rules) {
            PolicyResult policyResult = rule.evaluate(requestor, resource, action);
            if (PolicyResult.PERMIT.equals(policyResult)) {
                LOGGER.info("Rule {} retourneerde permit. Toegang toegestaan", rule.toString());
                return policyResult;
            }
        }
        LOGGER.info("Geen enkele rule retourneerde permit. Toegang geweigerd.");
        return PolicyResult.DENY;
    }
}
