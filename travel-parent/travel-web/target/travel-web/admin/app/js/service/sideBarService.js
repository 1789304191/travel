/**
 * Created by jackShall on 2016/5/11.
 */
(function() {
    'use strict';

    angular
        .module('app.sidebar')
        .service('SidebarLoader', SidebarLoader);

    SidebarLoader.$inject = ['$http'];
    function SidebarLoader($http) {
        this.getMenu = getMenu;

        ////////////////

        function getMenu(onReady, onError) {
            var menuJson = '../admin/menu/menuList',
//        	var menuJson = 'server/sidebar-menu.json',
                menuURL  = menuJson + '?v=' + (new Date().getTime()); // jumps cache
            onError = onError || function() { alert('Failure loading menu'); };

            $http
                .get(menuURL)
                .success(onReady)
                .error(onError);
        }
    }
})();