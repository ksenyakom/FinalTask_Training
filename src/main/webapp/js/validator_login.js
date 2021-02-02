function validateLogin(form) {
    var message = "";
    var login = form.login.value;
    var password = form.password.value;
    var loginPattern = /[A-Za-z0-9_-]{5,}/;

    if (loginPattern.test(login) === false) {
        message += "Логин введен неверно.";
    }
    if (login.length > 255) {
        message += "Логин слишком длинный, должен быть не более 255 символов.";
    }
    if (password.length < 5 || password.length > 128) {
        message += "<br>" + "Пароль должен состоять из 5 символов минимум, но не более 128";
    }

    if (message !== "") {
        document.getElementById("errorMessage").innerHTML = message;
        return false;
    }
}