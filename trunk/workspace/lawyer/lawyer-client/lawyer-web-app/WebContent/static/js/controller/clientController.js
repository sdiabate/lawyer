'use strict';

angular.module('myApp').controller('clientController', ['$scope', 'clientService', function($scope, clientService) {
	$scope.genders = [{name:'MALE',label:'M'}, {name:'FEMALE',label:'F'}];
	$scope.selectedGender = {name:'',label:''};
	$scope.client = {id:null,firstName:'',lastName:'',address:'',mobilePhone:''};
	$scope.clients = [];
	
	$scope.reset = reset;
	$scope.loadAllClients = loadAllClients;
	
	loadAllClients();	
	
	function loadAllClients() {
	    clientService.getAllClients().then(
	    		function(clients) {
	    			console.log('Load all the clients');
	    			$scope.clients = clients;
	    		},
	    		function(err) {
	    			console.error('Error when fetching clients');
	    		}
		);
	};

	$scope.submit = function() {
    	$scope.client.gender = $scope.selectedGender.name;
    	if($scope.client.id == null) {
    		console.log('Client creation');
    		clientService.createClient($scope.client);
        	loadAllClients();
    	} else {
    		console.log('Client update');
    		clientService.updateClient($scope.client);
        	loadAllClients();
    	}
    	reset();
    };

    $scope.edit = function(c) {
    	for(var i = 0; i < $scope.genders.length; i++) {
    		if($scope.genders[i].name == c.gender) {
    			$scope.selectedGender = $scope.genders[i];
    			break;
    		}
    	}
    	$scope.client = c;
    };
    
    $scope.remove = function(c) {
	    clientService.deleteClient(c.id).then(
	    		function(response) {
	    			console.log('Client with id = ' + c.id + ' has been deleted');
	    			loadAllClients();
	    		},
	    		function(err) {
	    			console.error('Error when deleting client with id ' + c.id);
	    		}
		);
    };
    
    function reset() {
    	$scope.client = {id:null,firstName:'',lastName:'',address:'',mobilePhone:''};
    	$scope.selectedGender = {name:'',label:''};
    };
	
}]);