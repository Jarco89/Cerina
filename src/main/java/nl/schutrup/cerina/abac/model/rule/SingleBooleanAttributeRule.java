package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.attribute.Attribute;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Library;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleBooleanAttributeRule extends Rule {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleBooleanAttributeRule.class);

    private final String expectedAttributeNameForValidation;
    private final Boolean expectedAttributeValueForValidation;
    private final Operation allowedOperation;


    public SingleBooleanAttributeRule(String expectedAttributeNameForValidation, Boolean expectedAttributeValueForValidation, Operation allowedOperation) {
        this.expectedAttributeNameForValidation = expectedAttributeNameForValidation;
        this.expectedAttributeValueForValidation = expectedAttributeValueForValidation;
        this.allowedOperation = allowedOperation;
    }

    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation action) {
        if (resource instanceof Library) {
            if (allowedOperation.equals(action)) {
                for (Attribute attribute : subject.getAttributeList()) {
                    if (expectedAttributeNameForValidation.equalsIgnoreCase(attribute.getName())) {
                        Boolean value = getBooleanValue (attribute.getValue());
                        if (expectedAttributeValueForValidation.equals(value)) {
                            LOGGER.info("Toegang toegestaan: {} => {}", attribute.getName(), value);
                            return PolicyResult.PERMIT;
                        } else {
                            LOGGER.info("Toegang geweigerd: {} => {}", attribute.getName(), value);
                            return PolicyResult.DENY;
                        }
                    }
                }
            }
        } else {
            LOGGER.info("Geen match op resource {}", resource.toString());
        }
        return PolicyResult.DENY;
    }

    private Boolean getBooleanValue(String val) {
        if ("Ja".equalsIgnoreCase(val)) {
            return true;
        } else if ("Nee".equalsIgnoreCase(val)) {
            return false;
        }
        return Boolean.valueOf(val);
    }

    @Override
    public String toString() {
        return "SingleBooleanAttributeRule{" +
                "expectedAttributeNameForValidation='" + expectedAttributeNameForValidation + '\'' +
                ", expectedAttributeValueForValidation=" + expectedAttributeValueForValidation +
                ", allowedOperation=" + allowedOperation +
                '}';
    }
}
