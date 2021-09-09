package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalTime;

@Component
public class DayTimeRule extends Rule {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayTimeRule.class);

    @Autowired
    private Clock clock;

    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation operation) {
        if (Operation.READ_REQUEST.equals(operation)) {
            LocalTime now = getNow();

            LocalTime start = LocalTime.parse("09:00:00");
            LocalTime end = LocalTime.parse("17:00:00");

            boolean between = now.isAfter(start) && now.isBefore(end);

            if (between) {
                LOGGER.info("Toegang toegestaan. Nu {} is tussen {} - {} {} ", now, start, end, between);
                return PolicyResult.PERMIT;
            } else {
                LOGGER.info("Toegang niet toegestaan. Nu {} is niet tussen {} - {} {} ", now, start, end, between);
            }
        }

        return PolicyResult.DENY;
    }

    public LocalTime getNow() {
        return LocalTime.now(clock);
    }

    @Override
    public String toString() {
        return "DayTimeRule{" +
                "clock=" + clock +
                '}';
    }
}
