/**
 * Created by jackShall on 2016/5/11.
 */
/**=========================================================
 * Module: constants.js
 * Define constants to inject across the application
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.core')
        .constant('APP_MEDIAQUERY', {
            'desktopLG':             1200,
            'desktop':                992,
            'tablet':                 768,
            'mobile':                 480
        })
        //拦截页面
        .constant('CONTROL_URL', ['app.dashboard'])
    ;

})();
(function() {
    'use strict';

    angular
        .module('app.colors')
        .constant('APP_COLORS', {
            'primary':                '#5d9cec',
            'success':                '#27c24c',
            'info':                   '#23b7e5',
            'warning':                '#ff902b',
            'danger':                 '#f05050',
            'inverse':                '#131e26',
            'green':                  '#37bc9b',
            'pink':                   '#f532e5',
            'purple':                 '#7266ba',
            'dark':                   '#3a3f51',
            'yellow':                 '#fad732',
            'gray-darker':            '#232735',
            'gray-dark':              '#3a3f51',
            'gray':                   '#dde6e9',
            'gray-light':             '#e4eaec',
            'gray-lighter':           '#edf1f2'
        })
    ;
})();
