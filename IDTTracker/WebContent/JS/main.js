$(window).load(function() {
     $("body").removeClass("preload");
     $("#boxes").hide();
     
   });
   
   var markers = {};
     
	 console.log(x);

   $("#loginbutton").click(function(){

       $("#boxes").toggle(1000);
   });
 $(document).ready(function() { // Get UUID from text box
    $('.uuid').keydown(function(event) {
        if (event.keyCode == 13) {
            enterid(document.getElementById("uuidbutton").value);
			console.log(text);
            return false;
         }
    });
});
$(document).ready(function() { // Get UUID from text box
    $('.login').keydown(function(event) {
        if (event.keyCode == 13) {
            login();
            return false;
         }
    });
});
 
 
 function enterid(uuid){
  console.log(uuid);
 }
 function login(){
  var user = document.getElementById("user").value;
  var pass = document.getElementById("pass").value;
  var passhash = CryptoJS.MD5(pass);
  var send = "login " + user + " " + passhash;
  doSend(send);
  console.log(passhash);
 }
 function password()
 {
  var pswrd = document.getElementById("test").value = "\u5929\u5730\u7384\u9ec3";
 }
    
  function uuid()
  {
  
 
 
  var uuid = document.getElementById("uuidbutton").value;
  var newlat;
  var newlong;
  
  
  
  //document.getElementById("uuidbutton").value = dgt2;
  //document.write(newlat);
  //document.getElementById("output").value = " " + "Lat: " + newlat;
 // var dgtx = dgt1 + dgt2 + dgt3 + dgt4 + dgt5;
  
  doSend(uuid);
  
  
  
    
  
  }
  
   var wsUri = "ws://mc.m1gaming.net:8080/ws/";
  var output;
  var websocket = undefined;

  function init()
  {
    output = document.getElementById("output");
    testWebSocket();
  }

  function testWebSocket()
  {
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) { onOpen(evt) };
    //websocket.onclose = function(evt) { onClose(evt) };
    websocket.onmessage = function(evt) { onMessage(evt) };
    websocket.onerror = function(evt) { onError(evt) };
  }

  function onOpen(evt)
  {
    writeToScreen("CONNECTED");
    //doSend(evt);
  }

  function onClose(evt)
  {
    writeToScreen("DISCONNECTED");
  }

  function onMessage(evt)
  {
    writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data+'</span>');
	
	var aos = evt.data.split(" ");
	console.log(aos[0]);
	
	if(markers[aos[0]] == undefined && aos[0] != "NoPackages"){ // new Package
	console.log("New Package")
	var latlon = new google.maps.LatLng(aos[5], aos[6]);
	var marker = new google.maps.Marker({
    position: latlon,
    map: map,
    title: 'Click for Status'
  });
   markers[aos[0]] = {"name": aos[1], "dist": aos[2], "eta": aos[3] + " " + aos[4], "curlat": aos[5], "curlon": aos[6], "deslat": aos[7], "deslon": aos[8], "marker1": marker};
  }  else  if(markers[aos[0]] != undefined){
	markers[aos[0]].dist = aos[2];
	markers[aos[0]].eta = aos[3] + " " + aos[4];
	markers[aos[0]].curlat = aos[5]; 
  markers[aos[0]].curlon = aos[6];
  var latlon = new google.maps.LatLng(aos[5], aos[6]);
  markers[aos[0]].marker1.setPosition(latlon);
 
  }
	console.log(markers[aos[0]]);
	//move(markers[aos[0]].curlat, markers[aos[0]].curlon);
	//markers[aos[0]] = {"name": aos[1], "dist": aos[2], "eta": aos[3] + " " + aos[4], "curlat": aos[5], "curlon": aos[6], "deslat": aos[7], "deslon": aos[8], "marker": new google.maps.Marker(position: new google.maps.LatLng() )};
	//move(markers, aos[0]);
	//var latlng = new google.maps.LatLng(markers[aos[0]].curlat, markers[aos[0]].curlon);
			//markers[aos[0]].marker.setPosition(latlng);
	
	//info(markers[aos[0]], markers[aos[1]], markers[aos[2]], markers[aos[3]], markers[4]);
	
	var contentString = "";
    
			contentString = "<div>UUID: " + aos[0] + "</div><div> NAME: " + aos[1] + "</div> <div>DISTANCE TO DESTINATION: " + aos[2] +" miles"+ "</div> ESTIMATED TIME OF ARRIVAL: " + aos[3];
  }

  function onError(evt)
  {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
  }

  function doSend(evt)
  {
    writeToScreen("SENT: " + evt);
    websocket.send(evt);
  
  }
  /*function move(a, b)
	{
	        var latlng = new google.maps.LatLng(a[b].curlat, a[b].curlon);
			a[b].marker.setPosition(latlng);
	}
  */

  function writeToScreen(message)
  {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
  }

  window.addEventListener("load", init, false);