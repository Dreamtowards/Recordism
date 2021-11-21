
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
    entry: "./src/main.js",
    output: {
        path: __dirname + "/public",
        filename: "bundle.js"
    },
    devtool: "eval-source-map",
    devServer: {
        contentBase: "./public",
        port: 8081,
        historyApiFallback: true,
        inline: true
    },
    module: {
        rules: [{
            test: /\.css$/,
            use: ["style-loader", "css-loader"]
        }, {
            test: /\.vue$/,
            loader: "vue-loader"
        }, {
                test: /\.(png|jpe?g|gif|svg|ttf|eot|woff|woff2)$/,
                loader: 'url-loader'
        }]
    },
    plugins: [
        new VueLoaderPlugin()
    ]
}