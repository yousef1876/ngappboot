'use strict';

angular.module("todoApp.controllers", [])
    .controller("SecretCtrl", function($scope, Todo){
    	alert('Test');
    	 $scope.secretcode = function () {
    		 
    		 var mobile = $('#mobile').val();
    		 var data =  {'mobile' : mobile};
    		 $.ajax({
      			  type: "POST",
      			  url: '/rest/protei/secret',
      			  data: data,
      			  success: function(response){
      				  
      			  },
      			  dataType: 'json'
      			});        
    		 
    	 };
    	
    }).controller("RegCtrl", function($scope, Todo){
   	 $scope.registration = function () {
   		 var mobile = $('#mobile').val();
   			 var secert = $('#mobile').val();
   			 var data = {'mobile' : mobile , 'secret':secret}
   			$.ajax({
   			  type: "POST",
   			  url: '/rest/protei/registration',
   			  data: data,
   			  success: function(response){
   				  
   			  },
   			  dataType: 'json'
   			});
     };
	
});
   