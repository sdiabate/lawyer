'use strict';

angular.module('myApp').controller('clientsCtrl', function($scope) {
	$scope.client = [{id:null,firstName:'',lastName:'',address:'',mobilePhone:''}];
    $scope.clients = [
        {id:1,firstName:'Jani',lastName:'Norway',address:'2 av hhj',mobilePhone:'256667687878'},
        {id:2,firstName:'Carl',lastName:'Sweden',address:'2 av hhj',mobilePhone:'256667687878'},
        {id:3,firstName:'Margareth',lastName:'England',address:'2 av hhj',mobilePhone:'256667687878'},
        ];
    $scope.edit = function(c) {
    	$scope.client = c;
    };
    $scope.remove = function(c) {
    	var clientList = [];
    	for(var i = 0, j = 0; i < $scope.clients.length; i++) {
    		if($scope.clients[i].id != c.id) {
    			clientList[j++] = $scope.clients[i];
    		}
    	}
    	$scope.clients = clientList;
    };
});