package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.entities.Policy;
import nl.schutrup.cerina.abac.entities.Resource;
import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Library;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.abac.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DeleteResourceRuleTest {

    @InjectMocks
    private DeleteResourceRule deleteResourceRule;

    @Mock
    private ResourceRepository resourceRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationRuleTest.class);

    @BeforeEach
    void beforeTest() {
        Resource resource = new Resource();
        resource.setName("library");

        List<Resource> resources = new ArrayList<>();
        resources.add(resource);

        when(resourceRepository.findAll()).thenReturn(resources);
    }

    @DisplayName("Test DeleteResourceRule")
    @Test
    public void testConfirmation() {
        Subject subject = new Subject();
        Library library = new Library();

        PolicyResult policyResult = deleteResourceRule.evaluate(subject, library, Operation.DELETE_REQUEST);
        assertEquals(PolicyResult.PERMIT, policyResult);

        verify(resourceRepository, times(1)).findAll();
        verify(resourceRepository, times(1)).delete(any());
    }

}