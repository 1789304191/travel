/**
 * Created by jackShall on 2016/5/16.
 */
angular
    .module('app.tables')
    .controller('UserStatisticsCtrl', function($scope, $http,ngDialog,RouteHelpers,SweetAlert,$filter,$timeout,$rootScope,$interval){
    	var url = '../admin/user-statistics/userStatisticsGrid';
        //表格数据
        $scope.dataTable = {
            data: [],
            currentPage: 1,
            pageSize: 10,
            totalLength: 0
        };
        $scope.search={
            "condition":""
        };
        //数据初始化
        function  init(){
        	$http
            .post(url,{pageSize:$scope.dataTable.pageSize,pageNo:$scope.dataTable.currentPage})
            .then(function(response) {
            $scope.dataTable.data = response.data.dataList;
            $scope.dataTable.totalLength = response.data.total;
            angular.forEach($scope.dataTable.data, function(data,index,array){
            	if(data.lastLoginTime){
            		$scope.llt= $filter("date")(data.lastLoginTime.time, "yyyy-MM-dd HH:mm:ss");
            		data.lastLoginTime=$scope.llt;
            	}
            	if(data.startTimestamp){
            		$scope.sts = $filter("date")(data.startTimestamp.time, "yyyy-MM-dd HH:mm:ss");
            		data.startTimestamp=$scope.sts;
            	}
              });
             addReadControl($scope.dataTable.data);
            });
        };
        init();
        //是否可编辑
        function addReadControl(data){
            for(var i=0;i<data.length;i++){
                data[i].readControl=true;
            }
        };
        //前页数
        $scope.currentPageStart=function() {
            return (($scope.dataTable.currentPage - 1) * $scope.dataTable.pageSize) + 1;
        };
       //后页数
        $scope.currentPageEnd=function() {
            return ($scope.dataTable.currentPage - 1) * $scope.dataTable.pageSize + $scope.dataTable.data.length;
        };
        //保存
        $scope.tableSave = function(item,index){
            $scope.dataTable.data[index].readControl=true;
        };
        //取消
        $scope.tableCancel = function(index){
            $scope.dataTable.data[index].readControl=true;
        };
        //搜索
        $scope.search=function(){
            console.log("search for"+$scope.search.condition);
        };
        //查找
        $scope.searchChange=function(){
            console.log("search for"+$scope.search.condition);
        };
        //排序
        $scope.tableSort=function(item){
            console.log(item);
        };
        //查询页数
        $scope.pageChanged =function(){
        	$http
            .post(url,{pageSize:$scope.dataTable.pageSize,pageNo:$scope.dataTable.currentPage})
            .then(function(response) {
            $scope.dataTable.data = response.data.dataList;
            $scope.dataTable.totalLength = response.data.total;
            angular.forEach($scope.dataTable.data, function(data,index,array){
            	$scope.llt= $filter("date")(data.lastLoginTime.time, "yyyy-MM-dd HH:mm:ss");
             	data.lastLoginTime=$scope.llt;
             	$scope.sts = $filter("date")(data.startTimestamp.time, "yyyy-MM-dd HH:mm:ss");
             	data.startTimestamp=$scope.sts;
              });
             addReadControl($scope.dataTable.data);
            });
        };
        
    });