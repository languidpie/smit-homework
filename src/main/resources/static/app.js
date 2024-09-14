var app = angular.module('myApp', []);

app.controller('BookLoanController', function($scope, $http) {
    $http({
        method : 'GET',
        url : 'http://localhost:8080/api/greet'
    }).then(function successCallback(response) {
        $scope.message = response.data;
    }, function errorCallback(response) {
        console.log(response.statusText);
    });
});
