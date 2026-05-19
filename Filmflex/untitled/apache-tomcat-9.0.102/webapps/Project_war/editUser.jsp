<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Edit User</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
</head>

<body class="min-h-screen bg-gradient-to-br from-purple-300 via-pink-200 to-pink-400 flex items-center justify-center">

<!-- Wrapper -->
<div class="w-full max-w-lg bg-white p-8 rounded-xl shadow-xl">
    <h1 class="text-2xl font-bold text-center text-purple-700 mb-6 tracking-wide">Edit User</h1>

    <!-- Error Message -->
    <c:if test="${not empty error}">
        <div class="bg-red-100 text-red-700 p-3 rounded mb-4 border border-red-300">
            <i class="fas fa-exclamation-circle mr-2"></i>${error}
        </div>
    </c:if>

    <!-- User Edit Form -->
    <form action="${pageContext.request.contextPath}/admin/editUser" method="post" class="space-y-5">

        <!-- Hidden Fields -->
        <input type="hidden" name="username" value="${userToEdit.username}">
        <input type="hidden" name="role" value="${role}">

        <div>
            <label for="email" class="block mb-1 font-medium text-gray-700">Email</label>
            <input type="email" name="email" id="email" value="${userToEdit.email}"
                   class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-purple-400 focus:border-purple-400" required>
        </div>

        <div>
            <label for="password" class="block mb-1 font-medium text-gray-700">Password</label>
            <input type="password" name="password" id="password" value="${userToEdit.password}"
                   class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-purple-400 focus:border-purple-400" required>
        </div>

        <!-- Buttons -->
        <div class="flex justify-between items-center mt-6">
            <button type="submit"
                    class="flex items-center justify-center bg-gradient-to-r from-purple-600 to-pink-500 text-white px-6 py-2 rounded-lg shadow hover:opacity-90 transition-all duration-200">
                <i class="fas fa-save mr-2"></i> Save Changes
            </button>

            <a href="${pageContext.request.contextPath}/admin/userlist"
               class="text-sm text-gray-600 hover:text-red-500 transition-all duration-150">
                <i class="fas fa-arrow-left mr-1"></i> Cancel
            </a>
        </div>
    </form>
</div>

</body>
</html>
