var qotdModule = angular.module("qotdModule", [])
.service("qotdGetter", function(){
    this.getQotd = function(){
        return "The beginner's mind is a fresh place to come from.";
    }
});

angular.module("statusNodeApp", ["qotdModule"])
.controller("statusNodeController", ["$scope", "qotdGetter", function($scope, qotdGetter){
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

    $scope.qotd = function(){
        console.log(qotdGetter);
        $scope.statusUpdate = qotdGetter.getQotd();
        $scope.addStatus();
    }
}]);
