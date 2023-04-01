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
    // print(config.devServer.static)
    // config.devServer.static = {
    //     directory: config.devServer.static[0],
    //     watch: {
    //         "aggregateTimeout": 5000,
    //         "poll": 1000
    //     }
    // }

    //     += {
    //     port: 8088,
    //     devMiddleware: {
    //         publicPath: '/'
    //     }
    // }
})(config);

