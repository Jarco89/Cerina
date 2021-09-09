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
class SingleNumberAttributeRuleTest {

    @Test
    public void testValidNumber() {
        SingleNumberAttributeRule singleNumberAttributeRule = new SingleNumberAttributeRule(PolicyConstants.AANTAL_CORONA_VACCINATIES, PolicyConstants.AANTAL_CORONA_VACCINATIES_EXPECTED_WAARDE, Operation.ACCESS_REQUEST);

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.AANTAL_CORONA_VACCINATIES, "2");
        List<SubjectAttribute> subjectAttributeList = new ArrayList<>();
        subjectAttributeList.add(subjectAttribute);

        subject.setAttributeList(subjectAttributeList);
        Library library = new Library();

        PolicyResult policyResult = singleNumberAttributeRule.evaluate(subject, library, Operation.ACCESS_REQUEST);

        assertEquals(PolicyResult.PERMIT, policyResult);
    }


    @Test
    public void testInvalidValidNumber() {
        SingleNumberAttributeRule singleNumberAttributeRule = new SingleNumberAttributeRule(PolicyConstants.AANTAL_CORONA_VACCINATIES, PolicyConstants.AANTAL_CORONA_VACCINATIES_EXPECTED_WAARDE, Operation.ACCESS_REQUEST);

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.AANTAL_CORONA_VACCINATIES, "1");
        List<SubjectAttribute> subjectAttributeList = new ArrayList<>();
        subjectAttributeList.add(subjectAttribute);

        subject.setAttributeList(subjectAttributeList);
        Library library = new Library();

        PolicyResult policyResult = singleNumberAttributeRule.evaluate(subject, library, Operation.ACCESS_REQUEST);

        assertEquals(PolicyResult.DENY, policyResult);
    }

}