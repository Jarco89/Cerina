package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.attribute.Attribute;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Library;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleStringAttributeRule extends Rule {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleStringAttributeRule.class);

    private final String expectedAttributeNameForValidation;
    private final String expectedAttributeValueForValidation;
    private final Operation allowedOperation;

    public SingleStringAttributeRule(String expectedAttributeNameForValidation, String expectedAttributeValueForValidation, Operation allowedOperation) {
        this.expectedAttributeNameForValidation = expectedAttributeNameForValidation;
        this.expectedAttributeValueForValidation = expectedAttributeValueForValidation;
        this.allowedOperation = allowedOperation;
    }

    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation action) {
        if (resource instanceof Library) {
            Library library = (Library) resource;
            LOGGER.info("Validating for {}", library.toString());
            if (allowedOperation.equals(action)) {
                for (Attribute attribute : subject.getAttributeList()) {
                    if (expectedAttributeValueForValidation.equalsIgnoreCase(attribute.getValue()) && expectedAttributeNameForValidation.equalsIgnoreCase(attribute.getName())) {
                        LOGGER.info("Autorisatie toegestaan voor {} => {}", attribute.getName(), attribute.getValue());
                        return PolicyResult.PERMIT;
                    } else {
                        LOGGER.info("Autorisatie niet toegestaan voor {} => {}", attribute.getName(), attribute.getValue());
                        return PolicyResult.DENY;
                    }
                }
            }
        } else {
            LOGGER.info("Toegang matcht niet op resource: {} ", resource.toString());
        }
        LOGGER.info("Autorisatie geweigerd");
        return PolicyResult.DENY;
    }

    @Override
    public String toString() {
        return "SingleStringAttributeRule{" +
                "expectedAttributeNameForValidation='" + expectedAttributeNameForValidation + '\'' +
                ", expectedAttributeValueForValidation='" + expectedAttributeValueForValidation + '\'' +
                ", allowedOperation=" + allowedOperation +
                '}';
    }
}
