import 'bootstrap';
import 'admin-lte/build/js/AdminLTE';
import '@fortawesome/fontawesome-free/js/all';
import 'trumbowyg'

$(function () {
    $(document).on('click', '.delete-button', function (e) {
        e.preventDefault();
        $('#delete-modal-button-valid').attr('href', $(this).attr('href'));
        $('#delete-modal').modal('show')
    });
    $(document).on('click', '#addPrice', function (e) {
        e.preventDefault();
        let prototype = '<tr><td><input type="url" id="prices__name__.url" class="form-control" name="prices[__name__].url" required="required"></td><td><button th:data="__name__" class="btn btn-danger btn-sm delete-button"><i class="fas fa-trash"></i></button></td></tr>'
        let collectionPrice = $('#collectionPrice');
        let index = $('#collectionPrice').data('index');
        prototype = prototype.replace(/__name__/g, index + 1);
        collectionPrice.append(prototype);
        console.log(prototype, index);
    });
    $(document).on('click', '.chose-item', function (e) {
        e.preventDefault();
        $.ajax({
                url: $(this).attr('href'),
                method: 'GET',
                success: function (response) {
                    $('#modal .modal-dialog').html(response);
                    $('#modal').modal('show');
                }
            }
        )
    });
    $(document).on('submit', '#user-item-form', function (e) {
        e.preventDefault();
        $.ajax({
            url: $(this).attr('action'),
            method: $(this).attr('method'),
            data: $(this).serialize(),
            success: function(response) {
                $('#modal .modal-dialog').html(response);
            }
        })
    });
    $.trumbowyg.svgPath = "/dist/icons.svg";
    $('.editor').trumbowyg();
});