package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyConstants;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Library;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.abac.model.subject.SubjectAttribute;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class BetweenRuleTest {

    @Test
    public void testBetween() {
        BetweenRule regexRule = new BetweenRule(PolicyConstants.LEEFTIJD, 18, 67, Operation.ACCESS_REQUEST);

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.LEEFTIJD, "25");
        List<SubjectAttribute> subjectAttributeList = new ArrayList<>();
        subjectAttributeList.add(subjectAttribute);

        subject.setAttributeList(subjectAttributeList);
        Library library = new Library();

        PolicyResult policyResult = regexRule.evaluate(subject, library, Operation.ACCESS_REQUEST);

        assertEquals(PolicyResult.PERMIT, policyResult);
    }

    @Test
    public void testNotBetween() {
        BetweenRule regexRule = new BetweenRule(PolicyConstants.LEEFTIJD, 18, 67, Operation.ACCESS_REQUEST);

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.LEEFTIJD, "17");
        List<SubjectAttribute> subjectAttributeList = new ArrayList<>();
        subjectAttributeList.add(subjectAttribute);

        subject.setAttributeList(subjectAttributeList);
        Library library = new Library();

        PolicyResult policyResult = regexRule.evaluate(subject, library, Operation.ACCESS_REQUEST);

        assertEquals(PolicyResult.DENY, policyResult);
    }
}