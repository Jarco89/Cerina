package nl.schutrup.cerina.irma.client;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.schutrup.cerina.irma.client.disclosure.DisclosureRequest;
import nl.schutrup.cerina.irma.client.issuance.CoronaIssuanceRequest;
import nl.schutrup.cerina.irma.client.issuance.IssuanceRequest;
import nl.schutrup.cerina.irma.client.session.result.ServerResult;
import nl.schutrup.cerina.irma.client.session.start.response.ServerResponse;
import nl.schutrup.cerina.irma.client.session.status.Status;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.irmacard.api.common.ApiClient;
import org.irmacard.api.common.AttributeDisjunctionList;
import org.irmacard.credentials.info.CredentialIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@Service
public class IrmaServerClient {


    private static final String SESSION = "/session";
    private static final String RESULT = "/result";
    private static final String RESULT_JWT = "/result-jwt";
    private static final String STATUS = "/status";
    private static final String STATUSEVENTS = "/statusevents";

    private static final String CERINA_SCHEME_MANAGER = "cerina-demo";
    private static final String CERINA_ISSUER = "cerina-issuer";
    private static final String CERINA_CREDENTIAL = "cerina-credential";
    private static final String CERINA_CORONA_CREDENTIAL = "cerina-corona-credential";

    private static final String SCENARIO_1_REQUEST = "{\n" +
            "  \"@context\": \"https://irma.app/ld/request/disclosure/v2\",\n" +
            "  \"disclose\": [\n" +
            "    [\n" +
            "      [\n" +
            "        \"irma-demo.MijnOverheid.address.city\"\n" +
            "      ]\n" +
            "    ]\n" +
            "  ]\n" +
            "}";
    private static final String SCENARIO_2_REQUEST = "{\n" +
            "  \"@context\": \"https://irma.app/ld/request/disclosure/v2\",\n" +
            "  \"disclose\": [\n" +
            "    [\n" +
            "      [\n" +
            "        \"irma-demo.MijnOverheid.address.city\",\n" +
            "        \"irma-demo.MijnOverheid.ageLower.over18\",\n" +
            "        \"irma-demo.MijnOverheid.fullName.firstname\"\n" +
            "      ]\n" +
            "    ]\n" +
            "  ]\n" +
            "}";

    private static final Logger LOGGER = LoggerFactory.getLogger(IrmaServerClient.class);

    @Value("${irma.server.address}")
    private String irmaServerAddress;

    @Value("${irma.client.authorization.token}")
    private String irmaClientAuthorizationToken;

    @Autowired
    private Map<String, String> tokenToSession;

    @Autowired
    private IrmaServerClientConfiguration irmaServerClientConfiguration;


    /**
    public ServerResponse retrieveNewSessionScenario2() throws Exception {
        return retrieveNewSession(SCENARIO_2_REQUEST);
    }


    public ServerResponse retrieveNewSessionScenario1() throws Exception {
        return retrieveNewSession(SCENARIO_1_REQUEST);
    }
    **/


    public ServerResponse getIssuanceSession(CoronaIssuanceRequest issuanceRequest) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, URISyntaxException {
        String iss = "cerina";

        CredentialIdentifier credentialIdentifier = new CredentialIdentifier(CERINA_SCHEME_MANAGER, CERINA_ISSUER, CERINA_CORONA_CREDENTIAL);

        HashMap<CredentialIdentifier, HashMap<String, String>> credentialList = new HashMap<>();

        HashMap<String, String> values = new HashMap<>();

        // first
        //if (!issuanceRequest.getGevaccineerd().isEmpty() && !issuanceRequest.getAantalvaccinaties().isEmpty()) {
            values.put("gevaccineerd", issuanceRequest.getGevaccineerd());
            values.put("aantalvaccinaties", issuanceRequest.getAantalvaccinaties());
        //} else if (!issuanceRequest.getVaccinatiepaspoort().isEmpty() && !issuanceRequest.getSneltest().isEmpty()) {
            values.put("vaccinatiepaspoort", issuanceRequest.getVaccinatiepaspoort());
            values.put("sneltest", issuanceRequest.getSneltest());
        //} else if (!issuanceRequest.getAntilichamen().isEmpty() && !issuanceRequest.getAntilichamendatum().isEmpty()) {
            values.put("antilichamen", issuanceRequest.getAntilichamen());
            values.put("antilichamendatum", issuanceRequest.getAntilichamendatum());
        //} else if (!issuanceRequest.getSneltest2().isEmpty() && !issuanceRequest.getSneltestdatum().isEmpty()) {
            //values.put("sneltest", issuanceRequest.getSneltest());
            values.put("sneltestdatum", issuanceRequest.getSneltestdatum());
        //} else {
          //  throw new RuntimeException(("Not matching any of the or dislcosure request"));
       // }

        credentialList.put(credentialIdentifier, values);

        PrivateKey privateKey = irmaServerClientConfiguration.getRequestorJWTPrivateKey();

        String signed = ApiClient.getSignedIssuingJWT(credentialList, iss, SignatureAlgorithm.RS256, privateKey);

        LOGGER.info("Signed = {} ", signed);
        StringEntity params = new StringEntity(signed);
        HttpPost httppost = createHttpPost(irmaServerAddress + SESSION, params);
        httppost.setEntity(params);

        return executeRequest(httppost);
    }

