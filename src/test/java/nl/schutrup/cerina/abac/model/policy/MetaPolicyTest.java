package nl.schutrup.cerina.abac.model.policy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MetaPolicyTest {

    @Autowired
    private MetaPolicy metaPolicy;

    @Test
    public void testGetPolicy () {
        DisjunctionPolicy disjunctionPolicy = new DisjunctionPolicy(new ArrayList<>());
        disjunctionPolicy.setName("TEST");

        DisjunctionPolicy disjunctionPolicy2 = new DisjunctionPolicy(new ArrayList<>());
        disjunctionPolicy2.setName("TEST2");

        List<AbstractPolicy> policyList = new ArrayList<>();
        policyList.add(disjunctionPolicy);

        AbstractPolicy result = metaPolicy.getPolicyFor(policyList, "TEST");

        assertEquals(result.getName(), "TEST");

    }

}