'use strict';

angular.module("todoApp.services", [])

                .constant("baseURL","/rest/")

                .service('token', ['$resource', 'baseURL', function($resource,baseURL) {

                                   this.getResources = function(header){

                                       return $resource(baseURL+"token/:id" ,null,  {
                                            get: {
                                                    headers:header,
                                                    method: 'GET',
                                                    transformResponse: function(data, headers){

                                                        var response = {}
                                                        response.data = data;
                                                        response.headers = headers();
                                                        return response;
                                                    },
                                            },

                                            update:{method:'PUT' }
                                       });
                                   };
                }])

           

;
