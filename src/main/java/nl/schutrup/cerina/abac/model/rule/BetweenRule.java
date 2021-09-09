package nl.schutrup.cerina.abac.model.rule;


import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.attribute.Attribute;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Library;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.StandardEnvironment;

import java.time.LocalDate;

public class BetweenRule extends Rule {

    @Autowired
    private StandardEnvironment environment;

    private static final Logger LOGGER = LoggerFactory.getLogger(BetweenRule.class);

    private final String expectedAttributeNameForValidation;
    private final Integer startAttributeValueForValidation;
    private final Integer endAttributeValueForValidation;
    private final Operation allowedOperation;

    public BetweenRule(String expectedAttributeNameForValidation, Integer startAttributeValueForValidation, Integer endAttributeValueForValidation, Operation allowedOperation) {
        this.expectedAttributeNameForValidation = expectedAttributeNameForValidation;
        this.startAttributeValueForValidation = startAttributeValueForValidation;
        this.endAttributeValueForValidation = endAttributeValueForValidation;
        this.allowedOperation = allowedOperation;
    }

    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation action) {
        if (resource instanceof Library) {
            if (allowedOperation.equals(action)) {
                for (Attribute attribute : subject.getAttributeList()) {
                    if (expectedAttributeNameForValidation.equalsIgnoreCase(attribute.getName())) {
                        Integer attributeValue = Integer.valueOf(attribute.getValue());
                        LOGGER.info("Attribute waarde {} light tussen {} {} ", attributeValue, startAttributeValueForValidation, endAttributeValueForValidation);
                        if (startAttributeValueForValidation <= attributeValue && endAttributeValueForValidation >= attributeValue) {
                            LOGGER.info("Toegang toegestaan voor {} - {} ", attribute.getName(), attribute.getValue());
                            return PolicyResult.PERMIT;
                        }


                    }
                }
            }
        } else {
            LOGGER.info("Geen match voor resource {}", resource.toString());
        }
        LOGGER.info("Toegang geweigerd");
        return PolicyResult.DENY;
    }

    @Override
    public String toString() {
        return "BetweenRule{" +
                "environment=" + environment +
                ", expectedAttributeNameForValidation='" + expectedAttributeNameForValidation + '\'' +
                ", startAttributeValueForValidation=" + startAttributeValueForValidation +
                ", endAttributeValueForValidation=" + endAttributeValueForValidation +
                ", allowedOperation=" + allowedOperation +
                '}';
    }
}

