function initMap(a, b) {
	
	var lat = 50;
	var lng = 50;
  var latlong = {lat: lat, lng: lng};
  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 4,
    center: latlong
  });

  var contentString = "lat: " + lat + "\n" + "long:" + lng;

  var infowindow = new google.maps.InfoWindow({
    content: contentString
  });

  var marker = new google.maps.Marker({
    position: latlong,
    map: map,
    title: 'click for status'
  });
  marker.addListener('click', function() {
    infowindow.open(map, marker);
  });
}
    google.maps.event.addDomListener(window, 'load', initMap);