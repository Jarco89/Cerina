package nl.schutrup.cerina.abac.model.policy;

import org.springframework.stereotype.Component;

@Component
public class PolicyConstants {


    // Attributenames
    public static final String VOORNAAM = "voornaam";
    public static final String EXPECTED_VOORNAAM_VALUE = "John";

    public static final String STAD = "stad";
    public static final String EXPECTED_STAD_VALUE = "Amsterdam";

    public static final String LEEFTIJD = "Leeftijd";
    public static final Integer LEEFTIJD_MINIMAAL_VALUE = 16;
    public static final Integer LEEFTIJD_MAXIMAAL_VALUE = 65;

    public static final String CORONA_GEVACINEERD = "gevaccineerd";
    public static final Boolean CORONA_GEVACINEERD_EXPECTED_VALUE = true;

    public static final String AANTAL_CORONA_VACCINATIES = "aantalvaccinaties";
    public static final Integer AANTAL_CORONA_VACCINATIES_EXPECTED_WAARDE = 2;

    public static final String CORONA_VACCINATIE_PASPOORT = "vaccinatiepaspoort";
    public static final Boolean CORONA_VACCINATIE_PASPOORT_WAARDE = true;

    public static final String CORONA_SNELTEST = "sneltest";
    public static final Boolean CORONA_SNELTEST_WAARDE = true;
    public static final Integer CORONA_SNELTEST_MINUTEN = 30;
    public static final String CORONA_SNELTEST_DATUM = "sneltestdatum";

    public static final String CORONA_ANTILICHAMEN = "antilichamen";
    public static final String CORONA_ANTILICHAMEN_DATUM = "antilichamendatum";
    public static final Integer CORONA_ANTILICHAMEN_DAGEN_IN_VERLEDEN = 30;
    public static final Boolean CORONA_ANTISTOFFEN_WAARDE = true;
    public static final String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";

    // Regex pattern
    public static final String AMSTERDAM_POSTCODE = "postcode";
    public static final String AMSTERDAM_REGEX_POSTCODE = "[1-1]{1}[0-1]{1}[0-9]{1}[0-9]{1}[a-zA-Z]{2}";



    // Policynames
    public static final String CITY_POLICY = "CityPolicy";
    public static final String CITY_AND_NAME_POLICY = "CityAndNamePolicy";
    public static final String CITY_AND_NAME_AND_AGE_POLICY = "CityAndNameAndAgePolicy";

    public static final String VISITOR_COUNTER_POLICY = "VisitorCounterPolicy";
    public static final String MAXIMUM_NUMBER_OF_CUSTOMERS_PER_HOUR_POLICY = "MaximumNumberOfCustomersPerHourPolicy";
    public static final String MAXIMUM_NUMBER_OF_CUSTOMERS_PER_DAY_POLICY = "MaximumNumberOfCustomersPolicy";
    public static final String DELETE_RESOURCE_POLICY = "DeleteResourcePolicy";

    public static final String OPEN_DURING_DAYTIME_POLICY = "OpenDuringDayTimePolicy";
    public static final String CONFIRMATION_POLICY = "ConfirmationPolicy";
    public static final String OPEN_CLOSED_POLICY = "OpenClosedPolicy";
    public static final String REGEX_POLICY = "RegexPolicy";
    public static final String STARTSWITH_POLICY = "StartswithPolicy";
    public static final String CORONA_ACCESS_POLICY = "CoronaAccessPolicy";
    public static final String RELATION_POLICY = "RelationPolicy";



}