    public ServerResponse getIssuanceSession(IssuanceRequest issuanceRequest) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, URISyntaxException {
        String iss = "cerina";

        CredentialIdentifier credentialIdentifier = new CredentialIdentifier(CERINA_SCHEME_MANAGER, CERINA_ISSUER, CERINA_CREDENTIAL);

        HashMap<CredentialIdentifier, HashMap<String, String>> credentialList = new HashMap<>();

        HashMap<String, String> values = new HashMap<>();

        values.put("bsn", issuanceRequest.getBsn());
        values.put("stad", issuanceRequest.getStad());
        values.put("leeftijd", issuanceRequest.getLeeftijd());
        values.put("naam", issuanceRequest.getNaam());

        credentialList.put(credentialIdentifier, values);

        PrivateKey privateKey = irmaServerClientConfiguration.getRequestorJWTPrivateKey();

        String signed = ApiClient.getSignedIssuingJWT(credentialList, iss, SignatureAlgorithm.RS256, privateKey);

        LOGGER.info("Signed = {} ", signed);
        StringEntity params = new StringEntity(signed);
        HttpPost httppost = createHttpPost(irmaServerAddress + SESSION, params);
        httppost.setEntity(params);

        return executeRequest(httppost);
    }

    public ServerResponse retrieveNewSessionWithJWT(AttributeDisjunctionList attributeDisjunctions) throws Exception {
        PrivateKey privateKey = irmaServerClientConfiguration.getRequestorJWTPrivateKey();

        String signed = ApiClient.getDisclosureJWT(attributeDisjunctions, "cerina", SignatureAlgorithm.RS256, privateKey);
        LOGGER.info("Signed = {} ", signed);

        StringEntity params = new StringEntity(signed);
        HttpPost httppost = createHttpPost(irmaServerAddress + SESSION, params);
        return executeRequest(httppost);
    }

    public ServerResponse retrieveNewSessionWithJWT(DisclosureRequest disclosureRequest) throws Exception {
        PrivateKey privateKey = irmaServerClientConfiguration.getRequestorJWTPrivateKey();

        String signed = Jwts.builder()
                .setIssuer("cerina")
                .setSubject(disclosureRequest.getSub())
                .claim("sprequest", disclosureRequest.getSpRequest())
                .claim("iat", disclosureRequest.getIat())
                .signWith(
                        SignatureAlgorithm.RS256,
                        privateKey
                )
                .compact();

        LOGGER.info("Signed = {} ", signed);
        StringEntity params = new StringEntity(signed);
        HttpPost httppost = createHttpPost(irmaServerAddress + SESSION, params);

        return executeRequest(httppost);
    }

    public ServerResponse executeRequest(HttpPost httpPost) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpResponse response = httpclient.execute(httpPost);

        HttpEntity entity = response.getEntity();

        String responseString = EntityUtils.toString(entity, "UTF-8");
        LOGGER.info("Responsestring: {}", responseString);

        ObjectMapper mapper = new ObjectMapper();
        ServerResponse sP = mapper.readValue(responseString, ServerResponse.class); //jsonString is your actual json string.

        LOGGER.debug("Response: {} ", entity.toString());

