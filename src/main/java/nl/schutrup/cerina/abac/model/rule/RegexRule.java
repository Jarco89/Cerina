package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.attribute.Attribute;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Library;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexRule extends Rule {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegexRule.class);

    private final String expectedAttributeNameForValidation;
    private final Operation allowedOperation;
    private Pattern pattern;

    public RegexRule(String expectedAttributeNameForValidation, Operation allowedOperation, String regex) {
        this.expectedAttributeNameForValidation = expectedAttributeNameForValidation;
        this.allowedOperation = allowedOperation;
        this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation action) {
        if (resource instanceof Library) {
            if (allowedOperation.equals(action)) {
                for (Attribute attribute : subject.getAttributeList()) {
                    if (expectedAttributeNameForValidation.equalsIgnoreCase(attribute.getName())) {
                        Matcher m = pattern.matcher(attribute.getValue());
                        if (m.matches()) {
                            LOGGER.info("Toegang toegestaan. Regex matches voor {} ", attribute.getValue());
                            return PolicyResult.PERMIT;
                        }
                    } else {
                        LOGGER.info("Geen match attribute.getValue() = {} attribute.getName() = {} attributeNameForValidation = {}", attribute.getValue(), attribute.getName(), expectedAttributeNameForValidation);
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
        return "RegexRule{" +
                "expectedAttributeNameForValidation='" + expectedAttributeNameForValidation + '\'' +
                ", allowedOperation=" + allowedOperation +
                ", pattern=" + pattern +
                '}';
    }
}
