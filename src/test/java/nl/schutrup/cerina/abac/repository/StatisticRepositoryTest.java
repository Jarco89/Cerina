package nl.schutrup.cerina.abac.repository;

import nl.schutrup.cerina.abac.entities.Statistic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StatisticRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StatisticRepository statisticRepository;

    @Test
    public void findAllByVisitDateTimeBetween() {
        Statistic statistic = new Statistic();
        LocalDateTime now = LocalDateTime.of(2021, Month.MARCH, 31, 10, 10);

        statistic.setVisitDateTime(now);
        entityManager.persist(statistic);
        entityManager.flush();

        Statistic second = new Statistic();
        LocalDateTime secondLocalDateTime = LocalDateTime.of(2021, Month.MARCH, 31, 10, 11);

        second.setVisitDateTime(secondLocalDateTime);
        entityManager.persist(second);
        entityManager.flush();


        LocalDateTime start = LocalDateTime.of(2021, Month.MARCH, 31, 10, 00);
        LocalDateTime end = LocalDateTime.of(2021, Month.MARCH, 31, 10, 15);

        List<Statistic> all = statisticRepository.findAllByVisitDateTimeBetween(start, end);

        assertEquals(2, all.size());

    }

    @Test
    public void findNoneByVisitDateTimeBetween() {
        Statistic statistic = new Statistic();
        LocalDateTime now = LocalDateTime.of(2021, Month.MARCH, 31, 10, 10);

        statistic.setVisitDateTime(now);
        entityManager.persist(statistic);
        entityManager.flush();

        Statistic second = new Statistic();
        LocalDateTime secondLocalDateTime = LocalDateTime.of(2021, Month.MARCH, 31, 10, 11);

        second.setVisitDateTime(secondLocalDateTime);
        entityManager.persist(second);
        entityManager.flush();


        LocalDateTime start = LocalDateTime.of(2021, Month.MARCH, 31, 10, 15);
        LocalDateTime end = LocalDateTime.of(2021, Month.MARCH, 31, 10, 45);

        List<Statistic> all = statisticRepository.findAllByVisitDateTimeBetween(start, end);

        assertEquals(0, all.size());

    }

}