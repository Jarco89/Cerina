package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.entities.Resource;
import nl.schutrup.cerina.abac.entities.Statistic;
import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.abac.repository.ResourceRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DeleteResourceRule extends Rule{

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteResourceRule.class);

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public PolicyResult evaluate(Subject subject, nl.schutrup.cerina.abac.model.resource.Resource resource, Operation operation) {

        if (Operation.DELETE_REQUEST.equals(operation)) {
            List<Resource> resourceList = resourceRepository.findAll();

            if (!resourceList.isEmpty()) {
                Resource resourceToDelete = resourceList.get(0);
                LOGGER.info("Bevestigt dat resource verwijderd wordt {}", resourceToDelete.toString());
                resourceRepository.delete(resourceToDelete);
            } else {
                LOGGER.info("Geen resource om te verwijderen");
                return PolicyResult.DENY;
            }

            return PolicyResult.PERMIT;
        }
        return PolicyResult.DENY;
    }

    @Override
    public String toString() {
        return "DeleteResourceRule{" +
                "resourceRepository=" + resourceRepository +
                '}';
    }
}
