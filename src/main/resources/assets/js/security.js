import $ from 'jquery';
import 'bootstrap';

$(function () {
    $(document).on('click', '#forgot-password-modal', function (e) {
        e.preventDefault();
        $.ajax({
            url: $(this).attr('href'),
            method: 'GET',
            success: function (response) {
                $('#modal .modal-dialog').html(response);
                $('#modal').modal('show');
            }
        })
    });
    $(document).on('submit', '#forgot-password-form', function (e) {
        e.preventDefault();
        $('#modal-send-button').prop('disabled', true);
        $.ajax({
            'url': $(this).attr('action'),
            'method': $(this).attr('method'),
            'data': $(this).serialize(),
            'success': function (response) {
                $('#modal .modal-dialog').html(response);
            },
            'error': function () {

            },
            'complete': function () {
                $('#modal-send-button').prop('disabled', false);
            }
        })
    })
})