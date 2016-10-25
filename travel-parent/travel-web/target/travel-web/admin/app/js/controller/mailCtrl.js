/**
 * Created by jackShall on 2016/5/11.
 */
(function() {
    'use strict';

    angular
        .module('app.mailbox')
        .controller('MailViewController', MailViewController);

    MailViewController.$inject = ['mails', '$stateParams'];
    function MailViewController(mails, $stateParams) {
        var vm = this;

        activate();

        ////////////////

        function activate() {
            mails.get($stateParams.mid).then(function(mail){
                vm.mail = mail;
            });
        }
    }
})();
(function() {
    'use strict';

    angular
        .module('app.mailbox')
        .controller('MailFolderController', MailFolderController);

    MailFolderController.$inject = ['mails', '$stateParams'];
    function MailFolderController(mails, $stateParams) {
        var vm = this;

        activate();

        ////////////////

        function activate() {

            vm.folder = {};
            // no filter for inbox
            vm.folder.folder = $stateParams.folder === 'inbox' ? '' : $stateParams.folder;

            mails.all().then(function(mails){
                vm.mails = mails;
            });
        }
    }
})();

/**=========================================================
 * Module: demo-pagination.js
 * Provides a simple demo for pagination
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.mailbox')
        .controller('MailboxController', MailboxController);

    function MailboxController() {
        var vm = this;

        activate();

        ////////////////

        function activate() {
            vm.folders = [
                {name: 'Inbox',   folder: 'inbox',   alert: 42, icon: 'fa-inbox' },
                {name: 'Starred', folder: 'starred', alert: 10, icon: 'fa-star' },
                {name: 'Sent',    folder: 'sent',    alert: 0,  icon: 'fa-paper-plane-o' },
                {name: 'Draft',   folder: 'draft',   alert: 5,  icon: 'fa-edit' },
                {name: 'Trash',   folder: 'trash',   alert: 0,  icon: 'fa-trash'}
            ];

            vm.labels = [
                {name: 'Red',     color: 'danger'},
                {name: 'Pink',    color: 'pink'},
                {name: 'Blue',    color: 'info'},
                {name: 'Yellow',  color: 'warning'}
            ];

            vm.mail = {
                cc: false,
                bcc: false
            };
            // Mailbox editr initial content
            vm.content = '<p>Type something..</p>';
        }
    }
})();