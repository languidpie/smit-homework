var app = angular.module("BookLoanManagement", []);

app.controller("BookLoanController", function ($scope, $http) {
    $scope.books = [];
    $scope.bookForm = {
        id: -1,
        title: "",
        author: "",
        publisher: "",
        isbn: "",
        year: 0,
        genre: "",
        recipient: ""
    };

    _refreshBookData();

    $scope.saveBook = function () {
        if ($scope.bookForm.id === -1) {
            // Save new book
            $http({
                method: 'POST',
                url: 'api/books',
                data: JSON.stringify($scope.bookForm),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(_success, _error);
        } else {
            // Update existing book
            $http({
                method: 'PUT',
                url: 'api/books/' + $scope.bookForm.id,
                data: JSON.stringify($scope.bookForm),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(_success, _error);
        }
    };

    $scope.editBook = function (book) {
        // Copy the selected book's data into the form for editing
        $scope.bookForm = angular.copy(book);
    };

    $scope.deleteBook = function(book) {
        $http({
            method : 'DELETE',
            url : 'api/books/' + book.id
        }).then(_success, _error);
    };

    $scope.reserveBook = function (book) {
        $http({
            method : 'PUT',
            url : 'api/books/' + book.id + '/reserve',
        }).then(_success, _error);
    };

    $scope.loanBook = function (book) {
        $http({
            method : 'PUT',
            url : 'api/books/' + book.id + '/loan',
        }).then(_success, _error);
    }

    /* Private Methods */

    //HTTP GET- get all customers collection
    function _refreshBookData() {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/api/books'
        }).then(function successCallback(response) {
            $scope.books = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    function _success(response) {
        _refreshBookData();
        _clearFormData()
    }

    function _error(response) {
        console.log(response.statusText);
    }

    //Clear the form
    function _clearFormData() {
        $scope.bookForm.id = -1;
        $scope.bookForm.title = "";
        $scope.bookForm.author = "";
        $scope.bookForm.publisher = "";
        $scope.bookForm.isbn = "";
        $scope.bookForm.year = 0;
        $scope.bookForm.genre = "";
        $scope.bookForm.recipient = "";
    };
})