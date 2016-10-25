/**
 * Created by jackShall on 2016/5/16.
 */
angular
    .module('app.permissions')
    .controller('CustomerUserValidateFormCtrl',function($scope,$rootScope,$http,SweetAlert){
    	var url = '../admin/customerUser/customerUserGrid';
    	function addReadControl(data){
             for(var i=0;i<data.length;i++){
                 data[i].readControl=true;
             }
         };
    	function  init(){
            $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&name="+$scope.search.condition).success(function(data){
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
                addReadControl($scope.dataTable.data);
                angular.forEach($scope.dataTable.data, function(data,index,array){
                	if(data.status==0){
                		data.check=true;
                		data.status='删除'
                	}else{
                		data.check=false;
                		data.status='正常'
                	}
                 });
            });
        };
        $scope.confirm =function(CustomerUserForm){
           if($scope.CustomerUserForm.$invalid){
               $scope.CustomerUserForm.nickName.$dirty = true;
               $scope.CustomerUserForm.account.$dirty = true;
               $scope.CustomerUserForm.password.$dirty = true;
           }else{
        	   $http
               .post('../admin/customerUser/addCustomerUser', {nickName: $scope.customerUser.nickName, account:$scope.customerUser.account,password:$scope.customerUser.password,customerId:$scope.customerUser.customerId,roleId:$scope.customerUse.roleId,tel:$scope.customerUser.tel,email:$scope.customerUser.email})
               .then(function(response) {
               	if(response.data.result==1){
               		SweetAlert.swal("","添加成功", "success"); 
               	}
               }, function() {
            	   SweetAlert.swal("","添加失败", "success");
               });
               //关闭
               $scope.closeThisDialog(0);
           }
        };
    })
    .controller('ModifyCustomerUserValidateFormCtrl',function($scope,$rootScope,$http,SweetAlert){
        $scope.confirm =function(ModifyCustomerUserForm){
        	var url = '../admin/customerUser/customerUserGrid';
        	function addReadControl(data){
                 for(var i=0;i<data.length;i++){
                     data[i].readControl=true;
                 }
             };
        	function  init(){
                $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&name="+$scope.search.condition).success(function(data){
                    $scope.dataTable.data = data.dataList;
                    $scope.dataTable.totalLength = data.total;
                    addReadControl($scope.dataTable.data);
                    angular.forEach($scope.dataTable.data, function(data,index,array){
                    	if(data.status==0){
                    		data.check=true;
                    		data.status='删除'
                    	}else{
                    		data.check=false;
                    		data.status='正常'
                    	}
                     });
                });
            };
            if($scope.ModifyCustomerUserForm.$invalid){
                $scope.ModifyCustomerUserForm.tel.$dirty = true;
                $scope.ModifyCustomerUserForm.email.$dirty = true;
//                $scope.ModifyCustomerUserForm.account.$dirty = true;
//                $scope.ModifyCustomerUserForm.password.$dirty = true;
            }else{
         	   $http
         	   .post('../admin/customerUser/updateCustomerUser',{id:$scope.customerUser.id, nickName: $scope.customerUser.nickName, account:$scope.customerUser.account,password:$scope.customerUser.password,customerId:$scope.customer.customerId,roleId:$scope.role.roleId,tel:$scope.customerUser.tel,email:$scope.customerUser.email})
               .then(function(response) {
                	if(response.data.result==1){
                		init();
                		SweetAlert.swal("","修改成功", "success"); 
                	}
                }, function() {
                });
                //关闭
                $scope.closeThisDialog(0);
            }
         };
     })
    .controller('CustomerUserCtrl', ['$scope','$http','ngDialog','RouteHelpers','SweetAlert','$timeout',function($scope, $http,ngDialog,RouteHelpers,SweetAlert,$timeout){
    	
        var url = '../admin/customerUser/customerUserGrid';
//        var url = 'server/ng-table.json';
        $scope.tplPath = RouteHelpers.basepath('partials/customerUser-modify-dialogForm.html');
        $scope.addPath = RouteHelpers.basepath('partials/customerUser-add-dialogForm.html');
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
                angular.forEach($scope.dataTable.data, function(data,index,array){
                	if(data.status==0){
                		data.check=true;
                		data.status='删除'
                	}else{
                		data.check=false;
                		data.status='正常'
                	}
                 });
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
    		.post('../admin/customer/findCustomerNameList')
    		.then(function(response) {
    			var obj ={};
    			var arr = response.data;
    			angular.forEach(arr,function(item){
	      		obj[item.customerId]=item.name;
	      	});
	      	$scope.availableLocales=obj;
	      	$scope.customer = {customerId:$scope.dataTable.data[index].customerId+''};
    	    });
        	$http
     		.post('../admin/role_table/findRoleList')
     		.then(function(response) {
     			var obj ={};
     			var arr = response.data;
     			angular.forEach(arr,function(item){
 	      		obj[item.roleId]=item.name;
 	      	});
 	      	$scope.roles=obj;
 	      	$scope.role= {roleId:$scope.dataTable.data[index].roleId+''};
     		});
 	      	
            ngDialog.openConfirm({
                template: $scope.tplPath,
                controller:function($scope){
                    $scope.customerUser = angular.copy($scope.dataTable.data[index]);
                    $scope.customerUser.tel=parseInt($scope.customerUser.tel);
                    $scope.customerUser.nickName=$scope.customerUser.name;
                },
                className: 'ngdialog-theme-default',
                preCloseCallback: 'preCloseCallbackOnScope',
                scope: $scope
            }).then(function (value) {});
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
                .post('../admin/customerUser/deleteCustomerUser', {id:id})
                .then(function(response) {
               	if(response.data.result==1){
               		SweetAlert.swal("","删除成功", "success"); 
               		init();
               	}
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
        	$http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&name="+$scope.search.condition).success(function(data){
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
                addReadControl($scope.dataTable.data);
            });
        };
        //查找
        $scope.searchChange=function(){
        	$http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&name="+$scope.search.condition).success(function(data){
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
        $scope.search={condition:''};
        $scope.pageChanged =function(){
            $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage+"&name="+$scope.search.condition).success(function (data) {
            	$scope.dataTable.data = data.dataList;
                addReadControl($scope.dataTable.data);
            });
        };
        //新增
        $scope.add=function(){
        	$http
    		.post('../admin/customer/findCustomerNameList')
    		.then(function(response) {
    			var obj ={};
    			var arr = response.data;
    			angular.forEach(arr,function(item){
	      		obj[item.customerId]=item.name;
	      	});
	      	$scope.availableLocales=obj;
	      	$scope.customerUser = {customerId:'1'};
    	    });

	      	$http
     		.post('../admin/role_table/findRoleList')
     		.then(function(response) {
     			var obj ={};
     			var arr = response.data;
     			angular.forEach(arr,function(item){
 	      		obj[item.roleId]=item.name;
 	      	});
 	      	$scope.roles=obj;
 	      	$scope.customerUse = {roleId:'1'};
     		});
 	      	
        	 ngDialog.openConfirm({
                 template: $scope.addPath,
//                 controller:function($scope){
//                     $scope.userAdmin = angular.copy($scope.dataTable.data[index]);
//                 },
                 className: 'ngdialog-theme-default',
                 preCloseCallback: 'preCloseCallbackOnScope',
                 scope: $scope
             }).then(function (form) {
//            	 console.log($scope);
//            	 $scope.submitted = true;
//                 if ($scope.formValidate.$valid) {
//                 	 $http
//                      .post('../menu/menuAdd', {pId: form.id, text:form.text,sref:form.sref,icon:form.icon})
//                      .then(function(response) {
//                      	if(response.data.result==1){
//                      		SweetAlert.swal("","添加成功", "success"); 
//                      	}
//                      }, function() {
//                    	  $scope.authMsg = 'Server Request Error';
//                      });
//                     console.log('Submitted!!');
//                 } else {
//                     console.log('Not valid!!');
//                     return false;
//                 }
//                 console.log('Modal promise resolved. Value: ', value);
             }, function (reason) {
                 console.log('Modal promise rejected. Reason: ', reason);
             });
        };
        
 
        
    }]);