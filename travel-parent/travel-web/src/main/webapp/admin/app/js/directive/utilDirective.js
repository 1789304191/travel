/**
 * Created by jackShall on 2016/5/11.
 */
/**=========================================================
 * Module: trigger-resize.js
 * Triggers a window resize event from any element
 =========================================================*/
(function () {
    'use strict';

    angular
        .module('app.utils')
        .directive('triggerResize', triggerResize);

    triggerResize.$inject = ['$window', '$timeout'];
    function triggerResize($window, $timeout) {
        var directive = {
            link: link,
            restrict: 'A'
        };
        return directive;

        function link(scope, element) {
            element.on('click', function () {
                $timeout(function () {
                    $window.dispatchEvent(new Event('resize'));
                });
            });
        }
    }

})();
/**=========================================================
 * Module: table-checkall.js
 * Tables check all checkbox
 =========================================================*/
(function () {
    'use strict';

    angular
        .module('app.utils')
        .directive('checkAll', checkAll);

    function checkAll() {
        var directive = {
            link: link,
            restrict: 'A'
        };
        return directive;

        function link(scope, element) {
            element.on('change', function () {
                var $this = $(this),
                    index = $this.index() + 1,
                    checkbox = $this.find('input[type="checkbox"]'),
                    table = $this.parents('table');
                // Make sure to affect only the correct checkbox column
                table.find('tbody > tr > td:nth-child(' + index + ') input[type="checkbox"]')
                    .prop('checked', checkbox[0].checked);

            });
        }
    }

})();
/**=========================================================
 * Module: now.js
 * Provides a simple way to display the current time formatted
 =========================================================*/

(function () {
    'use strict';

    angular
        .module('app.utils')
        .directive('now', now);

    now.$inject = ['dateFilter', '$interval'];
    function now(dateFilter, $interval) {
        var directive = {
            link: link,
            restrict: 'EA'
        };
        return directive;

        function link(scope, element, attrs) {
            var format = attrs.format;

            function updateTime() {
                var dt = dateFilter(new Date(), format);
                element.text(dt);
            }

            updateTime();
            var intervalPromise = $interval(updateTime, 1000);

            scope.$on('$destroy', function () {
                $interval.cancel(intervalPromise);
            });

        }
    }

})();
/**=========================================================
 * Module: load-css.js
 * Request and load into the current page a css file
 =========================================================*/

(function () {
    'use strict';

    angular
        .module('app.utils')
        .directive('loadCss', loadCss);

    function loadCss() {
        var directive = {
            link: link,
            restrict: 'A'
        };
        return directive;

        function link(scope, element, attrs) {
            element.on('click', function (e) {
                if (element.is('a')) e.preventDefault();
                var uri = attrs.loadCss,
                    link;

                if (uri) {
                    link = createLink(uri);
                    if (!link) {
                        $.error('Error creating stylesheet link element.');
                    }
                }
                else {
                    $.error('No stylesheet location defined.');
                }

            });
        }

        function createLink(uri) {
            var linkId = 'autoloaded-stylesheet',
                oldLink = $('#' + linkId).attr('id', linkId + '-old');

            $('head').append($('<link/>').attr({
                'id': linkId,
                'rel': 'stylesheet',
                'href': uri
            }));

            if (oldLink.length) {
                oldLink.remove();
            }

            return $('#' + linkId);
        }
    }

})();
/**=========================================================
 * Module: fullscreen.js
 * Toggle the fullscreen mode on/off
 =========================================================*/

(function () {
    'use strict';

    angular
        .module('app.utils')
        .directive('toggleFullscreen', toggleFullscreen);

    toggleFullscreen.$inject = ['Browser'];
    function toggleFullscreen(Browser) {
        var directive = {
            link: link,
            restrict: 'A'
        };
        return directive;

        function link(scope, element) {
            // Not supported under IE
            if (Browser.msie) {
                element.addClass('hide');
            }
            else {
                element.on('click', function (e) {
                    e.preventDefault();

                    if (screenfull.enabled) {

                        screenfull.toggle();

                        // Switch icon indicator
                        if (screenfull.isFullscreen)
                            $(this).children('em').removeClass('fa-expand').addClass('fa-compress');
                        else
                            $(this).children('em').removeClass('fa-compress').addClass('fa-expand');

                    } else {
                        $.error('Fullscreen not enabled');
                    }

                });
            }
        }
    }


})();

