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