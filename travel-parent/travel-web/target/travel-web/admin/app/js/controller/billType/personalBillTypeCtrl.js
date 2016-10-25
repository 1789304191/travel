/**
 * Created by jackShall on 2016/5/16.
 */
angular
		.module('app.tables')
		// 新增弹出框
		.controller(
				'personalBillType-add-dialogFormCtrl',
				function($scope, $rootScope, $http, $interval, SweetAlert,
						$timeout, $filter) {
					var url = '../admin/personalBillType/personalBillTypeGrid';
					// 数据初始化
					function init() {
						$http.post(url, {
							pageSize : $scope.dataTable.pageSize,
							pageNo : $scope.dataTable.currentPage
						}).success(function(data) {
							$scope.dataTable.data = data.dataList;
							$scope.dataTable.totalLength = data.total;
						});
					};
					$scope.confirm = function(personalBillTypeForm) {
						if ($scope.personalBillTypeForm.$invalid) {
							$scope.personalBillTypeForm.compay.$dirty = true;
							$scope.personalBillTypeForm.amount.$dirty = true;
						} else {
							$http.post('../admin/personalBillType/add', {
								compay : $scope.personalBillType.compay,
								amount : $scope.personalBillType.amount
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

		// 修改
		.controller(
				'personalBillType-update-dialogFormCtrl',
				function($scope, $rootScope, $http, $interval, SweetAlert,
						$timeout, $filter) {
					var url = '../admin/personalBillType/personalBillTypeGrid';
					// 数据初始化
					function init() {
						$http.post(url, {
							pageSize : $scope.dataTable.pageSize,
							pageNo : $scope.dataTable.currentPage
						}).success(function(data) {
							$scope.dataTable.data = data.dataList;
							$scope.dataTable.totalLength = data.total;
						});
					}
					;
					$scope.$on("id", function(item, val) {
						$scope.personalBillType.id = val;
					});
					$scope.confirm = function(personalBillTypeForm) {
						if ($scope.personalBillTypeForm.$invalid) {
							$scope.personalBillTypeForm.compay.$dirty = true;
							$scope.personalBillTypeForm.amount.$dirty = true;
						} else {
							$http.post('../admin/personalBillType/update', {
								id : $scope.personalBillType.id,
								compay : $scope.personalBillType.compay,
								amount : $scope.personalBillType.amount,
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

		.controller(
				'PersonalBillTypeCtrl',
				[
						'$scope',
						'$http',
						'ngDialog',
						'RouteHelpers',
						'SweetAlert',
						'$filter',
						'$rootScope',
						function($scope, $http, ngDialog, RouteHelpers,
								SweetAlert, $filter, $rootScope) {
							var url = '../admin/personalBillType/personalBillTypeGrid';
							$scope.addPath = RouteHelpers
									.basepath('billType/personalBillType-add-dialogForm.html');
							$scope.editPath = RouteHelpers
									.basepath('billType/personalBillType-update-dialogForm.html');
							// 表格数据
							$scope.dataTable = {
								data : [],
								currentPage : 1,
								pageSize : 10,
								totalLength : 0,
								amountMoney : 0
							};

							// 数据初始化
							function init() {
								$http.post(url, {
									pageSize : $scope.dataTable.pageSize,
									pageNo : $scope.dataTable.currentPage
								}).success(function(data) {
									$scope.dataTable.data = data.dataList;
									$scope.dataTable.totalLength = data.total;
								});
							}
							;
							init();

							// 前页数
							$scope.currentPageStart = function() {
								if ($scope.dataTable.totalLength == 0) {
									return 0;
								}
								return (($scope.dataTable.currentPage - 1) * $scope.dataTable.pageSize) + 1;
							};
							// 后页数
							$scope.currentPageEnd = function() {
								return ($scope.dataTable.currentPage - 1)
										* $scope.dataTable.pageSize
										+ $scope.dataTable.data.length;
							};
							// 总条数
							$scope.totalNum = function() {
								return $scope.dataTable.totalLength;
							};
							// 搜索
							$scope.searchFuc = function() {
								$http
										.post(
												url,
												{
													key : $scope.query.key,
													pageSize : $scope.dataTable.pageSize,
													pageNo : $scope.dataTable.currentPage
												})
										.success(
												function(data) {
													$scope.dataTable.data = data.dataList;
													$scope.dataTable.totalLength = data.total;
													angular
															.forEach(
																	$scope.dataTable.data,
																	function(
																			data,
																			index,
																			array) {
																		if (data.status == 0) {
																			data.check = true;
																			data.status = '已删除'
																		}
																		if (data.status == 1) {
																			data.check = true;
																			data.status = '正常'
																		}
																	});
												});
							};
							// 序号
							$scope.serialNumber = function(item) {
								return ($scope.dataTable.currentPage - 1)
										* $scope.dataTable.pageSize + item
							};
							// 查询页数
							$scope.pageChanged = function() {
								$http
										.post(
												url,
												{
													key : $scope.query.key,
													pageSize : $scope.dataTable.pageSize,
													pageNo : $scope.dataTable.currentPage
												})
										.success(
												function(data) {
													$scope.dataTable.data = data.dataList;
													$scope.dataTable.totalLength = data.total;
													angular
															.forEach(
																	$scope.dataTable.data,
																	function(
																			data,
																			index,
																			array) {
																		if (data.status == 0) {
																			data.check = true;
																			data.status = '已删除'
																		}
																		if (data.status == 1) {
																			data.check = true;
																			data.status = '正常'
																		}
																	});
												});
							};

							// 新增
							$scope.add = function() {
								ngDialog
										.openConfirm(
												{
													template : $scope.addPath,
													className : 'ngdialog-theme-default',
													preCloseCallback : 'preCloseCallbackOnScope',
													scope : $scope
												}).then(function(form) {
										}, function(reason) {
										});
							};
							// 编辑
							$scope.tableEdit = function(index) {
								ngDialog
										.openConfirm({
											template : $scope.editPath,
											controller : function($scope) {
												$scope.personalBillType = angular
														.copy($scope.dataTable.data[index]);
												$scope.personalBillType.compay = $scope.dataTable.data[index].compay;
												$scope.personalBillType.amount = $scope.dataTable.data[index].amount;
												console.log($scope.personalBillType.amount);
											},
											className : 'ngdialog-theme-default',
											preCloseCallback : 'preCloseCallbackOnScope',
											scope : $scope
										});
								$rootScope.$broadcast("id",
										$scope.dataTable.data[index].id);
							};
							// 删除
							$scope.tableDelete = function(id) {
								swal({
									title : '你确定要删除吗？',
									type : 'warning',
									showCancelButton : true,
									cancelButtonText : '取消',
									confirmButtonColor : '#DD6B55',
									confirmButtonText : '确定',
									closeOnConfirm : false
								}, function() {
									$http.post('../admin/personalBillType/delete', {
										id : id
									}).then(
											function(response) {
												if (response.data.result == 1) {
													SweetAlert.swal("", "删除成功",
															"success");
													init();
												} else {
													SweetAlert.swal("", "删除失败",
															"error");
												}
											},
											function() {
												SweetAlert.swal("", "系统错误",
														"error");
											});
								});
							};
						} ]);