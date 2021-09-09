package nl.schutrup.cerina.abac.model.policy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MetaPolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaPolicy.class);

    public AbstractPolicy getPolicyFor(List<AbstractPolicy> policyList, String name) {
        AbstractPolicy res = policyList.stream().filter(abstractPolicy -> name.equalsIgnoreCase(abstractPolicy.getName())).findFirst().orElseGet(null);
        if (res != null) {
            LOGGER.info("Policy {} gevonden om te evalueren. ", name);
        } else {
            LOGGER.error("Geen policy met naam {} gevonden", name);
        }
        return res;
    }
}
