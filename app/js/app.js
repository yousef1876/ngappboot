'use strict';

var mainApp = angular.module("todoApp", ['ngRoute']);

mainApp.config(function($routeProvider) {
	$routeProvider
		.when('/seccode', {
			templateUrl: 'partial/secret.html',
			controller: 'SecretCtrl'
		})
		.when('/register', {
			templateUrl: 'partial/register.html',
			controller: 'RegisterCtrl'
		})
		
});

mainApp.controller('SecretCtrl', function($scope) {
	$scope.secretcode = function () {
		
		 var mobile = $('#mobile').val();
		 var data =  {'mobile' : mobile};
		 $.ajax({
 			  type: "POST",
 			  url: '/api/protei/secret',
 			  data: data,
 			  success: function(response){
 				  alert('Success')
 			  },
 			  dataType: 'json'
 			});        
		 
	 };


});

mainApp.controller('RegisterCtrl', function($scope) {
	 $scope.registration = function () {
		 
   		 var mobile = $('#mobile').val();
   			 var secert = $('#mobile').val();
   			 var data = {'mobile' : mobile , 'secret':secret}
   			$.ajax({
   			  type: "POST",
   			  url: '/api/protei/registration',
   			  data: data,
   			  success: function(response){
   				 alert('Success')
   			  },
   			  dataType: 'json'
   			});
     };


});