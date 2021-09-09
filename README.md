# Setup 

# Volg eerst de getting-started om IRMA te installeren op:
https://irma.app/docs/getting-started/

# Vervolgens, onze irma-frontend-packages uitchecken en naar wijzen. De directory
IN ~/Cerina/node_modules/@privacybydesign$ 
voer uit:
sudo npm link /home/jarco/irma-frontend-packages/plugins/irma-client

IN ~/Cerina/node_modules/@privacybydesign$ 
voer uit: 
sudo npm link /home/jarco/irma-frontend-packages/plugins/irma-web

~/Cerina/node_modules/@privacybydesign$ sudo npm link /home/jarco/afstuderen/irma-frontend-packages/irma-core

# Packages intalleren 
npm install

# De IRMA-server schema's downloaden.
irma scheme download

# IRMA starten met productie config location
irma server -c /home/jarco/irma-config/irma-config.yml -vv
 
# Voor private en public key waarbij de irma server de private key bevat:
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout jwt_private.key -out jwt_certificate.crt

# Voor private en public key waarbij de requestor (Cerina) de private key bevat:
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout requestor_jwt_private.key -out requestor_jwt_certificate.crt
openssl x509 -pubkey -noout -in requestor_jwt_certificate.crt  > requestor_jwt_certificate.pem

# ISSUING # 

# Maken van het schema 
cd /home/jarco/.local/share/irma/irma_configuration/cerina-demo
cd cerina-issuer
irma scheme issuer keygen
ls PublicKeys PrivateKeys
cd .. 

# Uitvoeren in cerina-demo:
irma scheme keygen
irma scheme sign
irma scheme verify

# Kopieer de cerina-demo naar: src/main/resources/schema/cerima-demo/ om voor back-up in het huidige project.

## Om op mobile te draaien:
flutter run --flavor alpha -t lib/main.dart
kopieer de cerina-demo naar: src/main/resources/schema/cerima-demo/ om deze te gebruiken in het huidige project.
Activeer debug mode op de app!

# Toevoegen entries in hostfile voor docker-compose
127.0.0.1 cerina

#H2 Console
http://192.168.178.17:8080/h2-console/
jdbc:h2:mem:cerinadbn.s.cerina.controller.CerinaController
username: cerina
password: <>

# Cerina opstarten
# Geef het ipadres op, bijvoorbeeld: server.port=8080 en server.address=192.168.178.17
mvn clean install
#
cd target
java -jar cerina.jar

# Navigeer vervolgens naar http://192.168.178.17:8080/ en start de scenario's.

 # Common errors:
 Bij CORS request did not succeed >> ga naar de IRMA server (bijvoorbeeld https://192.168.178.17:8088/irma/session/1tq8SvTXAoWVeT9ejJ8E/status) en accepteer 
 het self-signed certificaat handmatig.
