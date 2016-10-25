/**
 * Created by jackShall on 2016/5/11.
 */
/**=========================================================
 * Module: rickshaw.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.charts')
        .controller('ChartRickshawController', ChartRickshawController);

    function ChartRickshawController() {
        var vm = this;

        activate();

        ////////////////

        function activate() {

            vm.renderers = [{
                id: 'area',
                name: 'Area'
            }, {
                id: 'line',
                name: 'Line'
            }, {
                id: 'bar',
                name: 'Bar'
            }, {
                id: 'scatterplot',
                name: 'Scatterplot'
            }];

            vm.palettes = [
                'spectrum14',
                'spectrum2000',
                'spectrum2001',
                'colorwheel',
                'cool',
                'classic9',
                'munin'
            ];

            vm.rendererChanged = function(id) {
                vm['options' + id] = {
                    renderer: vm['renderer' + id].id
                };
            };

            vm.paletteChanged = function(id) {
                vm['features' + id] = {
                    palette: vm['palette' + id]
                };
            };

            vm.changeSeriesData = function(id) {
                var seriesList = [];
                for (var i = 0; i < 3; i++) {
                    var series = {
                        name: 'Series ' + (i + 1),
                        data: []
                    };
                    for (var j = 0; j < 10; j++) {
                        series.data.push({x: j, y: Math.random() * 20});
                    }
                    seriesList.push(series);
                    vm['series' + id][i] = series;
                }
                //vm['series' + id] = seriesList;
            };

            vm.series0 = [];

            vm.options0 = {
                renderer: 'area'
            };

            vm.renderer0 = vm.renderers[0];
            vm.palette0 = vm.palettes[0];

            vm.rendererChanged(0);
            vm.paletteChanged(0);
            vm.changeSeriesData(0);

            // Graph 2

            var seriesData = [ [], [], [] ];
            var random = new Rickshaw.Fixtures.RandomData(150);

            for (var i = 0; i < 150; i++) {
                random.addData(seriesData);
            }

            vm.series2 = [
                {
                    color: '#c05020',
                    data: seriesData[0],
                    name: 'New York'
                }, {
                    color: '#30c020',
                    data: seriesData[1],
                    name: 'London'
                }, {
                    color: '#6060c0',
                    data: seriesData[2],
                    name: 'Tokyo'
                }
            ];

            vm.options2 = {
                renderer: 'area'
            };

        }
    }
})();
/**=========================================================
 * Module: morris.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.charts')
        .controller('ChartMorrisController', ChartMorrisController);

    ChartMorrisController.$inject = ['$timeout', 'Colors'];
    function ChartMorrisController($timeout, Colors) {
        var vm = this;

        activate();

        ////////////////

        function activate() {
            vm.chartdata = [
                { y: '2006', a: 100, b: 90 },
                { y: '2007', a: 75,  b: 65 },
                { y: '2008', a: 50,  b: 40 },
                { y: '2009', a: 75,  b: 65 },
                { y: '2010', a: 50,  b: 40 },
                { y: '2011', a: 75,  b: 65 },
                { y: '2012', a: 100, b: 90 }
            ];

            /* test data update
             $timeout(function(){
             vm.chartdata[0].a = 50;
             vm.chartdata[0].b = 50;
             }, 3000); */

            vm.donutdata = [
                {label: 'Download Sales', value: 12},
                {label: 'In-Store Sales',value: 30},
                {label: 'Mail-Order Sales', value: 20}
            ];

            vm.donutOptions = {
                Colors: [ Colors.byName('danger'), Colors.byName('yellow'), Colors.byName('warning') ],
                resize: true
            };

            vm.barOptions = {
                xkey: 'y',
                ykeys: ['a', 'b'],
                labels: ['Series A', 'Series B'],
                xLabelMargin: 2,
                barColors: [ Colors.byName('info'), Colors.byName('danger') ],
                resize: true
            };

            vm.lineOptions = {
                xkey: 'y',
                ykeys: ['a', 'b'],
                labels: ['Serie A', 'Serie B'],
                lineColors: ['#31C0BE', '#7a92a3'],
                resize: true
            };

            vm.areaOptions = {
                xkey: 'y',
                ykeys: ['a', 'b'],
                labels: ['Serie A', 'Serie B'],
                lineColors: [ Colors.byName('purple'), Colors.byName('info') ],
                resize: true
            };

        }
    }
})();
/**=========================================================
 * Module: flot-chart.js
 * Setup options and data for flot chart directive
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.charts')
        .controller('FlotChartController', FlotChartController);

    FlotChartController.$inject = ['$scope', 'ChartData', '$timeout'];
    function FlotChartController($scope, ChartData, $timeout) {
        var vm = this;

        activate();

        ////////////////

        function activate() {

            // BAR
            // -----------------------------------
            vm.barData = ChartData.load('server/chart/bar.json');
            vm.barOptions = {
                series: {
                    bars: {
                        align: 'center',
                        lineWidth: 0,
                        show: true,
                        barWidth: 0.6,
                        fill: 0.9
                    }
                },
                grid: {
                    borderColor: '#eee',
                    borderWidth: 1,
                    hoverable: true,
                    backgroundColor: '#fcfcfc'
                },
                tooltip: true,
                tooltipOpts: {
                    content: function (label, x, y) { return x + ' : ' + y; }
                },
                xaxis: {
                    tickColor: '#fcfcfc',
                    mode: 'categories'
                },
                yaxis: {
                    position: ($scope.app.layout.isRTL ? 'right' : 'left'),
                    tickColor: '#eee'
                },
                shadowSize: 0
            };

            // BAR STACKED
            // -----------------------------------
            vm.barStackeData = ChartData.load('server/chart/barstacked.json');
            vm.barStackedOptions = {
                series: {
                    stack: true,
                    bars: {
                        align: 'center',
                        lineWidth: 0,
                        show: true,
                        barWidth: 0.6,
                        fill: 0.9
                    }
                },
                grid: {
                    borderColor: '#eee',
                    borderWidth: 1,
                    hoverable: true,
                    backgroundColor: '#fcfcfc'
                },
                tooltip: true,
                tooltipOpts: {
                    content: function (label, x, y) { return x + ' : ' + y; }
                },
                xaxis: {
                    tickColor: '#fcfcfc',
                    mode: 'categories'
                },
                yaxis: {
                    min: 0,
                    max: 200, // optional: use it for a clear represetation
                    position: ($scope.app.layout.isRTL ? 'right' : 'left'),
                    tickColor: '#eee'
                },
                shadowSize: 0
            };

            // SPLINE
            // -----------------------------------
            vm.splineData = ChartData.load('server/chart/spline.json');
            vm.splineOptions = {
                series: {
                    lines: {
                        show: false
                    },
                    points: {
                        show: true,
                        radius: 4
                    },
                    splines: {
                        show: true,
                        tension: 0.4,
                        lineWidth: 1,
                        fill: 0.5
                    }
                },
                grid: {
                    borderColor: '#eee',
                    borderWidth: 1,
                    hoverable: true,
                    backgroundColor: '#fcfcfc'
                },
                tooltip: true,
                tooltipOpts: {
                    content: function (label, x, y) { return x + ' : ' + y; }
                },
                xaxis: {
                    tickColor: '#fcfcfc',
                    mode: 'categories'
                },
                yaxis: {
                    min: 0,
                    max: 150, // optional: use it for a clear represetation
                    tickColor: '#eee',
                    position: ($scope.app.layout.isRTL ? 'right' : 'left'),
                    tickFormatter: function (v) {
                        return v/* + ' visitors'*/;
                    }
                },
                shadowSize: 0
            };

            // AREA
            // -----------------------------------
            vm.areaData = ChartData.load('server/chart/area.json');
            vm.areaOptions = {
                series: {
                    lines: {
                        show: true,
                        fill: 0.8
                    },
                    points: {
                        show: true,
                        radius: 4
                    }
                },
                grid: {
                    borderColor: '#eee',
                    borderWidth: 1,
                    hoverable: true,
                    backgroundColor: '#fcfcfc'
                },
                tooltip: true,
                tooltipOpts: {
                    content: function (label, x, y) { return x + ' : ' + y; }
                },
                xaxis: {
                    tickColor: '#fcfcfc',
                    mode: 'categories'
                },
                yaxis: {
                    min: 0,
                    tickColor: '#eee',
                    position: ($scope.app.layout.isRTL ? 'right' : 'left'),
                    tickFormatter: function (v) {
                        return v + ' visitors';
                    }
                },
                shadowSize: 0
            };

            // LINE
            // -----------------------------------
            vm.lineData = ChartData.load('server/chart/line.json');
            vm.lineOptions = {
                series: {
                    lines: {
                        show: true,
                        fill: 0.01
                    },
                    points: {
                        show: true,
                        radius: 4
                    }
                },
                grid: {
                    borderColor: '#eee',
                    borderWidth: 1,
                    hoverable: true,
                    backgroundColor: '#fcfcfc'
                },
                tooltip: true,
                tooltipOpts: {
                    content: function (label, x, y) { return x + ' : ' + y; }
                },
                xaxis: {
                    tickColor: '#eee',
                    mode: 'categories'
                },
                yaxis: {
                    position: ($scope.app.layout.isRTL ? 'right' : 'left'),
                    tickColor: '#eee'
                },
                shadowSize: 0
            };

            // PIE
            // -----------------------------------
            vm.pieData = ChartData.load('server/chart/pie.json');
            vm.pieOptions = {
                series: {
                    pie: {
                        show: true,
                        innerRadius: 0,
                        label: {
                            show: true,
                            radius: 0.8,
                            formatter: function (label, series) {
                                return '<div class="flot-pie-label">' +
                                        //label + ' : ' +
                                    Math.round(series.percent) +
                                    '%</div>';
                            },
                            background: {
                                opacity: 0.8,
                                color: '#222'
                            }
                        }
                    }
                }
            };

            // DONUT
            // -----------------------------------
            vm.donutData = ChartData.load('server/chart/donut.json');
            vm.donutOptions = {
                series: {
                    pie: {
                        show: true,
                        innerRadius: 0.5 // This makes the donut shape
                    }
                }
            };

            // REALTIME
            // -----------------------------------
            vm.realTimeOptions = {
                series: {
                    lines: { show: true, fill: true, fillColor:  { colors: ['#a0e0f3', '#23b7e5'] } },
                    shadowSize: 0 // Drawing is faster without shadows
                },
                grid: {
                    show:false,
                    borderWidth: 0,
                    minBorderMargin: 20,
                    labelMargin: 10
                },
                xaxis: {
                    tickFormatter: function() {
                        return '';
                    }
                },
                yaxis: {
                    min: 0,
                    max: 110
                },
                legend: {
                    show: true
                },
                colors: ['#23b7e5']
            };

            // Generate random data for realtime demo
            var data = [], totalPoints = 300;

            update();

            function getRandomData() {
                if (data.length > 0)
                    data = data.slice(1);
                // Do a random walk
                while (data.length < totalPoints) {
                    var prev = data.length > 0 ? data[data.length - 1] : 50,
                        y = prev + Math.random() * 10 - 5;
                    if (y < 0) {
                        y = 0;
                    } else if (y > 100) {
                        y = 100;
                    }
                    data.push(y);
                }
                // Zip the generated y values with the x values
                var res = [];
                for (var i = 0; i < data.length; ++i) {
                    res.push([i, data[i]]);
                }
                return [res];
            }
            function update() {
                vm.realTimeData = getRandomData();
                $timeout(update, 30);
            }
            // end random data generation


            // PANEL REFRESH EVENTS
            // -----------------------------------

            $scope.$on('panel-refresh', function(event, id) {

                console.log('Simulating chart refresh during 3s on #'+id);

                // Instead of timeout you can request a chart data
                $timeout(function(){

                    // directive listen for to remove the spinner
                    // after we end up to perform own operations
                    $scope.$broadcast('removeSpinner', id);

                    console.log('Refreshed #' + id);

                }, 3000);

            });


            // PANEL DISMISS EVENTS
            // -----------------------------------

            // Before remove panel
            $scope.$on('panel-remove', function(event, id, deferred){

                console.log('Panel #' + id + ' removing');

                // Here is obligatory to call the resolve() if we pretend to remove the panel finally
                // Not calling resolve() will NOT remove the panel
                // It's up to your app to decide if panel should be removed or not
                deferred.resolve();

            });

            // Panel removed ( only if above was resolved() )
            $scope.$on('panel-removed', function(event, id){

                console.log('Panel #' + id + ' removed');

            });

        }
    }
})();
/**=========================================================
 * Module: chart.controller.js
 * Controller for ChartJs
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.charts')
        .controller('ChartJSController', ChartJSController);

    ChartJSController.$inject = ['Colors'];
    function ChartJSController(Colors) {
        var vm = this;

        activate();

        ////////////////

        function activate() {

            // random values for demo
            var rFactor = function(){ return Math.round(Math.random()*100); };

            // Line chart
            // -----------------------------------

            vm.lineData = {
                labels : ['January','February','March','April','May','June','July'],
                datasets : [
                    {
                        label: 'My First dataset',
                        fillColor : 'rgba(114,102,186,0.2)',
                        strokeColor : 'rgba(114,102,186,1)',
                        pointColor : 'rgba(114,102,186,1)',
                        pointStrokeColor : '#fff',
                        pointHighlightFill : '#fff',
                        pointHighlightStroke : 'rgba(114,102,186,1)',
                        data : [rFactor(),rFactor(),rFactor(),rFactor(),rFactor(),rFactor(),rFactor()]
                    },
                    {
                        label: 'My Second dataset',
                        fillColor : 'rgba(35,183,229,0.2)',
                        strokeColor : 'rgba(35,183,229,1)',
                        pointColor : 'rgba(35,183,229,1)',
                        pointStrokeColor : '#fff',
                        pointHighlightFill : '#fff',
                        pointHighlightStroke : 'rgba(35,183,229,1)',
                        data : [rFactor(),rFactor(),rFactor(),rFactor(),rFactor(),rFactor(),rFactor()]
                    }
                ]
            };


            vm.lineOptions = {
                scaleShowGridLines : true,
                scaleGridLineColor : 'rgba(0,0,0,.05)',
                scaleGridLineWidth : 1,
                bezierCurve : true,
                bezierCurveTension : 0.4,
                pointDot : true,
                pointDotRadius : 4,
                pointDotStrokeWidth : 1,
                pointHitDetectionRadius : 20,
                datasetStroke : true,
                datasetStrokeWidth : 2,
                datasetFill : true,
            };


            // Bar chart
            // -----------------------------------

            vm.barData = {
                labels : ['January','February','March','April','May','June','July'],
                datasets : [
                    {
                        fillColor : Colors.byName('info'),
                        strokeColor : Colors.byName('info'),
                        highlightFill: Colors.byName('info'),
                        highlightStroke: Colors.byName('info'),
                        data : [rFactor(),rFactor(),rFactor(),rFactor(),rFactor(),rFactor(),rFactor()]
                    },
                    {
                        fillColor : Colors.byName('primary'),
                        strokeColor : Colors.byName('primary'),
                        highlightFill : Colors.byName('primary'),
                        highlightStroke : Colors.byName('primary'),
                        data : [rFactor(),rFactor(),rFactor(),rFactor(),rFactor(),rFactor(),rFactor()]
                    }
                ]
            };

            vm.barOptions = {
                scaleBeginAtZero : true,
                scaleShowGridLines : true,
                scaleGridLineColor : 'rgba(0,0,0,.05)',
                scaleGridLineWidth : 1,
                barShowStroke : true,
                barStrokeWidth : 2,
                barValueSpacing : 5,
                barDatasetSpacing : 1,
            };


            //  Doughnut chart
            // -----------------------------------

            vm.doughnutData = [
                {
                    value: 300,
                    color: Colors.byName('purple'),
                    highlight: Colors.byName('purple'),
                    label: 'Purple'
                },
                {
                    value: 50,
                    color: Colors.byName('info'),
                    highlight: Colors.byName('info'),
                    label: 'Info'
                },
                {
                    value: 100,
                    color: Colors.byName('yellow'),
                    highlight: Colors.byName('yellow'),
                    label: 'Yellow'
                }
            ];

            vm.doughnutOptions = {
                segmentShowStroke : true,
                segmentStrokeColor : '#fff',
                segmentStrokeWidth : 2,
                percentageInnerCutout : 85,
                animationSteps : 100,
                animationEasing : 'easeOutBounce',
                animateRotate : true,
                animateScale : false
            };

            // Pie chart
            // -----------------------------------

            vm.pieData =[
                {
                    value: 300,
                    color: Colors.byName('purple'),
                    highlight: Colors.byName('purple'),
                    label: 'Purple'
                },
                {
                    value: 40,
                    color: Colors.byName('yellow'),
                    highlight: Colors.byName('yellow'),
                    label: 'Yellow'
                },
                {
                    value: 120,
                    color: Colors.byName('info'),
                    highlight: Colors.byName('info'),
                    label: 'Info'
                }
            ];

            vm.pieOptions = {
                segmentShowStroke : true,
                segmentStrokeColor : '#fff',
                segmentStrokeWidth : 2,
                percentageInnerCutout : 0, // Setting this to zero convert a doughnut into a Pie
                animationSteps : 100,
                animationEasing : 'easeOutBounce',
                animateRotate : true,
                animateScale : false
            };

            // Polar chart
            // -----------------------------------

            vm.polarData = [
                {
                    value: 300,
                    color: Colors.byName('pink'),
                    highlight: Colors.byName('pink'),
                    label: 'Red'
                },
                {
                    value: 50,
                    color: Colors.byName('purple'),
                    highlight: Colors.byName('purple'),
                    label: 'Green'
                },
                {
                    value: 100,
                    color: Colors.byName('pink'),
                    highlight: Colors.byName('pink'),
                    label: 'Yellow'
                },
                {
                    value: 140,
                    color: Colors.byName('purple'),
                    highlight: Colors.byName('purple'),
                    label: 'Grey'
                },
            ];

            vm.polarOptions = {
                scaleShowLabelBackdrop : true,
                scaleBackdropColor : 'rgba(255,255,255,0.75)',
                scaleBeginAtZero : true,
                scaleBackdropPaddingY : 1,
                scaleBackdropPaddingX : 1,
                scaleShowLine : true,
                segmentShowStroke : true,
                segmentStrokeColor : '#fff',
                segmentStrokeWidth : 2,
                animationSteps : 100,
                animationEasing : 'easeOutBounce',
                animateRotate : true,
                animateScale : false
            };


            // Radar chart
            // -----------------------------------

            vm.radarData = {
                labels: ['Eating', 'Drinking', 'Sleeping', 'Designing', 'Coding', 'Cycling', 'Running'],
                datasets: [
                    {
                        label: 'My First dataset',
                        fillColor: 'rgba(114,102,186,0.2)',
                        strokeColor: 'rgba(114,102,186,1)',
                        pointColor: 'rgba(114,102,186,1)',
                        pointStrokeColor: '#fff',
                        pointHighlightFill: '#fff',
                        pointHighlightStroke: 'rgba(114,102,186,1)',
                        data: [65,59,90,81,56,55,40]
                    },
                    {
                        label: 'My Second dataset',
                        fillColor: 'rgba(151,187,205,0.2)',
                        strokeColor: 'rgba(151,187,205,1)',
                        pointColor: 'rgba(151,187,205,1)',
                        pointStrokeColor: '#fff',
                        pointHighlightFill: '#fff',
                        pointHighlightStroke: 'rgba(151,187,205,1)',
                        data: [28,48,40,19,96,27,100]
                    }
                ]
            };

            vm.radarOptions = {
                scaleShowLine : true,
                angleShowLineOut : true,
                scaleShowLabels : false,
                scaleBeginAtZero : true,
                angleLineColor : 'rgba(0,0,0,.1)',
                angleLineWidth : 1,
                /*jshint -W109*/
                pointLabelFontFamily : "'Arial'",
                pointLabelFontStyle : 'bold',
                pointLabelFontSize : 10,
                pointLabelFontColor : '#565656',
                pointDot : true,
                pointDotRadius : 3,
                pointDotStrokeWidth : 1,
                pointHitDetectionRadius : 20,
                datasetStroke : true,
                datasetStrokeWidth : 2,
                datasetFill : true
            };
        }
    }
})();
/**=========================================================
 * Module: chartist.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.charts')
        .controller('ChartistController', ChartistController);

    function ChartistController() {
        var vm = this;

        activate();

        ////////////////

        function activate() {

            // Line chart
            // -----------------------------------

            vm.lineData = {
                labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
                series: [
                    [12, 9, 7, 8, 5],
                    [2, 1, 3.5, 7, 3],
                    [1, 3, 4, 5, 6]
                ]
            };

            vm.lineOptions = {
                fullWidth: true,
                height: 220,
                chartPadding: {
                    right: 40
                }
            };

            // Bar bipolar
            // -----------------------------------

            vm.barBipolarOptions = {
                high: 10,
                low: -10,
                height: 220,
                axisX: {
                    labelInterpolationFnc: function(value, index) {
                        return index % 2 === 0 ? value : null;
                    }
                }
            };

            vm.barBipolarData = {
                labels: ['W1', 'W2', 'W3', 'W4', 'W5', 'W6', 'W7', 'W8', 'W9', 'W10'],
                series: [
                    [1, 2, 4, 8, 6, -2, -1, -4, -6, -2]
                ]
            };


            // Bar horizontal
            // -----------------------------------

            vm.barHorizontalData = {
                labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
                series: [
                    [5, 4, 3, 7, 5, 10, 3],
                    [3, 2, 9, 5, 4, 6, 4]
                ]
            };

            vm.barHorizontalOptions = {
                seriesBarDistance: 10,
                reverseData: true,
                horizontalBars: true,
                height: 220,
                axisY: {
                    offset: 70
                }
            };

            // Smil Animations
            // -----------------------------------

            // Let's put a sequence number aside so we can use it in the event callbacks
            var seq = 0,
                delays = 80,
                durations = 500;

            vm.smilData = {
                labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
                series: [
                    [12, 9, 7, 8, 5, 4, 6, 2, 3, 3, 4, 6],
                    [4,  5, 3, 7, 3, 5, 5, 3, 4, 4, 5, 5],
                    [5,  3, 4, 5, 6, 3, 3, 4, 5, 6, 3, 4],
                    [3,  4, 5, 6, 7, 6, 4, 5, 6, 7, 6, 3]
                ]
            };

            vm.smilOptions = {
                low: 0,
                height: 260
            };

            vm.smilEvents = {
                created: function() {
                    seq = 0;
                },
                draw: function(data) {
                    seq++;

                    if(data.type === 'line') {
                        // If the drawn element is a line we do a simple opacity fade in. This could also be achieved using CSS3 animations.
                        data.element.animate({
                            opacity: {
                                // The delay when we like to start the animation
                                begin: seq * delays + 1000,
                                // Duration of the animation
                                dur: durations,
                                // The value where the animation should start
                                from: 0,
                                // The value where it should end
                                to: 1
                            }
                        });
                    } else if(data.type === 'label' && data.axis === 'x') {
                        data.element.animate({
                            y: {
                                begin: seq * delays,
                                dur: durations,
                                from: data.y + 100,
                                to: data.y,
                                // We can specify an easing function from Chartist.Svg.Easing
                                easing: 'easeOutQuart'
                            }
                        });
                    } else if(data.type === 'label' && data.axis === 'y') {
                        data.element.animate({
                            x: {
                                begin: seq * delays,
                                dur: durations,
                                from: data.x - 100,
                                to: data.x,
                                easing: 'easeOutQuart'
                            }
                        });
                    } else if(data.type === 'point') {
                        data.element.animate({
                            x1: {
                                begin: seq * delays,
                                dur: durations,
                                from: data.x - 10,
                                to: data.x,
                                easing: 'easeOutQuart'
                            },
                            x2: {
                                begin: seq * delays,
                                dur: durations,
                                from: data.x - 10,
                                to: data.x,
                                easing: 'easeOutQuart'
                            },
                            opacity: {
                                begin: seq * delays,
                                dur: durations,
                                from: 0,
                                to: 1,
                                easing: 'easeOutQuart'
                            }
                        });
                    }
                }
            };


            // SVG PATH animation
            // -----------------------------------

            vm.pathData = {
                labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
                series: [
                    [1, 5, 2, 5, 4, 3],
                    [2, 3, 4, 8, 1, 2],
                    [5, 4, 3, 2, 1, 0.5]
                ]
            };

            vm.pathOptions = {
                low: 0,
                showArea: true,
                showPoint: false,
                fullWidth: true,
                height: 260
            };

            vm.pathEvents = {
                draw: function(data) {
                    if(data.type === 'line' || data.type === 'area') {
                        data.element.animate({
                            d: {
                                begin: 2000 * data.index,
                                dur: 2000,
                                from: data.path.clone().scale(1, 0).translate(0, data.chartRect.height()).stringify(),
                                to: data.path.clone().stringify(),
                                easing: Chartist.Svg.Easing.easeOutQuint
                            }
                        });
                    }
                }
            };

        }
    }
})();