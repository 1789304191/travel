/**
 * Created by jackShall on 2016/5/11.
 */
/**=========================================================
 * Module: access-login.js
 * Demo for login api
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.pages')
        .controller('LoginFormController', LoginFormController);

    LoginFormController.$inject = ['$http', '$state','$scope','$window'];
    function LoginFormController($http, $state,$scope,$window) {
        var vm = this;
        $scope.selected = '';
        //$scope.items=[];
        $http
        .post('../index/login')
        .then(function(response) {
//        	console.log(response);
            	//$scope.items = response.data;
        		if(response.data.isLogin){
        			$state.go('app.dashboard');
        		}else{
        			$scope.typeOptions = response.data.array;
        			$scope.login.account={customer : $scope.typeOptions[0].id};
        		}
        }, function() {
            vm.authMsg = 'Server Request Error';
        });
        activate();

        ////////////////

        function activate() {
            // bind here all data from the form
            vm.account = {};
            // place the message if something goes wrong
            vm.authMsg = '';

            vm.login = function() {
                vm.authMsg = '';

                if(vm.loginForm.$valid) {
                	//$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
                    $http
                        .post('../index/doLogin', {account: vm.account.email, password: vm.account.password,customerId:vm.account.customer})
                        .then(function(response) {
                            // assumes if ok, response is an object with some data, if not, a string with error
                            // customize according to your api
//                            if ( !response.account ) {
//                                vm.authMsg = 'Incorrect credentials.';
//                            }else{
//                                //$state.go('app.dashboard');
//                                $window.history.back();
//                            }
                        	if(response.data.code==-1){
                        		vm.authMsg=response.data.msg;
                        	}else{
                        		$state.go('app.dashboard');
                        	}
                        }, function() {
                            vm.authMsg = 'Server Request Error';
                        });
                }
                else {
                    // set as dirty if the user click directly to login so we show the validation messages
                    /*jshint -W106*/
                    vm.loginForm.account_email.$dirty = true;
                    vm.loginForm.account_password.$dirty = true;
                }
            };
        }
    }
})();