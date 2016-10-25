/**
 * Created by jackShall on 2016/5/11.
 */

/**=========================================================
 * Module: word-cloud.js
 * Controller for jqCloud
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.extras')
        .controller('WordCloudController', WordCloudController);

    function WordCloudController() {
        var vm = this;

        activate();

        ////////////////

        function activate() {

            vm.words = [
                {
                    text: 'Lorem',
                    weight: 13
                    //link: 'http://themicon.co'
                }, {
                    text: 'Ipsum',
                    weight: 10.5
                }, {
                    text: 'Dolor',
                    weight: 9.4
                }, {
                    text: 'Sit',
                    weight: 8
                }, {
                    text: 'Amet',
                    weight: 6.2
                }, {
                    text: 'Consectetur',
                    weight: 5
                }, {
                    text: 'Adipiscing',
                    weight: 5
                }, {
                    text: 'Sit',
                    weight: 8
                }, {
                    text: 'Amet',
                    weight: 6.2
                }, {
                    text: 'Consectetur',
                    weight: 5
                }, {
                    text: 'Adipiscing',
                    weight: 5
                }
            ];
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('app.extras')
        .controller('TodoController', TodoController);

    TodoController.$inject = ['$filter'];
    function TodoController($filter) {
        var vm = this;

        activate();

        ////////////////

        function activate() {
            vm.items = [
                {
                    todo: {title: 'Meeting with Mark at 7am.', description: 'Pellentesque convallis mauris eu elit imperdiet quis eleifend quam aliquet. '},
                    complete: true
                },
                {
                    todo: {title: 'Call Sonya. Talk about the new project.', description: ''},
                    complete: false
                },
                {
                    todo: {title: 'Find a new place for vacations', description: ''},
                    complete: false
                }
            ];

            vm.editingTodo = false;
            vm.todo = {};

            vm.addTodo = function() {

                if( vm.todo.title === '' ) return;
                if( !vm.todo.description ) vm.todo.description = '';

                if( vm.editingTodo ) {
                    vm.todo = {};
                    vm.editingTodo = false;
                }
                else {
                    vm.items.push({todo: angular.copy(vm.todo), complete: false});
                    vm.todo.title = '';
                    vm.todo.description = '';
                }
            };

            vm.editTodo = function(index, $event) {
                $event.preventDefault();
                $event.stopPropagation();
                vm.todo = vm.items[index].todo;
                vm.editingTodo = true;
            };

            vm.removeTodo = function(index/*, $event*/) {
                vm.items.splice(index, 1);
            };

            vm.clearAll = function() {
                vm.items = [];
            };

            vm.totalCompleted = function() {
                return $filter('filter')(vm.items, function(item){
                    return item.complete;
                }).length;
            };

            vm.totalPending = function() {
                return $filter('filter')(vm.items, function(item){
                    return !item.complete;
                }).length;
            };

        }
    }
})();
/**=========================================================
 * Module: article.js
 =========================================================*/
(function() {
    'use strict';

    angular
        .module('app.extras')
        .controller('ArticleController', ArticleController);

    function ArticleController() {
        var vm = this;

        activate();

        ////////////////

        function activate() {
            vm.htmlContent = 'Article content...';

            vm.postDemo = {};
            vm.postDemo.tags = ['coding', 'less'];
            vm.availableTags = ['coding', 'less', 'sass', 'angularjs', 'node', 'expressJS'];
            vm.postDemo.categories = ['JAVASCRIPT','WEB'];
            vm.availableCategories = ['JAVASCRIPT','WEB', 'BOOTSTRAP', 'SERVER', 'HTML5', 'CSS'];

            vm.reviewers = [
                { name: 'Adam',      email: 'adam@email.com',      age: 10 },
                { name: 'Amalie',    email: 'amalie@email.com',    age: 12 },
                { name: 'Wladimir',  email: 'wladimir@email.com',  age: 30 },
                { name: 'Samantha',  email: 'samantha@email.com',  age: 31 },
                { name: 'Estefanía', email: 'estefanía@email.com', age: 16 },
                { name: 'Natasha',   email: 'natasha@email.com',   age: 54 },
                { name: 'Nicole',    email: 'nicole@email.com',    age: 43 },
                { name: 'Adrian',    email: 'adrian@email.com',    age: 21 }
            ];


            vm.alerts = [
                { type: 'info', msg: 'There is an autosaved version of this article that is more recent than the version below. <a href="#" class="text-white">Restore</a>' }
            ];

            vm.closeAlert = function(index) {
                vm.alerts.splice(index, 1);
            };
        }
    }
})();

