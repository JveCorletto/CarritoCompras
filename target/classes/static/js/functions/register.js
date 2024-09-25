$(document).ready(function() {
    localStorage.clear();

    if ($(".alert-danger").length > 0 || $(".alert-success").length > 0) {
        var registerTab = new bootstrap.Tab(document.querySelector('#tab-register'));
        registerTab.show();
    }
});