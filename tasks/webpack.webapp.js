var webpack = require('webpack');

module.exports = {
    'entry': './target/javascript/index.js',
    'output': {
        'path': 'target/classes/resources/javascript',
        'filename': '[name].js',
        'sourceMapFilename': '[name].js.map'
    },
    'plugins': [
        new webpack.ProvidePlugin({
            '$': 'jquery',
            'jQuery': 'jquery',
            'Tether': 'tether'
        })
    ]
}
