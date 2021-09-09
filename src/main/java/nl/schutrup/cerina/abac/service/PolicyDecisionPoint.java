package nl.schutrup.cerina.abac.service;

import nl.schutrup.cerina.abac.model.policy.AbstractPolicy;
import nl.schutrup.cerina.abac.model.policy.MetaPolicy;
import nl.schutrup.cerina.abac.model.policy.PolicyConstants;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolicyDecisionPoint {

    @Autowired
    private PolicyAdministrationPoint policyAdministrationPoint;

    @Autowired
    private MetaPolicy metaPolicy;

    public PolicyResult evaluate(AccessRequest accessRequest, String scenario) {
        AbstractPolicy abstractPolicy;
        switch (scenario) {
            case "scenario-1-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.CITY_POLICY);
                break;
            case "scenario-2-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.CITY_AND_NAME_POLICY);
                break;
            case "scenario-4-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.CITY_AND_NAME_AND_AGE_POLICY);
                break;
            case "scenario-5-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.VISITOR_COUNTER_POLICY);
                break;
            case "scenario-6-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.MAXIMUM_NUMBER_OF_CUSTOMERS_PER_HOUR_POLICY);
                break;
            case "scenario-7-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.MAXIMUM_NUMBER_OF_CUSTOMERS_PER_DAY_POLICY);
                break;
            case "scenario-8-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.DELETE_RESOURCE_POLICY);
                break;
            case "scenario-9-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.CONFIRMATION_POLICY);
                break;
            case "scenario-10-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.OPEN_DURING_DAYTIME_POLICY);
                break;
            case "scenario-11-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.OPEN_CLOSED_POLICY);
                break;
            case "scenario-12-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.REGEX_POLICY);
                break;
            case "scenario-13-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.STARTSWITH_POLICY);
                break;
            case "scenario-15-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.CORONA_ACCESS_POLICY);
                break;
            case "scenario-16-jwt":
                abstractPolicy = metaPolicy.getPolicyFor(policyAdministrationPoint.loadPolicies(), PolicyConstants.RELATION_POLICY);
                break;

            default:
                throw new RuntimeException("Unsupported request: " + scenario);
        }

        return abstractPolicy.doAutorisationValidation(accessRequest.getSubject(), accessRequest.getResource(), accessRequest.getOperation());
    }
}
