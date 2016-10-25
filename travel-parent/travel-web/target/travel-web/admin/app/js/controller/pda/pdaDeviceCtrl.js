/**
 * Created by jackShall on 2016/5/16.
 */
angular
    .module('app.permissions')
    .controller('pdadevice-add-Ctrl',function($scope,$rootScope,$http,SweetAlert){
        $scope.confirm =function(pdadeviceForm){
           if($scope.pdadeviceForm.$invalid){
               $scope.pdadeviceForm.deviceNo.$dirty = true;
           }else{
        	   $http
               .post('../pdadevice/add', {deviceNo: $scope.pdadevice.pdaDeviceNo})
               .then(function(response) {
               	if(response.data.result==1){
               		SweetAlert.swal("","添加成功", "success"); 
               	}
               	init();
               }, function() {
               });
               //关闭
               $scope.closeThisDialog(0);
           }
        };
        
        var url = '../pdadevice/pdaDeviceGrid';
        function addReadControl(data){
            for(var i=0;i<data.length;i++){
                data[i].readControl=true;
            }
        };
        function  init(){
            $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage).success(function(data){
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
    })
    .controller('pdadevice-update-Ctrl',function($scope,$rootScope,$http,SweetAlert){
        $scope.confirm =function(pdadeviceForm){
        	var url = '../pdadevice/pdaDeviceGrid';
        	function addReadControl(data){
                 for(var i=0;i<data.length;i++){
                     data[i].readControl=true;
                 }
             };
        	function  init(){
                $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage).success(function(data){
                    $scope.dataTable.data = data.dataList;
                    $scope.dataTable.totalLength = data.total;
                    addReadControl($scope.dataTable.data);
                    angular.forEach($scope.dataTable.data, function(data,index,array){
                    	if(data.status==1){
                    		data.check=true;
                    		data.status='删除'
                    	}else{
                    		data.check=false;
                    		data.status='正常'
                    	}
                     });
                });
            };
            if($scope.pdadeviceForm.$invalid){
            	$scope.pdadeviceForm.pdaDeviceNo.$dirty = true;
//                $scope.ModifyCustomerUserForm.account.$dirty = true;
//                $scope.ModifyCustomerUserForm.password.$dirty = true;
            }else{
         	   $http
         	   .post('../pdadevice/update',{id:$scope.pdadevice.id, deviceNo: $scope.pdadevice.pdaDeviceNo})
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
    .controller('PdadeviceCtrl', ['$scope','$http','ngDialog','RouteHelpers','SweetAlert','$timeout',function($scope, $http,ngDialog,RouteHelpers,SweetAlert,$timeout){
    	
        var url = '../pdadevice/pdaDeviceGrid';
//        var url = 'server/ng-table.json';
        $scope.tplPath = RouteHelpers.basepath('pda/pdaDevice-modify-dialogForm.html');
        $scope.addPath = RouteHelpers.basepath('pda/pdaDevice-add-dialogForm.html');
        //表格数据
        $scope.dataTable = {
            data: [], 
            pageSize: 10,
            currentPage: 1,
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
            ngDialog.openConfirm({
                template: $scope.tplPath,
                controller:function($scope){
                    $scope.pdadevice = angular.copy($scope.dataTable.data[index]);
                    $scope.pdadevice.pdaDeviceNo=$scope.dataTable.data[index].deviceNo;
                },
                className: 'ngdialog-theme-default',
                preCloseCallback: 'preCloseCallbackOnScope',
                scope: $scope
            }).then(function (value) {});
        };
        //删除
        $scope.tableDelete=function(id){
        console.log(id);
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
                .post('../pdadevice/delete', {id:id})
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
                addReadControl($scope.dataTable.data);
            });
        };
        //新增
        $scope.add=function(){
        	/*$http
    		.post('../customer/findCustomerNameList')
    		.then(function(response) {
    			var obj ={};
    			var arr = response.data;
    			console.log(arr);
    			angular.forEach(arr,function(item){
	      		obj[item.customerId]=item.name;
	      	});
    		console.log(obj);
	      	$scope.availableLocales=obj;
	      	$scope.customerUser = {customerId:'1'};
    	    });*/

	      	/*$http
     		.post('../role/findRoleList')
     		.then(function(response) {
     			var obj ={};
     			var arr = response.data;
     			console.log(arr);
     			angular.forEach(arr,function(item){
 	      		obj[item.roleId]=item.name;
 	      	});
     		console.log(obj);
 	      	$scope.roles=obj;
 	      	$scope.customerUse = {roleId:'1'};
     		});*/
 	      	
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