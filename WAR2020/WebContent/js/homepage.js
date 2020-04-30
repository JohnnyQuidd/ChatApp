$(document).ready(function(){
    let currentUsername = sessionStorage.getItem('username');
    $.ajax({
        url: 'rest/users/loggedIn',
        type: 'GET',
        success: function(data) {
            for(var i = 0; i < data.length; i++){
               if(data[i].username !== currentUsername){
                    $('#onlineUsers').append('<p id="' + data[i].username + '">' + data[i].username + '</p>');
               }
            }
        },
        error: function(){
            console.log('Cannot get all logged in users');
        }
    });

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

    $('#clear').click(function(){
        $('#message-panel').val('');
    });

    $('#send').click(function(){
        let receiverUsername = $('#receiver').val();
        let subject = 'Welcome';
        let content = $('#message').val();
        let messageDetails = JSON.stringify({receiverUsername, subject, content});
        if(receiverUsername === ''){
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
        } else {
            $.ajax({
                url: 'rest/messages/' + receiverUsername,
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
        }

    });

    

    var socket;
    let username = sessionStorage.getItem('username');
    var host = "ws://localhost:8080/WAR2020/ws/" + username;
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

var usersSocket;
let username = sessionStorage.getItem('username');
let usersHost = "ws://localhost:8080/WAR2020/ws/users/" + username;
try{
    usersSocket = new WebSocket(usersHost);
    
    usersSocket.onopen = function(){
    	console.log('Ulogovan sam');
    }
    
    usersSocket.onmessage = function(msg){
        $('#onlineUsers').append('<p id="' + msg.data + '">' + msg.data + '</p>');
    }

    usersSocket.onclose = function(){
        usersSocket = null;
    }
} catch(exception){
    console.log(exception);
}

var logoutSocket;
let uname = sessionStorage.getItem('username');
let logoutHost = "ws://localhost:8080/WAR2020/ws/users/logout/" + uname;
try{
    logoutSocket = new WebSocket(logoutHost);
    
    logoutSocket.onopen = function(){
    	// console.log('Izlogovan sam');
    }
    
    logoutSocket.onmessage = function(msg){
        console.log('Izlogovan korisnik: ' + msg.data);
        $('#' + msg.data).remove();
    }

    logoutSocket.onclose = function(){
        usersSocket = null;
    }
} catch(exception){
    console.log(exception);
}