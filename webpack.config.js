/**
 * For LOCAL development mode webpack config
 **/
const os = require('os');
const path = require('path');
const { merge } = require('webpack-merge');
const commonConfig = require('./webpack.config.common');


module.exports = merge(commonConfig, {
  mode: 'development',
  devtool: 'eval-source-map',
  output: {
    filename: '[name].js',
    path: path.resolve(__dirname, './src/main/resources/public/bundle'),
  },
 module: {
     rules: [
       {
         test: /\.css$/i,
         use: [
           'style-loader',
           'css-loader'
         ]
       },
       {
         test: /\.woff2$/,
         loader: 'file-loader'
       },
      {
        test: /\.js$/i,
        use: {
          loader: 'babel-loader',
          options: {
            'presets': [
              [
                '@babel/preset-env',
                {
                  'targets': '> 0.25%, not dead',
                  'useBuiltIns': 'entry',
                  'corejs': { 'version': 3, 'proposals': true },
                },
              ],
            ],
          },
        },
       },
     ]
   },

});