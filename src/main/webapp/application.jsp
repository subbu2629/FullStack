<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.iiht.evaluation.eloan.model.LoanInfo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Loan Application Form</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
</head>
<body onload="myFunction()">

	<!--
	write the html code to accept laon info from user and send to placeloan servlet
-->


	<jsp:include page="header.jsp" />
	
	
  	<% if(request.getAttribute("state").equals("new") ){ 
		//request.setAttribute("state", "submitted");
	%>
	
	<form action="user?action=placeloan" method="POST">
		<div align="center">


			<!--  <table>
			<tr><td>Loan Amount</td><td><strong>${loan.fullName }</strong></td></tr>
			
		</table>-->
			<!--  <div>
			<div><label for="loginid">Enter your full name</label> </div>
			<div><input type="text" id="loginid" name="loginid"> </div>
		</div>-->

			<div>
				<div>
					<label for="Loan Name">Loan Name</label>
				</div>
				<div>
					<input type="text" id="loanname" name="loanname" required>
				</div>
			</div>

			<div>
				<div>
					<label for="amount">Enter your amount</label>
				</div>
				<div>
					<input type="number" id="loanamount" name="loanamount" required>
				</div>
			</div>

			<div>
				<div>
					<label for="Loan Application Date">Loan Application Date</label>
				</div>
				<div>
					<input type="text" id="loanapplicationdate"
						name="loanapplicationdate">
				</div>
			</div>



			<div>
				<div>
					<label for="BusinessStruture">BusinessStruture</label>
				</div>
				<div>

					<input type="radio" id="Individual" name="BusinessStruture"
						value="Individual" required> <label for="Individual">Individual</label>
					<input type="radio" id="Organisation" name="BusinessStruture"
						value="Organisation" required> <label for="Organisation">Organisation</label>

				</div>
			</div>

			<div>
				<div>
					<label for="BillingIndicator">BillingIndicator</label>
				</div>
				<div>
					<input type="radio" id="Salaried Person" name="BillingIndicator"
						value="Salaried Person" required> <label
						for="Salaried Person">Salaried Person</label> <input type="radio"
						id="Non Salaried Person" name="BillingIndicator"
						value="Non Salaried Person" required> <label
						for="Non Salaried Person">Non Salaried Person</label>
				</div>
			</div>

			<!--  <div>
				<div>
					<label for="TaxIndicator">TaxIndicator</label>
				</div>
				<div>
					<input type="radio" id="TaxPayee" name="TaxIndicator" value="Yes" required>
					<label for="Yes">Yes</label> 
					<input type="radio" id="No" name="TaxIndicator" value="No" required> 
						<label for="No">No</label>
				</div>
			</div>-->

			<div>
				<div>
					<label for="Address">Address</label>
				</div>
				<div>
					<Textarea id="address" name="address" required></Textarea>
				</div>
			</div>

			<div>
				<div>
					<label for="Mobile">Mobile</label>
				</div>
				<div>
					<input type="number" id="mobile" name="mobile" required>
				</div>
			</div>

			<div>
				<div>
					<label for="Email">Email</label>
				</div>
				<div>
					<input type="email" id="email" name="email" required>
				</div>
			</div>

			<input type="submit" value="Submit">
		</div>
		
		
	</form>
	
	  <% } else { 
		
		String state = request.getAttribute("state").toString();
	  	out.print(state);
		out.println("-------------------");
		if(state.equalsIgnoreCase("submitted"))
		{
			LoanInfo loanData =	(LoanInfo) request.getAttribute("loanDetails");
			String applicationID = loanData.getApplno();
		
		%>
			<p>Loan details submitted successfully !!</p>
			<p>Application ID : <%=applicationID %></p>
		<% }
		else
		{ %>
			<p>Loan details submission Unsuccessfully !!</p>
			
	<% } %>
		
	<a href="userhome1.jsp">Navigate Back</a>
	<% } %>
	
	
	
	<jsp:include page="footer.jsp" />


</body>
</html>