require('@privacybydesign/irma-css');

const IrmaCore = require('@privacybydesign/irma-core');
const Web      = require('@privacybydesign/irma-web');
const Client   = require('@privacybydesign/irma-client');

window.onload = function() {

var scenarioUrl =  document.getElementsByName('irma-scenario')[0].value;
var gevaccineerd =  document.getElementById('gevaccineerd').value;
var aantalvaccinaties =  document.getElementById('aantalvaccinaties').value;
var vaccinatiepaspoort =  document.getElementById('vaccinatiepaspoort').value;
var antilichamen =document.getElementById('antilichamen').value;
var antilichamendatum =document.getElementById('antilichamendatum').value;
var sneltest =document.getElementById('sneltest').value;
var sneltest2 =document.getElementById('sneltest2').value;
var sneltestdatum = document.getElementById('sneltestdatum').value



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
            url: o => `${o.url}/session/scenario-14/start`,
            method: 'POST',
             headers: {
                        'Content-Type': 'application/json'
                      },
               body: JSON.stringify({
              "gevaccineerd": gevaccineerd,
              "aantalvaccinaties": aantalvaccinaties,
              "vaccinatiepaspoort": vaccinatiepaspoort,
              "antilichamen": antilichamen,
              "antilichamendatum": antilichamendatum,
              "sneltest": sneltest,
              "sneltest2": sneltest2,
              "sneltestdatum": sneltestdatum,

            })
        },

        result: {
        url: (o, {sessionPtr, sessionToken}) => `${o.url}/session/scenario-14/${sessionToken}/result`,
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
