package nl.schutrup.cerina.controller;

import nl.schutrup.cerina.abac.repository.PolicyRepository;
import nl.schutrup.cerina.irma.client.IrmaServerClient;
import nl.schutrup.cerina.irma.client.session.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IrmaServerClient irmaServerClient;

    @Autowired
    private PolicyRepository policyRepository;

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
        return "index";
    }


    @GetMapping("/scenario-1")
    public String scenario1Page(Model model) {
        return "scenario-1";
    }

    @GetMapping("/{scenario}")
    public String scenario(@PathVariable String scenario) {
        LOGGER.info("starting scenario: {}", scenario);
        return scenario;
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

    // IRMA specific mapping...
    // Harcoded URL in the front-end

    @GetMapping(value = "/session/{sessionPointer}/status", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String status(@PathVariable(value = "sessionPointer") String sessionPointer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("status called session with sessionPointer {}", sessionPointer);
        String status = irmaServerClient.retrieveSessionStatus(sessionPointer);
        String payload = "\"" + status + "\"";
        return payload;
    }

    @GetMapping(value = "/session/{sessionPointer}/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String session(@PathVariable(value = "sessionPointer") String sessionPointer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("status called session with sessionPointer {}", sessionPointer);
        String status = irmaServerClient.retrieveSessionStatus(sessionPointer);
        String payload = "\"" + status + "\"";
        return payload;
    }

    @GetMapping(value = "/session/{sessionPointer}/statusevents")
    @ResponseBody
    public Status statusEvents(@PathVariable(value = "sessionPointer") String sessionPointer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("statusEvents called session with sessionPointer {}", sessionPointer);
        Status status = irmaServerClient.retrieveSessionStatusEvents(sessionPointer);
        return status;
    }

}
