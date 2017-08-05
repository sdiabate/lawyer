'use strict';

angular.module('myApp').factory('clientService', ['$http', '$q', function($http, $q) {
	
	var factory = {
		getAllClients: getAllClients,
		createClient: createClient,
		updateClient: updateClient,
		deleteClient: deleteClient
	};
	
	return factory;
	
	function getAllClients() {
		var deferred = $q.defer();
		$http.get("allClients").then(
			function(response) {
				deferred.resolve(response.data);
			},
			function(error) {
				deferred.reject(error);
			}
		);
		return deferred.promise;
	}

	function createClient(client) {
		var deferred = $q.defer();
		$http.post("createClient", client).then(
			function(response) {
				deferred.resolve(response.data);
			},
			function(error) {
				deferred.reject(error);
			}
		);
		return deferred.promise;
	}
	
	function updateClient(client) {
		var deferred = $q.defer();
		$http.put("updateClient", client).then(
			function(response) {
				deferred.resolve(response.data);
			},
			function(error) {
				deferred.reject(error);
			}
		);
		return deferred.promise;
	}
	
	function deleteClient(id) {
		var deferred = $q.defer();
		$http['delete']("deleteClient/" + id).then(
			function(response) {
				deferred.resolve(response.data);
			},
			function(error) {
				deferred.reject(error);
			}
		);
		return deferred.promise;
	}
	
}]);