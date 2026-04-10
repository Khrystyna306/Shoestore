<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Головна</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Головна сторінка</h1>

<a href="login.html">Увійти</a>
<a href="admin.html" id="adminLink" style="display:none;">Адмін-панель</a>
<button onclick="Controller.logout()">Вийти</button>

<script src="js/model.js"></script>
<script src="js/view.js"></script>
<script src="js/controller.js"></script>
</body>
</html>
