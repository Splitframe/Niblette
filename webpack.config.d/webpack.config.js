;(function(config) {
    config.mode = 'development'
    if(!config.hasOwnProperty('devServer')) {
        config.devServer = { }
    }
    config.devServer.port = 8088
    config.devServer.devMiddleware = {
        publicPath: '/',
        mimeTypes: { "text/html": ["phtml"] },
    }

    // config.devServer.historyApiFallback = true

})(config);

