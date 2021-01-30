function validateExercise(form) {
    var message = "";
    var title = form.title.value;
    var adjusting = document.getElementById('adjusting');
    var mistakes = document.getElementById('mistakes');

    if (title.length < 3 && title.length > 255) {
        message +=  "<br>" + "Длина названия должна быть не менее 3 и не более 255 символов";
    }

    if (adjusting.length <1 && adjusting.length > 1000) {
        message +=  "<br>" + "Длина поля отстройки должна быть не менее 3 и не более 255 символов";
    }

    if (mistakes.length <1 && mistakes.length > 1000) {
        message +=  "<br>" + "Длина поля ошибок должна быть не менее 3 и не более 255 символов";
    }

    if (message !== "") {
        document.getElementById("errorMessage").innerHTML = message;
        return false;
    }
}
