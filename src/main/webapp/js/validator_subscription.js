function validateSubscription(form) {
    var message = "";
    var beginDate = form.beginDate.value;
    var endDate = form.endDate.value;
    var price = form.price.value;


    if (beginDate >= endDate) {
        message += "Даты введены неверно.";
    }

    if (!price) {
        message += "<br>" + "Введите стоимость.";
    }

    if (message !== "") {
        document.getElementById("errorMessage").innerHTML = message;
        return false;
    }
}