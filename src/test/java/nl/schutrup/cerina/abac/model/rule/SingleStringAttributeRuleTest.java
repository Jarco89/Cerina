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
public class SingleStringAttributeRuleTest {

    @Test
    public void testValidSingleStringEqualsWith() {
        SingleStringAttributeRule singleStringAttributeRule = new SingleStringAttributeRule(PolicyConstants.VOORNAAM, "John", Operation.ACCESS_REQUEST);

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.VOORNAAM, "John");
        List<SubjectAttribute> subjectAttributeList = new ArrayList<>();
        subjectAttributeList.add(subjectAttribute);

        subject.setAttributeList(subjectAttributeList);
        Library library = new Library();

        PolicyResult policyResult = singleStringAttributeRule.evaluate(subject, library, Operation.ACCESS_REQUEST);

        assertEquals(PolicyResult.PERMIT, policyResult);
    }

    @Test
    public void testInvalidSingleStringEqualsWith() {
        SingleStringAttributeRule singleStringAttributeRule = new SingleStringAttributeRule(PolicyConstants.VOORNAAM, "Test", Operation.ACCESS_REQUEST);

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.VOORNAAM, "John");
        List<SubjectAttribute> subjectAttributeList = new ArrayList<>();
        subjectAttributeList.add(subjectAttribute);

        subject.setAttributeList(subjectAttributeList);
        Library library = new Library();

        PolicyResult policyResult = singleStringAttributeRule.evaluate(subject, library, Operation.ACCESS_REQUEST);

        assertEquals(PolicyResult.DENY, policyResult);
    }


    @Test
    public void testNotMatchingAttributeWith() {
        SingleStringAttributeRule singleStringAttributeRule = new SingleStringAttributeRule(PolicyConstants.VOORNAAM, "Test", Operation.ACCESS_REQUEST);

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.EXPECTED_STAD_VALUE, "Amsterdam");
        List<SubjectAttribute> subjectAttributeList = new ArrayList<>();
        subjectAttributeList.add(subjectAttribute);

        subject.setAttributeList(subjectAttributeList);
        Library library = new Library();

        PolicyResult policyResult = singleStringAttributeRule.evaluate(subject, library, Operation.ACCESS_REQUEST);

        assertEquals(PolicyResult.DENY, policyResult);
    }

}