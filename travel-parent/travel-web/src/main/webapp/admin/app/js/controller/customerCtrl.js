/**
 * Created by jackShall on 2016/5/16.
 */
angular
    .module('app.tables')
    .controller('customer-update-Ctrl',function($scope,$rootScope,$http,SweetAlert,$filter){
		$scope.$on("id",function(item,val){
			$scope.customer.id=val;
		});
	 var url = '../admin/customer/customerGrid';
	 function  init(){
         $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage).success(function(data){
             $scope.dataTable.data = data.dataList;
             $scope.dataTable.totalLength = data.total;
             angular.forEach($scope.dataTable.data, function(data,index,array){
             	$scope.sdt = $filter("date")(data.createDatetime.time, "yyyy-MM-dd HH:mm:ss");
             	data.createDatetime=$scope.sdt;
             });
             addReadControl($scope.dataTable.data);
         });
     };
     function addReadControl(data){
         for(var i=0;i<data.length;i++){
             data[i].readControl=true;
         }
     };
    $scope.confirm =function(customerForm){
    	if($scope.customerForm.$invalid){
            $scope.customerForm.customerName.$dirty= true;
            $scope.customerForm.customerCode.$dirty = true;
        }else{
    	   $http
           .post('../admin/customer/update', {id:$scope.customer.id,name:$scope.customer.customerName,code:$scope.customer.customerCode,regionId:$scope.region.regionId})
           .then(function(response) {
           	if(response.data.result==1){
           		SweetAlert.swal("","修改成功", "success");
           		init();
           	}else{
           		SweetAlert.swal("",response.data.message, "error");
           	}
           }, function() {
        	   SweetAlert.swal("","系统错误", "error");
           });
           //关闭
           $scope.closeThisDialog(0);
       }
    };
}).controller('customer-add-Ctrl',function($scope,$rootScope,$http,SweetAlert,$filter){
	 var url = '../admin/customer/customerGrid';
	 function  init(){
         $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage).success(function(data){
             $scope.dataTable.data = data.dataList;
             $scope.dataTable.totalLength = data.total;
             angular.forEach($scope.dataTable.data, function(data,index,array){
             	$scope.sdt = $filter("date")(data.createDatetime.time, "yyyy-MM-dd HH:mm:ss");
             	data.createDatetime=$scope.sdt;
             });
             addReadControl($scope.dataTable.data);
         });
     };
     function addReadControl(data){
         for(var i=0;i<data.length;i++){
             data[i].readControl=true;
         }
     };
     $scope.confirm =function(customerForm){
     	if($scope.customerForm.$invalid){
             $scope.customerForm.customerName.$dirty= true;
             $scope.customerForm.customerCode.$dirty = true;
         }else{
     	   $http
            .post('../admin/customer/add', {name:$scope.customer.customerName,code:$scope.customer.customerCode,regionId:$scope.region.regionId})
            .then(function(response) {
            	if(response.data.result==1){
            		SweetAlert.swal("",response.data.message, "success");
            		init();
            	}else{
            		SweetAlert.swal("",response.data.message, "error");
            	}
            }, function() {
         	   SweetAlert.swal("","系统错误", "error");
            });
            //关闭
            $scope.closeThisDialog(0);
        }
     };
})
    .controller('CustomerCtrl', ['$scope','$http','ngDialog','RouteHelpers','SweetAlert','$rootScope','$filter',function($scope, $http,ngDialog,RouteHelpers,SweetAlert,$rootScope,$filter){
        var url = '../admin/customer/customerGrid';
        $scope.updatePath = RouteHelpers.basepath('customer/customer-update-dialogForm.html');
        $scope.addPath = RouteHelpers.basepath('customer/customer-add-dialogForm.html');
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
            $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage).success(function(data){
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
                angular.forEach($scope.dataTable.data, function(data,index,array){
                	$scope.sdt = $filter("date")(data.createDatetime.time, "yyyy-MM-dd HH:mm:ss");
                	data.createDatetime=$scope.sdt;
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
        //编辑
        $scope.tableEdit=function(index){
        	$http
    		.post('../admin/admin-area/regionSelectList')
    		.then(function(response) {
    			var obj ={};
    			var arr = response.data;
    			angular.forEach(arr,function(item){
	      		obj[item.regionId]=item.name;
	      	});
    		$scope.regions=obj;	
	      	$scope.region = {regionId:$scope.dataTable.data[index].regionId+''};
    		});
            ngDialog.openConfirm({
                template: $scope.updatePath,
                controller:function($scope){
                    $scope.customer = angular.copy($scope.dataTable.data[index]);
                    $scope.customer.customerName=$scope.dataTable.data[index].name;
                    $scope.customer.customerCode=$scope.dataTable.data[index].code;
                },
                className: 'ngdialog-theme-default',
                preCloseCallback: 'preCloseCallbackOnScope',
                scope: $scope
            }).then(function (value) {
                $scope.dataTable.data[index] = value;
            }, function (reason) {
            });
            $rootScope.$broadcast("id",$scope.dataTable.data[index].id);
        };
        //新增
        $scope.add=function(){
        	$http
    		.post('../admin/admin-area/regionSelectList')
    		.then(function(response) {
    			var obj ={};
    			var arr = response.data;
    			angular.forEach(arr,function(item){
	      		obj[item.regionId]=item.name;
	      	});
    		$scope.regions=obj;	
	      	$scope.region = {regionId:response.data[0].regionId+''};
    		});
        	ngDialog.openConfirm({
            template: $scope.addPath,
            className: 'ngdialog-theme-default',
            preCloseCallback: 'preCloseCallbackOnScope',
            scope: $scope
        }).then(function (form) {
        }, function (reason) {
        });
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
                 .post('../admin/customer/delete', {id:id})
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
        //保存
        $scope.tableSave = function(item,index){
            console.log(item);
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
            $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage).success(function (data) {
            	 $scope.dataTable.data = data.dataList;
            	 angular.forEach($scope.dataTable.data, function(data,index,array){
                 	$scope.sdt = $filter("date")(data.createDatetime.time, "yyyy-MM-dd HH:mm:ss");
                 	data.createDatetime=$scope.sdt;
                 });
                addReadControl($scope.dataTable.data);
            });
        };

    }]);