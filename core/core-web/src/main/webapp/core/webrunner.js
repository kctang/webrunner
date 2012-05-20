var CrudSupportList = function (type, baseUrl) {
    var that = this;
    this.formSelector = 'form#' + type;

    $(this.formSelector).submit(function (e) {
        return that.doFind();
    });

    this.doFind = function () {
        var q = $(this.formSelector + ' div input#q').val();
        location = baseUrl + '?q=' + q;
        return false;
    };

    this.doNew = function () {
        location = baseUrl + '/new';
        return false;
    };

    this.doDelete = function () {
        alert('TODO: ???');
        return false;
    };
};

var CrudSupportForm = function(type, baseUrl) {
    var that = this;
    this.form = $('form#' + type);
    this.deleteForm= $('form#' + type + 'Delete');

    // bind cancel button
    this.form.find('button#cancel').click(function (e) {
        location = baseUrl;
        return false;
    });

    // bind delete button
    this.form.find('button#delete').click(function (e) {
        if(confirm('Are you sure you want to delete?')) {
            that.deleteForm.submit();
        }
        return false;
    });

};