/**
 * Created by jackShall on 2016/5/16.
 */
angular
    .module('app.tables')
    .controller('OrderCtrl', ['$scope','$http','ngDialog','RouteHelpers','SweetAlert','$filter',function($scope, $http,ngDialog,RouteHelpers,SweetAlert,$filter){
    	$scope.query={
    		"status":"-1",
    		"payId":"-1"
    	};
    	$scope.selectItems=[{"id":"-1","name":"请选择"},
    	                    {"id":"1","name":"已购买"},
    	                    {"id":"2","name":"已核销"}];
    	$scope.selectPayIdItems=[{"id":"-1","name":"请选择"},
    	                    {"id":"1","name":"支付宝"},
    	                    {"id":"2","name":"微信"},
    	                    {"id":"3","name":"银联"}];
    	var url = '../admin/order/orderGrid';
        $scope.tplPath = RouteHelpers.basepath('partials/dialogForm.html');
        //表格数据
        $scope.dataTable = {
            data: [],
            currentPage: 1,
            pageSize: 10,
            totalLength: 0,
            amountMoney:0
        };
        
        $scope.myKeyup = function(e){
            var keycode = window.event?e.keyCode:e.which;
            if(keycode==13){
            	$http.post(url, 
                		{orderSn:$scope.query.orderSn,
                		 name:$scope.query.name,
                		 mobilePhone:$scope.query.mobilePhone,
                		 payId:$scope.query.payId,
                		 startDatetime:$scope.query.startDatetime,
                		 endDatetime:$scope.query.endDatetime,
                		 status:$scope.query.status,
                		 pdaUserName:$scope.query.pdaUserName,
                		 pageSize:$scope.dataTable.pageSize,
                	     pageNo:$scope.dataTable.currentPage}
                ).success(function(data) {
                    $scope.dataTable.data = data.dataList;
                    $scope.dataTable.totalLength = data.total;
                    $scope.dataTable.amountMoney = data.amountMoney;
                    angular.forEach($scope.dataTable.data, function(data,index,array){
                    	$scope.dt = $filter("date")(data.createTime.time, "yyyy-MM-dd HH:mm:ss");
                    	data.createTime=$scope.dt;
                    	if(data.status==1){
                    		data.check=true;
                    		data.status='已购买'
                    	}
                    	if(data.status==2){
                    		data.check=true;
                    		data.status= '已核销';
                    	}
                    });
                });
            }
        };
        
        
 
        //数据初始化
        function  init(){
            $http.post(url, 
            		{pageSize:$scope.dataTable.pageSize,
            	     pageNo:$scope.dataTable.currentPage}
            ).success(function(data) {
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
                $scope.dataTable.amountMoney = data.amountMoney;
                angular.forEach($scope.dataTable.data, function(data,index,array){
                	$scope.dt = $filter("date")(data.createTime.time, "yyyy-MM-dd HH:mm:ss");
                	data.createTime=$scope.dt;
                	if(data.status==1){
                		data.check=true;
                		data.status='已购买'
                	}
                	if(data.status==2){
                		data.check=true;
                		data.status='已核销'
                	}
                });
            });
        };
        init();

        //前页数
        $scope.currentPageStart=function() {
        	if($scope.dataTable.totalLength == 0){
        		return 0;
        	}
            return (($scope.dataTable.currentPage - 1) * $scope.dataTable.pageSize) + 1;
        };
        //后页数
        $scope.currentPageEnd=function() {
            return ($scope.dataTable.currentPage - 1) * $scope.dataTable.pageSize + $scope.dataTable.data.length;
        };
        //总条数
        $scope.totalNum=function() {
        	return $scope.dataTable.totalLength;
        };
        //搜索
        $scope.searchFuc=function(){
            $http.post(url, 
            		{key: $scope.query.key,
            		 pageSize:$scope.dataTable.pageSize,
            	     pageNo:$scope.dataTable.currentPage}
            ).success(function(data) {
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
                $scope.dataTable.amountMoney = data.amountMoney;
                angular.forEach($scope.dataTable.data, function(data,index,array){
                	$scope.dt = $filter("date")(data.createTime.time, "yyyy-MM-dd HH:mm:ss");
                	data.createTime=$scope.dt;
                	if(data.status==1){
                		data.check=true;
                		data.status='已购买'
                	}
                	if(data.status==2){
                		data.check=true;
                		data.status='已核销'
                	}
                });
            });
        };
        //高级搜索
        $scope.superiorSearch=function(){
            $http.post(url, 
            		{orderSn:$scope.query.orderSn,
            		 name:$scope.query.name,
            		 mobilePhone:$scope.query.mobilePhone,
            		 payId:$scope.query.payId,
            		 startDatetime:$scope.query.startDatetime,
            		 endDatetime:$scope.query.endDatetime,
            		 status:$scope.query.status,
            		 pdaUserName:$scope.query.pdaUserName,
            		 pageSize:$scope.dataTable.pageSize,
            	     pageNo:$scope.dataTable.currentPage}
            ).success(function(data) {
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
                $scope.dataTable.amountMoney = data.amountMoney;
                angular.forEach($scope.dataTable.data, function(data,index,array){
                	$scope.dt = $filter("date")(data.createTime.time, "yyyy-MM-dd HH:mm:ss");
                	data.createTime=$scope.dt;
                	if(data.status==1){
                		data.check=true;
                		data.status='已购买'
                	}
                	if(data.status==2){
                		data.check=true;
                		data.status= '已核销';
                	}
                });
            });
        };
        //排序
        $scope.tableSort=function(item){
            console.log(item);
        };
        //序号
        $scope.serialNumber=function(item){
        	return ($scope.dataTable.currentPage - 1) * $scope.dataTable.pageSize + item
        };
        //订单总额
        $scope.amountMoney=function(item){
        	return $scope.dataTable.amountMoney;
        };
        //查询页数
        $scope.pageChanged =function(){
            $http.post(url,
            		{orderSn:$scope.query.orderSn,
            		 name:$scope.query.name,
            		 mobilePhone:$scope.query.mobilePhone,
            		 payId:$scope.query.payId,
            		 startDatetime:$scope.query.startDatetime,
            		 endDatetime:$scope.query.endDatetime,
            		 status:$scope.query.status,
            		 pdaUserName:$scope.query.pdaUserName,
            		 pageSize:$scope.dataTable.pageSize,
            	     pageNo:$scope.dataTable.currentPage}
            ).success(function(data) {
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
                $scope.dataTable.amountMoney = data.amountMoney;
                angular.forEach($scope.dataTable.data, function(data,index,array){
                	$scope.dt = $filter("date")(data.createTime.time, "yyyy-MM-dd HH:mm:ss");
                	data.createTime=$scope.dt;
                	if(data.status==1){
                		data.check=true;
                		data.status='已购买'
                	}
                	if(data.status==2){
                		data.check=true;
                		data.status='已核销'
                	}
                });
            });
        };

    }]);