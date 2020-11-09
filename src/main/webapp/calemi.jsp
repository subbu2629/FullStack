<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <!--
     Read the values from the admin servlet and cal emi and other details and send to
     to the same admin servlet to update the values in the database 
  -->  
  
  
  <%
  
  String dataInsertFlag = request.getAttribute("dataInsertFlag").toString();
  String statusUpateFlag = request.getAttribute("statusUpateFlag").toString();
  
  out.print(dataInsertFlag);
  out.print(statusUpateFlag);
  
  if(dataInsertFlag.equals("true")) { %>
  
  <h1>Loan Approved successfully !!</h1>
  
  	<% } else if(dataInsertFlag.equals("true")) { %>
	
	 <h1>Loan Declined successfully !!</h1>

	<% } else {%>

	<h1>Loan Approved Unsuccessfully !!</h1>

	<% } %>
	
	<a href="adminhome1.jsp"><input type="button" name="home" value="Home"></a>

</body>
</html>