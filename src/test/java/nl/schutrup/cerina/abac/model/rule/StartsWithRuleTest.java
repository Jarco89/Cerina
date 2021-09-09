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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StartsWithRuleTest {

    @Test
    public void testValidStartsWith() {
        StartsWithRule startsWithRule = new StartsWithRule(PolicyConstants.VOORNAAM, Operation.ACCESS_REQUEST, "J");

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.VOORNAAM, "John");
        List<SubjectAttribute> subjectAttributeList = new ArrayList<>();
        subjectAttributeList.add(subjectAttribute);

        subject.setAttributeList(subjectAttributeList);
        Library library = new Library();

        PolicyResult policyResult = startsWithRule.evaluate(subject, library, Operation.ACCESS_REQUEST);

        assertEquals(PolicyResult.PERMIT, policyResult);
    }

    @Test
    public void testInValidStartsWith() {
        StartsWithRule startsWithRule = new StartsWithRule(PolicyConstants.VOORNAAM, Operation.ACCESS_REQUEST, "J");

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.VOORNAAM, "Frank");
        List<SubjectAttribute> subjectAttributeList = new ArrayList<>();
        subjectAttributeList.add(subjectAttribute);

        subject.setAttributeList(subjectAttributeList);
        Library library = new Library();

        PolicyResult policyResult = startsWithRule.evaluate(subject, library, Operation.ACCESS_REQUEST);

        assertEquals(PolicyResult.DENY, policyResult);
    }

}