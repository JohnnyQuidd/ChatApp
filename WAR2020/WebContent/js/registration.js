$(document).ready(function() {
    $('#register').click(function() {
        let username = $('#username').val();
        let password = $('#password').val();
        let password2 = $('#password2').val();

        if(password !== password2){
            alert('Passwords must match');
        }

        $.ajax({
            url:'rest/users/register',
			type:"POST",
			data:JSON.stringify({username, password}),
			contentType:"application/json; charset=utf-8",
			success: function(){
				window.location='./login.html';
			},
			error: function(){
			    alert('Unable to register');
			}
        });
    });
});