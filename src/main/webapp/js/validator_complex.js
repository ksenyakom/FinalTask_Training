function validateComplex(form) {
    var message = "";
    var title = form.title.value;

    if (title.length < 3 && title.length > 255) {
        message +=  "<br>" + "Длина названия должна быть не менее 3 и не более 255 символов";
    }

    if (message !== "") {
        document.getElementById("errorMessage").innerHTML = message;
        return false;
    }
}
