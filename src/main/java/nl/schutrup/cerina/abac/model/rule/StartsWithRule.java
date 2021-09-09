package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.attribute.Attribute;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Library;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartsWithRule extends Rule {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegexRule.class);

    private final String expectedAttributeNameForValidation;
    private final Operation allowedOperation;
    private final String mustStartsWith;

    public StartsWithRule(String expectedAttributeNameForValidation, Operation allowedOperation, String mustStartsWith) {
        this.expectedAttributeNameForValidation = expectedAttributeNameForValidation;
        this.allowedOperation = allowedOperation;
        this.mustStartsWith = mustStartsWith;
    }

    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation action) {
        if (resource instanceof Library) {
            if (allowedOperation.equals(action)) {
                for (Attribute attribute : subject.getAttributeList()) {
                    if (expectedAttributeNameForValidation.equalsIgnoreCase(attribute.getName())) {
                        if (attribute.getValue().startsWith(mustStartsWith)) {
                            LOGGER.info("Toegang toegestaan => {} start met {} ", attribute.getValue(), mustStartsWith);
                            return PolicyResult.PERMIT;
                        } else {
                            LOGGER.info("Toegang niet toegestaan => {} start niet met {} ", attribute.getValue(), mustStartsWith);
                            return PolicyResult.DENY;
                        }
                    }
                }
            }
        } else {
            LOGGER.info("Skipping policy voor resource {}", resource.toString());
        }
        LOGGER.info("Toegang geweigerd");
        return PolicyResult.DENY;
    }

    @Override
    public String toString() {
        return "StartsWithRule{" +
                "expectedAttributeNameForValidation='" + expectedAttributeNameForValidation + '\'' +
                ", allowedOperation=" + allowedOperation +
                ", mustStartsWith='" + mustStartsWith + '\'' +
                '}';
    }
}
