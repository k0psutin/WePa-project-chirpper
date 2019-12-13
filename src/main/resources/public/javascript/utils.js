var text_max = 120;
        $('#count_content').html(text_max + ' remaining');
        $('#chirp_content').keyup(function () {
var text_length = $('#chirp_content').val().length;
        var text_remaining = text_max - text_length;
        $('#count_content').html(text_remaining + ' remaining');
        });
