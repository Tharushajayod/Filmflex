<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Subject</title>

    <!-- Font Awesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <!-- Custom CSS File Link -->
    <link rel="stylesheet" href="../style.css">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(90deg, #8e2de2, #ff6a88);
        }

        .container {
            max-width: 800px;
            margin: 60px auto;
            background: #fff;
            border-radius: 12px;
            padding: 40px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
        }

        .logo {
            font-size: 32px;
            font-weight: bold;
            color: #8e2de2;
            display: inline-block;
            margin-bottom: 20px;
        }

        .logo span {
            color: #ff6a88;
        }

        .heading {
            text-align: center;
            color: #c94bd6;
            font-size: 30px;
            margin-bottom: 30px;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            font-size: 15px;
            font-weight: 600;
            margin: 12px 0 4px;
        }

        input[type="text"],
        input[type="number"],
        select {
            padding: 10px;
            font-size: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
            margin-bottom: 10px;
        }

        button[type="submit"] {
            margin-top: 20px;
            padding: 12px;
            background-color: #343a40;
            color: white;
            font-size: 16px;
            font-weight: 600;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button[type="submit"]:hover {
            background-color: #212529;
        }

        @media (max-width: 600px) {
            .container {
                padding: 25px;
            }

            .heading {
                font-size: 24px;
            }

            input, select {
                font-size: 14px;
            }
        }
    </style>

</head>
<body>

<div class="container">
    <header>
        <a href="#" class="logo">Smart<span>Tutor</span></a>
    </header>

    <h1 class="heading">Add New Subject</h1>

    <!-- Add Subject Form -->
    <section class="add-subject-form">
        <!-- Form to add a subject -->
        <form action="${pageContext.request.contextPath}/tutor/addSubject" method="post" id="addSubjectForm">
            <!-- Subject Name Input -->
            <label for="subjectName">Subject Name:</label>
            <input type="text" name="subjectName" id="subjectName" required>

            <!-- Number of Lessons Input -->
            <label for="lessonCount">Number of Lessons:</label>
            <input type="number" name="lessonCount" id="lessonCount" min="1" required>

            <!-- Render lesson input fields dynamically using JavaScript -->
            <div id="lessonFieldsContainer"></div>

            <!-- Grade Selection -->
            <label for="grade">Grade:</label>
            <select name="grade" id="grade" required>
                <option value="">Select Grade</option>
                <option value="grade9">Grade 9</option>
                <option value="grade10">Grade 10</option>
                <option value="grade11">Grade 11</option>
                <option value="grade12">Grade 12</option>
            </select>

            <!-- Stream Selection -->
            <label for="stream">Stream:</label>
            <select name="stream" id="stream" required>
                <option value="">Select Stream</option>
                <option value="science">Science</option>
                <option value="commerce">Commerce</option>
                <option value="arts">Arts</option>
            </select>

            <!-- Submit Button -->
            <button type="submit">Add Subject</button>
        </form>
    </section>
</div>

<!-- JavaScript to handle dynamic input fields -->
<script>
    // Function to add lesson fields dynamically based on lesson count
    document.getElementById('lessonCount').addEventListener('input', function() {
        // Get the number of lessons to be added
        var lessonCount = parseInt(this.value);

        // Get the container where the lesson input fields will be added
        var container = document.getElementById('lessonFieldsContainer');

        // Clear the container before adding new fields
        container.innerHTML = '';

        // Dynamically create lesson input fields based on the lesson count
        for (var i = 0; i < lessonCount; i++) {
            // Create lesson name field
            var lessonNameLabel = document.createElement('label');
            lessonNameLabel.setAttribute('for', 'lessonName' + i);
            lessonNameLabel.textContent = 'Lesson ' + (i + 1) + ' Name:';

            var lessonNameInput = document.createElement('input');
            lessonNameInput.type = 'text';
            lessonNameInput.name = 'lessonName' + i;
            lessonNameInput.id = 'lessonName' + i;
            lessonNameInput.required = true;

            // Create lesson price field
            var priceLabel = document.createElement('label');
            priceLabel.setAttribute('for', 'price' + i);
            priceLabel.textContent = 'Lesson ' + (i + 1) + ' Price:';

            var priceInput = document.createElement('input');
            priceInput.type = 'number';
            priceInput.name = 'price' + i;
            priceInput.id = 'price' + i;
            priceInput.min = '0';
            priceInput.step = 'any';
            priceInput.required = true;

            // Append the labels and inputs to the container
            container.appendChild(lessonNameLabel);
            container.appendChild(lessonNameInput);
            container.appendChild(priceLabel);
            container.appendChild(priceInput);
            container.appendChild(document.createElement('br'));  // Add line break between fields
        }
    });
</script>

</body>
</html>
