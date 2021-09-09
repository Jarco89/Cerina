require('@privacybydesign/irma-css');

const IrmaCore = require('@privacybydesign/irma-core');
const Web      = require('@privacybydesign/irma-web');
const Client   = require('@privacybydesign/irma-client');

var scenarioUrl =  document.getElementsByName('irma-scenario')[0].value;

console.log("Scenariourl: ", scenarioUrl);

const irma = new IrmaCore({
    debugging: true,            // Enable to get helpful output in the browser console
    element:   '#irma-web-form', // Which DOM element to render to

    // Back-end options
    session: {
        // Point this to your controller:
        url: 'http://192.168.178.17:8080/cerina-endpoint',

        start: {
            url: o => `${o.url}/session/${scenarioUrl}/start`,
            method: 'GET'
        },

        result: {
        url: (o, {sessionPtr, sessionToken}) => `${o.url}/session/${scenarioUrl}/${sessionToken}/result`,
        method: 'GET',

        },
      }
});

irma.use(Web);
irma.use(Client);

irma.start()
.then(result => setTimeout(location.reload.bind(location), 5000))
.catch(error => console.error("Couldn't do what you asked 😢", error));

document.getElementById('abort-button').addEventListener('click', () => {
    irma.abort();

});