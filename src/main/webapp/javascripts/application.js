$(document).ready(function () {
    $(".datepicker").datepicker({ dateFormat: 'dd/mm/yy' });

    $('.ui.sidebar').sidebar();

    $('.add-user.modal').modal('attach events', '.add-user.button', 'show');
    $('.edit-user.modal').modal('attach events', '.edit-user.button', 'show');
    $('.remove-user.modal').modal('attach events', '.remove-user.button', 'show');
});