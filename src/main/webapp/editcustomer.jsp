<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Customer Registration</title>
        <style>
            
        </style>
    </head>

    <body>
        <div class="container">
            <div class="tabs-container">
            </div>
            <div class="content">
                <div class="form-container" id="customer-registration">
                    <form class="registration-form" id="registration-form" action="UpdateCustomerDetailsServlet"
                        method="post">
                        <input type="hidden" name="accountno" value="${account_no}">
                        <h2>Edit Customer Details</h2>
                        <div class="field">
                            <label for="customer-name">Customer Name</label>
                            <input type="text" id="customer-name" name="full_name" placeholder="Full Name" class="cname"
                                value="${full_name}" required>
                        </div>
                        <div class="field">
                            <label for="address">Address</label>
                            <input type="text" id="address" name="address" placeholder="Address" value="${address}"
                                required>
                        </div>
                        <div class="field">
                            <div class="row">
                                <div class="column">
                                    <label for="mobile-number">Mobile Number</label>
                                    <input type="number" id="mobile-number" name="mobile_no" placeholder="+91"
                                        class="rowint" required value="${mobile_no}">
                                </div>
                                <div class="column">
                                    <label for="mail-id">Mail ID</label>
                                    <input type="email" id="mail-id" name="email_id" placeholder="example@xyz.com"
                                        class="rowint" required value="${email_id}">
                                </div>
                                <div class="column">
                                    <label for="date-of-birth">Date Of Birth</label>
                                    <input type="date" id="date-of-birth" name="date_of_birth" placeholder="DD/MM/YYYY"
                                        class="rowint" required value="${date_of_birth}">
                                </div>
                            </div>
                        </div>

                        <div class="createbtn">
                            <button class="create" type="submit">Save Changes</button>
                            </form>
                            <form action="DeleteCustomer" method="post">
                            <input type="hidden" name="accountno" value="${account_no}">
                            <button class="delete" type="submit">Delete</button>
                            </form>
                        </div>
                </div>
            </div>

        </div>
        </div>
        <div class="form-container hidden" id="customer-details">
            <h2>Customer Details</h2>
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

            document.getElementById('registration-form').addEventListener('submit', function (event) {

                if (!this.checkValidity()) {
                    event.preventDefault();
                    alert('Please fill out all required fields.');
                }
            });
        </script>
    </body>

    </html>