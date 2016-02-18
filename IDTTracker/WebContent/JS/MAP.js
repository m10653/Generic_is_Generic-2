
    
    var map = undefined;
    var marker = undefined;
    var position = [0, 0];
    var x = "ch";
	
    function initMap() {
            
        var latlng = new google.maps.LatLng(position[0], position[1]);
        var myOptions = {
            zoom: 2,
            center: latlng,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById('map'), myOptions);
		
        marker = new google.maps.Marker({
           // position: latlng,
            map: map,
            title: "Your current location!"
        });
    
//infowindow.open(map, marker);
			 
			

function myLoop () {           //  create a loop function
   setTimeout(function () {    //  call a 3s setTimeout when the loop is called
      doSend("update");          //  your code here
                      //  increment the counter
              //  if the counter < 10, call the loop function
         myLoop();             //  ..  again which will trigger another 
                            //  ..  setTimeout()
   }, 3000)
}

myLoop();  


        }
    
	
	
    
	
	 
   
google.maps.event.addDomListener(window, 'load', initMap);
    



