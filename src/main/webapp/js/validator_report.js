function validateReport(form) {
    var message = "";
    var from = form.from.value;
    var to = form.to.value;
    var login = form.login.value;


    if (from && to && from >= to) {
        message += "Даты введены неверно.";
    }

    if (login.length > 255) {
        message += "Логин слишком длинный, должен быть не более 255 символов.";
    }

    if (message !== "") {
        document.getElementById("errorMessage").innerHTML = message;
        return false;
    }

}