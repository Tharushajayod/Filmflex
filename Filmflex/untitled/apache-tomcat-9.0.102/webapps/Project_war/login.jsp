<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Login</title>--%>
<%--    <style>--%>
<%--        body {--%>
<%--            font-family: 'Montserrat', sans-serif;--%>
<%--            background: linear-gradient(to right, #e2e2e2, #fad6da);--%>
<%--            display: flex;--%>
<%--            justify-content: center;--%>
<%--            align-items: center;--%>
<%--            height: 100vh;--%>
<%--            margin: 0;--%>
<%--        }--%>
<%--        .login-container {--%>
<%--            background-color: #fff;--%>
<%--            padding: 40px;--%>
<%--            border-radius: 15px;--%>
<%--            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);--%>
<%--            width: 100%;--%>
<%--            max-width: 400px;--%>
<%--            text-align: center;--%>
<%--        }--%>
<%--        h1 {--%>
<%--            color: #e86471;--%>
<%--            margin-bottom: 20px;--%>
<%--            font-size: 24px;--%>
<%--        }--%>
<%--        .social-icons {--%>
<%--            margin: 20px 0;--%>
<%--        }--%>
<%--        .social-icons a {--%>
<%--            display: inline-flex;--%>
<%--            justify-content: center;--%>
<%--            align-items: center;--%>
<%--            margin: 0 5px;--%>
<%--            width: 40px;--%>
<%--            height: 40px;--%>
<%--            border: 1px solid #ddd;--%>
<%--            border-radius: 50%;--%>
<%--            color: #333;--%>
<%--            text-decoration: none;--%>
<%--        }--%>
<%--        .divider {--%>
<%--            margin: 20px 0;--%>
<%--            color: #999;--%>
<%--            position: relative;--%>
<%--        }--%>
<%--        .divider::before {--%>
<%--            content: "";--%>
<%--            position: absolute;--%>
<%--            top: 50%;--%>
<%--            left: 0;--%>
<%--            right: 0;--%>
<%--            height: 1px;--%>
<%--            background-color: #eee;--%>
<%--            z-index: -1;--%>
<%--        }--%>
<%--        .divider span {--%>
<%--            background-color: #fff;--%>
<%--            padding: 0 10px;--%>
<%--        }--%>
<%--        input[type="text"], input[type="password"] {--%>
<%--            width: 100%;--%>
<%--            padding: 12px 15px;--%>
<%--            margin: 8px 0;--%>
<%--            border: 1px solid #ddd;--%>
<%--            border-radius: 8px;--%>
<%--            font-size: 14px;--%>
<%--            box-sizing: border-box;--%>
<%--        }--%>
<%--        input[type="submit"] {--%>
<%--            background-color: #e86471;--%>
<%--            color: white;--%>
<%--            border: none;--%>
<%--            padding: 12px 30px;--%>
<%--            margin-top: 15px;--%>
<%--            border-radius: 8px;--%>
<%--            cursor: pointer;--%>
<%--            font-size: 14px;--%>
<%--            font-weight: 600;--%>
<%--            width: 100%;--%>
<%--            transition: background-color 0.3s;--%>
<%--        }--%>
<%--        input[type="submit"]:hover {--%>
<%--            background-color: #c95762;--%>
<%--        }--%>
<%--        .forgot-password {--%>
<%--            margin: 15px 0;--%>
<%--        }--%>
<%--        .forgot-password a {--%>
<%--            color: #e86471;--%>
<%--            text-decoration: none;--%>
<%--            font-size: 13px;--%>
<%--        }--%>
<%--        .error {--%>
<%--            color: #e53e3e;--%>
<%--            margin-top: 15px;--%>
<%--            font-size: 14px;--%>
<%--        }--%>
<%--        .switch-form {--%>
<%--            margin-top: 20px;--%>
<%--            font-size: 14px;--%>
<%--        }--%>
<%--        .switch-form a {--%>
<%--            color: #e86471;--%>
<%--            text-decoration: none;--%>
<%--            font-weight: 600;--%>
<%--        }--%>
<%--    </style>--%>
<%--    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap" rel="stylesheet">--%>
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="login-container">--%>
<%--    <h1>Sign In</h1>--%>

