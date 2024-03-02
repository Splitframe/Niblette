const webpack = require("webpack");

;(function(config) {
    config.mode = 'development'
    if(!config.hasOwnProperty('devServer')) {
        config.devServer = { }
    }
    config.devServer.host = 'developer.local'
    config.devServer.port = 8080
    config.devServer.historyApiFallback = true
    config.devServer.devMiddleware = {

        publicPath: '/',
        mimeTypes: { "text/html": ["phtml"] },
    }
    // config.devServer.historyApiFallback = true
})(config);

module.exports = (env) => {
    const envKeys = Object.keys(env).reduce((prev, next) => {
        prev[`env.compiled.${next}`] = JSON.stringify(env[next]);
        return prev;
    }, {});
    const definePlugin = new webpack.DefinePlugin(envKeys)

    config.plugins.push(definePlugin)
    return config
}