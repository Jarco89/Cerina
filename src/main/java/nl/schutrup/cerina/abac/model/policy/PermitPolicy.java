package nl.schutrup.cerina.abac.model.policy;


import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.rule.Rule;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class PermitPolicy extends AbstractPolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConjuctionPolicy.class);
    private List<Rule> rules;

    public PermitPolicy (List<Rule> rules){
        this.rules = rules;
    }

    @Override
    public PolicyResult doAutorisationValidation(Subject requestor, Resource resource, Operation action) {
        List<PolicyResult> results = rules.stream().map(rule -> rule.evaluate(requestor, resource, action)).collect(Collectors.toList());

        return PolicyResult.PERMIT ;
    }
}
