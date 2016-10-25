/**
 * Created by jackShall on 2016/5/16.
 */
angular
    .module('app.tables')
    //新增弹出框
		    .controller(
				'compayBillType-add-dialogFormCtrl',
				function($scope, $rootScope, $http, $interval, SweetAlert,
						$timeout, $filter) {
					var url = '../admin/billType/billTypeGrid';
					//数据初始化
			        function  init(){
			            $http.post(url, 
			            		{pageSize:$scope.dataTable.pageSize,
			            	     pageNo:$scope.dataTable.currentPage}
			            ).success(function(data) {
			                $scope.dataTable.data = data.dataList;
			                $scope.dataTable.totalLength = data.total;
			            });
			        };
					$scope.confirm = function(compayBillTypeForm) {
						if ($scope.compayBillTypeForm.$invalid) {
							$scope.compayBillTypeForm.compay.$dirty = true;
							$scope.compayBillTypeForm.companyTax.$dirty = true;
							$scope.compayBillTypeForm.amount.$dirty = true;
							$scope.compayBillTypeForm.content.$dirty = true;
						} else {
							$http.post('../admin/billType/add', {
								compay : $scope.compayBillType.compay,
								companyTax : $scope.compayBillType.companyTax,
								amount : $scope.compayBillType.amount,
								content : $scope.compayBillType.content
							}).then(
									function(response) {
										if (response.data.result == 1) {
											SweetAlert.swal("", "添加成功",
													"success");
											init();
										} else {

											SweetAlert.swal("",
													response.data.message,
													"error");
										}
									}, function() {
										SweetAlert.swal("", "系统错误", "error");
									});
							// 关闭
							$scope.closeThisDialog(0);
						}
					};
				})
    
			//修改
		    .controller(
				'compayBillType-update-dialogFormCtrl',
				function($scope, $rootScope, $http, $interval, SweetAlert,
						$timeout, $filter) {
					var url = '../admin/billType/billTypeGrid';
					//数据初始化
			        function  init(){
			            $http.post(url, 
			            		{pageSize:$scope.dataTable.pageSize,
			            	     pageNo:$scope.dataTable.currentPage}
			            ).success(function(data) {
			                $scope.dataTable.data = data.dataList;
			                $scope.dataTable.totalLength = data.total;
			            });
			        };
					$scope.$on("id",function(item,val){
						$scope.compayBillType.id=val;
					});
					$scope.confirm = function(compayBillTypeForm) {
						if ($scope.compayBillTypeForm.$invalid) {
							$scope.compayBillTypeForm.compay.$dirty = true;
							$scope.compayBillTypeForm.companyTax.$dirty = true;
							$scope.compayBillTypeForm.amount.$dirty = true;
							$scope.compayBillTypeForm.content.$dirty = true;
						} else {
							$http.post('../admin/billType/update', {
								id : $scope.compayBillType.id,
								compay : $scope.compayBillType.compay,
								companyTax : $scope.compayBillType.companyTax,
								amount : $scope.compayBillType.amount,
								content : $scope.compayBillType.content
							}).then(
									function(response) {
										if (response.data.result == 1) {
											SweetAlert.swal("", "修改成功",
													"success");
											init();
										} else {
											SweetAlert.swal("",
													response.data.message,
													"error");
										}
									}, function() {
										SweetAlert.swal("", "系统错误", "error");
									});
							// 关闭
							$scope.closeThisDialog(0);
						}
					};
				})
    
				
				
				
    .controller('CompayBillTypeCtrl', ['$scope','$http','ngDialog','RouteHelpers','SweetAlert','$filter','$rootScope',function($scope, $http,ngDialog,RouteHelpers,SweetAlert,$filter,$rootScope){
    	$scope.query={
    		"status":"-1"
    	};
    	$scope.selectItems=[{"id":"-1","name":"请选择"},
    	                    {"id":"1","name":"已购买"},
    	                    {"id":"2","name":"已核销"}];
    	var url = '../admin/billType/billTypeGrid';
        $scope.addPath = RouteHelpers.basepath('billType/compayBillType-add-dialogForm.html');
        $scope.editPath = RouteHelpers.basepath('billType/compayBillType-update-dialogForm.html');
        //表格数据
        $scope.dataTable = {
            data: [],
            currentPage: 1,
            pageSize: 10,
            totalLength: 0,
            amountMoney:0
        };
 
        //数据初始化
        function  init(){
            $http.post(url, 
            		{pageSize:$scope.dataTable.pageSize,
            	     pageNo:$scope.dataTable.currentPage}
            ).success(function(data) {
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
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
                angular.forEach($scope.dataTable.data, function(data,index,array){
                	if(data.status==0){
                		data.check=true;
                		data.status='已删除'
                	}
                	if(data.status==1){
                		data.check=true;
                		data.status='正常'
                	}
                });
            });
        };
        //序号
        $scope.serialNumber=function(item){
        	return ($scope.dataTable.currentPage - 1) * $scope.dataTable.pageSize + item
        };
        //查询页数
        $scope.pageChanged =function(){
            $http.post(url,
            		{key: $scope.query.key,
            		 pageSize:$scope.dataTable.pageSize,
            	     pageNo:$scope.dataTable.currentPage}
            ).success(function(data) {
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
                angular.forEach($scope.dataTable.data, function(data,index,array){
                	if(data.status==0){
                		data.check=true;
                		data.status='已删除'
                	}
                	if(data.status==1){
                		data.check=true;
                		data.status='正常'
                	}
                });
            });
        };

        //新增
        $scope.add=function(){
        	ngDialog.openConfirm({
            template: $scope.addPath,
            className: 'ngdialog-theme-default',
            preCloseCallback: 'preCloseCallbackOnScope',
            scope: $scope
        }).then(function (form) {
        }, function (reason) {
        });
        };
        //编辑
        $scope.tableEdit=function(index){
            ngDialog.openConfirm({
                template: $scope.editPath,
                controller:function($scope){
                	$scope.compayBillType = angular.copy($scope.dataTable.data[index]);
                	$scope.compayBillType.compay =$scope.dataTable.data[index].compay;
                	$scope.compayBillType.companyTax =$scope.dataTable.data[index].companyTax;
                	$scope.compayBillType.amount =$scope.dataTable.data[index].amount;
                	$scope.compayBillType.content =$scope.dataTable.data[index].content;
                },
                className: 'ngdialog-theme-default',
                preCloseCallback: 'preCloseCallbackOnScope',
                scope: $scope
            });
            $rootScope.$broadcast("id",$scope.dataTable.data[index].id);
        };
        //删除
        $scope.tableDelete=function(id){
            swal({
                title: '你确定要删除吗？',
                type: 'warning',
                showCancelButton: true,
                cancelButtonText:'取消',
                confirmButtonColor: '#DD6B55',
                confirmButtonText: '确定',
                closeOnConfirm: false
            }, function () {
            	$http
                .post('../admin/billType/delete', {id:id})
                .then(function(response) {
                	if(response.data.result==1){
                		SweetAlert.swal("","删除成功", "success");
                		init();
                	}else{
                		SweetAlert.swal("","删除失败", "error");
                	}
                }, function() {
             	   SweetAlert.swal("","系统错误", "error");
                });
            });
        };
    }]);