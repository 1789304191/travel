/**
 * Created by jackShall on 2016/5/11.
 */
/**=========================================================
 * Module: vmaps,js
 * jVector Maps support
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.maps')
        .controller('VectorMapController', VectorMapController);

    function VectorMapController() {
        var vm = this;

        activate();

        ////////////////

        function activate() {
            vm.seriesData = {
                'CA': 11100,   // Canada
                'DE': 2510,    // Germany
                'FR': 3710,    // France
                'AU': 5710,    // Australia
                'GB': 8310,    // Great Britain
                'RU': 9310,    // Russia
                'BR': 6610,    // Brazil
                'IN': 7810,    // India
                'CN': 4310,    // China
                'US': 839,     // USA
                'SA': 410      // Saudi Arabia
            };

            vm.markersData = [
                { latLng:[41.90, 12.45],  name:'Vatican City'          },
                { latLng:[43.73, 7.41],   name:'Monaco'                },
                { latLng:[-0.52, 166.93], name:'Nauru'                 },
                { latLng:[-8.51, 179.21], name:'Tuvalu'                },
                { latLng:[7.11,171.06],   name:'Marshall Islands'      },
                { latLng:[17.3,-62.73],   name:'Saint Kitts and Nevis' },
                { latLng:[3.2,73.22],     name:'Maldives'              },
                { latLng:[35.88,14.5],    name:'Malta'                 },
                { latLng:[41.0,-71.06],   name:'New England'           },
                { latLng:[12.05,-61.75],  name:'Grenada'               },
                { latLng:[13.16,-59.55],  name:'Barbados'              },
                { latLng:[17.11,-61.85],  name:'Antigua and Barbuda'   },
                { latLng:[-4.61,55.45],   name:'Seychelles'            },
                { latLng:[7.35,134.46],   name:'Palau'                 },
                { latLng:[42.5,1.51],     name:'Andorra'               }
            ];
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('app.maps')
        .controller('GMapController', GMapController);

    GMapController.$inject = ['$timeout'];
    function GMapController($timeout) {
        var vm = this;

        activate();

        ////////////////

        function activate() {
            var position = [
                new google.maps.LatLng(33.790807, -117.835734),
                new google.maps.LatLng(33.790807, -117.835734),
                new google.maps.LatLng(33.790807, -117.835734),
                new google.maps.LatLng(33.790807, -117.835734),
                new google.maps.LatLng(33.787453, -117.835858)
            ];

            vm.addMarker = addMarker;
            // we use timeout to wait maps to be ready before add a markers
            $timeout(function(){
                addMarker(vm.myMap1, position[0]);
                addMarker(vm.myMap2, position[1]);
                addMarker(vm.myMap3, position[2]);
                addMarker(vm.myMap5, position[3]);
            });

            vm.mapOptions1 = {
                zoom: 14,
                center: position[0],
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                scrollwheel: false
            };

            vm.mapOptions2 = {
                zoom: 19,
                center: position[1],
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            vm.mapOptions3 = {
                zoom: 14,
                center: position[2],
                mapTypeId: google.maps.MapTypeId.SATELLITE
            };

            vm.mapOptions4 = {
                zoom: 14,
                center: position[3],
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            // for multiple markers
            $timeout(function(){
                addMarker(vm.myMap4, position[3]);
                addMarker(vm.myMap4, position[4]);
            });

            // custom map style
            var MapStyles = [{'featureType':'water','stylers':[{'visibility':'on'},{'color':'#bdd1f9'}]},{'featureType':'all','elementType':'labels.text.fill','stylers':[{'color':'#334165'}]},{featureType:'landscape',stylers:[{color:'#e9ebf1'}]},{featureType:'road.highway',elementType:'geometry',stylers:[{color:'#c5c6c6'}]},{featureType:'road.arterial',elementType:'geometry',stylers:[{color:'#fff'}]},{featureType:'road.local',elementType:'geometry',stylers:[{color:'#fff'}]},{featureType:'transit',elementType:'geometry',stylers:[{color:'#d8dbe0'}]},{featureType:'poi',elementType:'geometry',stylers:[{color:'#cfd5e0'}]},{featureType:'administrative',stylers:[{visibility:'on'},{lightness:33}]},{featureType:'poi.park',elementType:'labels',stylers:[{visibility:'on'},{lightness:20}]},{featureType:'road',stylers:[{color:'#d8dbe0',lightness:20}]}];
            vm.mapOptions5 = {
                zoom: 14,
                center: position[3],
                styles: MapStyles,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                scrollwheel: false
            };

            ///////////////

            function addMarker(map, position) {
                return new google.maps.Marker({
                    map: map,
                    position: position
                });
            }

        }
    }
})();
/**=========================================================
 * Module: modals.js
 * Provides a simple way to implement bootstrap modals from templates
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.maps')
        .controller('ModalGmapController', ModalGmapController);

    ModalGmapController.$inject = ['$modal'];
    function ModalGmapController($modal) {
        var vm = this;

        activate();

        ////////////////

        function activate() {

            vm.open = function (size) {

                //var modalInstance =
                $modal.open({
                    templateUrl: '/myModalContent.html',
                    controller: ModalInstanceCtrl,
                    size: size
                });
            };

            // Please note that $modalInstance represents a modal window (instance) dependency.
            // It is not the same as the $modal service used above.

            ModalInstanceCtrl.$inject = ['$scope', '$modalInstance', '$timeout'];
            function ModalInstanceCtrl($scope, $modalInstance, $timeout) {

                $modalInstance.opened.then(function () {
                    var position = new google.maps.LatLng(33.790807, -117.835734);

                    $scope.mapOptionsModal = {
                        zoom: 14,
                        center: position,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };

                    // we use timeout to wait maps to be ready before add a markers
                    $timeout(function(){
                        // 1. Add a marker at the position it was initialized
                        new google.maps.Marker({
                            map: $scope.myMapModal,
                            position: position
                        });
                        // 2. Trigger a resize so the map is redrawed
                        google.maps.event.trigger($scope.myMapModal, 'resize');
                        // 3. Move to the center if it is misaligned
                        $scope.myMapModal.panTo(position);
                    });

                });

                $scope.ok = function () {
                    $modalInstance.close('closed');
                };

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            }

        }
    }

})();
