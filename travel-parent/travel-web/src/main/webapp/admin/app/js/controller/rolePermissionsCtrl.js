/**
 * Created by jackShall on 2016/5/16.
 */
angular
    .module('app.permissions')
    .controller('rolePermission-add-Ctrl',function($scope,$rootScope,$http,SweetAlert){
    	var url = '../admin/menu/menuGrid';
    	 //数据初始化
        function  init(){
            $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&text="+$scope.search.condition).success(function(data){
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
        $scope.confirm =function(permissionForm){
           if($scope.PermissionForm.$invalid){
               $scope.PermissionForm.text.$dirty = true;
               $scope.PermissionForm.sref.$dirty = true;
           }else{
        	   console.log($scope.permissionForm);
        	   $http
               .post('../admin/menu/add', {pId: $scope.permissionForm.id, text:$scope.permissionForm.text,sref:$scope.permissionForm.sref,icon:$scope.permissionForm.icon})
               .then(function(response) {
               	if(response.data.result==1){
               		SweetAlert.swal("","添加成功", "success"); 
               		init();
               	}
               }, function() {
               });
               //关闭
               $scope.closeThisDialog(0);
           }
        };
    })
    .controller('rolePermission-update-Ctrl',function($scope,$rootScope,$http,SweetAlert){
    	if($scope.search.condition==undefined){
    		$scope.search.condition='';
    	}
    	var url = '../admin/menu/menuGrid';
    	 //数据初始化
        function  init(){
            $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&text="+$scope.search.condition).success(function(data){
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
                addReadControl($scope.dataTable.data);
            });
        };
        //是否可编辑
        function addReadControl(data){
            for(var i=0;i<data.length;i++){
                data[i].readControl=true;
            }
        };
        $scope.confirm =function(permissionForm){
           if($scope.rolePermissionForm.$invalid){
               $scope.rolePermissionForm.text.$dirty = true;
               $scope.rolePermissionForm.sref.$dirty = true;
           }else{
               $http
               .post('../admin/menu/update',{id:$scope.permissionForm.id, text:$scope.permissionForm.text,sref:$scope.permissionForm.sref,icon:$scope.permissionForm.icon})
               .then(function(response) {
            		if(response.data.result==1){
                   		SweetAlert.swal("",response.data.message, "success"); 
                   		init();
                   	}else{
                   		SweetAlert.swal("",response.data.message, "error");
                   	}
               }, function() {
               			SweetAlert.swal("","修改失败", "error"); 
               });
               //关闭
               $scope.closeThisDialog(0);
           }
        };
    })
    .controller('RolePermissionsCtrl', ['$scope','$http','ngDialog','RouteHelpers','SweetAlert',function($scope, $http,ngDialog,RouteHelpers,SweetAlert){
    	var url = '../admin/menu/menuGrid';
    	$scope.modifyPath = RouteHelpers.basepath('partials/rolePermission-modify-dialogForm.html');
        $scope.addPath = RouteHelpers.basepath('partials/rolePermission-add-dialogForm.html');
        $scope.dataTable = {
            data: [],
            currentPage: 1,
            pageSize: 10,
            totalLength: 0
        };
        //数据初始化
        function  init(){
            $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage).success(function(data){
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
        //编辑
        $scope.tableEdit=function(index){
            ngDialog.openConfirm({
                template: $scope.modifyPath,
                controller:function($scope){
                    $scope.permissionForm = angular.copy($scope.dataTable.data[index]);
                },
                className: 'ngdialog-theme-default',
                preCloseCallback: 'preCloseCallbackOnScope',
                scope: $scope
            })
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
                closeOnConfirm: true
            }, function () {
            	 $http
         		.post('../admin/menu/delete',{id:id})
         		.then(function(response) {
         			if(!response.data.isError){
                		SweetAlert.swal("","删除成功", "success");
                		init();
                	}
         	     }, function() {  
         		    SweetAlert.swal("","删除失败", "error"); 
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
        	$http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&text="+$scope.search.condition).success(function(data){
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
                addReadControl($scope.dataTable.data);
            });
        };
        //查找
        $scope.search={condition:''};
        $scope.searchChange=function(search){
            $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&text="+$scope.search.condition).success(function(data){
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
                addReadControl($scope.dataTable.data);
            });
            
        };
        //排序
        $scope.tableSort=function(item){
            console.log(item);
        };
        //查询页数
        $scope.pageChanged =function(){
        	console.log("search for"+$scope.search);
            $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&text="+$scope.search.condition).success(function (data) {
            	 $scope.dataTable.data = data.dataList;
                addReadControl($scope.dataTable.data);
            });
        };
        //新增
        $scope.add=function(){
        	$http
    		.post('../admin/privilege/privilegesAll')
    		.then(function(response) {
    			var obj ={};
    			obj={'0':'一级菜单'}
    			var arr = response.data;
    			angular.forEach(arr,function(item){
	      		obj[item.id]=item.text;
	      	});
	      	$scope.availableLocales=obj;
	      	$scope.permissionForm = {id:'0'};
    	}, function() {
      });
        ngDialog.openConfirm({
                 template: $scope.addPath,
                 className: 'ngdialog-theme-default',
                 preCloseCallback: 'preCloseCallbackOnScope',
                 scope: $scope
             })
        };

    }]);