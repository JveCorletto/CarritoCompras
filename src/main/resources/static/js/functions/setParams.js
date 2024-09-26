$(document).ready(function() {
    getMyUser();
    getProductsOnKart();

    callPageFunctions();
});

function getMyUser() {
    let userNameLabel = $('#userNameLabel');
    let myId = localStorage.getItem("myId") ? localStorage.getItem("myId") : "";
    let myUser = localStorage.getItem("myUser") ? localStorage.getItem("myUser") : "";

    if (myId == "" && myUser == "") {
        $.ajax({
            url: '/API/Usuarios/getMyUser',
            type: 'GET',
            success: function(data) {
                localStorage.setItem("myId", data.userId);
                localStorage.setItem("myUser", data.username);

                myUser = localStorage.getItem("myUser") ? localStorage.getItem("myUser") : "";
            },
            error: function(xhr, status, error) {
                console.error('Error al obtener los datos del usuario:', error);
            }
        });
    }

    userNameLabel.text(myUser);
}

function getProductsOnKart() {
    let productsOnKart = localStorage.getItem("productsOnKart") ? parseInt(localStorage.getItem("productsOnKart")) : 0;

    $.ajax({
        url: '/API/Compras/GetProductsOnKart',
        type: 'GET',
        success: function(response) {
            if (response.status == 1) {
                productsOnKart = parseInt(response.data);
                localStorage.setItem("productsOnKart", productsOnKart);
            } else {
                productsOnKart = 0;
            }

            let carritoCounter = $('#carritoCounter');
            carritoCounter.text(productsOnKart);
        },
        error: function(xhr, status, error) {
            productsOnKart = 0;

            let carritoCounter = $('#carritoCounter');
            carritoCounter.text(productsOnKart);
        }
    });
}

function paginate(id) {
    return $('#' + id).DataTable({
        destroy: true,
        "language": {
            "lengthMenu": "Mostrar _MENU_ registros por página",
            "zeroRecords": "Sin registros",
            "info": "Mostrando _PAGE_ de _PAGES_",
            "infoEmpty": "Sin registros que mostrar",
            "infoFiltered": "(Buscando en _MAX_ registros)",
            "thousands": ",",
            "decimal": ".",
            "search": "Buscar",
            "loadingRecords": "Cargando...",
            "processing": "Procesando...",
            "paginate": {
                "first": "Primero",
                "last": "Último",
                "next": "Siguiente",
                "previous": "Anterior"
            }
        }
    });
}