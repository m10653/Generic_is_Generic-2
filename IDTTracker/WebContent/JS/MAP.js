
    
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
    
        google.maps.event.addListener(map, 'click', function(me) {
           move(50, -50);
			console.log("moved");
			infowindow.open(map, marker);
        });
    }
	
	function move(a, b)
	{
		 var result = [a, b];
            transition(result);
			console.log("moved 2");
	}
    
    var numDeltas = 100;
    var delay = 10; //milliseconds
    var i = 0;
    var deltaLat;
    var deltaLng;
    function transition(result){
        i = 0;
        deltaLat = (result[0] - position[0])/numDeltas;
        deltaLng = (result[1] - position[1])/numDeltas;
        moveMarker();
    }
	
	 var contentString = "" + "apple";
		  var infowindow = new google.maps.InfoWindow({
    content: contentString
  });
    
    function moveMarker(){
        position[0] += deltaLat;
        position[1] += deltaLng;
        var latlng = new google.maps.LatLng(position[0], position[1]);
        marker.setPosition(latlng);
        if(i!=numDeltas){
            i++;
            setTimeout(moveMarker, delay);
        }
    }
google.maps.event.addDomListener(window, 'load', initMap);
    