/**=========================================================
 * Module: clear-storage.js
 * Removes a key from the browser storage via element click
 =========================================================*/

(function () {
    'use strict';

    angular
        .module('app.utils')
        .directive('resetKey', resetKey);

    resetKey.$inject = ['$state', '$localStorage'];
    function resetKey($state, $localStorage) {
        var directive = {
            link: link,
            restrict: 'A',
            scope: {
                resetKey: '@'
            }
        };
        return directive;

        function link(scope, element) {
            element.on('click', function (e) {
                e.preventDefault();

                if (scope.resetKey) {
                    delete $localStorage[scope.resetKey];
                    $state.go($state.current, {}, {reload: true});
                }
                else {
                    $.error('No storage key specified for reset.');
                }
            });
        }
    }

})();
/**=========================================================
 * Module: animate-enabled.js
 * Enable or disables ngAnimate for element with directive
 =========================================================*/

(function () {
    'use strict';

    angular
        .module('app.utils')
        .directive('animateEnabled', animateEnabled);

    animateEnabled.$inject = ['$animate'];
    function animateEnabled($animate) {
        var directive = {
            link: link,
            restrict: 'A'
        };
        return directive;

        function link(scope, element, attrs) {
            scope.$watch(function () {
                return scope.$eval(attrs.animateEnabled, scope);
            }, function (newValue) {
                $animate.enabled(!!newValue, element);
            });
        }
    }

})();
/**=========================================================
 * Module: notify.js
 * Directive for notify plugin
 =========================================================*/

(function () {
    'use strict';

    angular
        .module('app.notify')
        .directive('notify', notify);

    notify.$inject = ['$window', 'Notify'];
    function notify($window, Notify) {

        var directive = {
            link: link,
            restrict: 'A',
            scope: {
                options: '=',
                message: '='
            }
        };
        return directive;

        function link(scope, element) {

            element.on('click', function (e) {
                e.preventDefault();
                Notify.alert(scope.message, scope.options);
            });
        }

    }

})();
/**=========================================================
 * Module: scroll.js
 * Make a content box scrollable
 =========================================================*/

