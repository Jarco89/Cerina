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

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class MaximumNumberOfCustomersPerHourRule extends Rule {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaximumNumberOfCustomersPerHourRule.class);

    @Autowired
    private StandardEnvironment environment;

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private Clock clock;

    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation operation) {
        Integer maxVisitorsPerHour = environment.getProperty("max.visitors.per.hour", Integer.class);

        LOGGER.info("Max. aantal bezoekers per uur = {}", maxVisitorsPerHour);
        LocalDate localDate = getNow();
        LocalDateTime start = localDate.atTime(LocalTime.now().getHour(),00,00);
        LocalDateTime end = localDate.atTime(LocalTime.now().getHour(),59,59);
        List<Statistic> statisticList = statisticRepository.findAllByVisitDateTimeBetween(start, end);

        LOGGER.debug("Bezoekers tot dusver = {}",statisticList);

        if (statisticList.size() >= maxVisitorsPerHour) {
            LOGGER.info("Bezoekersaantal voor dit uur bereikt");
            return PolicyResult.DENY;
        } else {
            LOGGER.info("Bezoekersaantal voor dit uur nog niet bereikt. Toegang toegestaan");
            return PolicyResult.PERMIT;
        }
    }

    public LocalDate getNow () {
        return LocalDate.now(clock);
    }

    @Override
    public String toString() {
        return "MaximumNumberOfCustomersPerHourRule{" +
                "environment=" + environment +
                ", statisticRepository=" + statisticRepository +
                ", clock=" + clock +
                '}';
    }
}
