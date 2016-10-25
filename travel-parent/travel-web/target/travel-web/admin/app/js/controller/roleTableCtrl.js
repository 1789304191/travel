/**
 * Created by jackShall on 2016/5/16.
 */
angular
    .module('app.tables')
     .controller('roleTable-add-Ctrl',function($scope,$rootScope,$http,SweetAlert,$filter){
    	 var url = '../admin/role_table/roleGrid';
    	 function  init(){
             $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage).success(function(data){
                 $scope.dataTable.data = data.dataList;
                 $scope.dataTable.totalLength = data.total;
                 addReadControl($scope.dataTable.data);
                 angular.forEach($scope.dataTable.data, function(data,index,array){
                 	$scope.dt = $filter("date")(data.createDatetime.time, "yyyy-MM-dd HH:mm:ss");
                 	data.createDatetime=$scope.dt;
                 });
             });
         };
         function addReadControl(data){
             for(var i=0;i<data.length;i++){
                 data[i].readControl=true;
             }
         };
        $scope.confirm =function(roleForm){
           if($scope.roleForm.$invalid){
               $scope.roleForm.roleName.$dirty= true;
               $scope.roleForm.description.$dirty = true;
           }else{
        	   $http
               .post('../admin/role_table/add', {name:$scope.role.roleName, description:$scope.role.description})
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
    .controller('roleTable-update-Ctrl',function($scope,$rootScope,$http,SweetAlert,$filter){
		$scope.$on("id",function(item,val){
		  		 $scope.role.id=val;
		});
   	 var url = '../admin/role_table/roleGrid';
	 function  init(){
         $http.get(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage).success(function(data){
             $scope.dataTable.data = data.dataList;
             $scope.dataTable.totalLength = data.total;
             addReadControl($scope.dataTable.data);
             angular.forEach($scope.dataTable.data, function(data,index,array){
             	$scope.dt = $filter("date")(data.createDatetime.time, "yyyy-MM-dd HH:mm:ss");
             	data.createDatetime=$scope.dt;
             });
         });
     };
     function addReadControl(data){
         for(var i=0;i<data.length;i++){
             data[i].readControl=true;
         }
     };
    $scope.confirm =function(roleForm){
       if($scope.roleForm.$invalid){
           $scope.roleForm.roleName.$dirty= true;
           $scope.roleForm.description.$dirty = true;
       }else{
    	   $http
           .post('../admin/role_table/update', {id:$scope.role.id,name:$scope.role.roleName, description:$scope.role.description})
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
    .controller('RoleTableCtrl', ['$scope','$http','ngDialog','RouteHelpers','SweetAlert','$filter','$rootScope','$timeout',function($scope, $http,ngDialog,RouteHelpers,SweetAlert,$filter,$rootScope,$timeout){
        var url = '../admin/role_table/roleGrid';
        $scope.tplPath = RouteHelpers.basepath('roleTable/roleTable-permission-dialogForm.html');
        $scope.addPath = RouteHelpers.basepath('roleTable/roleTable-add-dialogForm.html');
        $scope.editPath = RouteHelpers.basepath('roleTable/roleTable-update-dialogForm.html');
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
                addReadControl($scope.dataTable.data);
                angular.forEach($scope.dataTable.data, function(data,index,array){
                	$scope.dt = $filter("date")(data.createDatetime.time, "yyyy-MM-dd HH:mm:ss");
                	data.createDatetime=$scope.dt;
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
        //分配权限
        $scope.authority=function(index){
            ngDialog.openConfirm({
                template: $scope.tplPath,
                controller:function($scope){
                    $scope.userAdmin = angular.copy($scope.dataTable.data[index]);
                },
                className: 'ngdialog-theme-default',
                preCloseCallback: 'preCloseCallbackOnScope',
                scope: $scope
            }).then(function (value) {
            	$scope.codes="";
            	angular.forEach(selectNodes, function(data,index,array){
            		$scope.codes+=(data.id+",");
            	});
            	if($scope.codes!=""){
            		$scope.codes=$scope.codes.substring(0,$scope.codes.length-1);
            	}
            	$http
                .post('../admin/privilege/setPrivilege', {id:index.id, codes:$scope.codes})
                .then(function(response) {
                	if(response.data.result==1){
                		SweetAlert.swal("","设置成功", "success"); 
                		init();
                	}else{
                		SweetAlert.swal("","设置失败", "error");
                	}
                }, function() {
                    vm.authMsg = 'Server Request Error';
                });
            }, function (reason) {
            });
            $timeout(function(){
        	var setting = {
        			view: {
        				selectedMulti: false
        			},
        			check: {
        				enable: true
        			},
        			data: {
        				simpleData: {
        					enable: true
        				}
        			},
        			callback: {
        				onCheck: onCheck
        			}
        		};
        		
        		var zNodes=[];
        	
        		var submitData={
        	                id:index.id,
        	                code:index.privilegeCodes
        	            };
        		var stamp=new Date().getTime();
        		$.ajax({  
        	        url : '../admin/privilege/privilegeGrid?timestamp='+stamp,  
        	        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
        	        type : "POST",  
        	        dataType : "json", 
        	        data:submitData,
        	        success : function(result) {  
        	        	zNodes=result;
        	        }  
        	    });  
        		var clearFlag = false;
        		function onCheck(e, treeId, treeNode) {
        			count();
        			if (clearFlag) {
        				clearCheckedOldNodes();
        			}
        		}
        		function clearCheckedOldNodes() {
        			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        			nodes = zTree.getChangeCheckedNodes();
        			for (var i=0, l=nodes.length; i<l; i++) {
        				nodes[i].checkedOld = nodes[i].checked;
        			}
        		}
        		function count() {
        			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        			checkCount = zTree.getCheckedNodes(true).length,
        			nocheckCount = zTree.getCheckedNodes(false).length,
        			changeCount = zTree.getChangeCheckedNodes().length;
        			$("#checkCount").text(checkCount);
        			$("#nocheckCount").text(nocheckCount);
        			$("#changeCount").text(changeCount);
        			selectNodes=zTree.getCheckedNodes(true);
        			
        		};
        		function createTree() {
        			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
        			count();
        			clearFlag = $("#last").attr("checked");
        		}
    			createTree();			
    			$("#init").bind("change", createTree);
    			$("#last").bind("change", createTree);
            },50);
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
                .post('../admin/role_table/delete', {id:id})
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
                    $scope.role = angular.copy($scope.dataTable.data[index]);
                    $scope.role.roleName=$scope.dataTable.data[index].name;
                },
                className: 'ngdialog-theme-default',
                preCloseCallback: 'preCloseCallbackOnScope',
                scope: $scope
            });
            $rootScope.$broadcast("id",$scope.dataTable.data[index].id);
        };

    }]);