        httpclient.close();
        return sP;
    }


    private ServerResponse retrieveNewSession(String request) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(irmaServerAddress + SESSION);

        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");
        httppost.setHeader("Authorization", irmaClientAuthorizationToken);

        StringEntity params = new StringEntity(request);

        // Request parameters and other properties.
        httppost.setEntity(params);

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);

        HttpEntity entity = response.getEntity();

        String responseString = EntityUtils.toString(entity, "UTF-8");
        LOGGER.debug("Responsestring: {}", responseString);

        ObjectMapper mapper = new ObjectMapper();
        ServerResponse sP = mapper.readValue(responseString, ServerResponse.class); //jsonString is your actual json string.

        LOGGER.debug("Putting: {} ", sP.getSessionPtr().getU());
        tokenToSession.put(sP.getSessionPtr().getU(), sP.getToken());
        LOGGER.debug("sP: {}", sP);

        LOGGER.debug("Response: {} ", entity.toString());

        httpclient.close();
        return sP;
    }

    public Status retrieveSessionStatusEvents(String sessionPointer) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String token = tokenToSession.get("session/" + sessionPointer);
        LOGGER.debug("Found token: {} for sessionpointer {}", token, sessionPointer);

        HttpGet httpGet = createHttpGet(irmaServerAddress + SESSION + "/" + token + STATUSEVENTS);

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httpGet);

        HttpEntity entity = response.getEntity();

        String responseString = EntityUtils.toString(entity, "UTF-8");
        LOGGER.debug("Responsestring: {}", responseString);

        ObjectMapper mapper = new ObjectMapper();
        Status status = mapper.readValue(responseString, Status.class); //jsonString is your actual json string.

        LOGGER.debug("sP: {}", status);

        LOGGER.debug("Response: {} ", entity.toString());

        httpclient.close();

        return status;
    }

    public String retrieveSessionStatus(String sessionPointer) throws IOException {

        String token = tokenToSession.get("session/" + sessionPointer);
        LOGGER.debug("Found token: {} for sessionpointeru {}", token, sessionPointer);


        CloseableHttpClient httpclient = HttpClients.createDefault();
        LOGGER.debug("token is here {}", token);
        HttpGet httppost = createHttpGet(irmaServerAddress + SESSION + "/" + token + STATUS);

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);

        HttpEntity entity = response.getEntity();

        String responseString = EntityUtils.toString(entity, "UTF-8");
        LOGGER.debug("Responsestring: {}", responseString);

        ObjectMapper mapper = new ObjectMapper();
        String status = mapper.readValue(responseString, String.class); //jsonString is your actual json string.

        LOGGER.debug("sP: {}", status);

        LOGGER.debug("Response: {} ", entity.toString());

        httpclient.close();

        return status;
    }

    public ServerResult retrieveSessionResultJWT(String token) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException, IllegalAccessException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = createHttpGet(irmaServerAddress + SESSION + "/" + token + RESULT_JWT);

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httpGet);

        HttpEntity entity = response.getEntity();

        String responseString = EntityUtils.toString(entity, "UTF-8");
        LOGGER.debug("Responsestring: {}", responseString);

        RSAPublicKey rsaPublicKey = irmaServerClientConfiguration.getIrmaServerJWTPublicKey();

        Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, null);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("irmaserver")
                .build(); //Reusable verifier instance

        // Valid JWT, so the response is not modified
        DecodedJWT jwt = verifier.verify(responseString);
        String payload = jwt.getPayload();

        LOGGER.debug("jwt res: {}", jwt);
        LOGGER.debug("jwt payload: {}", payload);

        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();

        String headerJson = new String(decoder.decode(jwt.getHeader()));
        String payloadJson = new String(decoder.decode(jwt.getPayload()));

        LOGGER.debug("headerJson: {}", headerJson);
        LOGGER.debug("payloadJson: {}", payloadJson);

        ServerResult sP = transformToServerResult(payloadJson); //jsonString is your actual json string.

        String proofStatus = sP.getProofStatus();
        LOGGER.debug("Proof status: {}", proofStatus);

        // We rely on the proof from te IRMA server.
        if (!"VALID".equalsIgnoreCase(proofStatus)) {
            throw new IllegalAccessException("Proof is not valid: " + proofStatus);
        }

        httpclient.close();

        return sP;
    }


    public ServerResult retrieveSessionResult(String token) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = createHttpGet(irmaServerAddress + SESSION + "/" + token + RESULT);

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httpGet);

        HttpEntity entity = response.getEntity();

        String responseString = EntityUtils.toString(entity, "UTF-8");
        ServerResult sP = transformToServerResult(responseString);

        httpclient.close();

        return sP;
    }

    private ServerResult transformToServerResult(String responseString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        LOGGER.debug("Responsestring: {}", responseString);
        ServerResult sP = mapper.readValue(responseString, ServerResult.class); //jsonString is your actual json string.
        LOGGER.debug("sP: {}", sP);
        return sP;

    }


    private HttpPost createHttpPost(String url, StringEntity entity) {
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "text/plain");

        // Request parameters and other properties.
        httppost.setEntity(entity);

        return httppost;
    }

    private HttpGet createHttpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", irmaClientAuthorizationToken);
        return httpGet;

    }
}
