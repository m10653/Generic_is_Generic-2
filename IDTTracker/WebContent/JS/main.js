$(document).ready(function() {

    initMap();
});

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
			document.getElementById("uuidbutton").value = "";
		
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
   doSend("uuid " + uuid);
 }
 function login(){
  var user = document.getElementById("user").value;
  var pass = document.getElementById("pass").value;
  var passhash = CryptoJS.MD5(pass);
  var send = "login " + user + " " + passhash;
  doSend(send);
  console.log(passhash);
 } 
 
  function uuid()
  {
  var uuid = document.getElementById("uuidbutton").value;
  doSend(uuid);
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
    //doSend(evt);
  }

  function onClose(evt)
  {
    writeToScreen("DISCONNECTED");
  }

  function onMessage(evt)
  {
    writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data+'</span>');
	console.log(evt.data);
	var aos = evt.data.split(" ");
	var uuid = aos[0];
	var name = aos[1];
	var dist = aos[2];
	var eta = aos[3] + " " + aos[4];
	
	var curlat = aos[5];
	var curlon = aos[6];
	var deslat = aos[7];
	var deslon = aos[8];
	//info(uuid, name, dist, eta);
	console.log(curlat);
	console.log(curlon);
	move(curlat, curlon);
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
	console.log("orange");
  }

  window.addEventListener("load", init, false);