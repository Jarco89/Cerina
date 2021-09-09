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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static nl.schutrup.cerina.abac.model.policy.PolicyConstants.DATE_TIME_PATTERN;

@Component
public class SingleTimeAttributeRule extends Rule {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleTimeAttributeRule.class);

    private String expectedAttributeNameForValidation;
    private Integer amountInPast;
    private Operation allowedOperation;

    @Autowired
    private Clock clock;

    public SingleTimeAttributeRule() {

    }

    public SingleTimeAttributeRule(String expectedAttributeNameForValidation, Integer minutesInPast, Operation allowedOperation) {
        this.expectedAttributeNameForValidation = expectedAttributeNameForValidation;
        this.amountInPast = minutesInPast;
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
                        LocalDateTime maximumTestSpace = coronateTestTime.plusMinutes(amountInPast);
                        if (getNow().isBefore(maximumTestSpace.toLocalTime())) {
                            LOGGER.info("Toegang toegestaan. Testtijdstip is recent genoeg {} - {} ", maximumTestSpace, getNow());
                            return PolicyResult.PERMIT;
                        } else {
                            LOGGER.info("Toegang niet toegestaan. Testtijdstip is niet recent genoeg {} - {} ", maximumTestSpace, getNow());
                            return PolicyResult.DENY;
                        }
                    }
                }
            }
        } else {
            LOGGER.info("Geen match voor resource {}", resource.toString());
        }
        LOGGER.info("SingleTimeAttributeRule toegang geweigerd");
        return PolicyResult.DENY;
    }

    public LocalTime getNow() {
        if (clock == null) {
            return LocalTime.now();
        }
        return LocalTime.now(clock);
    }

    @Override
    public String toString() {
        return "SingleTimeAttributeRule{" +
                "expectedAttributeNameForValidation='" + expectedAttributeNameForValidation + '\'' +
                ", amountInPast=" + amountInPast +
                ", allowedOperation=" + allowedOperation +
                ", clock=" + clock +
                '}';
    }
}