/**=========================================================
 * Module: calendar-ui.js
 * This script handle the calendar demo with draggable
 * events and events creations
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.extras')
        .directive('calendar', calendar);

    calendar.$inject = ['$rootScope'];
    function calendar ($rootScope) {
        var directive = {
            link: link,
            restrict: 'EA'
        };
        return directive;

        function link(scope, element) {

            if(!$.fn.fullCalendar) return;

            // The element that will display the calendar
            var calendar = element;

            var demoEvents = createDemoEvents();

            initExternalEvents(calendar);

            initCalendar(calendar, demoEvents, $rootScope.app.layout.isRTL);
        }
    }


    // global shared var to know what we are dragging
    var draggingEvent = null;


    /**
     * ExternalEvent object
     * @param jQuery Object elements Set of element as jQuery objects
     */
    function ExternalEvent(elements) {

        if (!elements) return;

        elements.each(function() {
            var $this = $(this);
            // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
            // it doesn't need to have a start or end
            var calendarEventObject = {
                title: $.trim($this.text()) // use the element's text as the event title
            };

            // store the Event Object in the DOM element so we can get to it later
            $this.data('calendarEventObject', calendarEventObject);

            // make the event draggable using jQuery UI
            $this.draggable({
                zIndex: 1070,
                revert: true, // will cause the event to go back to its
                revertDuration: 0  //  original position after the drag
            });

        });
    }

    /**
     * Invoke full calendar plugin and attach behavior
     * @param  jQuery [calElement] The calendar dom element wrapped into jQuery
     * @param  EventObject [events] An object with the event list to load when the calendar displays
     */
    function initCalendar(calElement, events, isRTL) {

        // check to remove elements from the list
        var removeAfterDrop = $('#remove-after-drop');

        calElement.fullCalendar({
            isRTL: isRTL,
            header: {
                left:   'prev,next today',
                center: 'title',
                right:  'month,agendaWeek,agendaDay'
            },
            buttonIcons: { // note the space at the beginning
                prev:    ' fa fa-caret-left',
                next:    ' fa fa-caret-right'
            },
            buttonText: {
                today: 'today',
                month: 'month',
                week:  'week',
                day:   'day'
            },
            editable: true,
            droppable: true, // this allows things to be dropped onto the calendar
            drop: function(date, allDay) { // this function is called when something is dropped

                var $this = $(this),
                // retrieve the dropped element's stored Event Object
                    originalEventObject = $this.data('calendarEventObject');

                // if something went wrong, abort
                if(!originalEventObject) return;

                // clone the object to avoid multiple events with reference to the same object
                var clonedEventObject = $.extend({}, originalEventObject);

                // assign the reported date
                clonedEventObject.start = date;
                clonedEventObject.allDay = allDay;
                clonedEventObject.backgroundColor = $this.css('background-color');
                clonedEventObject.borderColor = $this.css('border-color');

                // render the event on the calendar
                // the last `true` argument determines if the event "sticks"
                // (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
                calElement.fullCalendar('renderEvent', clonedEventObject, true);

                // if necessary remove the element from the list
                if(removeAfterDrop.is(':checked')) {
                    $this.remove();
                }
            },
            eventDragStart: function (event/*, js, ui*/) {
                draggingEvent = event;
            },
            // This array is the events sources
            events: events
        });
    }

    /**
     * Inits the external events panel
     * @param  jQuery [calElement] The calendar dom element wrapped into jQuery
     */
    function initExternalEvents(calElement){
        // Panel with the external events list
        var externalEvents = $('.external-events');

        // init the external events in the panel
        new ExternalEvent(externalEvents.children('div'));

        // External event color is danger-red by default
        var currColor = '#f6504d';
        // Color selector button
        var eventAddBtn = $('.external-event-add-btn');
        // New external event name input
        var eventNameInput = $('.external-event-name');
        // Color switchers
        var eventColorSelector = $('.external-event-color-selector .circle');

        // Trash events Droparea
        $('.external-events-trash').droppable({
            accept:       '.fc-event',
            activeClass:  'active',
            hoverClass:   'hovered',
            tolerance:    'touch',
            drop: function(event, ui) {

                // You can use this function to send an ajax request
                // to remove the event from the repository

                if(draggingEvent) {
                    var eid = draggingEvent.id || draggingEvent._id;
                    // Remove the event
                    calElement.fullCalendar('removeEvents', eid);
                    // Remove the dom element
                    ui.draggable.remove();
                    // clear
                    draggingEvent = null;
                }
            }
        });

        eventColorSelector.click(function(e) {
            e.preventDefault();
            var $this = $(this);

            // Save color
            currColor = $this.css('background-color');
            // De-select all and select the current one
            eventColorSelector.removeClass('selected');
            $this.addClass('selected');
        });

        eventAddBtn.click(function(e) {
            e.preventDefault();

            // Get event name from input
            var val = eventNameInput.val();
            // Dont allow empty values
            if ($.trim(val) === '') return;

            // Create new event element
            var newEvent = $('<div/>').css({
                    'background-color': currColor,
                    'border-color':     currColor,
                    'color':            '#fff'
                })
                .html(val);

            // Prepends to the external events list
            externalEvents.prepend(newEvent);
            // Initialize the new event element
            new ExternalEvent(newEvent);
            // Clear input
            eventNameInput.val('');
        });
    }

    /**
     * Creates an array of events to display in the first load of the calendar
     * Wrap into this function a request to a source to get via ajax the stored events
     * @return Array The array with the events
     */
    function createDemoEvents() {
        // Date for the calendar events (dummy data)
        var date = new Date();
        var d = date.getDate(),
            m = date.getMonth(),
            y = date.getFullYear();

        return  [
            {
                title: 'All Day Event',
                start: new Date(y, m, 1),
                backgroundColor: '#f56954', //red
                borderColor: '#f56954' //red
            },
            {
                title: 'Long Event',
                start: new Date(y, m, d - 5),
                end: new Date(y, m, d - 2),
                backgroundColor: '#f39c12', //yellow
                borderColor: '#f39c12' //yellow
            },
            {
                title: 'Meeting',
                start: new Date(y, m, d, 10, 30),
                allDay: false,
                backgroundColor: '#0073b7', //Blue
                borderColor: '#0073b7' //Blue
            },
            {
                title: 'Lunch',
                start: new Date(y, m, d, 12, 0),
                end: new Date(y, m, d, 14, 0),
                allDay: false,
                backgroundColor: '#00c0ef', //Info (aqua)
                borderColor: '#00c0ef' //Info (aqua)
            },
            {
                title: 'Birthday Party',
                start: new Date(y, m, d + 1, 19, 0),
                end: new Date(y, m, d + 1, 22, 30),
                allDay: false,
                backgroundColor: '#00a65a', //Success (green)
                borderColor: '#00a65a' //Success (green)
            },
            {
                title: 'Open Google',
                start: new Date(y, m, 28),
                end: new Date(y, m, 29),
                url: '//google.com/',
                backgroundColor: '#3c8dbc', //Primary (light-blue)
                borderColor: '#3c8dbc' //Primary (light-blue)
            }
        ];
    }

})();

