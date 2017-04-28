
$.extend({
    jc: function () {
    },
    constant: function () { 
        return {
            MAX_FILES: 10,
        }
    }

});

$.jc.prototype.alertOk = function (options) {
    options = options.length ? {text: options} : ( options || {} );
    options.title = options.title || 'Successful';
    options.text = options.text;
    options.showCancelButton = false;
    options.showCloseButton = false;
    options.type = 'success';
    this.alertBox(options);
};

$.jc.prototype.alertOkAndReload = function (text) {
    this.alertOk({
        text: text, then: function () {
            setTimeout(function () {
                window.location.reload();
            }, 800);
        }
    });
};

$.jc.prototype.alertWarn = function (options) {
    options = options.length ? {text: options} : ( options || {} );
    options.title = options.title || 'Warning';
    options.text = options.text;
    options.timer = 3000;
    options.type = 'warning';
    this.alertBox(options);
};

$.jc.prototype.alertConfirm = function (options) {
    options = options || {};
    options.title = options.title || 'Delete confirmed?';
    options.text = options.text;
    options.showCancelButton = true;
    options.type = 'question';
    this.alertBox(options);
};

$.jc.prototype.alertError = function (options) {
    options = options.length ? {text: options} : ( options || {} );
    options.title = options.title || 'Error';
    options.text = options.text;
    options.type = 'error';
    this.alertBox(options);
};

$.jc.prototype.alertBox = function (options) {
    swal({
        title: options.title,
        text: options.text,
        type: options.type,
        timer: options.timer || 9999,
        showCloseButton: options.showCloseButton,
        showCancelButton: options.showCancelButton,
        showLoaderOnConfirm: options.showLoaderOnConfirm || false,
        confirmButtonColor: options.confirmButtonColor || '#3085d6',
        cancelButtonColor: options.cancelButtonColor || '#d33',
        confirmButtonText: options.confirmButtonText || 'Yes',
        cancelButtonText: options.cancelButtonText || 'No'
    }).then(function (e) {
        options.then && options.then(e);
    }).catch(swal.noop);
};

$.jc.prototype.post = function (options) {
    var self = this;
    $.ajax({
        type: 'POST',
        url: options.url,
        data: options.data || {},
        async: options.async || false,
        dataType: 'json',
        success: function (result) {
            options.success && options.success(result);
        },
        error: function () {
            //
        }
    });
};

$.jc.prototype.showError = function (selector, msg) {
  $(selector).removeClass('hidden');
  $(selector).find('label').text(msg);
};