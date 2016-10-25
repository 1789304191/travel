/**
 * Created by jackShall on 2016/5/16.
 */
angular
    .module('app.tables')
    .controller('testFormCtrl',function($scope,$rootScope){
        $scope.confirm =function(){
           if($scope.testForm.$invalid){
               $scope.testForm.CompanyNumber.$dirty = true;
               $scope.testForm.two.$dirty = true;
               $scope.testForm.one.$dirty = true;
               console.log("表单验证不通过");
           }else{
               //关闭
               $scope.closeThisDialog(0);
           }
        };
    })
    .controller('UserTableCtrl', ['$scope', '$http', 'ngDialog', 'RouteHelpers', 'SweetAlert', '$resource', function ($scope, $http, ngDialog, RouteHelpers, SweetAlert, $resource) {
        var url = '../admin/event/eventGrid';
//        var url = 'server/ng-table.json';
        $scope.tplPath = RouteHelpers.basepath('partials/dialogForm.html');
        //表格数据
        $scope.dataTable = {
            data: [],
            currentPage: 1,
            pageSize: 10,
            totalLength: 0
        };
        $scope.search = {
            "condition": ""
        };
        //数据初始化
        function init() {
            $http.get(url + "?pageSize=" + $scope.dataTable.pageSize + "&pageNo=" + $scope.dataTable.currentPage).success(function (data) {
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
                addReadControl($scope.dataTable.data);
            });
            ngDialog.openConfirm({
                template: $scope.tplPath,
                controller: function ($scope) {
                    //$scope.userAdmin = angular.copy($scope.dataTable.data[index]);
                },
                className: 'ngdialog-theme-default',
                preCloseCallback: 'preCloseCallbackOnScope',
                scope: $scope
            }).then(function (value) {
                $scope.dataTable.data[index] = value;
                console.log('Modal promise resolved. Value: ', value);
            }, function (reason) {
                console.log('Modal promise rejected. Reason: ', reason);
            });
        };
        init();
        //是否可编辑
        function addReadControl(data) {
            for (var i = 0; i < data.length; i++) {
                data[i].readControl = true;
            }
        };
        //$scope.$on("add",function(item,val){
        //    console.log(item);
        //    console.log(val);
        //})
        //前页数
        $scope.currentPageStart = function () {
            return (($scope.dataTable.currentPage - 1) * $scope.dataTable.pageSize) + 1;
        };
        //后页数
        $scope.currentPageEnd = function () {
            return ($scope.dataTable.currentPage - 1) * $scope.dataTable.pageSize + $scope.dataTable.data.length;
        };

        //编辑
        $scope.tableEdit = function (index) {
            ngDialog.openConfirm({
                template: $scope.tplPath,
                controller: function ($scope) {
                    $scope.userAdmin = angular.copy($scope.dataTable.data[index]);
                },
                className: 'ngdialog-theme-default',
                preCloseCallback: 'preCloseCallbackOnScope',
                scope: $scope
            }).then(function (value) {
                $scope.dataTable.data[index] = value;
                console.log('Modal promise resolved. Value: ', value);
            }, function (reason) {
                console.log('Modal promise rejected. Reason: ', reason);
            });
            //$scope.dataTable.data[index].readControl=false;
            //for(var i=0;i<$scope.dataTable.data.length;i++){
            //    if(i!=index){
            //        $scope.dataTable.data[i].readControl=true;
            //    }
            //}
        };
        //删除
        $scope.tableDelete = function (id) {
            SweetAlert.swal({
                title: '你确定要删除吗？',
                type: 'warning',
                showCancelButton: true,
                cancelButtonText: '取消',
                confirmButtonColor: '#DD6B55',
                confirmButtonText: '确定',
                closeOnConfirm: true
            }, function () {
            });
            console.log("删除" + id);
        };
        //保存
        $scope.tableSave = function (item, index) {
            console.log(item);
            $scope.dataTable.data[index].readControl = true;
        };
        //取消
        $scope.tableCancel = function (index) {
            $scope.dataTable.data[index].readControl = true;
        };
        //搜索
        $scope.search = function () {
            console.log("search for" + $scope.search.condition);
        };
        //查找
        $scope.searchChange = function () {
            console.log("search for" + $scope.search.condition);
        };
        //排序
        $scope.tableSort = function (item) {
            console.log(item);
        };
        //查询页数
        $scope.pageChanged = function () {
            $http.get(url + "?pageSize=" + $scope.dataTable.pageSize + "&pageNo=" + $scope.dataTable.currentPage).success(function (data) {
                $scope.dataTable.data = data.dataList;
                addReadControl($scope.dataTable.data);
            });
        };
        var tree;
        // This is our API control variable
        $scope.my_tree = tree = {};

        $scope.my_data = [];
        $scope.doing_async = true;

        $http.get('server/treedata.json').success(function(data){
            $scope.doing_async = false;
            $scope.my_data = data.data;
        })

    }]);