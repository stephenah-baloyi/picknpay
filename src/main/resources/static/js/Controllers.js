var application = angular.module("RhulaniApp.controllers",[]);

application.controller("homeController",function($rootScope,$http,$location){
    
    alert("%%%%%%%%");
    
    $rootScope.save = function(){
        alert("ggggggggggggg");
    };
    
     $rootScope.goto = function(url){
        $location.path(url);
    };
    
});

application.controller("registerController",function($rootScope,$http,$location,$scope){
    
   $scope.register = function(){
    
        
        
        $http.post("/register",$scope.person).then(function(response){
            
           alert(response.data.message);
           
           if(response.data.status === "OK"){
               $rootScope.goto("/shop");
           }
           
        });

//        $http.get("/register/"+$scope.person).then(function(response){
//            
//           alert(response.data.m);
//           
//           if(response.data.status === "OK"){
//               $rootScope.goto("/home");
//           }
//           
//        });
 };
    
});
application.controller("loginController",function($rootScope,$http,$location,$scope){
    
   $scope.register = function(){
    
        
        
        $http.post("/register",$scope.person).then(function(response){
            
           alert(response.data.message);
           
           if(response.data.status === "OK"){
               $rootScope.goto("/shop");
           }
           
        });

//        $http.get("/register/"+$scope.person).then(function(response){
//            
//           alert(response.data.m);
//           
//           if(response.data.status === "OK"){
//               $rootScope.goto("/home");
//           }
//           
//        });
 };
    
});

