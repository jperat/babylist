const MiniCssExtractPlugin = require('mini-css-extract-plugin');
var webpack = require("webpack");

module.exports = {
    entry: {
        app: ['./assets/js/app.js','./assets/scss/app.scss'],
        security: ['./assets/js/security.js', './assets/scss/security.scss'],
    },
    output: {
        filename: '[name].js',
        path: __dirname + '/static/dist'
    },
    module: {
        rules: [
            {
                test: /\.scss$/,
                use: [
                    // fallback to style-loader in development
                    MiniCssExtractPlugin.loader,
                    'css-loader',
                    'sass-loader',
                ],
            }
        ]
    },
    plugins: [
        new MiniCssExtractPlugin({
            filename: '[name].css',
        }),
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery"
        })
    ],
};