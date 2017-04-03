const path = require( "path" )
const ExtractTextPlugin = require( "extract-text-webpack-plugin" )

module.exports = {
    entry: path.resolve( __dirname, "index.js" ),

    output: {
      path: path.resolve( __dirname, "dist" ),
      filename: "[name].js"
    },

    devServer: {
      publicPath: "/"
    },

    devtool: "source-map",

    module: {
      rules: [
        {
          test: /\.js$/,
          exclude: /node_modules/,
          use: [
            { loader: "babel-loader" }
          ]
        },
        {
          test: /\.css$/,
          use: ExtractTextPlugin.extract( {
            fallback: [
              { loader: "style-loader", options: { sourceMap: true } }
            ],
            use: [
              { loader: "css-loader", options: { sourceMap: true } }
            ]
          } )
        },
        {
          test: /\.scss$/,
          use: ExtractTextPlugin.extract( {
            fallback: [
              { loader: "style-loader" }
            ],
            use: [
              { loader: "css-loader", options: { sourceMap: true, modules: true } },
              { loader: "sass-loader", options: { sourceMap: true } }
            ]
          } )
        },
        {
          test: /\.md$/,
          use: [
            { loader: "html-loader" },
            { loader: "markdown-loader" }
          ]
        }
      ]
    },

    resolve: {
      extensions: [ ".webpack.js", ".web.js", ".ts", ".tsx", ".js" ]
    },

    externals: {
      tarea: "tarea"
    },

    plugins: [
      new ExtractTextPlugin("styles.css")
    ]
};