(function () {
    'use strict';

    angular
        .module('app.elements')
        .directive('scrollable', scrollable)
        .directive("timePicker", timePicker)
        .directive('superiorDire',superiorDire)
        .directive('limitNum',limitNum)
        .directive("picUpload", picUpload)
        .directive("wdatePicker", wdatePicker)
    	.directive("ckeditor", ckUpload);
    function scrollable() {
        var directive = {
            link: link,
            restrict: 'EA'
        };
        return directive;

        function link(scope, element, attrs) {
            var defaultHeight = 250;
            element.slimScroll({
                height: (attrs.height || defaultHeight)
            });
        }
    };
    //时间选择器
    function timePicker() {
        return {
            restrict: 'A',
            link: function (scope, element, tAttrs) {
                element.appendDtpicker({
                    "closeOnSelected": true,//控制是否选完后关闭
                    "autodateOnStart": false,//开始是否需要当前时间作为默认值
                    "futureOnly": true,//不允许选择小于当前时间,
                    "timelistScrolling":false
                });
                element.bind('change',function(){
                        if(tAttrs.maxDate){
                            if(element.val()) {
                                $("#" + tAttrs.maxDate).handleDtpicker('setMinDate', element.val());
                            }else{
                                $("#" + tAttrs.maxDate).handleDtpicker('setMinDate', null);
                            }
                        }
                        if(tAttrs.minDate){
                            if(element.val()) {
                                $("#"+tAttrs.minDate).handleDtpicker('setMaxDate', element.val());
                            }else{
                                $("#"+tAttrs.minDate).handleDtpicker('setMaxDate', '9999-12-12 00:00:00');
                            }
                        }
                });
            }
        }
    };
    function wdatePicker() {
    	return {
            restrict: 'A',
            require: 'ngModel',
            link: function (scope, element, tAttrs, ngModel) {
                element.val(ngModel.$viewValue);
                function onpicking(dp) {
                    var date = dp.cal.getNewDateStr();
                    scope.$apply(function () {
                        ngModel.$setViewValue(date);
                    });
                }
                function oncleared(){
                	scope.$apply(function () {
                        ngModel.$setViewValue("");
                    });
                }
                element.bind('click', function () {
                	if(tAttrs.minTime){
                		WdatePicker({
                            onpicking: onpicking,
                            oncleared: oncleared,
                        	el:element[0].id,
                            dateFmt: tAttrs.dateFmt,
                            minDate:"#F{$dp.$D("+"'"+tAttrs.minTime+"'"+")}"
                        })
                	}
                	if(tAttrs.maxTime){
                		 WdatePicker({
                             onpicking: onpicking,
                             oncleared: oncleared,
                         	 el:element[0].id,
                             dateFmt: tAttrs.dateFmt,
                             maxDate:"#F{$dp.$D("+"'"+tAttrs.maxTime+"'"+")}"
                         })
                	}
                	if(!tAttrs.maxTime&&!tAttrs.minTime){
                		WdatePicker({
                            onpicking: onpicking,
                            oncleared: oncleared,
                        	el:element[0].id,
                            dateFmt: tAttrs.dateFmt
                        })
                	}
                   
                });
            }
        };
    };
    //高级搜索
    function superiorDire(){
        return{
            restrict:'A',
            scope:true,
            link:function(scope, element, tAttrs){
                element.bind('click',function(){
                    $("#"+tAttrs.superiorDire).slideToggle(500);
                })
            }
        }
    };
    //图片上传
    function picUpload(SweetAlert,$rootScope) {
    	 return{
             restrict:'A',
             scope:true,
             link:function(scope, element, tAttrs){
            	 var button = element;
         		$.ajax_upload(
         		   button,
         		   {
         			   data:{
         	          	 from:tAttrs.picUpload  	 
         	             },
         			   action: '../file/upload',
         			   name: 'file',
         			   onSubmit: function(file, extension) {
         			        if (!(extension && /^(jpg|jpeg|bmp|gif|png)$/.test(extension.toLowerCase()))) {
         			        	SweetAlert.swal("","请上传图片文件，文件格式为jpg、jpeg、bmp、gif、png", "error"); 
         	                        return false;
         	                    }
         			   },
         			   onComplete: function(file, data){
         				    var obj = eval("("+data+")"); 
         				    $rootScope.$broadcast("fileName",obj.fileName);
         					$(".dash").attr("src",obj.path);
         				}
         		   }
         		
         		);
             }
         }
    };
    //ckeditor
    function ckUpload(){
    	return {
        	require : '?ngModel',
        	link : function(scope, element, attrs, ngModel) {
        		var ckeditor = CKEDITOR.replace(element[0],
        			{ image_previewText : ' ', filebrowserUploadUrl: '../ckEditor/upload?from='+attrs.ckeditor}
        	);
        	if (!ngModel) {
        		return;
        	}
        	ckeditor.on('instanceReady', function() {
        		ckeditor.setData(ngModel.$viewValue);
        	});
        	ckeditor.on('pasteState', function() {
        		scope.$apply(function() {
        			ngModel.$setViewValue(ckeditor.getData());
        		});
        	});
        	ngModel.$render = function(value) {
        		ckeditor.setData(ngModel.$viewValue);
        			};
        		}
        	};
    };
    //格式化数字
    function limitNum(){
    	return {
    		restrict:'A',
    		require:"?ngModel",
    		scope:false,
    		link:function(scope, element, attrs,ngModel){
    			var defaul=0.00;
                if (!ngModel)
                {
                    return; // do nothing if no ng-model
                }
                ngModel.$render = function() {
                	if(typeof(ngModel.$viewValue)=='undefined'){
                		return;
                	}
                	if(ngModel.$viewValue<0){
                		ngModel.$setViewValue(0);
               	    }
                	var temp= parseFloat(ngModel.$viewValue).toFixed(attrs.limitNum);
                    element.val(temp || defaul.toFixed(attrs.limitNum));
                };
               element.bind('blur',function(){
            	   if(typeof(ngModel.$viewValue)=='undefined'| isNaN(ngModel.$viewValue)){
            		   ngModel.$setViewValue(0);
               	   }
            	   if(ngModel.$viewValue<0){
               			ngModel.$setViewValue(0);
              	   }
            	   var temp= parseFloat(ngModel.$viewValue).toFixed(attrs.limitNum);
                   element.val(temp);
                   scope.$apply(function(){ 
                		ngModel.$setViewValue(element.val());
                   });
               });
    	  }
    	}
    };
})();