<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="com.bank.dao.AdminDAO,com.bank.model.Customerlogin,java.util.*" %>
          
      
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Registration</title>
</head>

<body>
<%
	AdminDAO admin = new AdminDAO();
	List<Customerlogin> customers = admin.getCustomerDetails();

%>

    <div class="container">
        <div class="tabs-container">
            <div class="tabs">
                <button class="tab-button" id="customer-details-tab" onclick="showCustomerDetails()">Customer
                    Details</button>
                <button class="tab-button active" id="customer-registration-tab"
                    onclick="showCustomerRegistration()">Customer Registration</button>
            </div>
            <div class="logout">
                <form action="adminlogout">
                	<button class="tab-button" id="logout" type="submit">Logout</button>
                </form>
            </div>
        </div>
        <div class="content">
            <div class="form-container" id="customer-registration">
                <form class="registration-form" action="RegisterCustomerServlet" method="post">
                    <h2>Personal Details</h2>
                    <div class="field">
                        <label for="customer-name">Customer Name</label>
                        <input type="text" id="customer-name" placeholder="Full Name"
                            class="cname" name="fullName" required>
                    </div>
                    <div class="field">
                        <label for="address">Address</label>
                        <input type="text" id="address" placeholder="Address" name="address" required>
                    </div>
                    <div class="field">
                        <div class="row">
                            <div class="column">
                                <label for="mobile-number">Mobile Number</label>
                                <input type="number" id="mobile-number" placeholder="+91"
                                    class="rowint"  name="mobileNo" required>
                            </div>
                            <div class="column">
                                <label for="mail-id">Mail ID</label>
                                <input type="email" id="mail-id" name="emailId" required placeholder="example@xyz.com"
                                    class="rowint">
                            </div>
                            <div class="column">
                                <label for="date-of-birth">Date Of Birth</label>
                                <input type="date" id="date-of-birth" name="dob" required placeholder="DD/MM/YYYY"
                                    class="rowint">
                            </div>
                        </div>
                    </div>

                    <h2>Account Details</h2>
                    <div class="field">
                        <div class="account-details-row">
                            <div class="account-detail">
                                <label for="account-type">Account Type</label>
                                <select id="account-type" name="accountType">
                                    <option value="" disabled selected>Select</option>
                                    <option value="savings">Savings</option>
                                    <option value="current">Current</option>
                                </select>
                            </div>
                            <div class="account-detail">
                                <label for="initial-balance">Initial Balance</label>
                                <input type="number" id="initial-balance" name="initialBalance" min="1000" required
                                    placeholder="Min Rs.1000">
                            </div>
                            <div class="account-detail">
                                <label for="id-proof">ID Proof</label>
                                <select id="id-proof" name="idProof">
                                    <option value="" disabled selected>Select</option>
                                    <option value="savings">Pan Card</option>
                                    <option value="current">Aadhar Card</option>
                                    <option value="current">Passport</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="createbtn">
                        <button class="resett" type="reset"> Reset</button>
                        <button class="create" type="submit">Create Account</button>

                    </div>

                </form>
            </div>

        </div>
        
       
        <div class="form-container hidden" id="customer-details">	
         <br><br>
        
	        <form action="CustomerDetailsServlet" method="post" class="CustomerDetails">
	        
		        <label>Enter Account No:</label>
		        <input type="text" name="accountno"  required>
				<button class="btn btn-primary" type="submit">
					Actions
				</button>
			</form> <br><br>			
            <h2>Customer Details</h2>
            
            <table class="table table-hover">
				<thead>
					<th>Account No.</th>
					<th>Full Name </th>
					<th> Actions</th>
				</thead>
				<%
					for(Customerlogin customer:customers){
						%>
						<tr>
							<td> <%= customer.getAccountNo() %></td>
							<td> <%= customer.getFullName() %>
							<td>
							</td>
						</tr>
					<%}
				%>
				</table>
        </div>
    </div>


    <script>
        function showCustomerDetails() {
            document.getElementById('customer-registration-tab').classList.remove('active');
            document.getElementById('customer-details-tab').classList.add('active');
            document.getElementById('customer-registration').classList.add('hidden');
            document.getElementById('customer-details').classList.remove('hidden');
            document.querySelector('.content').style.transform = 'translateX(-100%)';
        }

        function showCustomerRegistration() {
            document.getElementById('customer-details-tab').classList.remove('active');
            document.getElementById('customer-registration-tab').classList.add('active');
            document.getElementById('customer-details').classList.add('hidden');
            document.getElementById('customer-registration').classList.remove('hidden');
            document.querySelector('.content').style.transform = 'translateX(0)';
        }

        function logout() {
            window.location.href = '/logout';
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>

</html>
