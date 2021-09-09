package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.entities.Statistic;
import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.abac.repository.StatisticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class MaximumNumberOfCustomersRule extends Rule {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaximumNumberOfCustomersRule.class);

    @Autowired
    private StandardEnvironment environment;

    @Autowired
    private StatisticRepository statisticRepository;

    public MaximumNumberOfCustomersRule() {
    }

    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation operation) {
        Integer maxVisitors = environment.getProperty("max.visitors.total", Integer.class);

        LOGGER.info("Totaal aantal toegestane bezoekers {}", maxVisitors);
        LocalDate localDate = LocalDate.now();
        LocalDateTime start = localDate.atStartOfDay();
        LocalDateTime end = localDate.atTime(23, 59, 59);
        List<Statistic> statisticList = statisticRepository.findAllByVisitDateTimeBetween(start, end);

        if (statisticList.size() >= maxVisitors) {
            LOGGER.info("Geweigerd. Aantal bezoekers {} is reeds gelijk aan max. toegestane bezoekers {} ", statisticList.size(), maxVisitors);
            return PolicyResult.DENY;
        }
        LOGGER.info("Toegestaan. Aantal bezoekers {} is kleinder dan max. toegestane bezoekers {}",statisticList.size(), maxVisitors);
        return PolicyResult.PERMIT;
    }

    @Override
    public String toString() {
        return "MaximumNumberOfCustomersRule{" +
                "environment=" + environment +
                ", statisticRepository=" + statisticRepository +
                '}';
    }
}
