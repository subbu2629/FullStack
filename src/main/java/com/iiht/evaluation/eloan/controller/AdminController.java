package com.iiht.evaluation.eloan.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.eloan.dao.ConnectionDao;
import com.iiht.evaluation.eloan.dto.LoanDto;
import com.iiht.evaluation.eloan.model.ApprovedLoan;
import com.iiht.evaluation.eloan.model.LoanInfo;


@WebServlet("/admin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ConnectionDao connDao;
	
	public void setConnDao(ConnectionDao connDao) {
		this.connDao = connDao;
	}
	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config.getServletContext().getInitParameter("jdbcPassword");
		System.out.println(jdbcURL + jdbcUsername + jdbcPassword);
		this.connDao = new ConnectionDao(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action =  request.getParameter("action");
		System.out.println(action);
		String viewName = "";
		try {
			switch (action) {
			case "listall" : 
				viewName = listall(request, response);
				break;
			case "process":
				viewName=process(request,response);
				break;
			case "calemi":
				viewName=calemi(request,response);
				break;
			case "updatestatus":
				viewName=updatestatus(request,response);
				break;
			case "logout":
				viewName = adminLogout(request, response);
				break;	
			default : viewName = "notfound.jsp"; break;		
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		RequestDispatcher dispatch = 
					request.getRequestDispatcher(viewName);
		dispatch.forward(request, response);
		
		
	}

	private String updatestatus(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub	
		/* write the code for updatestatus of loan and return to admin home page */
		HttpSession session = request.getSession();
		System.out.println("updatestatus");
		LoanInfo loaninfo = (LoanInfo) session.getAttribute("loaninfo");
		
		boolean flag = connDao.updateLoanStatus(loaninfo.getApplno(), session.getAttribute("updateStatus").toString());
		System.out.println(flag);
		session.setAttribute("statusUpateFlag", Boolean.toString(flag));
		System.out.println(session.getAttribute("statusUpateFlag"));
		
		return "calemi.jsp";
		
	}
	private String calemi(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
	/* write the code to calculate emi for given applno and display the details */
		HttpSession session = request.getSession();
		System.out.println("Calemi Method");
		LoanInfo loanData = (LoanInfo) session.getAttribute("loaninfo");
		
		if(request.getParameter("status").equals("Approved"))
		{
			
		System.out.println("Approved");
		ApprovedLoan approvedLoan=new ApprovedLoan();
		approvedLoan.setApplno(loanData.getApplno());
		approvedLoan.setAmotsanctioned(Integer.parseInt(request.getParameter("amountsanctioned")));
		approvedLoan.setLoanterm(Integer.parseInt(request.getParameter("loanterm")));
		approvedLoan.setInterestrate(Double.parseDouble(request.getParameter("interestrate")));
		approvedLoan.setPsd(request.getParameter("paymentstartdate"));
		approvedLoan.setLcd(getLastPaymentDate(request.getParameter("paymentstartdate"), Integer.parseInt(request.getParameter("loanterm"))));
		double termPaymentAmount = getTermPaymentAmount(Integer.parseInt(request.getParameter("amountsanctioned")),Double.parseDouble(request.getParameter("interestrate")),Integer.parseInt(request.getParameter("loanterm")));
		approvedLoan.setTermpaymentamount(termPaymentAmount);
		approvedLoan.setEmi(getMonthlyPayment(termPaymentAmount, Integer.parseInt(request.getParameter("loanterm"))));
		
		boolean resultFlag  = connDao.addLoanSanctionDetails(approvedLoan);
		session.setAttribute("dataInsertFlag", resultFlag);
		System.out.println(session.getAttribute("dataInsertFlag"));
		session.setAttribute("applicationNum", loanData.getApplno());
		
		
		}
		else
		{
			session.setAttribute("dataInsertFlag", "declined");
		}
		
		session.setAttribute("updateStatus", request.getParameter("status"));
		
		return updatestatus(request, response);
	}
	private String process(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
	/* return to process page */

		System.out.println("Process");
		if(request.getParameter("applicationnumber")!=null)
		{
			System.out.println("pp");
			LoanInfo fetchedLoanData = connDao.getLoanInfoByAppNumber(request.getParameter("applicationnumber"));
			request.setAttribute("fetchedLoanData", fetchedLoanData);
		}		
		
		
		return "process.jsp";
		
		
		//return  null;
	}
	private String adminLogout(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	/* write code to return index page */
		return null;
	}

	private String listall(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	/* write the code to display all the loans */
		ArrayList<LoanInfo> list_loans = connDao.getLoanInfo();
		request.setAttribute("list_loans", list_loans);
		
		String view = "listall.jsp";
		
		return view;
		
	}

	
	public static double getTermPaymentAmount(double sanctionLoan, double interestRate, int termOfLoan)
	{
		return (sanctionLoan * Math.pow(1 + (interestRate/100), termOfLoan)); 
	}
	
	public static double getMonthlyPayment(double termPaymentAmount, int termLoan)
	{
		return termPaymentAmount / termLoan;
	}
	
	public static String getLastPaymentDate(String startDate, int termLoan)
	{
		 LocalDate date = LocalDate.parse(startDate); 
		 LocalDate returnvalue = date.plusMonths(termLoan);

		 System.out.println(returnvalue.toString()); 

		return returnvalue.toString();
	}
	
	
}