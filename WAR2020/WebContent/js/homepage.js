$(document).ready(function(){
    // Get all logged in users
    let currentUsername = sessionStorage.getItem('username');
    $.ajax({
        url: 'rest/users/loggedIn',
        type: 'GET',
        success: function(data) {
            for(var i = 0; i < data.length; i++){
               if(data[i].username !== currentUsername){
                    $('#onlineUsers').append('<p id="' + data[i].username + '" class="user"><i class="fa fa-user"> ' + data[i].username + '</p>');
               }
            }
        },
        error: function(){
            console.log('Cannot get all logged in users');
        }
    });

    // Get all registered users
    $.ajax({
        url: 'rest/users/registered',
        type: 'GET',
        success: function(data) {
            for(var i = 0; i < data.length; i++){
                $('#registeredUsers').append('<p id="' + data[i].username + '" class="user"><i class="fa fa-user"> ' + data[i].username + '</p>');
            }
        },
        error: function(){
            console.log('Cannot get all registered in users');
        }
    });

    // Get all previous messages
    $.ajax({
        url: 'rest/messages/' + currentUsername,
        type: 'GET',
        success: function(data) {
            for(var i = 0; i < data.length; i++){
                $('#message-panel').append(data[i].content + '\n');
            }
        },
        error: function(){
            console.log('Cannot get all previous messages');
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
              console.log('Neuspena akcija');
            }
          });
    });

    $('#clear').click(function(){
        $('#message-panel').val('');
    });

    $('#send').click(function(){
        let receiverUsername = $('#receiver').val();
        let subject = $('#subject').val();
        let content = currentUsername + ': ' + $('#message').val();
        let messageDetails = JSON.stringify({receiverUsername, subject, content});
        if(receiverUsername === ''){
            $.ajax({
                url: 'rest/messages/all',
                type: 'POST',
                data: messageDetails,
                contentType:"application/json; charset=utf-8", 
                success: function(){
                    $('#message-panel').append(content + '\n');
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
                    $('#message-panel').append(content + '\n');
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
	
	    socket.onopen = function(){

	    }
	
	    socket.onmessage = function(msg){
            $('#message-panel').append('<p class="messageInstance">' + msg.data + '\n' + "</p>");
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
    }
    
    logoutSocket.onmessage = function(msg){
        $('#' + msg.data).remove();
    }

    logoutSocket.onclose = function(){
        usersSocket = null;
    }
} catch(exception){
    console.log(exception);
}