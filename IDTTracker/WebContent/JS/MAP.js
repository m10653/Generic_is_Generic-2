
    
    var map = undefined;
    var marker = undefined;
    var position = [50, 50];
    
    function initMap() {
            
        var latlng = new google.maps.LatLng(position[0], position[1]);
        var myOptions = {
            zoom: 4,
            center: latlng,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById('map'), myOptions);
    
        marker = new google.maps.Marker({
            position: latlng,
            map: map,
            title: "Your current location!"
        });
    
			infowindow.open(map, marker);
			 
			 marker.addListener('click', function() {
    infowindow.open(map, marker);
  });

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
    
	
	function move(a, b)
	{
	        var latlng = new google.maps.LatLng(a, b);
			marker.setPosition(latlng);
	}
    
	
	 var contentString = "";
    function info(a, b, c, d)
	{
			contentString = "uuid: " + a + "name: " + b + "distance to destination: " + c + "estimated time of arrival: " + d;
			console.log("banana");
	}
	
	var infowindow = new google.maps.InfoWindow({	  
    content: contentString
  });
  
		
  
google.maps.event.addDomListener(window, 'load', initMap);
    



