/**
 * Created by jackShall on 2016/5/11.
 */

(function() {
    'use strict';

    angular
        .module('app.sidebar')
        .controller('UserBlockController', UserBlockController);

    UserBlockController.$inject = ['$rootScope'];
    function UserBlockController($rootScope) {

        activate();

        ////////////////

        function activate() {
            $rootScope.user = {
                name:     'WuYingjie',
                job:      'ng-developer',
                picture:  ''
            };
            $rootScope.userBlockVisible = true;
            // Hides/show user avatar on sidebar
            $rootScope.toggleUserBlock = function(){
                //$rootScope.$broadcast('toggleUserBlock');
                $rootScope.userBlockVisible = ! $rootScope.userBlockVisible;
            };

            //$rootScope.$on('toggleUserBlock', function(/*event, args*/) {
            //
            //    $rootScope.userBlockVisible = ! $rootScope.userBlockVisible;
            //
            //});
        }
    }
})();