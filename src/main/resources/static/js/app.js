
var demoApp = angular.module('demoApp',['ngRoute']);

demoApp.config(['$routeProvider', function($routeProvider){
    $routeProvider
        .when('/',{template:'这是首页页面'})
        .when('/computers',{template:'这是电脑分类页面'})
        .when('/printers',{template:'这是打印机页面'})
        .otherwise({redirectTo:'/'});
}]);

demoApp.controller('myCtrl', function($scope, $http) {
    $http.get("/emp/detail/5")
        .success(function (data) {$scope.data = data});
});