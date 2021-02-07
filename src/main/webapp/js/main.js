function validateDelete(form) {
    var remove =form.remove.value;
    let result = false;

    if($('.require-one:checked').length > 0)
    {
        // at least one checkbox was checked
        result = confirm("Подтвердите удаление");
        return result;
    }
    else
    {
        // no checkbox was checked
        alert("Вы не выбрали ни одной записи для удаления");
        return false;
    }

}

function validateAdd(form) {
    var addId =form.addId.value;

    if($('.require-one:checked').length == 0){
        // no checkbox was checked
        alert("Вы не выбрали ни одной записи для добавления");
        return false;
    }

}

function changeLanguage(form){
  //   document.cookie = ;
    document.getElementById("test").innerHTML = "language=" + form.lang.value;
    return false;
}