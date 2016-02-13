$(window).load(function() {
  	  $("body").removeClass("preload");
  	  $("#boxes").hide();
  	  
  	});
  	
  	
  	$("#loginbutton").click(function(){

  	    $("#boxes").toggle(1000);
  	});
	$(document).ready(function() { // Get UUID from text box
    $('.uuid').keydown(function(event) {
        if (event.keyCode == 13) {
            enterid(document.getElementById("uuidbutton").value);
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
		
		
		
		document.getElementById("uuidbutton").value = dgt2;
		//document.write(newlat);
		document.getElementById("output").value = "	" + "Lat: " + newlat;
		var dgtx = dgt1 + dgt2 + dgt3 + dgt4 + dgt5;
		
		doSend(uuid);
		
		
		//{lat: 25.363, lng: 131.044};
		var myLatLng = {};
		myLatLng = {lat: newlat, lng: newlong};
		
    	  // Create a map object and specify the DOM element for display.
    	  var map = new google.maps.Map(document.getElementById('map'), {
    	    center: myLatLng,
    	    scrollwheel: true,
    	    zoom: 4
    	  });

    	  // Create a marker and set its position.
    	  var marker = new google.maps.Marker({
    	    map: map,
    	    position: myLatLng,
    	    title: 'Hello World!'
    	  });
		  
		  
		
  }
  
   var wsUri = "ws://mc.m1gaming.net:8080/ws/";
  var output;

  function init()
  {
    output = document.getElementById("output");
    testWebSocket();
  }

  function testWebSocket()
  {
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) { onOpen(evt) };
    websocket.onclose = function(evt) { onClose(evt) };
    websocket.onmessage = function(evt) { onMessage(evt) };
    websocket.onerror = function(evt) { onError(evt) };
  }

  function onOpen(evt)
  {
    writeToScreen("CONNECTED");
    doSend(evt);
  }

  function onClose(evt)
  {
    writeToScreen("DISCONNECTED");
  }

  function onMessage(evt)
  {
    writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data+'</span>');
    websocket.close();
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

  function writeToScreen(message)
  {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
  }

  window.addEventListener("load", init, false);
  
  
