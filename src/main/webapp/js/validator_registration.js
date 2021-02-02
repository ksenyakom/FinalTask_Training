function validateRegistration(form) {
    var message = "";
    var login = form.login.value;
    var loginPattern = /[A-Za-z0-9_-]{5,}/;

    var password = form.password.value;
    var passwordRepeat = form.password2.value;
    var email = form.email.value;
    var emailPattern = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+/;

    if (loginPattern.test(login) === false) {
        message += "Логин может включать прописные и строчные буквы, цифры, символы _,-. Не менее 5 символов.";
    }

    if (login.length > 255) {
        message += "Логин слишком длинный, должен быть не более 255 символов.";
    }

    if (password.length < 5 || password.length > 128) {
        message += "<br>" + "Пароль должен состоять из 5 символов минимум, но не более 128";
    } else if (password !== passwordRepeat) {
        message +="<br>" + "Пароли не совпадают."
    }

    if (!email) {
        message += "<br>" + "Вы не ввели email.";
    } else if (emailPattern.test(email) === false) {
        message +="<br>" + "Email введен неверно, проверьте правильность ввода."
    }

    if (message !== "") {
        document.getElementById("errorMessage").innerHTML = message;
        return false;
    }
}