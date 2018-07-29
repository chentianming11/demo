const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const CleanWebpackPlugin = require("clean-webpack-plugin");
//__dirname是node.js中的一个全局变量，它指向当前执行脚本所在的目录
module.exports = {//注意这里是exports不是export
    devtool: 'eval-source-map', // dev的时候开启，正式环境不需要

    // 打包配置
    entry: __dirname + "/app/index.js",//唯一入口文件，就像Java中的main方法
    output: {//输出目录
        path: __dirname + "/build",//打包后的js文件存放的地方
        filename: "bundle-[hash].js"//打包后的js文件名
    },


    // node Server配置
    devServer: {
        contentBase: "./app",//本地服务器所加载的页面所在的目录
        historyApiFallback: true,//不跳转
        inline: true,//实时刷新
        port: 9000, // 端口
        proxy: [{  // 代理
            context: ["/v1/**"],
            target: 'http://localhost:8888/',
        }],
    },

    // 模块配置
    module: {
        rules: [
            {
                test: /(\.jsx|\.js)$/,
                use: {
                    loader: "babel-loader",
                },
                exclude: /node_modules/
            },
            {
                test: /\.css$/,
                use: [
                    {
                        loader: "style-loader"
                    }, {
                        loader: "css-loader",
                        // options: {
                        //     modules: true, // 指定启用css modules
                        //     localIdentName: '[name]__[local]--[hash:base64:5]' // 指定css的类名格式
                        // }
                    }
                ]
            }

        ]
    },

    // 插件配置
    plugins: [
        new webpack.BannerPlugin('版权所有，翻版必究'),
        new HtmlWebpackPlugin({
            template: __dirname + "/app/index.temp.html"//new 一个这个插件的实例，并传入相关的参数
        }),
        new webpack.HotModuleReplacementPlugin(),//热加载插件
        new webpack.optimize.OccurrenceOrderPlugin(),
        // new ExtractTextPlugin("style.css"),
        // new CleanWebpackPlugin('build/*.*', {
        //     root: __dirname,
        //     verbose: true,
        //     dry: false
        // })
    ],

};