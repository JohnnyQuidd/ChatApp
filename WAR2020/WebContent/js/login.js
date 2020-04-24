$(document).ready(function() {
	$("#loginButton").click(function() {
		let username=$('#username').val();
		let password=$('#password').val();

		$.ajax({
			  url:'rest/users/login',
			  type:"POST",
			  data:JSON.stringify({username, password}),
			  contentType:"application/json; charset=utf-8",
			  success: function(data){
					sessionStorage.setItem('username', data);
					window.location='./homepage.html';
			  },
			  error: function(){
				alert('Neispravni kredencijali');
			  }
			});
	});
});