(function() {
    'use strict';

    angular
        .module('app.extras')
        .service('LoadTreeService', LoadTreeService);

    LoadTreeService.$inject = ['$resource'];
    function LoadTreeService($resource) {
        // Loads the list of files to populate the treeview
        return $resource('server/editor/filetree.json');
    }

})();
/**=========================================================
 * Module: code-editor.js
 * Codemirror code editor controller
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.extras')
        .controller('CodeEditorController', CodeEditorController);

    CodeEditorController.$inject = ['$rootScope', '$scope', '$http', '$ocLazyLoad', 'filetree'];
    function CodeEditorController($rootScope, $scope, $http, $ocLazyLoad, filetree) {
        var vm = this;

        layout();
        activate();

        ////////////////
        /*jshint -W106*/
        function layout() {
            // Setup the layout mode
            $rootScope.app.useFullLayout = true;
            $rootScope.app.hiddenFooter = true;
            $rootScope.app.layout.isCollapsed = true;

            // Restore layout for demo
            $scope.$on('$destroy', function(){
                $rootScope.app.useFullLayout = false;
                $rootScope.app.hiddenFooter = false;
            });

        }

        function activate() {

            // Set the tree data into the scope
            vm.filetree_data = filetree;

            // Available themes
            vm.editorThemes = ['3024-day','3024-night','ambiance-mobile','ambiance','base16-dark','base16-light','blackboard','cobalt','eclipse','elegant','erlang-dark','lesser-dark','mbo','mdn-like','midnight','monokai','neat','neo','night','paraiso-dark','paraiso-light','pastel-on-dark','rubyblue','solarized','the-matrix','tomorrow-night-eighties','twilight','vibrant-ink','xq-dark','xq-light'];

            vm.editorOpts = {
                mode: 'javascript',
                lineNumbers: true,
                matchBrackets: true,
                theme: 'mbo',
                viewportMargin: Infinity
            };

            vm.refreshEditor = 0;

            // Load dinamically the stylesheet for the selected theme
            // You can use ozLazyLoad to load also the mode js based
            // on the file extension that is loaded (see handle_filetree)
            vm.loadTheme = function() {
                var BASE = 'vendor/codemirror/theme/';
                $ocLazyLoad.load(BASE + vm.editorOpts.theme + '.css');
                vm.refreshEditor = !vm.refreshEditor;
            };
            // load default theme
            vm.loadTheme(vm.editorOpts.theme);
            // Add some initial text
            vm.code = '// Open a file from the left menu \n' +
                '// It will be requested to the server and loaded into the editor\n' +
                '// Also try adding a New File from the toolbar\n';


            // Tree

            var selectedBranch;
            vm.handle_filetree = function(branch) {

                selectedBranch = branch;

                var basePath = 'server/editor/';
                var isFolder = !!branch.children.length;

                console.log('You selected: ' + branch.label + ' - isFolder? ' + isFolder);

                if ( ! isFolder ) {

                    $http
                        .get( basePath + branch.path )
                        .success(function(response){

                            console.log('Loaded.. ' + branch.path);
                            // set the new code into the editor
                            vm.code = response;

                            vm.editorOpts.mode = detectMode(branch.path);
                            console.log( 'Mode is: ' + vm.editorOpts.mode);

                        });
                }
            };

            function detectMode(file) {
                var ext = file.split('.');
                ext = ext ? ext[ext.length - 1] : '';
                switch (ext) {
                    case 'html':  return 'htmlmixed';
                    case 'css':   return 'css';
                    default:      return 'javascript';
                }
            }

            var tree;
            tree = vm.filetree = {};

            // Adds a new branch to the tree
            vm.new_filetree = function() {
                var b;
                b = tree.get_selected_branch();

                // if we select a leaf -> select the parent folder
                if ( b && b.children.length === 0 ) {
                    b = tree.get_parent_branch(b);
                }

                return tree.add_branch(b, {
                    'label': 'another.html',
                    'path': 'source/another.html'
                });
            };
        }
    }
})();