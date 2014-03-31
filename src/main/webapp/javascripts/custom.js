$(document).ready(function() {
    $('.ui.tabular.menu .item').tab();
    $('.ui.accordion').accordion();
    $('.ui.dropdown').dropdown();

    $('.chosen-select').chosen({
        allow_single_deselect: true,
        no_results_text: 'Nothing found',
        width: '200px'
    });
});