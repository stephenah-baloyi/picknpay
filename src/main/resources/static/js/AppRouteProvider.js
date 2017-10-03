var RhulaniApp = angular.module('RhulaniApp',['ngRoute']);

RhulaniApp.config(["$routeProvider", function ($routeProvider){
        
        $routeProvider.when("/",{
            
            templateUrl:"shop.html",
            controller:"homeController"
            
        }).when("/register",{
            
            templateUrl:"register.html",
            controller:"registerController"
            
        }).when("/login",{
            
            templateUrl:"login.html",
            controller:"loginController"
            
        }).when("/admin/",{
            
            templateUrl:"admin.html",
            controller:"adminController"
            
        }).when("/admin/products",{
            
            templateUrl:"product.html",
            controller:"productController"
            
        }).when("/checkout",{
            
            templateUrl:"checkout.html",
            controller:"checkoutController"
            
        }).when("/success",{
            
            templateUrl:"success.html",
            controller:"checkoutController"
            
        }).when("/cancel",{
            
            templateUrl:"cancel.html",
            controller:"checkoutController"
            
        }).otherwise({
            redirectTo:"/error.html"
        });
        
        
}]);

RhulaniApp.controller("homeController",function($rootScope,$scope,$http,$location){

    $rootScope.logUser = sessionStorage.getItem("loggedCustomer");
    $rootScope.logUserId = sessionStorage.getItem("logUserId");
    $rootScope.loginStatus = sessionStorage.getItem("loginStatus");
    $rootScope.shoppingCart = localStorage.getItem("shoppingCart");
    $rootScope.shippingId = localStorage.getItem("shippingId");
    $rootScope.checkoutItems = localStorage.getItem("checkoutItems");
    $rootScope.sessionId =  sessionStorage.getItem("sessionId");
    
    $rootScope.goto = function(url){
        $location.path(url);
    };
       
    $rootScope.displayProduct = function(){        
    
       $http.post("/admin/display").then(function(response){
           
       $rootScope.displays = response.data;
 });

 };
 
$scope.cart = [];
$scope.itemCount = 0;
$scope.subtotal = 0;
$scope.total = 0;

$scope.addToCart = function (id,name,price,quantity) {
if($rootScope.logUser !== null )
{
var found = false;
var x = 0;
for( x ; x < $scope.cart.length; x++){
            
    if($scope.cart[x].Id === id && quantity > 0){

    found = true;
    $scope.itemCount++;
    $scope.cart[x].quantity +=  quantity;
    $scope.subtotal += $scope.cart[x].price * $scope.cart[x].quantity;
    $scope.total =  $scope.subtotal;  
    //quantity--;

    }
 localStorage.setItem("shoppingCart",JSON.stringify($scope.cart));
}

if (!found && quantity > 0) {
        //alert(quantity);      
        var item = {
        Id:id,
        productName:name,
        price:price,
        quantity: quantity
        };

        $scope.cart.push(item);
        quantity--;
        $scope.itemCount++;
        $scope.cart[x].quantity += quantity;
        $scope.subtotal += $scope.cart[x].price * $scope.cart[x].quantity;
        $scope.total = $scope.subtotal; 
        localStorage.setItem("shoppingCart",JSON.stringify($scope.cart));
   }
 }
 else{
     $rootScope.goto("/login");
 }
};

});

