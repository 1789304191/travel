/**
 * Created by jackShall on 2016/5/11.
 */
(function() {
    'use strict';

    angular
        .module('app.locale')
        .config(localeConfig)
    ;
    localeConfig.$inject = ['tmhDynamicLocaleProvider'];
    function localeConfig(tmhDynamicLocaleProvider){

        tmhDynamicLocaleProvider.localeLocationPattern('vendor/angular-i18n/angular-locale_{{locale}}.js');
        // tmhDynamicLocaleProvider.useStorage('$cookieStore');

    }
})();