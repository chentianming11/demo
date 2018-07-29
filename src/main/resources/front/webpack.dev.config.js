const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const CleanWebpackPlugin = require("clean-webpack-plugin");

module.exports = {//注意这里是exports不是export

    /******** 模式mode配置 *****/
    mode: 'development',

    /********* 入口配置 ********/
    entry: __dirname + "/app/index.js",


    /*********** 出口配置 ************/
    output: {
        path: __dirname + "/build",
        filename: "bundle-[hash].js"
    },


    /********* node Server配置 *********/
    devServer: {
        contentBase: "./app",
        historyApiFallback: true,//不跳转
        inline: true,//实时刷新
        port: 9000,
        proxy: [{
            context: ["/v1/**"],
            target: 'http://localhost:8888/',
        }],
    },

    /********** 模块module配置 *****************/
    module: {
        rules: [

            /*************** babel-loader，其他babel-loader配置在.babelrc文件中 ****************/
            {
                test: /(\.jsx|\.js)$/,
                use: {
                    loader: "babel-loader",
                },
                exclude: /node_modules/
            },

            /***************  style-loader && css-loader配置 ***************************/
            {
                test: /\.css|\.scss$/,
                use: [
                    {
                        loader: "style-loader"
                    }, {
                        loader: "css-loader",
                        // options: {
                        //     modules: true, // 指定启用css modules
                        //     localIdentName: '[name]__[local]--[hash:base64:5]' // 指定css的类名格式
                        // }
                    }, {
                        loader: "postcss-loader"
                    }
                ],
            }, {
                test: /\.module\.less$/,
                loader: ExtractTextPlugin.extract(
                    'css?sourceMap&modules&localIdentName=[local]___[hash:base64:5]!!' +
                    'postcss!' +
                    `less-loader?{"sourceMap":true,"modifyVars":${JSON.stringify(theme)}}`
                ),
            },

        ]
    },

    /************** 插件plugins配置 **********************/
    plugins: [
        new webpack.BannerPlugin('版权所有，翻版必究'),

        /********************* 热加载插件 ********************/
        new webpack.HotModuleReplacementPlugin(),

        /***** OccurenceOrderPlugin :为组件分配ID ***/
        new webpack.optimize.OccurrenceOrderPlugin(),

        /******** ExtractTextPlugin：分离CSS和JS文件 ******/
        new ExtractTextPlugin("style.css"),

        /****** 去除build文件中的残余文件 ****/
        new CleanWebpackPlugin('build/*.*', {
            root: __dirname,
            verbose: true,
            dry: false
        }),

        /*** HtmlWebpackPlugin:依据一个简单的index.html模板，生成一个自动引用你打包后的JS文件的新index.html ****/
        new HtmlWebpackPlugin({
            template: __dirname + "/app/index.temp.html"//new 一个这个插件的实例，并传入相关的参数
        }),

    ],

};