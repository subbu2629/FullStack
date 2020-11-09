<%@page import="com.iiht.evaluation.eloan.model.LoanInfo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Loan Details</title>

</head>
<body>
<jsp:include page="header.jsp"/>

	<%

	String  loanStatus= request.getAttribute("status").toString();
	if(loanStatus.equals("Submitted"))
	{
	LoanInfo loanInfo = (LoanInfo) request.getAttribute("loanData");
	
	%>

	<form action="user?action=editLoanProcess" method="POST">
	
	
		<div align="center">
			<div>
				<div>
					<label for="loanname">Loan Name: </label><input type="text" id="loanname" value="<%=loanInfo.getPurpose() %>" name="loanname">
				</div>
			</div>
			<div>
				<div>
					<label for="loanamount">Loan Amount: </label><input type="text" id="loanamount" value="<%=loanInfo.getAmtrequest() %>" name="loanamount">
				</div>
			</div>
				<div>
				<div>
					<label for="BusinessStruture">BusinessStruture</label>
				</div>
				<div>
					<%String bstru = loanInfo.getBstructure(); %>
					
					<input type="radio" id="Individual" name="BusinessStruture" value="Individual"  <%=bstru.equals("Individual")? "checked" : ""%>> 
					<label for="Individual">Individual</label>
					
					
					<input type="radio" id="Organisation" name="BusinessStruture" value="Organisation"  <%=bstru.equals("Organisation")? "checked" : ""%>>
					 <label for="Organisation">Organisation</label>

				</div>
			</div>
			
			
			
			
			<div>
				<div>
					<label for="BillingIndicator">BillingIndicator</label>
				</div>
				<div>
				<%String bind = loanInfo.getBindicator(); %>
					<input type="radio" id="Salaried Person" name="BillingIndicator"
						value="Salaried Person" <%=bind.equals("Salaried Person")? "checked" : ""%>> <label
						for="Salaried Person">Salaried Person</label> <input type="radio"
						id="Non Salaried Person" name="BillingIndicator"
						value="Non Salaried Person" <%=bind.equals("Non Salaried Person")? "checked" : ""%>> <label
						for="Non Salaried Person">Non Salaried Person</label>
				</div>
			</div>

			

			<div>
				<div>
					<label for="Address">Address</label>
				</div>
				<div>
					<Textarea id="address" name="address" required><%=loanInfo.getAddress()%></Textarea>
				</div>
			</div>

			<div>
				<div>
					<label for="Mobile">Mobile</label>
				</div>
				<div>
					<input type="number" id="mobile" name="mobile" value="<%=loanInfo.getMobile()%>" required>
				</div>
			</div>

			<div>
				<div>
					<label for="Email">Email</label>
				</div>
				<div>
					<input type="email" id="email" name="email" value="<%=loanInfo.getEmail()%>" required>
				</div>
			</div>
			
			<input type="submit" name="updatedetails" value="Update Details">
	</form>
	
			
	
	<% } 
	else { 
		
%>

		<h2>Loan details cannot be edited as Loan-status is in <%=loanStatus %></h2>
		
<%	
	request.removeAttribute("status");	
	} %>
		
		<a href="userhome1.jsp"><input type="button" name="home" value="Home"></a>
	


<jsp:include page="footer.jsp"/>
</body>
</html>