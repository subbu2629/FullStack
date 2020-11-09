<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.iiht.evaluation.eloan.dto.UserInfo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>user home</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<%
	UserInfo userDetails = (UserInfo) session.getAttribute("userInfo");

%>

<h1>Hi <%=userDetails.getFirstName()%></h1>

<h4>User Dash Board</h4>
<div align="right"><a href="index.jsp">Logout</a></div>
<a href="user?action=application&state=new">Apply for Loan</a><br>
<a href="trackloan.jsp">Track Loan Application</a><br>
<a href="editloan.jsp">Edit Loan Application</a>
<jsp:include page="footer.jsp"/>
</body>
</html>