RhulaniApp.controller("registerController",function($rootScope,$http,$location,$scope){
   $rootScope.gotoRegister = function(url){
       $location.path(url);
   }; 
   $scope.register = function(){      
        
        $http.post("/register",$scope.customer).then(function(response){
            
           alert(response.data.message);
           
           if(response.data.status === "OK"){
               $rootScope.gotoRegister("/");
           }
           
        });
        //$scope.direct();
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
RhulaniApp.controller("loginController",function($rootScope,$http,$location,$scope){
 $rootScope.logUser = sessionStorage.getItem("loggedCustomer");
 $rootScope.logUserId = sessionStorage.getItem("logUserId");
 $rootScope.loginStatus = sessionStorage.getItem("loginStatus");
 $rootScope.sessionId =  sessionStorage.getItem("sessionId");
    
 $rootScope.gotoCust = function(url){
        $location.path(url);
  };
$scope.login = function(){       
        var user = $scope.user;
        $http.post("/login",$scope.log).then(function(response){
                
                    var requestStatus = response.data.HttpStatus;

                    if (requestStatus === "FOUND") {
                        alert(response.data.message);
                        user = response.data.loggedCustomer;
                        var sessionId = response.data.sessionId;
                        sessionStorage.setItem("loggedCustomer", JSON.stringify(user));
                        sessionStorage.setItem("sessionId",sessionId);

                            $rootScope.loginStatus = user.name;
                            $rootScope.logUserId = response.data.userId;
                            
                            sessionStorage.setItem("loginStatus", $rootScope.loginStatus);
                            sessionStorage.setItem("logUserId",$rootScope.logUserId);
                            //alert(sessionStorage.getItem("sessionId"));
                            $rootScope.gotoCust("/");
                           //$rootScope.goto('/');
                    }

                    if (sessionStorage.getItem("currentUrl") !== null && sessionStorage.getItem("sessionId") !== null) {

                        var url = sessionStorage.getItem("currentUrl");
                        sessionStorage.removeItem("currentUrl");
                        //alert(sessionStorage.getItem("sessionID"));
                        $rootScope.gotoCust("/");

                    } 
    });
};
                
 $scope.logout = function() {
        $scope.empSession = sessionStorage.getItem("sessionId");
        $scope.employeeId = sessionStorage.getItem("logUserId");
        
        $http.post("/logout/"+ $scope.empSession +"/"+ $scope.employeeId).then(function(response)
         {   
            $scope.status = response.data.status;
            if ($scope.status === "OK") {

                    sessionStorage.removeItem("loginStatus");
                    $rootScope.loginStatus = null;
                    $rootScope.logUserId = null;
                    sessionStorage.removeItem("loggedCustomer");
                    sessionStorage.removeItem("sessionId");
                    alert("You have Successfuly logged out.");                    
                    $scope.gotoAdmin("/");
            }
        });          
};

});

RhulaniApp.controller("productController",function($rootScope,$http,$location,$scope){
$rootScope.logEmployee = sessionStorage.getItem("loggedEmp");
$rootScope.logEmpId = sessionStorage.getItem("logEmpId");
$rootScope.loginName = sessionStorage.getItem("loginName");
$rootScope.sessionAdmin =  sessionStorage.getItem("sessionAdmin");

$http.defaults.headers.post["Content-Type"] = "application/json";
$scope.image= null;					
var imageCopy = null;
var image = null;
var handleImageSelect = function(evt)
{
var files = evt.target.files;
var file = files[0];

    if (files && file) {
    var reader = new FileReader();
    reader.onload = function(readerEvt) {
    var binaryString = readerEvt.target.result;
    imageCopy = btoa(binaryString);
    image = 'data:image/octet-stream;base64,'+ imageCopy;    
    $scope.image=image;
    console.log($scope.image);
    };
    reader.readAsBinaryString(file);	
    }
};
if(window.File && window.FileReader && window.FileList && window.Blob){
    document.getElementById('filePickerImage').addEventListener('change', handleImageSelect, false);
  }else {
        alert('The File APIs are not fully supported in this browser.');
  }
  
$scope.addProduct = function(){
    
    $http.post("/admin/product",{
    productName: $scope.productName,
    price: $scope.price,								 
    quantity: $scope.quantity,								   
    category: $scope.category,
    image:  $scope.image
								   
}).then(function(response) {
   alert(response.data.message);
           
           if(response.data.status === "OK"){
             alert(response.data.prod.productName + " has been added" );
             $rootScope.goto("/admin/products");
             $scope.listProducts();
         }
						
    });

 };
 
 $scope.listCarts = function(){        

        $http.post("/admin/products").then(function(response){
            //alert(JSON.stringify(response.data) );
          //$rootScope.goto("/product");
          
          $rootScope.carts = response.data;
 });

 };
 $scope.listProducts = function(){        

        $http.post("/admin/products").then(function(response){
            //alert(JSON.stringify(response.data) );
          //$rootScope.goto("/product");
          
          $rootScope.products = response.data;
 });

 };

$scope.updateProduct = function(productId){        
            alert("Updating...");
        $http.post("/admin/updateProduct/" + productId,$scope.product).then(function(response){
                 alert("Updating...22");       
           if(response.data.status === "OK"){
             alert(response.data.message);
             $scope.listProducts();
           }
           
 });

 };
$scope.deleteProduct = function(productId){        

        $http.post("/admin/deleteProduct/" + productId).then(function(response){
         $scope.listProducts();
 });
 };
    
$scope.product = [];
$scope.updateProduct = function (id) {
var x = 0;
        var item = localStorage.getItem("shoppingCart");
        //alert(item);
        
        var jsonObj = JSON.parse(item);
        
         for (var i = 0, max = jsonObj.length; i < max; i++) {
            var orderData = {
            Id:jsonObj[i].Id,
            productName:jsonObj[i].productName,
            price:jsonObj[i].price,
            quantity: jsonObj[i].quantity
        };
            
            $scope.carts.push(orderData);
         }          
    for( x ; x < $scope.carts.length; x++){

        $scope.total += $scope.carts[x].price * $scope.carts[x].quantity;

    } 
      localStorage.setItem("checkoutItems",JSON.stringify($scope.carts));
};
    
});

RhulaniApp.controller("adminController", function($rootScope,$http,$location,$scope){
    
    $rootScope.logEmployee = sessionStorage.getItem("loggedEmp");
    $rootScope.logEmpId = sessionStorage.getItem("logEmpId");
    $rootScope.loginName = sessionStorage.getItem("loginName");
    $rootScope.sessionAdmin =  sessionStorage.getItem("sessionAdmin");
    
    $rootScope.gotoAdmin = function(url){
        $location.path(url);
    };
    
    $scope.adminLogin = function(){
        
        var adminUser = $scope.adminUser;
        
        $http.post("/admin/adminLogin",$scope.admin).then(function(response){
            
           var requestStatus = response.data.status;
            
            if (requestStatus === "FOUND") {
                alert(response.data.message);
                
                adminUser = response.data.loggedEmp;
                 
                 var sessionId = response.data.sessionId;
                 sessionStorage.setItem("loggedEmp", JSON.stringify(adminUser));
                 sessionStorage.setItem("sessionAdmin",sessionId);

                $rootScope.loginName = adminUser.name;
                $rootScope.logEmpId = response.data.userId;
                sessionStorage.setItem("loginName", $rootScope.loginName);
                sessionStorage.setItem("logEmpId",$rootScope.logEmpId);
                $rootScope.gotoAdmin("/admin/products");

            }
            if (sessionStorage.getItem("currentUrl") !== null && sessionStorage.getItem("sessionAdmin") !== null) {

                var url = sessionStorage.getItem("currentUrl");
                sessionStorage.removeItem("currentUrl");
                $rootScope.gotoAdmin("/admin/products");

            } 
        });
    };
    
    $scope.logoutAdmin = function(url){
        
        //alert(sessionStorage.getItem("sessionAdmin"));
        //alert(sessionStorage.getItem("logEmpId"));
        
        $scope.empSession = sessionStorage.getItem("sessionAdmin");
        $scope.employeeId = sessionStorage.getItem("logEmpId");
        
        $http.post(url+"/"+ $scope.empSession +"/"+ $scope.employeeId).then(function(response)
         {              
            $scope.status = response.data.status;
            if ($scope.status === "OK") {

                    sessionStorage.removeItem("loginNmae");
                    $rootScope.loginName = null;
                    sessionStorage.removeItem("loggedEmp");
                    sessionStorage.removeItem("sessionAdmin");
                    alert("You have Successfuly logged out.");
                    $scope.gotoAdmin("/admin/");
            }
        });
            
};
    
});

RhulaniApp.controller("checkoutController",function($rootScope,$http,$location,$scope){
$scope.carts = [];
$scope.total = 0;
$scope.checkoutProcess = function () {
var x = 0;
        var item = localStorage.getItem("shoppingCart");
        //alert(item);
        
        var jsonObj = JSON.parse(item);
        
         for (var i = 0, max = jsonObj.length; i < max; i++) {
            var orderData = {
            Id:jsonObj[i].Id,
            productName:jsonObj[i].productName,
            price:jsonObj[i].price,
            quantity: jsonObj[i].quantity
        };
            
            $scope.carts.push(orderData);
         }          
    for( x ; x < $scope.carts.length; x++){

        $scope.total += $scope.carts[x].price * $scope.carts[x].quantity;

    } 
      localStorage.setItem("checkoutItems",JSON.stringify($scope.carts));
};
    
$scope.Shipping = function () {
       
     $http.post("/shipping",$scope.ship).then(function(response){
            
            localStorage.setItem("shippingId",response.data.ship);
           alert(response.data.message); 
        });
       
   } ;

$scope.checkout = function () {
    
$scope.check = [];
$scope.checktotal = 0;
$scope.checktotal2 = 0;

var checkItem = localStorage.getItem("checkoutItems");
var checkObj = JSON.parse(checkItem);
var x =0;      
for (var i = 0, max = checkObj.length; i < max; i++) {
            var items = {
            Id:checkObj[i].Id,
            productName:checkObj[i].productName,
            price:checkObj[i].price,
            quantity: checkObj[i].quantity

        };
            
   $scope.check.push(items);
}          
       
$scope.checktotal += $scope.check[x].price * $scope.check[x].quantity;
$scope.checktotal2 = $scope.checktotal + $scope.delivery;

$scope.custId = sessionStorage.getItem("logUserId");

       
      $scope.id =  $scope.check[x].Id;
      var url = "/checkout/"+ $scope.id + "/" + $scope.custId;//$scope.id

    $http.post(url,$scope.check).then(function () {
    alert("You have successfully placed your Order");
       // $rootScope.goto("/");
    });
       
};
   
$scope.removeItem = function (id){
 var x =0;       
    
  for( x = 0 ; x < $scope.carts.length; x++){
      
   if($scope.carts[x].Id === id && $scope.carts[x].quantity > 0){
       $scope.carts.splice(x,1) ;
       localStorage.setItem("shoppingCart",$scope.carts);
       
       var item = localStorage.getItem("shoppingCart");
        var jsonObj = JSON.parse(item);
       for (var i = 0, max = jsonObj.length; i < max; i++) {
            var orderData = {
            Id:jsonObj[i].Id,
            productName:jsonObj[i].productName,
            price:jsonObj[i].price,
            quantity: jsonObj[i].quantity
        };
            
            $scope.carts.push(orderData);
     }          
        
        //$scope.total = $scope.total - $scope.carts[x].price;
        //localStorage.setItem("shoppingCart",$scope.carts);        
        $scope.checkoutProcess();
        
   }   
}           
};
$scope.cancel = function (){
 
        alert("Are you sure you want to cancel your Order?");    
};

$scope.updateCart = function (id, quantity){
 var x =0;       
    
  for( x = 0 ; x < $scope.carts.length; x++){
      
   if($scope.carts[x].Id === id && $scope.carts[x].quantity > 0){
       
       $scope.carts[x].quantity = quantity;
       localStorage.setItem("shoppingCart",$scope.carts);
       
       var item = localStorage.getItem("shoppingCart");
        var jsonObj = JSON.parse(item);
       for (var i = 0, max = jsonObj.length; i < max; i++) {
            var orderData = {
            Id:jsonObj[i].Id,
            productName:jsonObj[i].productName,
            price:jsonObj[i].price,
            quantity: jsonObj[i].quantity
        };
            
            $scope.carts.push(orderData);
     }          
        
        //$scope.total = $scope.total - $scope.carts[x].price;
        //localStorage.setItem("shoppingCart",$scope.carts);        
        $scope.checkoutProcess();
        
   }   
}           
};

});