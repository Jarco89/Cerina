package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.entities.Policy;
import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.abac.repository.PolicyRepository;
import nl.schutrup.cerina.abac.repository.StatisticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

@Component
public class ConfirmationRule extends Rule {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaximumNumberOfCustomersRule.class);

    @Autowired
    private StandardEnvironment environment;

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private PolicyRepository policyRepository;


    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation operation) {
        Integer confirmationUsers = environment.getProperty("confirmation.users", Integer.class);
        Policy policy = policyRepository.findPolicyByName("ConfirmationPolicy");

        Integer currentValue = Integer.valueOf(policy.getCurrentValue());
        LOGGER.info("Aantal users dat nodig is om te bevestigen {}", confirmationUsers);
        LOGGER.info("Huidig aantal confirmaties = {} ", currentValue);

        if (currentValue == confirmationUsers) {
            LOGGER.info("Toegang toegestaan. Confirmatie gelukt");
            policy.setCurrentValue(String.valueOf(currentValue + 1));
            policyRepository.save(policy);
            return PolicyResult.PERMIT;

        } else {
            LOGGER.info("Toegang toegestaan, maar wel eerst bevestigen");
            policy.setCurrentValue(String.valueOf(currentValue + 1));
            policyRepository.save(policy);
            return PolicyResult.PERMIT;
        }
    }

    @Override
    public String toString() {
        return "ConfirmationRule{" +
                "environment=" + environment +
                ", statisticRepository=" + statisticRepository +
                ", policyRepository=" + policyRepository +
                '}';
    }
}
