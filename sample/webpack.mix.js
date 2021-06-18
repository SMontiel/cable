const mix = require('laravel-mix');
// DON NOT send js files directly to '/src/main/resources/static/js' because the browser
// cannot find dinamically imported modules. First, send to './js/app_original.js' then copy to '/src/main/resources/static/js'
mix.ts('assets/js/app.ts', './src/main/resources/static/js/app.js')
    .webpackConfig({
        plugins: [
            //new (require('./vendor/archtechx/airwire/resources/js/AirwireWatcher'))(require('chokidar'), 'app/**/*.php'),
        ],
        module: {
            rules: [
                // We're registering the TypeScript loader here. It should only
                // apply when we're dealing with a `.ts` or `.tsx` file.
                {
                    test: /\.tsx?$/,
                    loader: 'ts-loader',
                    options: { appendTsSuffixTo: [/\.vue$/] },
                    exclude: /node_modules/,
                },
            ],
        },

        resolve: {
            // We need to register the `.ts` extension so Webpack can resolve
            // TypeScript modules without explicitly providing an extension.
            // The other extensions in this list are identical to the Mix
            // defaults.
            extensions: ['*', '.js', '.jsx', '.vue', '.ts', '.tsx'],
        },
    })
    .vue(/*{ version: 3 }*/)
    .postCss('assets/css/base.css', './src/main/resources/static/css/app.css', [
      require('tailwindcss'),
      require('autoprefixer')
    ])
    .options({
        autoprefixer: false
    })
    //.extract();

