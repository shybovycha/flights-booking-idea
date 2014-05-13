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

    window.sessionStorage.setItem('last_tr_index', null);

    $(this).find('tbody tr:eq(0) .actions').css({ visibility: 'visible' });

    $('table.ui.table tbody tr').hover(function() {
        $(this).parent().find('.actions').css({ visibility: 'hidden' });
        $(this).find('.actions').css({ visibility: 'visible' });

        window.sessionStorage.setItem('last_tr_index', $(this).index());
    });

    $('table.ui.table').mouseleave(function() {
        var stored_index = window.sessionStorage.getItem('last_tr_index') || 1;

        $(this).find('tr .actions').css({ visibility: 'hidden' });
        $(this).find('tbody tr:eq(' + stored_index + ') .actions').css({ visibility: 'visible' });
    });
});