<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bank.model.Customerlogin, java.util.*, com.bank.model.Transaction, com.bank.dao.CustomerDAO" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Dashboard</title>
    
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100..900&display=swap" rel="stylesheet">
    
    <!-- Custom CSS 
    <style>
        body {
            font-family: 'Montserrat', sans-serif;
        }
        .container-fluid {
            padding: 20px;
        }
        .welcome h4 {
            margin: 0;
        }
        .balance {
            text-align: right;
        }
        .span-view-icon {
            display: flex;
            align-items: center;
        }
        .span-view-icon .btn {
            background: none;
            border: none;
            cursor: pointer;
        }
        .span-view-icon .material-symbols-outlined {
            font-size: 24px;
        }
        .tablink {
            background-color: #f1f1f1;
            color: black;
            border: none;
            outline: none;
            cursor: pointer;
            padding: 14px 16px;
            font-size: 17px;
        }
        .tablink.active {
            background-color: #555;
            color: white;
        }
        .tabcontent {
            display: none;
            padding: 6px 12px;
            border-top: none;
        }
        .tabcontent input, .tabcontent button {
            display: block;
            margin: 10px 0;
        }
        .transactions {
            border: 1px solid #ccc;
            padding: 10px;
        }
        .transaction-item {
            display: flex;
            align-items: center;
        }
        .transaction-item p {
            margin: 0 10px;
        }
    </style>-->
    
    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    
    <!-- Custom JavaScript -->
    <script>
        $(document).ready(function(){
            fetchBalance();
        });
        
        function fetchBalance(){
            $.ajax({
                url: 'BalanceServlet',
                type: 'GET',
                success: function(data){
                    $('#balance').text(data.balance);
                },
                error: function(){
                    alert('Failure while fetching balance!');
                }
            });
        }

        function openPage(pageName, elmnt) {
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablink");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(pageName).style.display = "block";
            elmnt.className += " active";
        }
        
        document.getElementById("defaultOpen").click();
    </script>
</head>

<body>
<% 
    if (session == null || session.getAttribute("customer") == null) {
        response.sendRedirect("CustomerLogin.jsp");
        return;
    }

    Customerlogin customer = (Customerlogin) session.getAttribute("customer");
    CustomerDAO customerDAO = new CustomerDAO();
    int accountNo = (int) session.getAttribute("accountNo");
    List<Transaction> transactions = customerDAO.getTransactions(accountNo);
%>

    <div class="main container-fluid">

        <!-- Header Section -->
        <div class="row mb-4">
            <div class="col-md-8 welcome">
                <h4>Welcome back,</h4>
                <h1><%= customer.getFullName() %>!</h1>
            </div>
            <div class="col-md-4 balance text-end">
                <p>Total Balance</p>
                <div class="span-view-icon">
                    <div style="min-height: 120px;">
                        <span>Rs. </span>
                        <div class="collapse collapse-horizontal" id="showBalance">
                            <span class="text" id="balance"></span>
                        </div>
                    </div>
                    <button class="btn" type="button" data-bs-toggle="collapse" data-bs-target="#showBalance"
                        aria-expanded="false" aria-controls="showBalance">
                        <span class="material-symbols-outlined">visibility</span>
                    </button>
                </div>
            </div>
        </div>

        <!-- Main Content -->
        <div class="row">
            <!-- Options Section -->
            <div class="col-md-8">
                <div class="options">
                    <div class="options1">
                        <div class="options-2">
                            <button class="tablink" onclick="openPage('details', this)" id="defaultOpen">Details</button>
                            <button class="tablink" onclick="openPage('withdraw', this)">Withdraw</button>
                            <button class="tablink" onclick="openPage('deposit', this)">Deposit</button>
                        </div>
                        <form action="logout">
                            <button class="logout">Logout</button>
                        </form>
                    </div>
                    <hr>
                    <div class="tabcontent" id="details">
                        <div class="details-1">
                            <p>Customer Details</p>
                            <div class="details-2">
                                <div class="details3">
                                    <span class="material-symbols-outlined">Name</span>
                                    <%= customer.getFullName() %><br>
                                    <span class="material-symbols-outlined">Ph no</span>
                                    <%= customer.getMobileNo() %><br>
                                    <span class="material-symbols-outlined">Email</span>
                                    <%= customer.getEmailId() %><br>
                                    <span class="material-symbols-outlined">Date of Birth</span>
                                    <%= customer.getDateOfBirth() %><br>
                                    <span class="material-symbols-outlined">Adderess</span>
                                    <%= customer.getAddress() %><br>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="details-1">
                            <p>Account Details</p>
                            <div class="details-2">
                                <div class="details3">
                                    <span class="material-symbols-outlined">account_balance</span>
                                    <%= customer.getAccountNo() %><br>
                                    <span class="material-symbols-outlined">savings</span>
                                    <%= customer.getAccountType() %>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="withdraw" class="tabcontent">
                        <form action="WithdrawServlet" method="post">
                            <label for="withdraw">Enter Amount</label><br>
                            <input type="number" id="withdraw" name="amount" min="1" placeholder="INR"><br>
                            <button class="btn btn-primary" type="submit">Submit</button>
                        </form>
                    </div>

                    <div id="deposit" class="tabcontent">
                        <form action="DepositServlet" method="post">
                            <label for="deposit">Enter Amount</label><br>
                            <input type="number" id="deposit" name="amount" min="1" placeholder="INR"><br>
                            <button class="btn btn-primary" type="submit">Submit</button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Transactions Section -->
            <div class="col-md-4">
                <div class="transactions">
                    <div class="title">
                        <div class="title-text">Transaction History</div>
                        <div class="title-icon-class">
                            <button class="btn" type="button" data-bs-toggle="collapse" aria-expanded="false" 
                                aria-controls="showTransactions" data-bs-target="#showTransactions">
                                <span class="material-symbols-outlined">visibility</span>
                            </button>
                            <form action="downloadPDF" method="get" class="d-inline">
                                <button class="btn" type="submit">
                                    <span class="material-symbols-outlined">print</span>
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="transaction-list collapse" id="showTransactions">
                        <% for (Transaction transaction : transactions) { %>
                            <div class="transaction-item">
                                <p><%= transaction.getTransactionType() %></p>
                                <p><%= transaction.getAmount() %></p>
                                <hr>
                            </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
