function fillModal(modalName, title, body, alertClass, btnClass, show) {
    $('#modal' + modalName + 'Title').text(title);
    //$('#modalUserBody').text(body);
    $('#modal' + modalName + ' .modal-header').addClass(alertClass);
    $('#modal' + modalName + 'CloseButton').addClass(btnClass);
    if (show) {
        $('#modal' + modalName).modal('show');
    }
}

function showModalOnly(modalName, name) {
    if (name !== null)
        $('#modal' + modalName + 'Title').text(name);
    $('#modal' + modalName).modal('show');
}

function hideModalOnly(modalName) {
    $('#modal' + modalName).modal('hide');
}