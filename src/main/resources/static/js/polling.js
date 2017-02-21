/**
 * Created by Evgeniy on 16.02.2017.
 */
var app = angular.module("Polling", []);
app.controller("PollingController", function ($scope, $http) {
    $http({
        method: 'GET',
        url: window.location.pathname + "/statistic"
    }).then(function successCallback(response) {
        $scope.statistic = response.data;
    });
    $http({
        method: 'GET',
        url: window.location.pathname + '/value'
    }).then(function successCallback(response) {
        $scope.data = response.data.data;
        $scope.isOwner = response.data.isOwner;
    });
    $scope.startPolling = function () {
        $http({
            method: "POST",
            url: window.location.pathname + "/start"
        }).then(function successCallback(response){
            alert(response.data)
        })
    };
    $scope.endPolling = function () {
        $http({
            method: "POST",
            url: window.location.pathname + "/stop"
        }).then(function successCallback(response){
            alert(response.data)
        })
    };
    $scope.vote = function () {
        var voteValue = {
            id: null,
            voter: null,
            polling: $scope.data,
            pollingChoose: {pollingVariant: $scope.radioButton}
        };
        $http({
            method: "POST",
            url: "/vote",
            data: JSON.stringify(voteValue)
        }).then(function successCallback(response){
            alert(response.data)
        });
        $scope.radioButton = 1;
    }
});
