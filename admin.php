<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Адмінка</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Адмін-панель</h1>

<input type="text" id="name" placeholder="Ім'я">
<select id="role">
    <option value="user">User</option>
    <option value="admin">Admin</option>
</select>
<button onclick="Controller.addUser()">Додати</button>

<table border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>Ім'я</th>
            <th>Роль</th>
            <th>Дія</th>
        </tr>
    </thead>
    <tbody id="userTable"></tbody>
</table>

<script src="js/model.js"></script>
<script src="js/view.js"></script>
<script src="js/controller.js"></script>
</body>
</html>
