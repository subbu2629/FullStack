<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>New user registration</title>
</head>
<body>
	<!-- read user name and password from user to create account
	     and send to usercontrollers registeruser method
	     
	-->
	<% if(request.getAttribute("newUser")!=null) {%>
		<form action="user?action=registernewuser" method="post">
		<div>
				<div><label for="username">UserName for the Account : </label> </div>
				<div><input type="text" id="username" name="username"> </div>
			</div>
			
			<div>
				<div><label for="password">Password for the Account : </label> </div>
				<div><input type="text" id="password" name="password"> </div>
			</div>
			<div>
				<div><label for="firstname">First Name</label> </div>
				<div><input type="text" id="firstname" name="firstname"> </div>
			</div>
			<div>
				<div><label for="lastname">Last Name</label> </div>
				<div><input type="text" id="lastname" name="lastname"> </div>
			</div>
			<div>
				<div><label for="dob">Date of Birth</label> </div>
				<div><input type="text" placeholder="yyyy-mm-dd" id="dob" name="dob"> </div>
			</div>
			<div>
				<div><label for="mobile">Mobile</label> </div>
				<div><input type="text" id="mobile" name="mobile"> </div>
			</div>
			<div>
				<div><label for="email">Email</label> </div>
				<div><input type="email" id="email" name="email"> </div>
			</div>
			<div>
				<div><label for="city">City</label> </div>
				<div><input type="text" id="city" name="city"> </div>
			</div>
			<div>
				<div><label for="state">State</label> </div>
				<div><input type="text" id="state" name="state"> </div>
			</div>
			<div>
				<div><label for="country">Country</label> </div>
				<div><input type="text" id="country" name="country"> </div>
			</div>
			<div>
				<div><label for="pincode">Pincode</label> </div>
				<div><input type="text" id="pincode" name="pincode"> </div>
			</div>
			<div>
				<div><label for="address">Address</label> </div>
				<div><TextArea id="address" name="address"></TextArea> </div>
			</div>
			<br>
			<br>
			
			
			<div>
				<div><input type="submit" value="Register" id="register"></div>
			</div>
			<br/>
		</form>
		
		<%} else 
		{ 
		if(request.getAttribute("userCreation").toString().equals("success")) 
		{
		%>
			<h1>New User successfully created !!</h1>
			
		<% } 
		else 
		{ %>
		
		<h1>New User creation failed !!</h1>
		
		
		<%} %>
		
		
		
		<%} %>
		
		<a href="index.jsp"><input type="button" value="Home" name="home"/></a>
</body>
</html>