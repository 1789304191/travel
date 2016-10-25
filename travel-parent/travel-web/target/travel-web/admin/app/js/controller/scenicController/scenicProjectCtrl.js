/**
 * Created by jackShall on 2016/5/16.
 */
angular
    .module('app.tables')
    
    .controller('scenic_project_add_dialogFormCtrl',function($scope,$rootScope,$http,$interval,SweetAlert,$timeout,$filter){
    	$scope.scenicProject={};
    	$scope.$on("fileName",function(item,val){
   		 $scope.scenicProject.firstPic=val;
    	});
        $scope.confirm =function(scenicProjectForm){
           if($scope.scenicProjectForm.$invalid){
        	   $scope.scenicProjectForm.name.$dirty = true;
               $scope.scenicProjectForm.price.$dirty = true;
               $scope.scenicProjectForm.firstPic.$dirty = true;
               $scope.scenicProjectForm.description.$dirty = true;
               $scope.scenicProjectForm.startDatetime.$dirty = true;
               $scope.scenicProjectForm.endDatetime.$dirty = true;
           }else{
        	   $http
               .post('../admin/scenic-project/add', {name: $scope.scenicProject.name,
            	   							  price:$scope.scenicProject.price,
            	   							  firstPic:$scope.scenicProject.firstPic,
            	   							  description:$scope.scenicProject.description,
            	   							  startDatetime:$scope.scenicProject.startDatetime,
            	   							  endDatetime:$scope.scenicProject.endDatetime})
               .then(function(response) {
               	if(response.data.result==1){
               		SweetAlert.swal("","添加成功", "success");
               		init();
               	}else{
               		SweetAlert.swal("",response.data.message, "error");
               	}
               }, function() {
               });
               //关闭
               $scope.closeThisDialog(0);
           }
        };
        var url = '../admin/scenic-project/scenicProjectGrid';
        function init(){
        	$http
            .post(url,{pageSize:$scope.dataTable.pageSize,pageNo:$scope.dataTable.currentPage,name:$scope.query.name,
            	stime:$scope.query.stime,etime:$scope.query.etime,status:$scope.query.status})
            .then(function(response) {
            $scope.dataTable.data = response.data.dataList;
            $scope.dataTable.totalLength = response.data.total;
            angular.forEach($scope.dataTable.data, function(data,index,array){
                if(data.status==1){
             		data.check=true;
             		data.status='已发布'
             	}else{
             		data.check=false;
             		data.status='未发布'
             	}
              });
             addReadControl($scope.dataTable.data);
            });
        };
        function addReadControl(data){
            for(var i=0;i<data.length;i++){
                data[i].readControl=true;
            }
        };
    })
    .controller('scenic_project_update_dialogFormCtrl',function($scope,$rootScope,$http,$interval,SweetAlert,$timeout,$filter){
    	$scope.$on("fileName",function(item,val){
   		 	$scope.scenicProject.firstPic=val;
    	});
        $scope.confirm =function(scenicProjectForm){
           if($scope.scenicProjectForm.$invalid){
        	   $scope.scenicProjectForm.name.$dirty = true;
               $scope.scenicProjectForm.price.$dirty = true;
               $scope.scenicProjectForm.firstPic.$dirty = true;
               $scope.scenicProjectForm.description.$dirty = true;
               $scope.scenicProjectForm.startDatetime.$dirty = true;
               $scope.scenicProjectForm.endDatetime.$dirty = true;
           }else{
        	   $http
               .post('../admin/scenic-project/update', {id:$scope.scenicProject.id,
            	   							  name: $scope.scenicProject.name,
            	   							  price:$scope.scenicProject.price,
            	   							  firstPic:$scope.scenicProject.firstPic,
            	   							  description:$scope.scenicProject.description,
            	   							  startDatetime:$scope.scenicProject.startDatetime,
            	   							  endDatetime:$scope.scenicProject.endDatetime})
               .then(function(response) {
               	if(response.data.result==1){
               		SweetAlert.swal("","修改成功", "success");
               		init();
               	}else{
               		SweetAlert.swal("",response.data.message, "error");
               	}
               }, function() {
            	   SweetAlert.swal("","修改失败", "error");
            	   init();
               });
               //关闭
               $scope.closeThisDialog(0);
           }
        };
        var url = '../admin/scenic-project/scenicProjectGrid';
        function  init(){
        	$http
            .post(url,{pageSize:$scope.dataTable.pageSize,pageNo:$scope.dataTable.currentPage,name:$scope.query.name,
            	stime:$scope.query.stime,etime:$scope.query.etime,status:$scope.query.status})
            .then(function(response) {
            $scope.dataTable.data = response.data.dataList;
            $scope.dataTable.totalLength = response.data.total;
            angular.forEach($scope.dataTable.data, function(data,index,array){
                if(data.status==1){
             		data.check=true;
             		data.status='已发布'
             	}else{
             		data.check=false;
             		data.status='未发布'
             	}
              });
             addReadControl($scope.dataTable.data);
            });
        };
        function addReadControl(data){
            for(var i=0;i<data.length;i++){
                data[i].readControl=true;
            }
        };
        
    })
    .controller('ScenicProjectCtrl', function($scope, $http,ngDialog,RouteHelpers,SweetAlert,$filter,$timeout,$rootScope,$interval){
    	$scope.states={1:"已发布",2:"未发布"};
    	var url = '../admin/scenic-project/scenicProjectGrid';
        $scope.addPath = RouteHelpers.basepath('scenic/scenic-project-add-dialogForm.html');
        $scope.updatePath = RouteHelpers.basepath('scenic/scenic-project-update-dialogForm.html');
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
                if(data.status==1){
             		data.check=true;
             		data.status='已发布'
             	}else{
             		data.check=false;
             		data.status='未发布'
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
        //编辑
        $scope.tableEdit=function(index){
            ngDialog.openConfirm({
                template: $scope.updatePath,
                controller:function($scope){
                    $scope.scenicProject = angular.copy($scope.dataTable.data[index]);
                    var picture=$scope.dataTable.data[index].firstPic;
                    var submitData={
                    		firstPic:picture,
                    		from:'spot'
        	            };
                    var pic=null;
                    $.ajax({  
            	        url : '../file/getPic',
            	        async :false, 
            	        type : "POST",  
            	        dataType : "json", 
            	        data:submitData,
            	        success : function(result) {  
            	        	pic=result.path;
            	        }  
            	    });
                    $scope.firstPic=pic;
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
                closeOnConfirm: false
            }, function () {
            	$http
                .post('../admin/scenic-project/delete', {id:id})
                .then(function(response) {
               	if(response.data.result==1){
               		SweetAlert.swal("","删除成功", "success");
               	}else{
               		SweetAlert.swal("",response.data.message, "error");
               	}
               });
            	init();
            });
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
            .post(url,{pageSize:$scope.dataTable.pageSize,pageNo:$scope.dataTable.currentPage,name:$scope.query.name,
            	stime:$scope.query.stime,etime:$scope.query.etime,status:$scope.query.status})
            .then(function(response) {
            $scope.dataTable.data = response.data.dataList;
            $scope.dataTable.totalLength = response.data.total;
            angular.forEach($scope.dataTable.data, function(data,index,array){
                if(data.status==1){
             		data.check=true;
             		data.status='已发布'
             	}else{
             		data.check=false;
             		data.status='未发布'
             	}
              });
             addReadControl($scope.dataTable.data);
            });
        };
        //发布
        $scope.publish=function(id,check){
        	$scope.status=null;
        	if(check){
        		$scope.status=1;
        	}else{
        		$scope.status=2;
        	}
        	 $http
             .post('../admin/scenic-project/update', {id:id,status:$scope.status})
             .then(function(response) {
             	if(response.data.result==1){
             		init();
             	}else{
             		SweetAlert.swal("",response.data.message, "error");
             	}
             }, function() {
          	   SweetAlert.swal("","操作失败", "error");
             });
        }
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
        };
        
        $scope.query={};
        $scope.superior=function(query){
        	var json=$scope.query;
        	$scope.query=deleteJson(json);
//        	if(jQuery.isEmptyObject($scope.query)){
//        		SweetAlert.swal("",'查询条件不能为空', "error");
//        	}else{
    		$http
            .post('../admin/scenic-project/scenicProjectGrid',$scope.query)
            .then(function(response) {
            	$scope.dataTable.data = response.data.dataList;
                $scope.dataTable.totalLength = response.data.total;
                angular.forEach($scope.dataTable.data, function(data,index,array){
                    if(data.status==1){
                 		data.check=true;
                 		data.status='已发布'
                 	}else{
                 		data.check=false;
                 		data.status='未发布'
                 	}
                  });
                 addReadControl($scope.dataTable.data);
            }, function() {
         	   SweetAlert.swal("","操作失败", "error");
            });
    	}
//        }
        
        //删除数据
        function deleteJson(json)
        {
        	for(var x in json){
        		if(json[x]==''||json[x]==null){
        			delete json[x];
        		}
        	}
            return json;
        }
        
    });