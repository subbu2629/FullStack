package com.iiht.evaluation.eloan.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;

import com.iiht.evaluation.eloan.dto.LoanDto;
import com.iiht.evaluation.eloan.dto.UserInfo;
import com.iiht.evaluation.eloan.model.ApprovedLoan;
import com.iiht.evaluation.eloan.model.LoanInfo;
import com.iiht.evaluation.eloan.model.User;


public class ConnectionDao {
	private static final long serialVersionUID = 1L;
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection = null;
	
	
	public ConnectionDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

	public  Connection connect() throws SQLException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		return jdbcConnection;
	}

	public void disconnect() throws SQLException {
		//if (!jdbcConnection.isClosed()) {//jdbcConnection != null && 
			jdbcConnection.close();
		//}
	}
	

	/*======================= All SQL queries used in DAO file ====================*/
	static final String FETCH_USER_CRED = "select user_name, user_password from eloan.user;";
	static final String FETCH_USER_INFO = "select * from eloan.userinfo where user_name='%s'";
	static final String FETCH_LOAN_INFO = "select * from eloan.loan where mobile='%s'";
	static final String FETCH_LOAN_INFO_BY_ID = "select status from eloan.loan where applno='%s' and user_name='%s'";
	static final String FETCH_LOAN_ALLINFO_BY_ID = "select * from eloan.loan where applno=%s";
	static final String FETCH_ALL_LOAN_INFO = "select * from eloan.loan";
	static final String UPDATE_LOAN_INFO_BY_ID = "UPDATE eloan.loan SET USER_NAME='%s',purpose='%s',amtrequest=%s,bstructure='%s',bindicator='%s',address='%s',email='%s',mobile='%s' where applno='%s' and status='Submitted'";
	static final String UPDATE_LOAN_STATUS_BY_ID = "UPDATE eloan.loan SET status='%s' WHERE applno ='%s'";
	public static final String INS_LOANINFO_QRY = "INSERT INTO eloan.loan(USER_NAME,purpose,amtrequest,doa,bstructure,bindicator,address,email,mobile,status) VALUES(?,?,?,?,?,?,?,?,?,?)";
	public static final String INS_LOANSANCTION_QRY = "INSERT INTO eloan.ApprovedLoan(applno,AmountSanctioned,LoanTerm,PaymentStartDate,LoanClosureDate,InterestRate,TermPaymentAmount,MonthlyPayment) VALUES(?,?,?,?,?,?,?,?)";
	public static final String INS_USERCRED_QRY = "INSERT INTO eloan.User(USER_NAME,USER_PASSWORD) VALUES(?,?)";
	public static final String INS_USERINFO_QRY = "INSERT INTO eloan.UserInfo(USER_NAME,USER_FIRSTNAME,USER_LASTNAME,MOBILE,EMAIL,CITY,STATE,COUNTRY,ZIPCODE,CUSTOMER_ADDRESS,DOB) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	
	/*=========================================================================*/
	
	public boolean addLoanDetails(LoanInfo loanInfo) {
		PreparedStatement pst = null;
		
		boolean successFlag = false;
		
		if(loanInfo!=null) {
			try {
				jdbcConnection = this.connect();
				pst = jdbcConnection.prepareStatement(INS_LOANINFO_QRY);
				//purpose
				pst.setString(1, loanInfo.getUserName());
				pst.setString(2, loanInfo.getPurpose());
				pst.setString(3, loanInfo.getAmtrequest());
				pst.setString(4, loanInfo.getDoa());
				pst.setString(5, loanInfo.getBstructure());
				pst.setString(6,loanInfo.getBindicator());
				pst.setString(7, loanInfo.getAddress());
				pst.setString(8, loanInfo.getEmail());
				pst.setString(9, loanInfo.getMobile());
				pst.setString(10, "Submitted");
				
				int updatedRows = pst.executeUpdate();
				
				successFlag = updatedRows==1;
				
			}catch(SQLException exp) {
				System.out.println(exp.getMessage());
			}
			finally {
				try {
					pst.close();
					this.disconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
		return successFlag;
	}
	
	public HashMap<String, String> getUserCredData()
	{
		Statement stmt = null;
		HashMap<String, String> cred_map = new HashMap<String, String>();
		
		try
		{
			jdbcConnection = this.connect();
			stmt = jdbcConnection.createStatement();
			ResultSet rs = stmt.executeQuery(FETCH_USER_CRED);

			while(rs.next())
			{
				cred_map.put(rs.getString("user_name"), rs.getString("user_password"));
			}
			return cred_map;
		} 
		catch(Exception e)
		{
			System.out.println("Unable to fetch User credential data");
			System.out.println("Error : "+e.getMessage());
			return cred_map;
		}
		finally 
		{
			try 
			{
				stmt.close();
				this.disconnect();
			} 
			catch (SQLException e) 
			{
				System.out.println("Error : "+ e.getMessage());
			}
		}
	}
		
		
	public UserInfo getUserInfo(String userName) 
	{
			Statement stmt = null;
			UserInfo userInfo = null;

			try
			{
				jdbcConnection = this.connect();
				stmt = jdbcConnection.createStatement();
				ResultSet rs = stmt.executeQuery(String.format(FETCH_USER_INFO,userName));
				while(rs.next())
				{
					System.out.println(rs.getString("USER_FIRSTNAME")+"-"+rs.getString("USER_LASTNAME"));
					
					userInfo = new UserInfo(rs.getString("USER_NAME"), rs.getString("USER_FIRSTNAME"), 
							rs.getString("USER_LASTNAME"), rs.getString("DOB"), rs.getString("MOBILE"), rs.getString("EMAIL"), 
							rs.getString("CITY"), rs.getString("STATE"), rs.getString("COUNTRY"), 
							rs.getString("ZIPCODE"), rs.getString("CUSTOMER_ADDRESS"));
					
				}
				return userInfo;
			} 
			catch(Exception e)
			{
				System.out.println("Unable to fetch User Info data");
				System.out.println("Error : "+e.getMessage());
				return userInfo;
			}
			finally 
			{
				try 
				{
					stmt.close();
					this.disconnect();
				} 
				catch (SQLException e) 
				{
					System.out.println("Error : "+ e.getMessage());
				}
			}
	}

	public LoanInfo getLoanData(String mobile) {
		
		
		Statement stmt = null;
		LoanInfo loanInfo = null;

		try
		{
			jdbcConnection = this.connect();
			stmt = jdbcConnection.createStatement();
			ResultSet rs = stmt.executeQuery(String.format(FETCH_LOAN_INFO,mobile));
			while(rs.next())
			{
				loanInfo = new LoanInfo(rs.getString("applno"), rs.getString("purpose"), rs.getString("amtrequest"), 
						rs.getString("doa"), rs.getString("bstructure"), rs.getString("bindicator"), rs.getString("address"), 
						rs.getString("email"), rs.getString("mobile"), rs.getString("status"));
			}
			return loanInfo;
		} 
		catch(Exception e)
		{
			System.out.println("Unable to fetch Loan Info data");
			System.out.println("Error : "+e.getMessage());
			return loanInfo;
		}
		finally 
		{
			try 
			{
				stmt.close();
				this.disconnect();
			} 
			catch (SQLException e) 
			{
				System.out.println("Error : "+ e.getMessage());
			}
		}
		
		
		
	}
	
	public String getLoanById(String applno, String userName) {
		
		Statement stmt = null;
		String status = "";
		try
		{
			jdbcConnection = this.connect();
			stmt = jdbcConnection.createStatement();
			ResultSet rs = stmt.executeQuery(String.format(FETCH_LOAN_INFO_BY_ID,applno, userName));
			while(rs.next())
			{
				status = rs.getString("status");
			}
			return status;
		} 
		catch(Exception e)
		{
			System.out.println("Unable to fetch Status data");
			System.out.println("Error : "+e.getMessage());
			return status;
		}
		finally 
		{
			try 
			{
				stmt.close();
				this.disconnect();
			} 
			catch (SQLException e) 
			{
				System.out.println("Error : "+ e.getMessage());
			}
		}
		
		
		
	}
	
	public LoanInfo getLoanInfoByAppNumber(String applNo) {
		
		
		Statement stmt = null;
		LoanInfo loanInfo = null;
System.out.println("fff");
		try
		{
			jdbcConnection = this.connect();
			stmt = jdbcConnection.createStatement();
			ResultSet rs = stmt.executeQuery(String.format(FETCH_LOAN_ALLINFO_BY_ID,applNo));
			while(rs.next())
			{
				

				loanInfo = new LoanInfo(rs.getString("USER_NAME"),rs.getString("applno"), rs.getString("purpose"), rs.getString("amtrequest"), 
						rs.getString("doa"), rs.getString("bstructure"), rs.getString("bindicator"), rs.getString("address"), 
						rs.getString("email"), rs.getString("mobile"), rs.getString("status"));
			}
			return loanInfo;
		} 
		catch(Exception e)
		{
			System.out.println("Unable to fetch Loan Info data");
			System.out.println("Error : "+e.getMessage());
			return loanInfo;
		}
		finally 
		{
			try 
			{
				stmt.close();
				this.disconnect();
			} 
			catch (SQLException e) 
			{
				System.out.println("Error : "+ e.getMessage());
			}
		}
		
		
		
	}
	
	public ArrayList<LoanInfo> getLoanInfo() {
			
			
			Statement stmt = null;
			LoanInfo loanInfo = null;
			ArrayList<LoanInfo> list_loans =new ArrayList<LoanInfo>();
			
			
			try
			{
				jdbcConnection = this.connect();
				stmt = jdbcConnection.createStatement();
				ResultSet rs = stmt.executeQuery(String.format(FETCH_ALL_LOAN_INFO));
				while(rs.next())
				{
					loanInfo = new LoanInfo(rs.getString("user_name"), rs.getString("applno"), rs.getString("purpose"), rs.getString("amtrequest"), 
							rs.getString("doa"), rs.getString("bstructure"), rs.getString("bindicator"), rs.getString("address"), 
							rs.getString("email"), rs.getString("mobile"), rs.getString("status"));
					list_loans.add(loanInfo);
				}
				
				return list_loans;
			} 
			catch(Exception e)
			{
				System.out.println("Unable to fetch Loan Info data");
				System.out.println("Error : "+e.getMessage());
				return list_loans;
			}
			finally 
			{
				try 
				{
					stmt.close();
					this.disconnect();
				} 
				catch (SQLException e) 
				{
					System.out.println("Error : "+ e.getMessage());
				}
			}
			
			
			
		}
	
	public boolean updateLoanInfoByAppNumber(LoanInfo loanData) {
		
		PreparedStatement pst = null;
		boolean successFlag = false;
	
		try
		{
			jdbcConnection = this.connect();
			System.out.println("insert query");
			pst = jdbcConnection.prepareStatement(String.format(UPDATE_LOAN_INFO_BY_ID,loanData.getUserName(), loanData.getPurpose(), loanData.getAmtrequest(), loanData.getBstructure(), loanData.getBindicator(), loanData.getAddress(), loanData.getEmail(), loanData.getMobile(), loanData.getApplno()));
			int updatedRows = pst.executeUpdate();
			System.out.println(updatedRows);
			successFlag = updatedRows==1;
			return successFlag;
		} 
		catch(Exception e)
		{
			System.out.println("Unable to fetch Loan Info data");
			System.out.println("Error : "+e.getMessage());
			return successFlag;
		}
		finally 
		{
			try 
			{
				pst.close();
				this.disconnect();
			} 
			catch (SQLException e) 
			{
				System.out.println("Error : "+ e.getMessage());
			}
		}
	}
	
	public boolean updateLoanStatus(String applno,String status) {
		
		PreparedStatement pst = null;
		boolean successFlag = false;
	
		try
		{
			jdbcConnection = this.connect();
			System.out.println("insert query");
			pst = jdbcConnection.prepareStatement(String.format(UPDATE_LOAN_STATUS_BY_ID,status,applno));
			int updatedRows = pst.executeUpdate();
			System.out.println(updatedRows);
			successFlag = updatedRows==1;
			return successFlag;
			
		} 
		catch(Exception e)
		{
			System.out.println("Unable to fetch Loan Info data");
			System.out.println("Error : "+e.getMessage());
			return successFlag;
		}
		finally 
		{
			try 
			{
				pst.close();
				this.disconnect();
			} 
			catch (SQLException e) 
			{
				System.out.println("Error : "+ e.getMessage());
			}
		}
	}
	
	public boolean addLoanSanctionDetails(ApprovedLoan approvedLoan) {
		PreparedStatement pst = null;
		System.out.println("addLoanSanctionDetails");
		boolean successFlag = false;
		
		if(approvedLoan!=null) {
			try {
				jdbcConnection = this.connect();
				pst = jdbcConnection.prepareStatement(INS_LOANSANCTION_QRY);
				//purpose
				pst.setString(1, approvedLoan.getApplno());
				pst.setInt(2, approvedLoan.getAmotsanctioned());
				pst.setInt(3, approvedLoan.getLoanterm());
				pst.setString(4, approvedLoan.getPsd());
				pst.setString(5, approvedLoan.getLcd());
				pst.setDouble(6, approvedLoan.getInterestrate());
				pst.setDouble(7, approvedLoan.getTermpaymentamount());
				pst.setDouble(8, approvedLoan.getEmi());
				
				//INSERT INTO eloan.ApprovedLoan(applno,AmountSanctioned,LoanTerm,PaymentStartDate,LoanClosureDate,InterestRate,TermPaymentAmount,MonthlyPayment) VALUES(?,?,?,?,?,?,?,?)
				
				//INSERT INTO eloan.ApprovedLoan(applno,AmountSanctioned,LoanTerm,PaymentStartDate,LoanClosureDate,InterestRate,TermPaymentAmount,MonthlyPayment) VALUES(2,1000,1,'2020-01-01','2020-02-01',10,1100,1100);
	
				int updatedRows = pst.executeUpdate();
				
				successFlag = updatedRows==1;
				
			}catch(SQLException exp) {
				System.out.println(exp.getMessage());
			}
			finally {
				try {
					pst.close();
					this.disconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
		return successFlag;
	}
	
	
	public boolean registerNewUser(User user) {
			
			PreparedStatement pst = null;
			boolean successFlag = false;
			
			try {
					jdbcConnection = this.connect();
					pst = jdbcConnection.prepareStatement(INS_USERCRED_QRY);
	
					pst.setString(1, user.getUsername());
					pst.setString(2, user.getPassword());
					
					int updatedRows = pst.executeUpdate();
					
					successFlag = updatedRows==1;
					
				}catch(SQLException exp) {
					System.out.println(exp.getMessage());
				}
				finally {
					try {
						pst.close();
						this.disconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			return successFlag;
		}
	
	
	public boolean registerNewUserInfo(UserInfo userInfo) {
		
		PreparedStatement pst = null;
		boolean successFlag = false;

		try {
				jdbcConnection = this.connect();
				pst = jdbcConnection.prepareStatement(INS_USERINFO_QRY);

				pst.setString(1, userInfo.getUsername());
				pst.setString(2, userInfo.getFirstName());
				pst.setString(3, userInfo.getLastName());
				pst.setString(4, userInfo.getMobile());
				pst.setString(5, userInfo.getEmail());
				pst.setString(6, userInfo.getCity());
				pst.setString(7, userInfo.getState());
				pst.setString(8, userInfo.getCountry());
				pst.setString(9, userInfo.getPincode());
				pst.setString(10, userInfo.getAddress());
				pst.setString(11, userInfo.getDob());
				int updatedRows = pst.executeUpdate();
				
				successFlag = updatedRows==1;
				
			}catch(SQLException exp) {
				System.out.println(exp.getMessage());
			}
			finally {
				try {
					pst.close();
					this.disconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		return successFlag;
	}


}

