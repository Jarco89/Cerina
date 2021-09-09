package nl.schutrup.cerina.abac.model.rule;


import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;

public abstract class Rule {

    public abstract PolicyResult evaluate (Subject subject, Resource resource, Operation operation);
}
