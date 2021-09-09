require('@privacybydesign/irma-css');

const IrmaCore = require('@privacybydesign/irma-core');
const Web      = require('@privacybydesign/irma-web');
const Client   = require('@privacybydesign/irma-client');

window.onload = function() {

var scenarioUrl =  document.getElementsByName('irma-scenario')[0].value;
var bsn =  document.getElementById('bsn').value;
var naam =  document.getElementById('naam').value;
var stad =  document.getElementById('stad').value;
var leeftijd =document.getElementById('leeftijd').value;

const irma = new IrmaCore({
    debugging: true,            // Enable to get helpful output in the browser console
    element:   '#irma-web-form', // Which DOM element to render to

    // Back-end options
    session: {
        // Point this to your controller:
        url: 'http://192.168.178.17:8080/cerina-endpoint',

 // Define your disclosure request:
        start: {
          method: 'POST',
            url: o => `${o.url}/session/scenario-3/start`,
            method: 'POST',
             headers: {
                        'Content-Type': 'application/json'
                      },
               body: JSON.stringify({
              "bsn": bsn,
              "naam": naam,
              "stad": stad,
              "leeftijd": leeftijd,
            })
        },

        result: {
        url: (o, {sessionPtr, sessionToken}) => `${o.url}/session/scenario-3/${sessionToken}/result`,
        method: 'GET',

        },
      }
});

irma.use(Web);
irma.use(Client);

document.getElementById('issuance').addEventListener('click', () => {

    irma.start()
    .then(result => console.log("Successful disclosure! 🎉", result))
    .catch(error => console.error("Couldn't do what you asked 😢", error));
    });

document.getElementById('abort-button').addEventListener('click', () => {
    irma.abort();
});


}