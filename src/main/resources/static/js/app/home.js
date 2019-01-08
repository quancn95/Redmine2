/**
 * Created by ntquan on 1/7/2019.
 */

var app = angular.module("myRedmineHome", ['ui.bootstrap']);
app.controller("homeController", function ($scope, $http) {
    $scope.home = "This is the homepage";
    $scope.sprint=-1;
    $scope.status = "";
    $scope.isLoading = false;

    $scope.closed = 0;
    $scope.new = 0;
    $scope.inprogress = 0;
    $scope.resolved = 0;
    $scope.feedback = 0 ;
    $scope.rejected = 0 ;
    $scope.cancelled = 0;

    $scope.low = 0;
    $scope.normal = 0;
    $scope.high = 0;
    $scope.urgent = 0;
    $scope.immediate = 0;

    $scope.deleteTextStatus = function () {
        $scope.status = "";
    }
    
    function getDataRedmine(sprint) {
        $http.get("getAllIssue?sprint="+sprint)
            .then(function successCallback(response) {
                if(response.data && response.data.length>0){
                    $scope.listData = angular.copy(response.data);
                    countRedmine($scope.listData);
                    // $scope.isLoading = false;
                }else {
                    alert("No data found")
                    $scope.status = "Data not found";
                    // $scope.isLoading = false;
                }
            }, function errorCallback(response) {
                console.log("Unable to perform get request");
            });
    }
    function countRedmine(dataRedmine) {
        var low=0, normal=0, hight=0, urgent=0, inmediate=0;
        var closed=0, newr= 0, inprogress=0, resolved=0, feedback=0, rejected=0, cancelled=0;
        angular.forEach(dataRedmine, function (value, key) {
            if(value.priority){
                if(value.priority === "Low"){
                    low++
                    $scope.low = low;
                }
                else if(value.priority === "Normal"){
                    normal++;
                    $scope.normal = normal;
                }
                else if(value.priority === "High"){
                    hight++;
                    $scope.high = hight;
                }
                else if(value.priority === "Urgent"){
                    urgent++;
                    $scope.urgent = urgent;
                }
                else if(value.priority === "Immediate"){
                    inmediate++;
                    $scope.immediate = inmediate;
                }
            }
            if (value.status) {
                if (value.status === "Closed")
                    closed++;
                    $scope.closed =closed;
                if (value.status === "New")
                    newr++;
                    $scope.new = newr;
                if (value.status=== "Inprogress")
                    inprogress++;
                    $scope.inprogress = inprogress;
                if (value.status=== "Resolved")
                    resolved++;
                    $scope.resolved = resolved;
                if (value.status === "Feedback")
                    feedback++;
                    $scope.feedback = feedback ;
                if (value.status === "Rejected")
                    rejected++;
                    $scope.rejected = rejected;
                if (value.status === "Cancelled")
                    cancelled++;
                    $scope.cancelled = cancelled;
            }

        })
    }
    
    $scope.getRedmineBySprint =  function() {
        $scope.isLoading = true;
        getDataRedmine($scope.sprint);
    }
});


