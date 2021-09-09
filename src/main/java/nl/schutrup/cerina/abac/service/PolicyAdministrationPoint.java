package nl.schutrup.cerina.abac.service;

import nl.schutrup.cerina.abac.entities.Policy;
import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.*;
import nl.schutrup.cerina.abac.model.rule.*;
import nl.schutrup.cerina.abac.repository.PolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static nl.schutrup.cerina.abac.model.policy.PolicyConstants.*;

@Component
/**
 * Policy Administration Point (PAP): Provides a user interface for creating, managing, testing, and
 * debugging DPs and MPs, and storing these policies in the appropriate repository.
 */

public class PolicyAdministrationPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyAdministrationPoint.class);

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private VisitorCounterRule visitorCounterRule;

    @Autowired
    private DayTimeRule singleDayTimeRule;

    @Autowired
    private DeleteResourceRule deleteResourceRule;

    @Autowired
    private MaximumNumberOfCustomersRule maximumNumberOfCustomersRule;

    @Autowired
    private MaximumNumberOfCustomersPerHourRule maximumNumberOfCustomersPerHourRule;

    @Autowired
    private OpenClosedRule openClosedRule;

    @Autowired
    private ConfirmationRule confirmationRule;

    public List<AbstractPolicy> loadPolicies() {
        List<AbstractPolicy> policies = new ArrayList<>();

        SingleStringAttributeRule woonplaatsRule = new SingleStringAttributeRule(STAD, EXPECTED_STAD_VALUE, Operation.ACCESS_REQUEST);
        SingleStringAttributeRule voornaamRule = new SingleStringAttributeRule(VOORNAAM, EXPECTED_VOORNAAM_VALUE, Operation.ACCESS_REQUEST);
        BetweenRule leeftijdTussenRule = new BetweenRule(LEEFTIJD, 16, 67, Operation.ACCESS_REQUEST);

        RegexRule regexRule = new RegexRule(AMSTERDAM_POSTCODE, Operation.ACCESS_REQUEST, AMSTERDAM_REGEX_POSTCODE);
        StartsWithRule startsWithRule = new StartsWithRule(VOORNAAM, Operation.ACCESS_REQUEST, "J");

        SingleBooleanAttributeRule gevaccineerdRule = new SingleBooleanAttributeRule(CORONA_GEVACINEERD, CORONA_GEVACINEERD_EXPECTED_VALUE, Operation.ACCESS_REQUEST);
        SingleNumberAttributeRule aantalVaccinatiesRule = new SingleNumberAttributeRule(AANTAL_CORONA_VACCINATIES, AANTAL_CORONA_VACCINATIES_EXPECTED_WAARDE, Operation.ACCESS_REQUEST);

        List<Rule> a = new ArrayList<>();
        a.add(gevaccineerdRule);
        a.add(aantalVaccinatiesRule);

        SingleBooleanAttributeRule vaccinatiePaspoortAanwezig = new SingleBooleanAttributeRule(CORONA_VACCINATIE_PASPOORT, CORONA_VACCINATIE_PASPOORT_WAARDE, Operation.ACCESS_REQUEST);
        SingleBooleanAttributeRule snelTestAanwezig = new SingleBooleanAttributeRule(CORONA_SNELTEST, CORONA_SNELTEST_WAARDE, Operation.ACCESS_REQUEST);

        List<Rule> b = new ArrayList<>();
        b.add(vaccinatiePaspoortAanwezig);
        b.add(snelTestAanwezig);

        SingleBooleanAttributeRule antiStoffenAanwezig = new SingleBooleanAttributeRule(CORONA_ANTILICHAMEN, CORONA_ANTISTOFFEN_WAARDE, Operation.ACCESS_REQUEST);
        SingleDateAttributeRule antilichamendatum = new SingleDateAttributeRule(CORONA_ANTILICHAMEN_DATUM, CORONA_ANTILICHAMEN_DAGEN_IN_VERLEDEN, Operation.ACCESS_REQUEST);

        List<Rule> c = new ArrayList<>();
        c.add(antiStoffenAanwezig);
        c.add(antilichamendatum);

        SingleTimeAttributeRule sneltestdatumtijd = new SingleTimeAttributeRule(CORONA_SNELTEST_DATUM, CORONA_SNELTEST_MINUTEN, Operation.ACCESS_REQUEST);
        List<Rule> d = new ArrayList<>();
        d.add(snelTestAanwezig);
        d.add(sneltestdatumtijd);

        List<List<Rule>> disjunction = new ArrayList<>();
        disjunction.add(a);
        disjunction.add(b);
        disjunction.add(c);
        disjunction.add(d);

        Map<String, String> relations = new HashMap<> ();
        relations.put(STAD, EXPECTED_STAD_VALUE);

        RelationRule relationRule = new RelationRule(relations, Arrays.asList(Operation.ACCESS_REQUEST), Arrays.asList("Library"));

        Policy woonplaatsPolicy = policyRepository.findPolicyByName(CITY_POLICY);
        Policy woonplaatsNaamPolicy = policyRepository.findPolicyByName(CITY_AND_NAME_POLICY);
        Policy woonplaatsNaamLeeftijdPolicy = policyRepository.findPolicyByName(CITY_AND_NAME_AND_AGE_POLICY);
        Policy visitorCounter = policyRepository.findPolicyByName(VISITOR_COUNTER_POLICY);
        Policy maximumNumberOfCustomersPerHour = policyRepository.findPolicyByName(MAXIMUM_NUMBER_OF_CUSTOMERS_PER_HOUR_POLICY);
        Policy maximumNumberOfCustomers = policyRepository.findPolicyByName(MAXIMUM_NUMBER_OF_CUSTOMERS_PER_DAY_POLICY);
        Policy deleteResource = policyRepository.findPolicyByName(DELETE_RESOURCE_POLICY);
        Policy openedDuringDayTime = policyRepository.findPolicyByName(OPEN_DURING_DAYTIME_POLICY);
        Policy openClosed = policyRepository.findPolicyByName(OPEN_CLOSED_POLICY);
        Policy confirmation = policyRepository.findPolicyByName(CONFIRMATION_POLICY);
        Policy regex = policyRepository.findPolicyByName(REGEX_POLICY);
        Policy startsWith = policyRepository.findPolicyByName(STARTSWITH_POLICY);
        Policy coronaAccess = policyRepository.findPolicyByName(CORONA_ACCESS_POLICY);
        Policy relation = policyRepository.findPolicyByName(RELATION_POLICY);

        AbstractPolicy singleWoonplaatsRulePolicy = createDisjunctionPolicy(woonplaatsPolicy, Arrays.asList(woonplaatsRule));
        AbstractPolicy naamEnWoonplaatsPolicy = createConjuctionPolicy(woonplaatsNaamPolicy, Arrays.asList(voornaamRule, woonplaatsRule));
        AbstractPolicy naamEnWoonplaatsLeeftijdPolicy = createConjuctionPolicy(woonplaatsNaamLeeftijdPolicy, Arrays.asList(voornaamRule, woonplaatsRule, leeftijdTussenRule));
        AbstractPolicy visitorCounterPolicy = createPermitPolicy(visitorCounter, Arrays.asList(visitorCounterRule));
        AbstractPolicy MaximumNumberOfCustomersPerHourPolicy = createConjuctionPolicy(maximumNumberOfCustomersPerHour, Arrays.asList(maximumNumberOfCustomersPerHourRule));
        AbstractPolicy maximumNumberOfCustomersPolicy = createConjuctionPolicy(maximumNumberOfCustomers, Arrays.asList(maximumNumberOfCustomersRule));
        AbstractPolicy deleteResourcePolicy = createDisjunctionPolicy(deleteResource, Arrays.asList(deleteResourceRule));
        AbstractPolicy openedDuringDayTimePolicy = createDisjunctionPolicy(openedDuringDayTime, Arrays.asList(singleDayTimeRule));
        AbstractPolicy openClosedPolicy = createConjuctionPolicy(openClosed, Arrays.asList(openClosedRule));
        AbstractPolicy confirmationPolicy = createConjuctionPolicy(confirmation, Arrays.asList(confirmationRule));
        AbstractPolicy regexPolicy = createConjuctionPolicy(regex, Arrays.asList(regexRule));
        AbstractPolicy startsWithPolicy = createConjuctionPolicy(startsWith, Arrays.asList(startsWithRule));
        AbstractPolicy coronaAccessPolicy = createConjuctionDisjunctionPolicy(coronaAccess, disjunction);
        AbstractPolicy relationPolicy = createDisjunctionPolicy(relation, Arrays.asList(relationRule));

        policies.add(singleWoonplaatsRulePolicy);
        policies.add(naamEnWoonplaatsPolicy);
        policies.add(naamEnWoonplaatsLeeftijdPolicy);
        policies.add(visitorCounterPolicy);
        policies.add(MaximumNumberOfCustomersPerHourPolicy);
        policies.add(maximumNumberOfCustomersPolicy);
        policies.add(deleteResourcePolicy);
        policies.add(openedDuringDayTimePolicy);
        policies.add(openClosedPolicy);
        policies.add(confirmationPolicy);
        policies.add(regexPolicy);
        policies.add(startsWithPolicy);
        policies.add(coronaAccessPolicy);
        policies.add(relationPolicy);

        policyRepository.findAll().stream().forEach(found -> LOGGER.debug(found.toString()));
        return policies;
    }

    public AbstractPolicy createConjuctionPolicy(Policy policy, List<Rule> rules) {
        ConjuctionPolicy conjuctionPolicy = new ConjuctionPolicy(rules);
        conjuctionPolicy.setName(policy.getName());
        conjuctionPolicy.setId(policy.getId());

        return conjuctionPolicy;
    }

    public AbstractPolicy createConjuctionDisjunctionPolicy(Policy policy, List<List<Rule>> rules) {
        ConjunctionDisjunctionPolicy conjuctionPolicy = new ConjunctionDisjunctionPolicy(rules);
        conjuctionPolicy.setName(policy.getName());
        conjuctionPolicy.setId(policy.getId());

        return conjuctionPolicy;
    }

    public AbstractPolicy createDisjunctionPolicy(Policy policy, List<Rule> rules) {
        DisjunctionPolicy disjunctionRulePolicy = new DisjunctionPolicy(rules);
        disjunctionRulePolicy.setId(policy.getId());
        disjunctionRulePolicy.setName(policy.getName());

        return disjunctionRulePolicy;
    }

    public AbstractPolicy createPermitPolicy(Policy policy, List<Rule> rules) {
        PermitPolicy permitPolicy = new PermitPolicy(rules);
        permitPolicy.setId(policy.getId());
        permitPolicy.setName(policy.getName());

        return permitPolicy;
    }

}
