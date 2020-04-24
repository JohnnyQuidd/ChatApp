$(document).ready(function(){

    $('#logout').click(function(){
        let username = sessionStorage.getItem('username');
        $.ajax({
            url:'rest/users/loggedIn/' + username,
            type:"DELETE",
            success: function(){
                   window.location='./login.html';
                   sessionStorage.removeItem(username);
            },
            error: function(){
              alert('Neuspena akcija');
            }
          });
    });

    $('#send').click(function(){
        let receiverUsername = 'guest';
        let subject = 'Welcome';
        let content = $('#message').val();
        let messageDetails = JSON.stringify({receiverUsername, subject, content});
        $.ajax({
            url: 'rest/messages/all',
            type: 'POST',
            data: messageDetails,
            contentType:"application/json; charset=utf-8", 
            success: function(){
                console.log('Message sent successfully');
                $('#message').val('');
            },
            error: function(err){
                console.log(err);
            }
        });

    });

    

    var socket;
    var host = "ws://localhost:8080/WAR2020/ws";
    try{
	    socket = new WebSocket(host);
	    console.log('connect: Socket Status: '+socket.readyState);
	
	    socket.onopen = function(){
	   	 console.log('onopen: Socket Status: '+socket.readyState+' (open)');
	    }
	
	    socket.onmessage = function(msg){
            $('#message-panel').append(msg.data + '\n');
	   	 console.log('Received: '+ msg.data);
	    }
	
	    socket.onclose = function(){
	    	socket = null;
	    }			
	
	} catch(exception){
	   console.log('Error'+exception);
	}
});

document.addEventListener('DOMContentLoaded', function() {
    let username = sessionStorage.getItem('username');
    $.ajax({
        url: 'rest/messages/' + username,
        type: 'GET',
        success: function(data){
            let object = JSON.parse(data);
            console.log('data-content: ' + data.content);
            $('#message-panel').append(data.content + '\n');
        },
        error: function() {
            console.log('Unable to fetch messages');
        }
    });
}, false);