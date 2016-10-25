/**
 * Created by jackShall on 2016/5/11.
 */
/**=========================================================
 * Module: tags-input.js
 * Initializes the tag inputs plugin
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.forms')
        .directive('tagsinput', tagsinput);

    tagsinput.$inject = ['$timeout'];
    function tagsinput ($timeout) {
        var directive = {
            link: link,
            require: 'ngModel',
            restrict: 'A'
        };
        return directive;

        function link(scope, element, attrs, ngModel) {
            element.on('itemAdded itemRemoved', function(){
                // check if view value is not empty and is a string
                // and update the view from string to an array of tags
                if(ngModel.$viewValue && ngModel.$viewValue.split) {
                    ngModel.$setViewValue( ngModel.$viewValue.split(',') );
                    ngModel.$render();
                }
            });

            $timeout(function(){
                element.tagsinput();
            });
        }
    }

})();
/**=========================================================
 * Module: validate-form.js
 * Initializes the validation plugin Parsley
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.forms')
        .directive('validateForm', validateForm);

    function validateForm () {
        var directive = {
            link: link,
            restrict: 'A'
        };
        return directive;

        function link(scope, element) {
            var $elem = $(element);
            if($.fn.parsley)
                $elem.parsley();
        }
    }

})();
