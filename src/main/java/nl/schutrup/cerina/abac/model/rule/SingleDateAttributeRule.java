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
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static nl.schutrup.cerina.abac.model.policy.PolicyConstants.DATE_TIME_PATTERN;

@Component
public class SingleDateAttributeRule extends Rule {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleDateAttributeRule.class);

    private String expectedAttributeNameForValidation;
    private Integer amountInPast;
    private Operation allowedOperation;

    @Autowired
    private Clock clock;

    public SingleDateAttributeRule() {
    }

    public SingleDateAttributeRule(String expectedAttributeNameForValidation, Integer daysInPast, Operation allowedOperation) {
        this.expectedAttributeNameForValidation = expectedAttributeNameForValidation;
        this.amountInPast = daysInPast;
        this.allowedOperation = allowedOperation;
    }

    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation action) {
        if (resource instanceof Library) {
            if (allowedOperation.equals(action)) {
                for (Attribute attribute : subject.getAttributeList()) {
                    if (expectedAttributeNameForValidation.equalsIgnoreCase(attribute.getName())) {
                        DateTimeFormatter f = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
                        LocalDateTime coronateTestTime = LocalDateTime.from(f.parse(attribute.getValue()));
                        LocalDateTime maximumTestSpace = getNow().minusDays(amountInPast);
                        if (coronateTestTime.isAfter(maximumTestSpace)) {
                            LOGGER.info("Antistoffen zijn recent genoeg {} => {} ", maximumTestSpace, getNow());
                            return PolicyResult.PERMIT;
                        } else {
                            LOGGER.info("Antistoffen zijn niet recent genoeg {} => {} ", maximumTestSpace, getNow());
                            return PolicyResult.DENY;
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

    public LocalDateTime getNow() {
        if (clock == null) {
            return LocalDateTime.now(Clock.systemDefaultZone());
        }
        return LocalDateTime.now(clock);
    }

    @Override
    public String toString() {
        return "SingleDateAttributeRule{" +
                "expectedAttributeNameForValidation='" + expectedAttributeNameForValidation + '\'' +
                ", amountInPast=" + amountInPast +
                ", allowedOperation=" + allowedOperation +
                ", clock=" + clock +
                '}';
    }
}
