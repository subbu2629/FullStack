<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!-- write html code to read the application number and send to usercontrollers'
             displaystatus method for displaying the information
	-->
	
	<jsp:include page="header.jsp"/>
	<form action="user?action=trackloan" method="POST">
	<div>
				<div>
					<label for="Application Number">Enter your application number</label>
				</div>
				<div>
					<input type="text" id="applicationnumber" name="applicationnumber" required placeholder="Enter your application number">
				</div>
				
				
				<div>
					<input type="submit" id="trackapplication" name="trackapplication" value="Track">
				</div>
	</div>
	</form>
	<a href="userhome1.jsp"><input type="button" name="navigateback" value="Navigate Back"></a>
	<!--  <a href="userhome1.jsp"><input type="button" name="home" value="Home"></a>-->
	<!-- <a href="userhome1.jsp">Navigate Back</a> -->
	<jsp:include page="footer.jsp"/>
	

</body>
</html>