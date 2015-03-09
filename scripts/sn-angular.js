angular.module("statusNodeApp", []).controller("statusNodeController", function($scope){
    $scope.statusUpdates = [
        {"timestamp": "2/9/15 12:00:00",
         "update": "Happy Birthday!!!"},
        {"timestamp": "1/28/15 12:00:00",
         "update": "Happy Birthday J!!!"},
        {"timestamp": "12/4/14 12:00:00",
         "update": "Happy Birthday S!!!"}
    ]

    $scope.addStatus = function(){
        $scope.statusUpdates.unshift({"timestamp": getTimestamp(),
          "update": $scope.statusUpdate});
        $scope.statusUpdate="";
    }
});
