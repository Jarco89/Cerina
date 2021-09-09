package nl.schutrup.cerina.abac.model.policy;


import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.rule.Rule;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ConjunctionDisjunctionPolicy extends AbstractPolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConjunctionDisjunctionPolicy.class);
    private List<List<Rule>> rules;

    public ConjunctionDisjunctionPolicy(List<List<Rule>> rules) {
        this.rules = rules;
    }

    @Override
    public PolicyResult doAutorisationValidation(Subject requestor, Resource resource, Operation action) {
        for (List<Rule> and : rules) {
            List<PolicyResult> results = and.stream().map(rule -> rule.evaluate(requestor, resource, action)).collect(Collectors.toList());
            boolean allPermit = results.stream().allMatch(PolicyResult.PERMIT::equals);
            if (allPermit) {
                LOGGER.info("In de conjunctie gaf een disjunctie permit terug. Toegang toegestaan.");
                return PolicyResult.PERMIT;
            }
        }
        LOGGER.info("Geen van de rules gaf een permit. Toegang geweigerd.");
        return PolicyResult.DENY;
    }
}
