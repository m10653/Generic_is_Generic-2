$(window).load(function() {
  	  $("body").removeClass("preload");
  	  $("#boxes").hide();
  	  
  	});
  	
  	
  	$("#loginbutton").click(function(){

  	    $("#boxes").toggle(1000);
  	});
  	   
	function password()
	{
		var pswrd = document.getElementById("test").value = "\u5929\u5730\u7384\u9ec3";
	}
	   
  function uuid()
  {
		
	
	
		var uuid = document.getElementById("uuidbutton").value;
		var dgt1 = "";
		var temp, temp2;
		var newlat;
		var newlong;
		for(var i = 0; i < uuid.length; i ++)
		{
			if(uuid.charAt(i) === '-')
				{
					dgt1 = uuid.substring(0, i);
					temp = i + 1;
				break;
				}
		}
		
		var dgt2 = "";
		for(var i = temp; i < uuid.length; i ++)
		{
			if(uuid.charAt(i) === '-')
				{
					dgt2 = uuid.substring(temp, i);
					temp = i + 1;
				break;
				}
		}
		var dgt3 = "";
		for(var i = temp; i < uuid.length; i ++)
		{
			if(uuid.charAt(i) === '-')
				{
					dgt3 = uuid.substring(temp, i);
					temp = i + 1;
				break;
				}
		}
		var dgt4 = "";
		for(var i = temp; i < uuid.length; i ++)
		{
			if(uuid.charAt(i) === '-')
				{
					dgt4 = uuid.substring(temp, i);
					temp = i + 1;
				break;
				}
		}
		var dgt5 = "";
		for(var i = temp; i < uuid.length; i ++)
		{
			if(uuid.charAt(i) === '-')
				{
					dgt5 = uuid.substring(temp, i);
					temp = i + 1;
				break;
				}
		}
		
		
				
				
		for (var i = 0; i < uuid.length; i++)
		{
			if(uuid.charAt(i) === '&')
			{
				for(var j = i + 1; j < uuid.length; j++)
				{
					if(uuid.charAt(j) === '&')
					{
						newlat = uuid.substring(i + 16, j);
						temp2 = j;
						break;
					}
				}
				break;
			}
		}
		
		for(var j = temp2 + 1; j < uuid.length; j++)
				{
					if(uuid.charAt(j) === '&')
					{
						newlong = uuid.substring(temp2 + 16, j);
						break;
					}
				}
		
		
		document.getElementById("uuidbutton").value = dgt2;
		//document.write(newlat);
		document.getElementById("output").value = "	" + "Lat: " + newlat;
		var dgtx = dgt1 + dgt2 + dgt3 + dgt4 + dgt5;
		init(uuid);
		
		
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

  function init(evt)
  {
    output = document.getElementById("output");
    testWebSocket(evt);
  }

  function testWebSocket(evt)
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
    doSend("test");
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
  
  
