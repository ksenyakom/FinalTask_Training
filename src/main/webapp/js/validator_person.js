function validatePerson(form) {
    var message = "";
    var name = form.name.value;
    var surname = form.surname.value;
    var patronymic = form.patronymic.value;
    var address = form.address.value;
    var phone = form.phone.value;
    var phonePattern = /\+[0-9]{12}/;

    message += checkText(name,"Имя");
    message += checkText(surname,"Фамилия");
    message += checkText(patronymic,"Отчество");
    message += checkText(address,"Адрес");

    if (phone.length>0 && phonePattern.test(phone) === false) {
        message += "Телефон введен неверно.";
    }

    if (message !== "") {
        document.getElementById("errorMessage").innerHTML = message;
        return false;
    }
}

function checkText(param, paramName) {
    if (param.length >255) {
        return "<br>" + paramName + " : поле слишком длинное, должно быть не более 255 символов.";
    } else {
        return "";
    }

}