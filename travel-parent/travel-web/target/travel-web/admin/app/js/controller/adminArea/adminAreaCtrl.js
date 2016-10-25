/**
 * Created by jackShall on 2016/5/16.
 */
angular
    .module('app.tables')
     .controller('adminArea-add-Ctrl',function($scope,$rootScope,$http,SweetAlert,$filter){
    	 var url = '../admin/admin-area/areaGrid';
    	 function  init(){
             $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&name="+$scope.search.condition).success(function(data){
                 $scope.dataTable.data = data.dataList;
                 $scope.dataTable.totalLength = data.total;
                 addReadControl($scope.dataTable.data);
             });
         };
         function addReadControl(data){
             for(var i=0;i<data.length;i++){
                 data[i].readControl=true;
             }
         };
        $scope.confirm =function(adminAreaForm){
           if($scope.adminAreaForm.$invalid){
               $scope.adminAreaForm.areaName.$dirty= true;
               $scope.adminAreaForm.sort.$dirty = true;
           }else{
        	   $http
               .post('../admin/admin-area/add', {name:$scope.adminArea.areaName, sort:$scope.adminArea.sort})
               .then(function(response) {
               	if(response.data.result==1){
               		SweetAlert.swal("","添加成功", "success"); 
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
    .controller('adminArea-update-Ctrl',function($scope,$rootScope,$http,SweetAlert,$filter){
		$scope.$on("id",function(item,val){
			$scope.adminArea.id=val;
		});
   	 var url = '../admin/admin-area/areaGrid';
	 function  init(){
         $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&name="+$scope.search.condition).success(function(data){
             $scope.dataTable.data = data.dataList;
             $scope.dataTable.totalLength = data.total;
             addReadControl($scope.dataTable.data);
         });
     };
     function addReadControl(data){
         for(var i=0;i<data.length;i++){
             data[i].readControl=true;
         }
     };
    $scope.confirm =function(adminAreaForm){
    	if($scope.adminAreaForm.$invalid){
            $scope.adminAreaForm.areaName.$dirty= true;
            $scope.adminAreaForm.sort.$dirty = true;
        }else{
    	   $http
           .post('../admin/admin-area/update', {id:$scope.adminArea.id,name:$scope.adminArea.areaName, sort:$scope.adminArea.sort})
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
})
    .controller('adminAreaCtrl', ['$scope','$http','ngDialog','RouteHelpers','SweetAlert','$filter','$rootScope',function($scope, $http,ngDialog,RouteHelpers,SweetAlert,$filter,$rootScope){
        var url = '../admin/admin-area/areaGrid';
        $scope.addPath = RouteHelpers.basepath('adminArea/adminArea-add-dialogForm.html');
        $scope.editPath = RouteHelpers.basepath('adminArea/adminArea-update-dialogForm.html');
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
            $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&name="+$scope.search.condition).success(function(data){
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
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
                .post('../admin/admin-area/delete', {id:id})
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
        $scope.searchCommit=function(){
        	$http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&name="+$scope.search.condition).success(function (data) {
              	 $scope.dataTable.data = data.dataList;
              	 $scope.dataTable.totalLength = data.total;
              	 angular.forEach($scope.dataTable.data, function(data,index,array){
                   	$scope.dt = $filter("date")(data.createDatetime.time, "yyyy-MM-dd HH:mm:ss");
                   	data.createDatetime=$scope.dt;
                   });
                addReadControl($scope.dataTable.data);
              });
        };
        //查找
        $scope.searchChange=function(){
        	$http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&name="+$scope.search.condition).success(function (data) {
           	 $scope.dataTable.data = data.dataList;
           	 $scope.dataTable.totalLength = data.total;
           	 angular.forEach($scope.dataTable.data, function(data,index,array){
                	$scope.dt = $filter("date")(data.createDatetime.time, "yyyy-MM-dd HH:mm:ss");
                	data.createDatetime=$scope.dt;
                });
             addReadControl($scope.dataTable.data);
           });
        };
        //排序
        $scope.tableSort=function(item){
            console.log(item);
        };
        //查询页数
        $scope.pageChanged =function(){
            $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&name="+$scope.search.condition).success(function (data) {
            	 $scope.dataTable.data = data.dataList;
            	 angular.forEach($scope.dataTable.data, function(data,index,array){
                 	$scope.dt = $filter("date")(data.createDatetime.time, "yyyy-MM-dd HH:mm:ss");
                 	data.createDatetime=$scope.dt;
                 });
                addReadControl($scope.dataTable.data);
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
                	$scope.adminArea = angular.copy($scope.dataTable.data[index]);
                	$scope.adminArea.areaName =$scope.dataTable.data[index].name;
                },
                className: 'ngdialog-theme-default',
                preCloseCallback: 'preCloseCallbackOnScope',
                scope: $scope
            });
            $rootScope.$broadcast("id",$scope.dataTable.data[index].id);
        };

    }]);