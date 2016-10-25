/**
 * Created by jackShall on 2016/5/16.
 */
var  AngularDataTable = function ($scope,$http,url) {
    $scope.dataTable = {
        data: [],
        currentPage: 1,
        pageSize: 10,
        totalLength: 0,
        init:init,
        numberOfPages: numberOfPages,
        currentPageStart: currentPageStart,
        currentPageEnd: currentPageEnd,
        pages: pages,
        goToPage: goToPage,
        next: next,
        previous: previous,
        onFirstPage: onFirstPage,
        onLastPage: onLastPage,
        resetPaging: resetPaging,
        addReadControl:addReadControl
    };
    return $scope.dataTable;
    function  init(){
        $http.get(url).success(function(data){
            $scope.dataTable.data = data.data;
            $scope.dataTable.totalLength = data.totalLength;
            addReadControl($scope.dataTable.data);
        });
    }
    function numberOfPages() {
        return Math.ceil(this.totalLength / this.pageSize);
    };

    function currentPageStart() {
        return ((this.currentPage - 1) * this.pageSize) + 1;
    };

    function currentPageEnd() {
        return (this.currentPage - 1) * this.pageSize + this.data.length;
    };

    function pages() {
        var p = [];
        for (var i = 1; i <= this.numberOfPages(); i++) {
            p.push(i);
        }
        return p;
    };

    function goToPage(page) {
        $http.get(url+"?pageNum="+page).success(function (data) {
            $scope.dataTable.data = data.data;
            addReadControl($scope.dataTable.data);
        });
        this.currentPage = page;
    };
    function addReadControl(data){
        for(var i=0;i<data.length;i++){
            data[i].readControl=true;
        }
    };

    function next() {
        if (!this.onLastPage()){
            this.currentPage += 1;
        } else{
            return;
        }
        $http.get(url+"?pageNum="+(this.currentPage + 1)).success(function (data) {
            $scope.dataTable.data = data.data;
            addReadControl($scope.dataTable.data);
        });
    };

    function previous() {
        if (!this.onFirstPage()){
            this.currentPage -= 1;
        } else {
            return;
        }
        $http.get(url+"?pageNum="+(this.currentPage - 1)).success(function (data) {
            $scope.dataTable.data = data.data;
            addReadControl($scope.dataTable.data);
        });
    };

    function onFirstPage() {
        return (this.currentPage === 1);
    };

    function onLastPage() {
        return (this.currentPage === this.numberOfPages());
    };

    function resetPaging() {
        this.currentPage = 1;
    }
};