package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.entities.Statistic;
import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.attribute.Attribute;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.abac.repository.StatisticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static nl.schutrup.cerina.abac.model.policy.PolicyConstants.STAD;

@Component
public class VisitorCounterRule extends Rule {

    @Autowired
    private StatisticRepository statisticRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorCounterRule.class);


    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation operation) {
        if (Operation.UPDATE_REQUEST.equals(operation)) {
            Statistic statistic = new Statistic();

            for (Attribute attribute : subject.getAttributeList()) {
                if (STAD.equalsIgnoreCase(attribute.getName())) {
                    LOGGER.info("Opslaan stad voor bezoeker uit {} ", attribute.getValue());
                    statistic.setVisitorCity(attribute.getValue());
                }
            }

            statistic.setVisitDateTime(LocalDateTime.now());
            statisticRepository.save(statistic);

        }
        return PolicyResult.PERMIT;
    }

    @Override
    public String toString() {
        return "VisitorCounterRule{" +
                "statisticRepository=" + statisticRepository +
                '}';
    }
}
