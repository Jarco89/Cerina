package nl.schutrup.cerina.abac.model.rule;


import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.attribute.Attribute;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelationRule extends Rule {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelationRule.class);

    private final Map<String, String> expectedAttributeNameAndValueForValidation;
    private final List<String> allowedObjectsForValidation;
    private final List<Operation> allowedOperations;


    public RelationRule(Map<String, String> expectedAttributeNameAndValueForValidation, List<Operation> allowedOperations, List<String> allowedObjectsForValidation) {
        this.expectedAttributeNameAndValueForValidation = expectedAttributeNameAndValueForValidation;
        this.allowedOperations = allowedOperations;
        this.allowedObjectsForValidation = allowedObjectsForValidation;
    }

    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation action) {
            Map<String, String> kv = subject.getAttributeList().stream().collect(Collectors.toMap(Attribute::getName, Attribute::getValue));
            if (expectedAttributeNameAndValueForValidation.entrySet().containsAll(kv.entrySet()) && allowedOperations.contains(action) && allowedObjectsForValidation.contains(resource.getName()))  {
                LOGGER.info("Toegang toegestaan: {} == {} && {} == {} && {} == {} ", expectedAttributeNameAndValueForValidation, kv, action, allowedOperations, allowedObjectsForValidation, resource.getName());
                return PolicyResult.PERMIT;

            } else {
                LOGGER.info("Toegang geweigerd: {} != {} && {} != {} && {} == {}", expectedAttributeNameAndValueForValidation, kv, action, allowedOperations, allowedObjectsForValidation, resource.getName());
                return PolicyResult.DENY;
            }
    }

    @Override
    public String toString() {
        return "RelationRule{" +
                "expectedAttributeNameAndValueForValidation=" + expectedAttributeNameAndValueForValidation +
                ", allowedObjectsForValidation=" + allowedObjectsForValidation +
                ", allowedOperations=" + allowedOperations +
                '}';
    }
}
