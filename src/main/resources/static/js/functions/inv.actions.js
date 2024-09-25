$('#chargeImage').click(function() {
    const imageUrl = $('#fotoProducto').val().trim();

    if (imageUrl) {
        const imageElement = `<img src="${imageUrl}" alt="Foto del Producto" style="max-width: 100%; max-height: 150px;" class="img-thumbnail">`;
        $('#miniaturaContainer').html(imageElement);

        Swal.fire({
            title: 'Información',
            icon: "warning",
            html: 'Si la imagen no aparece, por favor, valide la URL de la imagen.',
            timer: 2000,
            timerProgressBar: true,
            didOpen: () => {
                Swal.showLoading();
            },
        });
    } else {
        Swal.fire({
            title: 'Validación',
            icon: "warning",
            html: 'No se ha proporcionado una URL válida',
            timer: 2000,
            timerProgressBar: true,
            didOpen: () => {
                Swal.showLoading();
            },
        });
    }
});

$('#btnSave').click(function () {
    if ($('#Producto').val().trim() != "" && $('#Categoria').val() > 0 && $('#Stock').val() > 0 && $('#Precio').val() > 0
        && $('#Descripcion').val().trim() != "" && $('#fotoProducto').val().trim() != "") {

        var Obj = {
            producto: $('#Producto').val().trim(),
            categoria: {
                idCategoria: $('#Categoria').val()
            },
            stock: parseInt($('#Stock').val()),
            precio: parseFloat($('#Precio').val()),
            imagen: $('#fotoProducto').val().trim(),
            descripcion: $('#Descripcion').val().trim(),
        };

        $.ajax({
            type: 'POST',
            url: '/API/Productos',
            contentType: "Application/json",
            data: JSON.stringify(Obj),
            success: function (response) {
                if (response.status == 1) {
                    Swal.fire({
                        title: 'Exito',
                        icon: "success",
                        html: response.message,
                        timer: 5000,
                        timerProgressBar: true,
                        didOpen: () => {
                            Swal.showLoading();
                        },
                    }).then(function () {
                        $('#btnCancel').click();
                        getProducts();
                    });
                }
                else {
                    Swal.fire({
                        title: 'Error',
                        icon: "warning",
                        html: response.message,
                        timer: 5000,
                        timerProgressBar: true,
                        didOpen: () => {
                            Swal.showLoading();
                        },
                    });
                }
            },
            error: function (data) {
                Swal.fire({
                    title: 'Error',
                    icon: "warning",
                    html: "Ocurrió un error, intente nuevamente",
                    timer: 5000,
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading();
                    },
                });
            }
        });
    }
    else {
        Swal.fire({
            title: 'Validación',
            icon: "warning",
            html: 'Para continuar, debe de rellenar todos los campos.',
            timer: 1000,
            timerProgressBar: true,
            didOpen: () => {
                Swal.showLoading();
            },
        });
    }
});

$('#btnEliminar').click(function () {
    Swal.fire({
        title: 'Validación',
        text: "¿En verdad desea eliminar este Producto?",
        icon: 'warning',
        showCancelButton: true,

        cancelButtonColor: '#181C32',
        confirmButtonColor: '#FF3D60',

        cancelButtonText: "Cancelar",
        confirmButtonText: "Eliminar",
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type: 'DELETE',
                url: 'API/Productos/' + parseInt($("#IdProducto").val()),
                success: function (response) {
                    if (response.status == 1) {
                        Swal.fire({
                            title: 'Operación Exitosa',
                            icon: "success",
                            html: response.message,
                            timer: 2000,
                            timerProgressBar: true,
                            didOpen: () => {
                                Swal.showLoading();
                            },
                        }).then(function () {
                            $('#btnCancel').click();
                            getProducts();
                        });
                    }
                    else {
                        Swal.fire({
                            title: 'Error',
                            icon: "warning",
                            html: response.message,
                            timer: 2000,
                            timerProgressBar: true,
                            didOpen: () => {
                                Swal.showLoading();
                            },
                        });
                    }
                }
            });
        }
    });
});

$('#btnEdit').click(function () {
    if ($('#Producto').val().trim() != "" && $('#Categoria').val() > 0 && $('#Stock').val() > 0 && $('#Precio').val() > 0
        && $('#Descripcion').val().trim() != "" && $('#fotoProducto').val().trim() != "") {

        var Obj = {
            idProducto: $('#IdProducto').val().trim(),
            producto: $('#Producto').val().trim(),
            categoria: {
                idCategoria: $('#Categoria').val()
            },
            stock: parseInt($('#Stock').val()),
            precio: parseFloat($('#Precio').val()),
            imagen: $('#fotoProducto').val().trim(),
            descripcion: $('#Descripcion').val().trim(),
        };

        $.ajax({
            type: 'PUT',
            url: '/API/Productos',
            contentType: "Application/json",
            data: JSON.stringify(Obj),
            success: function (response) {
                if (response.status == 1) {
                    Swal.fire({
                        title: 'Exito',
                        icon: "success",
                        html: response.message,
                        timer: 5000,
                        timerProgressBar: true,
                        didOpen: () => {
                            Swal.showLoading();
                        },
                    }).then(function () {
                        $('#btnCancel').click();
                        getProducts();
                    });
                }
                else {
                    Swal.fire({
                        title: 'Error',
                        icon: "warning",
                        html: response.message,
                        timer: 5000,
                        timerProgressBar: true,
                        didOpen: () => {
                            Swal.showLoading();
                        },
                    });
                }
            },
            error: function (data) {
                Swal.fire({
                    title: 'Error',
                    icon: "warning",
                    html: "Ocurrió un error, intente nuevamente",
                    timer: 5000,
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading();
                    },
                });
            }
        });
    }
    else {
        Swal.fire({
            title: 'Validación',
            icon: "warning",
            html: 'Para continuar, debe de rellenar todos los campos.',
            timer: 1000,
            timerProgressBar: true,
            didOpen: () => {
                Swal.showLoading();
            },
        });
    }
});