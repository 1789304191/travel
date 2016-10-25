/**
 * Created by jackShall on 2016/5/16.
 */
angular
    .module('app.permissions')
    .controller('pdauser-add-Ctrl',function($scope,$rootScope,$http,$interval,SweetAlert,$timeout,$filter){
    	$scope.pdadevice={};
    	$scope.$on("fileName",function(item,val){
      		 $scope.pdadevice.firstPic=val;
       	});
    	var url = '../pdauser/pdaUserGrid';
    	function addReadControl(data){
            for(var i=0;i<data.length;i++){
                data[i].readControl=true;
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
                angular.forEach($scope.dataTable.data, function(data,index,array){
                	$scope.dt = $filter("date")(data.createTime.time, "yyyy-MM-dd HH:mm:ss");
                	data.createTime=$scope.dt;
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
        $scope.confirm =function(pdauserForm){
           if($scope.pdauserForm.$invalid){
               $scope.pdauserForm.name.$dirty = true;
               $scope.pdauserForm.account.$dirty = true;
               $scope.pdauserForm.password.$dirty = true;
           }else{
        	   $http
               .post('../pdauser/add', {account: $scope.pdadevice.account, name:$scope.pdadevice.name, firstPic:$scope.pdadevice.firstPic, password:$scope.pdadevice.password})
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
    .controller('pdauser-update-Ctrl',function($scope,$rootScope,$http,SweetAlert){
        $scope.confirm =function(pdauserForm){
        	var url = '../pdauser/pdaUserGrid';
        	function addReadControl(data){
                 for(var i=0;i<data.length;i++){
                     data[i].readControl=true;
                 }
             };
             function  init(){
            	 $http.post(url, 
                 		{
                 		 pageSize:$scope.dataTable.pageSize,
                 	     pageNo:$scope.dataTable.currentPage}
                 ).success(function(data) {
                     $scope.dataTable.data = data.dataList;
                     $scope.dataTable.totalLength = data.total;
                     angular.forEach($scope.dataTable.data, function(data,index,array){
                     	$scope.dt = $filter("date")(data.createTime.time, "yyyy-MM-dd HH:mm:ss");
                     	data.createTime=$scope.dt;
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
            if($scope.pdauserForm.$invalid){
            	 $scope.pdauserForm.name.$dirty = true;
                 $scope.pdauserForm.account.$dirty = true;
                 $scope.pdauserForm.password.$dirty = true;
//                $scope.ModifyCustomerUserForm.account.$dirty = true;
//                $scope.ModifyCustomerUserForm.password.$dirty = true;
            }else{
         	   $http
         	   .post('../pdauser/update',{id: $scope.pdadevice.id ,account: $scope.pdadevice.account, name:$scope.pdadevice.name, firstPic:$scope.pdadevice.firstPic, password:$scope.pdadevice.password})
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
     
     .controller('pdauser-bind-Ctrl',function($scope,$rootScope,$http,SweetAlert,$timeout){
    	 $scope.bindCheckData=[];
         //表格数据
         $scope.bindTable = {
             data: [], 
             pageSize: 10,
             currentPage: 1,
             totalLength: 0
         };
         var url = '../admin/scenic-project/listAll';
         console.log($scope.bindItem.id);
         $http.get(url+"?pageSize="+$scope.bindTable.pageSize+"&pageNo="+$scope.bindTable.currentPage+"&pdaUserId="+$scope.bindItem.id).success(function(data){
	         $scope.bindTable.data = data.dataList;
	         $scope.bindTable.totalLength = data.total;
	         if(data.eventIds != null) {
	        	 $scope.bindCheckData = data.eventIds;
	         }
	         $timeout(function(){
	             var temp = document.querySelectorAll(".check-bind");
	             for(var i=0;i<$scope.bindCheckData.length;i++){
	            	 for(var j=0;j< $scope.bindTable.data.length;j++){
	            		 if($scope.bindCheckData[i]==$scope.bindTable.data[j].id){
	            			 temp[j].checked = true;
	            		 }
	            	 }
	             }
	         },30)
         });
         
         //绑定保存按钮
        $scope.confirm =function(){
           $http
       	   .post('../pdauser/bind',{eventId: $scope.bindCheckData ,pdaUserId: $scope.bindItem.id})
             .then(function(response) {
              	if(response.data.result==1){
              		SweetAlert.swal("","绑定成功", "success"); 
              	} 
              	 $scope.closeThisDialog(0);
              }, function() {
              });
           console.log($scope.bindItem.id);
           console.log($scope.bindCheckData);
        };
        
        var isAllCheck = false;
        //判断是否有选中
        function checkIsAll(){
        	var temp = document.querySelectorAll(".check-bind");
        	console.log(temp);
        	for(var i=0;i<temp.length;i++){
       		 if(temp[i].checked){
       			 isAllCheck = true;
       		 }else{
       			 isAllCheck =false;
       			 return;
       		 }
        	}
        };
        //全选
        $scope.allCheck = function(){
        	checkIsAll();
        	var temp = document.querySelectorAll(".check-bind");
        	if(isAllCheck){
        		angular.forEach(temp, function(data,index){
        			data.checked = false;
        		})
        	}else{
        		angular.forEach(temp, function(data,index){
        			data.checked = true;
        		})
        	}
         	angular.forEach(temp, function(data,index){
         		 var temp = $scope.bindTable.data[index].id;
          		 if(data.checked){
          			unique(temp);
          		 }else{
          			angular.forEach($scope.bindCheckData, function(item,ind){
                        if(item==temp){
                       	 $scope.bindCheckData.splice(ind, 1);
                        }
                    });
          		 }
                });
        };
        //复选框
        $scope.chooseCheck = function(event,id){
        	if(event.target.checked){
        		unique(id);
        	}else{
        		angular.forEach($scope.bindCheckData, function(data,index){
                     if(data==id){
                    	 $scope.bindCheckData.splice(index, 1);
                     }
                 });
        	}
        };
        //数组中添加值去重
        function unique(val){
        	if($scope.bindCheckData.indexOf(val)==-1){
        		$scope.bindCheckData.push(val);
        	}
        };
         //查询页数
         $scope.bindPageChanged =function(){ 
             $http.get(url+"?pageSize="+$scope.bindTable.pageSize+"&pageNo="+$scope.bindTable.currentPage).success(function (data) {
             	 $scope.bindTable.data = data.dataList;
                 $timeout(function(){
                     var temp = document.querySelectorAll(".check-bind");
                     for(var i=0;i<$scope.bindCheckData.length;i++){
                    	 for(var j=0;j< $scope.bindTable.data.length;j++){
                    		 if($scope.bindCheckData[i]==$scope.bindTable.data[j].id){
                    			 temp[j].checked = true;
                    		 }
                    	 }
                     }
                 },300)
             });
         };
         //前页数
         $scope.bindPageStart=function() {
             return (($scope.bindTable.currentPage - 1) * $scope.bindTable.pageSize) + 1;
         };
        //后页数
         $scope.bindPageEnd=function() {
             return ($scope.bindTable.currentPage - 1) * $scope.bindTable.pageSize + $scope.bindTable.data.length;
         };
     })
     
    .controller('PdauserCtrl', ['$scope','$http','ngDialog','RouteHelpers','SweetAlert','$filter','$timeout','$rootScope',function($scope, $http,ngDialog,RouteHelpers,SweetAlert,$filter,$timeout,$rootScope){
    	
        var url = '../pdauser/pdaUserGrid';
        $scope.tplPath = RouteHelpers.basepath('pda/pdaUser-modify-dialogForm.html');
        $scope.addPath = RouteHelpers.basepath('pda/pdaUser-add-dialogForm.html');
        $scope.bindPath = RouteHelpers.basepath('pda/pdaUser-bind-dialogForm.html');
        //表格数据
        $scope.dataTable = {
            data: [], 
            pageSize: 10,
            currentPage: 1,
            totalLength: 0
        };
        $scope.myKeyup = function(e){
            var keycode = window.event?e.keyCode:e.which;
            if(keycode==13){
            	$http.post(url, 
                		{account:$scope.query.account,
                		 name:$scope.query.name,
                		 employeeNo:$scope.query.employeeNo,
                		 pageSize:$scope.dataTable.pageSize,
                	     pageNo:$scope.dataTable.currentPage}
                ).success(function(data) {
                    $scope.dataTable.data = data.dataList;
                    $scope.dataTable.totalLength = data.total;
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
            }
        };
        $scope.search={
            "condition":""
        };
        //数据初始化
        function  init(){
        	$http.post(url, 
            		{
            		 pageSize:$scope.dataTable.pageSize,
            	     pageNo:$scope.dataTable.currentPage}
            ).success(function(data) {
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
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
        //前页数
        $scope.currentPageStart=function() {
            return (($scope.dataTable.currentPage - 1) * $scope.dataTable.pageSize) + 1;
        };
       //后页数
        $scope.currentPageEnd=function() {
            return ($scope.dataTable.currentPage - 1) * $scope.dataTable.pageSize + $scope.dataTable.data.length;
        };
        //绑定
        $scope.tableBind=function(index){
        	 ngDialog.openConfirm({
                 template: $scope.bindPath,
                 controller:function($scope){
                     $scope.bindItem = angular.copy($scope.dataTable.data[index]);
                 },
                 className: 'ngdialog-theme-default',
                 preCloseCallback: 'preCloseCallbackOnScope',
                 scope: $scope
             }).then(function (value) {});
        };
        
      //编辑
        $scope.tableEdit=function(index){
        	 ngDialog.openConfirm({
                 template: $scope.tplPath,
                 controller:function($scope){
                     $scope.pdadevice = angular.copy($scope.dataTable.data[index]);
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
                .post('../pdauser/delete', {id:id})
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
        //高级搜索
        $scope.superiorSearch=function(){
            $http.post(url, 
            		{account:$scope.query.account,
            		 name:$scope.query.name,
            		 employeeNo:$scope.query.employeeNo,
            		 pageSize:$scope.dataTable.pageSize,
            	     pageNo:$scope.dataTable.currentPage}
            ).success(function(data) {
                $scope.dataTable.data = data.dataList;
                $scope.dataTable.totalLength = data.total;
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
        //排序
        $scope.tableSort=function(item){
            console.log(item);
        };
        //查询页数
        $scope.pageChanged =function(){
            $http.post(url+"?pageSize="+$scope.dataTable.pageSize+"&pageNo="+$scope.dataTable.currentPage).success(function (data) {
            	 $scope.dataTable.data = data.dataList;
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
                console.log('Modal promise rejected. Reason: ', reason);
            });
        	$timeout(function(){
       		 var button = $(".dash");
       			$.ajax_upload(
       			   button,
       			   {
       				   data:{
       		          	 from:'pda'  	 
       		             },
       				   action: '../file/upload',
       				   name: 'file',
       				   onSubmit: function(file, extension) {
       				        if (!(extension && /^(jpg|jpeg|bmp|gif|png)$/.test(extension.toLowerCase()))) {
       				        	SweetAlert.swal("","请上传图片文件，文件格式为jpg、jpeg、bmp、gif、png", "error"); 
       		                        return false;
       		                    }
       				   },
       				   onComplete: function(file, data){
       					    var obj = eval("("+data+")"); 
       					    console.log(obj.fileName);
       					    $rootScope.$broadcast("fileName",obj.fileName);
       						$(".dash").attr("src",obj.path);
       					}
       			   }
       			
       			);
//           		 CKEDITOR.replace( 'content',
//           				    {
//           				        startupFocus : true,
//           				        toolbar :
//           				            [
//           				                ['ajaxsave'],
//           				                ['Bold', 'Italic', 'Underline', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink' ],
//           				                ['Cut','Copy','Paste','PasteText'],
//           				                ['Undo','Redo','-','RemoveFormat'],
//           				                ['TextColor','BGColor'],
//           				                ['Maximize', 'Image']
//           				            ],
//           				        filebrowserUploadUrl : '/notes/add/ajax/upload-inline-image/index.cfm'
//           				    }
//           				);
       	 },100);
            	
            };
        
 
        
    }]);