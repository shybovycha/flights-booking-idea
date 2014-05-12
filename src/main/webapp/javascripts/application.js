window.destinationMatcher = function(strs) {
    return function findMatches(q, cb) {
        var matches, substringRegex;

        // an array that will be populated with substring matches
        matches = [];

        // regex used to determine if a string contains the substring `q`
        substrRegex = new RegExp(q, 'i');

        // iterate through the pool of strings and for any string that
        // contains the substring `q`, add it to the `matches` array
        $.each(strs, function(i, str) {
            if (substrRegex.test(str)) {
                // the typeahead jQuery plugin expects suggestions to a
                // JavaScript object, refer to typeahead docs for more info
                matches.push({ value: str });
            }
        });

        cb(matches);
    };
};

window.updateTotalCost = function updateTotal() {
    var tickets = parseInt($('#ticketAmount').val() || 0),
        ticketCost = parseFloat($('#ticketCost').val());

    if (isNaN(tickets))
        tickets = 0;

    $('#totalCost').val(tickets * ticketCost);
};

window.displayMarkAsSoldButton = function() {
    var marked = $('.marked:checked').length;

    if (marked < 1) {
        $('.mark-as-sold').hide();
    } else {
        $('.mark-as-sold').show();
    }
};

$(document).ready(function () {
    $(".from-now.datepicker").datepicker({ dateFormat: 'dd/mm/yy', minDate: 0 });
    $(".datepicker").datepicker({ dateFormat: 'dd/mm/yy' });

    if ($(".clockpicker").length > 0)
        $(".clockpicker").clockpicker({ format: 'HH:mm', autoclose: true });

    $('.ui.dropdown').dropdown();
    $('.ui.sidebar').sidebar();

    $('.tabular.menu .item').tab();

    $('.add-user.modal').modal('attach events', '.add-user.button', 'show');
    $('.edit-user.modal').modal('attach events', '.edit-user.button', 'show');
    $('.remove-user.modal').modal('attach events', '.remove-user.button', 'show');

    $('.clear.button').click(function() {
        $($(this).attr('data-input')).val('');
    });

    $('table.ui.table tr:gt(0)').hover(function() {
        $(this).parent().find('tr:first .actions').css({ visibility: 'hidden' });
    });

    $('table.ui.table tr:eq(1)').hover(function() {
        $(this).parent().find('tr:first .actions').css({ visibility: 'visible' });
    });

    $('table.ui.table tr:gt(0)').mouseleave(function() {
        $(this).parent().find('tr:first .actions').css({ visibility: 'visible' });
    });
});