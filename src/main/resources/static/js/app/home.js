/**
 * Created by ntquan on 1/7/2019.
 */

var app = angular.module("myRedmineHome", ['ui.bootstrap']);
app.controller("homeController", function ($scope, $http) {
    $scope.home = "This is the homepage";
    $scope.sprint = -1;
    $scope.status = "";
    $scope.isLoading = false;

    $scope.closed = 0;
    $scope.new = 0;
    $scope.inprogress = 0;
    $scope.resolved = 0;
    $scope.feedback = 0;
    $scope.rejected = 0;
    $scope.cancelled = 0;

    $scope.low = 0;
    $scope.normal = 0;
    $scope.high = 0;
    $scope.urgent = 0;
    $scope.immediate = 0;

    $scope.deleteTextStatus = function () {
        $scope.status = "";
        $scope.closed = 0;
        $scope.new = 0;
        $scope.inprogress = 0;
        $scope.resolved = 0;
        $scope.feedback = 0;
        $scope.rejected = 0;
        $scope.cancelled = 0;

        $scope.low = 0;
        $scope.normal = 0;
        $scope.high = 0;
        $scope.urgent = 0;
        $scope.immediate = 0;
    }

    function getDataRedmine(sprint) {
        $http.get("getAllIssue?sprint=" + sprint)
            .then(function successCallback(response) {
                if (response.data && response.data.length > 0) {
                    $scope.listData = angular.copy(response.data);
                    countRedmine($scope.listData);
                    $scope.isLoading = false;
                } else {
                    alert("No data found")
                    $scope.status = "Data not found";
                    $scope.isLoading = false;
                }
            }, function errorCallback(response) {
                console.log("Unable to perform get request");
            });
    }

    function countRedmine(dataRedmine) {
        $scope.deleteTextStatus();
        angular.forEach(dataRedmine, function (value, key) {
            if(value.tracker === 'Bug'){
                console.log(value.issueId)
                if (value.priority) {
                    if (value.priority === "Low") {
                        $scope.low++;
                    }
                    else if (value.priority === "Normal") {
                        $scope.normal++;
                    }
                    else if (value.priority === "High") {
                        $scope.high++;
                    }
                    else if (value.priority === "Urgent") {
                        $scope.urgent++;
                    }
                    else if (value.priority === "Immediate") {
                        $scope.immediate++;
                    }
                }
                if (value.status) {
                    if (value.status === "Closed")
                        $scope.closed++;
                    if (value.status === "New")
                        $scope.new++;
                    if (value.status === "Inprogress")
                        $scope.inprogress++;
                    if (value.status === "Resolved")
                        $scope.resolved++;
                    if (value.status === "Feedback")
                        $scope.feedback++;
                    if (value.status === "Rejected")
                        $scope.rejected++;
                    if (value.status === "Cancelled")
                        $scope.cancelled++;
                }
            }


        })
    }

    $scope.getRedmineBySprint = function () {
        $scope.isLoading = true;
        getDataRedmine($scope.sprint);
    }
});


