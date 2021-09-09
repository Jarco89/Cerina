package nl.schutrup.cerina.irma.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;

@Configuration
public class IrmaServerClientConfiguration {

    @Autowired
    private ResourceLoader resourceLoader;

    // requestor_jwt_private
    public PrivateKey getRequestorJWTPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException, IOException {
        Resource resource= resourceLoader.getResource("classpath:/requestor_jwt_private.key");

        // read key
        String privateKeyB64 = new BufferedReader(new InputStreamReader(resource.getInputStream())).lines().collect(Collectors.joining());

        privateKeyB64 = privateKeyB64.replace("-----BEGIN PRIVATE KEY-----", "");
        privateKeyB64 = privateKeyB64.replace("-----END PRIVATE KEY-----", "");

        byte[] privateKeyDecoded = Base64.getDecoder().decode(privateKeyB64);

        //create key spec
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyDecoded);

        // create key form spec
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(spec);

        return privateKey;
    }

    // irma_server.pem
    public RSAPublicKey getIrmaServerJWTPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException, IOException {
        Resource resource= resourceLoader.getResource("classpath:/irma_server.pem");
        String key =  new BufferedReader(new InputStreamReader(resource.getInputStream())).lines().collect(Collectors.joining());

        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

        return publicKey;

    }
}
