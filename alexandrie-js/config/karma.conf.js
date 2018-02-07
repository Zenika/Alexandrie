const webpackConfig = require('./config/webpack.config.js');
webpackConfig.entry = {alexandrie_js_test: "./classes/kotlin/test/alexandrie-js_test.js"};
// add our code to the resolvable modules
webpackConfig.resolve.modules.push('./classes/kotlin/main');

module.exports = function (config) {
    config.set({
        "basePath": "./",
        "frameworks": [
            "qunit",
            "sinon"
        ],
        "reporters": [
            "progress",
            "junit"
        ],
        "files": [
            "./classes/kotlin/test/alexandrie-js_test.js",
        ],
        "exclude": [
            "*~",
            "*.swp",
            "*.swo"
        ],
        "port": 9876,
        "runnerPort": 9100,
        "colors": false,
        "autoWatch": true,
        "browsers": ['FirefoxHeadless'],
        "customLaunchers": {
            "FirefoxHeadless": {
                "base": 'Firefox',
                "flags": ['-headless'],
            },
        },
        "captureTimeout": 60000,
        "singleRun": false,
        "preprocessors": {
            "./classes/kotlin/test/alexandrie-js_test.js": ['webpack', 'sourcemap']
        },
        "webpack": webpackConfig,
        "webpackMiddleware": {
            "noInfo": true
        },
        "client": {
            "clearContext": false,
            "qunit": {
                "showUI": true,
                "testTimeout": 5000
            }
        },
        "junitReporter": {
            "outputFile": "reports/karma.xml",
            "suite": "karma"
        }
    })
};
