const path = require('path');
const webpack = require('webpack');

const srcdir = path.resolve(__dirname, 'src/main/frontend');

const entries = {
  irma: path.join(srcdir, 'entry/index-with-irma.js'),
  irmarefresher: path.join(srcdir, 'entry/index-with-irma-refresher.js'),
  irmaissuer: path.join(srcdir, 'entry/index-with-irma-issuer.js'),
  irmaissuercorona: path.join(srcdir, 'entry/index-with-irma-issuer-corona.js')
};

module.exports = {
  entry: entries,
  output: {
    publicPath: '/static/bundle/',
  },

  resolve: {
    extensions: ['.js'],
    modules: [srcdir, 'node_modules'],
    mainFields: ['browser', 'main', 'module'],
  }

};