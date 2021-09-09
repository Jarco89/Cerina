package nl.schutrup.cerina.controller;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.resource.Library;
import nl.schutrup.cerina.abac.service.PolicyEnforcementPoint;
import nl.schutrup.cerina.irma.client.issuance.CoronaIssuanceRequest;
import nl.schutrup.cerina.irma.client.issuance.IssuanceRequest;
import nl.schutrup.cerina.irma.client.session.result.ServerResult;
import nl.schutrup.cerina.irma.client.session.start.response.ServerResponse;
import nl.schutrup.cerina.service.IrmaClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("cerina-endpoint")
public class CerinaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CerinaController.class);
    private static final String REFUSED = "REFUSED";


    @Autowired
    private IrmaClientService irmaClientService;

    @Autowired
    private PolicyEnforcementPoint policyEnforcementPoint;

    @Autowired
    private Map<String, String> tokenToSession;


    @GetMapping("/session/{scenario}/start")
    public ServerResponse scenarioStartJwt(@PathVariable String scenario) throws Exception {
        LOGGER.info("start called for signed init session JWT {}", scenario);
        return irmaClientService.retrieveNewSessionJWT(scenario);
    }

    @GetMapping("/session/{scenario}/{token}/result")
    public ServerResult scenarioResultJWT(@PathVariable String scenario, @PathVariable(value = "token") String token, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("result called for end session scenario {} with token {} for jwt", scenario, token);

        ServerResult result;
        switch (scenario) {
            case "scenario-1-jwt":
            case "scenario-2-jwt":
            case "scenario-4-jwt":
            case "scenario-6-jwt":
            case "scenario-7-jwt":
            case "scenario-9-jwt":
            case "scenario-11-jwt":
            case "scenario-12-jwt":
            case "scenario-13-jwt":
            case "scenario-15-jwt":
            case "scenario-16-jwt":
                result = policyEnforcementPoint.autoriseer(token, scenario, new Library(), Operation.ACCESS_REQUEST);
                break;
            case "scenario-8-jwt":
                result = policyEnforcementPoint.autoriseer(token, scenario, new Library(), Operation.DELETE_REQUEST);
                break;
            case "scenario-3": // issue attributes
            case "scenario-14":
                result = irmaClientService.retrieveSessionResult(token);
                break;
            case "scenario-5-jwt":
                result = policyEnforcementPoint.autoriseer(token, scenario, new Library(), Operation.UPDATE_REQUEST); // counter
                break;
            case "scenario-10-jwt":
                result = policyEnforcementPoint.autoriseer(token, scenario, new Library(), Operation.READ_REQUEST);
                break;
            default:
                throw new RuntimeException("Unsupported request: " + scenario);
        }

        return getServerResult(response, result);
    }


    /**
    // TOOD remove old.
    @GetMapping("/session/scenario-1/start")
    public ServerResponse scenario1Start() throws Exception {
        LOGGER.debug("start called for init session");
        return irmaClientService.retrieveNewSessionScenario1();
    }

    @GetMapping("/session/scenario-2/start")
    public ServerResponse start() throws Exception {
        LOGGER.info("start called for init session");
        return irmaClientService.retrieveNewSessionScenario2();
    }
     **/

    @PostMapping("/session/scenario-3/start")
    public ServerResponse start(@RequestBody IssuanceRequest json) throws Exception {
        LOGGER.info("start called for init session 3 with params {}", json);
        return irmaClientService.getIssuanceSession(json);
    }

    @PostMapping("/session/scenario-14/start")
    public ServerResponse startScenario14(@RequestBody CoronaIssuanceRequest json) throws Exception {
        LOGGER.info("start called for init session 14 with params {}", json);
        return irmaClientService.getIssuanceSession(json);
    }

    @PostMapping("/session/scenario-3-cerina/start")
    public ServerResponse startCerinaIssuer(@RequestBody IssuanceRequest json) throws Exception {
        LOGGER.info("start called for init session 3 with params {}", json);
        return null;
    }

    @GetMapping("/session/scenario-1/{token}/result")
    @ResponseBody
    public ServerResult scenario1Result(@PathVariable(value = "token") String token, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("result called for end session scenario 1 with token {}", token);
        ServerResult serverResult = irmaClientService.retrieveSessionResult(token);
        //ServerResult result = policyEnforcementPoint.autoriseerScenario1(serverResult);
        //return getServerResult(response, serverResult, result);
        return null;
    }

    @GetMapping("/session/scenario-2/{token}/result")
    @ResponseBody
    public ServerResult scenario2Result(@PathVariable(value = "token") String token, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("result called for end session scenario 1  with token {}", token);
        ServerResult serverResult = irmaClientService.retrieveSessionResult(token);
        //ServerResult result = policyEnforcementPoint.autoriseerScenario2(serverResult);
        //return getServerResult(response, serverResult, result);
        return null;
    }

    private ServerResult getServerResult(HttpServletResponse response, ServerResult result) {
        if (result.getStatus().equals(REFUSED)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return result;
    }


}
