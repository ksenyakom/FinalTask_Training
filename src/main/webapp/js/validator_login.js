function validateLogin(form) {
    var message = "";
    var login = form.login.value;
    var password = form.password.value;
    var loginPattern = /[A-Za-z0-9_-]{5,}/;

    if (loginPattern.test(login) === false) {
        message += "Логин введен неверно.";
    }

    if (password.length < 5) {
        message += "<br>" + "Пароль должен состоять из 5 символов минимум.";
    }

    if (message !== "") {
        document.getElementById("errorMessage").innerHTML = message;
        return false;
    }
}