<%--    <div class="social-icons">--%>
<%--        <a href="#"><i class="fab fa-google"></i></a>--%>
<%--        <a href="#"><i class="fab fa-facebook-f"></i></a>--%>
<%--        <a href="#"><i class="fab fa-github"></i></a>--%>
<%--        <a href="#"><i class="fab fa-linkedin-in"></i></a>--%>
<%--    </div>--%>

<%--    <div class="divider"><span>or use your email password</span></div>--%>

<%--    <form action="login" method="post">--%>
<%--        <input type="text" name="username" placeholder="Email" required>--%>
<%--        <input type="password" name="password" placeholder="Password" required>--%>

<%--        <div class="forgot-password">--%>
<%--            <a href="#">Forget Your Password?</a>--%>
<%--        </div>--%>

<%--        <input type="submit" value="Sign In">--%>
<%--        <p class="error">${errorMessage}</p>--%>
<%--    </form>--%>

<%--    <div class="switch-form">--%>
<%--        Don't have an account? <a href="register.jsp">Sign up</a>--%>
<%--    </div>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: 'Montserrat', sans-serif;
            background: linear-gradient(to right, #e2e2e2, #fad6da);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .login-container {
            background-color: #fff;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }
        h1 {
            color: #e86471;
            margin-bottom: 20px;
            font-size: 24px;
        }
        .social-icons {
            margin: 20px 0;
        }
        .social-icons a {
            display: inline-flex;
            justify-content: center;
            align-items: center;
            margin: 0 5px;
            width: 40px;
            height: 40px;
            border: 1px solid #ddd;
            border-radius: 50%;
            color: #333;
            text-decoration: none;
        }
        .divider {
            margin: 20px 0;
            color: #999;
            position: relative;
        }
        .divider::before {
            content: "";
            position: absolute;
            top: 50%;
            left: 0;
            right: 0;
            height: 1px;
            background-color: #eee;
            z-index: -1;
        }
        .divider span {
            background-color: #fff;
            padding: 0 10px;
        }
        input[type="text"], input[type="password"], select {
            width: 100%;
            padding: 12px 15px;
            margin: 8px 0;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 14px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background-color: #e86471;
            color: white;
            border: none;
            padding: 12px 30px;
            margin-top: 15px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 600;
            width: 100%;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #c95762;
        }
        .forgot-password {
            margin: 15px 0;
        }
        .forgot-password a {
            color: #e86471;
            text-decoration: none;
            font-size: 13px;
        }
        .error {
            color: #e53e3e;
            margin-top: 15px;
            font-size: 14px;
        }
        .switch-form {
            margin-top: 20px;
            font-size: 14px;
        }
        .switch-form a {
            color: #e86471;
            text-decoration: none;
            font-weight: 600;
        }
    </style>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
</head>
<body>
<div class="login-container">
    <h1>Sign In</h1>

    <div class="social-icons">
        <a href="#"><i class="fab fa-google"></i></a>
        <a href="#"><i class="fab fa-facebook-f"></i></a>
        <a href="#"><i class="fab fa-github"></i></a>
        <a href="#"><i class="fab fa-linkedin-in"></i></a>
    </div>

    <div class="divider"><span>or use your email password</span></div>

    <form action="login" method="post">
        <input type="text" name="username" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <!-- Dropdown for role selection -->
        <select name="role" required>
            <option value="" disabled selected>Select Role</option>
            <option value="student">Student</option>
            <option value="tutor">Tutor</option>
            <option value="admin">Admin</option>
        </select>

        <div class="forgot-password">
            <a href="#">Forget Your Password?</a>
        </div>

        <input type="submit" value="Sign In">
        <p class="error">${errorMessage}</p>
    </form>

    <div class="switch-form">
        Don't have an account? <a href="register.jsp">Sign up</a>
    </div>
</div>
</body>
</html>
