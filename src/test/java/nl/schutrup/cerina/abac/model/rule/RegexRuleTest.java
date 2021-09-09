package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyConstants;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Library;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.abac.model.subject.SubjectAttribute;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RegexRuleTest {


    @Test
    public void testAmsterdamZipCode() {
        RegexRule regexRule = new RegexRule(PolicyConstants.AMSTERDAM_POSTCODE, Operation.ACCESS_REQUEST, PolicyConstants.AMSTERDAM_REGEX_POSTCODE);

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.AMSTERDAM_POSTCODE, "1000AB");
        List<SubjectAttribute> subjectAttributeList = new ArrayList<>();
        subjectAttributeList.add(subjectAttribute);

        subject.setAttributeList(subjectAttributeList);
        Library library = new Library();

        PolicyResult policyResult = regexRule.evaluate(subject, library, Operation.ACCESS_REQUEST);

        assertEquals(PolicyResult.PERMIT, policyResult);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of("1000AB", PolicyResult.PERMIT),
                Arguments.of("1100AB", PolicyResult.PERMIT),
                Arguments.of("1010AB", PolicyResult.PERMIT),
                Arguments.of("1020AB", PolicyResult.PERMIT),
                Arguments.of("1109AB", PolicyResult.PERMIT),
                Arguments.of("2109AB", PolicyResult.DENY),
                Arguments.of("3109AB", PolicyResult.DENY),
                Arguments.of("4109AB", PolicyResult.DENY),
                Arguments.of("5109AB", PolicyResult.DENY),
                Arguments.of("6109AB", PolicyResult.DENY),
                Arguments.of("7109AB", PolicyResult.DENY),
                Arguments.of("8109AB", PolicyResult.DENY),
                Arguments.of("9109AB", PolicyResult.DENY)

        );
    }


    @ParameterizedTest
    @MethodSource("provideParameters")
    public void testAmsterdamZipCodes(String zipcode, PolicyResult expected) {
        RegexRule regexRule = new RegexRule(PolicyConstants.AMSTERDAM_POSTCODE, Operation.ACCESS_REQUEST, PolicyConstants.AMSTERDAM_REGEX_POSTCODE);

        Subject subject = new Subject();
        SubjectAttribute subjectAttribute = new SubjectAttribute(PolicyConstants.AMSTERDAM_POSTCODE, zipcode);
        List<SubjectAttribute> subjectAttributeList = new ArrayList<>();
        subjectAttributeList.add(subjectAttribute);

        subject.setAttributeList(subjectAttributeList);
        Library library = new Library();

        PolicyResult policyResult = regexRule.evaluate(subject, library, Operation.ACCESS_REQUEST);

        assertEquals(expected, policyResult);
    }
}