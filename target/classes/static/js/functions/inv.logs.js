function callPageFunctions() {
    getProducts();
    loadCategorias();
}

function resetForm() {
    $('#dataProducto').trigger('reset');
    $('#editionMode').hide();
    $('#btnEdit').hide();

    $('#btnSave').show();
    $('#btnCancel').show();

    $('#Producto').attr("disabled", false);
    $("#Categoria").val('0').change();
    $('#Categoria').attr("disabled", false);

    $('#Stock').attr("disabled", false);
    $('#Precio').attr("disabled", false);

    $('#Descripcion').attr("disabled", false);
    $('#fotoProducto').attr("disabled", false);
    $('#chargeImage').attr("disabled", false);

    $("#miniaturaContainer").html(null);

    $("#tituloModal").text("Nuevo Producto");
}

$('#btnEdition').click(function () {
    $('#editionMode').hide();
    $('#btnEdit').show();

    $('#Producto').attr("disabled", false);
    $('#Categoria').attr("disabled", false);

    $('#Stock').attr("disabled", false);
    $('#Precio').attr("disabled", false);

    $('#Descripcion').attr("disabled", false);
    $('#fotoProducto').attr("disabled", false);
    $('#chargeImage').attr("disabled", false);

    $("#tituloModal").html("Edición del Producto");
});

function getProducts() {
    $.ajax({
        url: '/API/Productos',
        type: 'GET',
        success: function(response) {
            let tProductos = $('#tProductos');
            tProductos.empty();

            if (response.status == 1) {
                response.data.forEach(function(producto) {
                    if ($.fn.dataTable.isDataTable('#tableProductos')) {
                        $('#tableProductos').DataTable().clear().destroy();
                    }

                    let productRow = `
                    <tr>
                        <td>
                            <center>
                                <img src="${producto.imagen ? producto.imagen : 'https://via.placeholder.com/150'}" class="card-img-top" alt="${producto.producto}" style="object-fit: contain; height: 20%; width: 20%;">
                            </center>
                        </td>
                        <td>${producto.producto}</td>
                        <td>${producto.categoria.categoria}</td>
                        <td>$${producto.precio}</td>
                        <td>${producto.stock}</td>
                        <td>
                            <button type='button' title='Edicion de Producto' class='btn btn-info' onclick='getProduct(${producto.idProducto})' data-toggle='modal' data-target='#staticProducto'><i class='fas fa-edit'></i></button>
                        </td>
                    </tr>
                    `;

                    tProductos.append(productRow);
                });

                paginate('tableProductos');
            } else {
                tProductos.append(`<tr>
                        <td colspan="7">${response.message}</td>
                    </tr>`);
            }
        },
        error: function(xhr, status, error) {
            console.error('Error al obtener productos:', error);
        }
    });
}

function loadCategorias(IdCategoria) {
    var Obj = {
        Token: localStorage.getItem("UserToken"),
        ActualRute: window.location.hash.replace('#', '')
    };
    var api = localStorage.getItem('apiURL');

    $.ajax({
        type: 'GET',
        url: '/API/Categorias',
        contentType: "application/json",
        success: function(response) {
            let categoriaSelect = $("#Categoria");
            categoriaSelect.empty();

            if (response.status === 1) {
                let html = "<option value='0'>Elija una Categoría</option>";

                response.data.forEach(categoria => {
                    html += `<option value="${categoria.idCategoria}">${categoria.categoria}</option>`;
                });
                categoriaSelect.html(html);

                if (IdCategoria > 0) {
                    categoriaSelect.val(`${IdCategoria}`).change();
                } else {
                    categoriaSelect.val('0').change();
                }
            } else {
                categoriaSelect.html(`<option value='0'>${response.message}</option>`);
            }
        },
        error: function(xhr, status, error) {
            $("#Categoria").html("<option value='0'>Error al cargar categorías</option>");
        }
    });

}

function getProduct(IdProducto) {
    $.ajax({
        type: 'GET',
        url: '/API/Productos/' + IdProducto,
        contentType: "Application/json",
        success: function (response) {
            if (response.status == 1) {
                $("#tituloModal").text("Datos del Producto");

                $('#IdProducto').val(response.data.idProducto);

                $('#Producto').val(response.data.producto).attr("disabled", true);
                $('#Categoria').attr("disabled", true);
                loadCategorias(response.data.categoria.idCategoria);

                $('#Stock').val(response.data.stock).attr("disabled", true);
                $('#Precio').val(response.data.precio).attr("disabled", true);
                $('#Descripcion').val(response.data.descripcion).attr("disabled", true);

                $('#fotoProducto').val(response.data.imagen).attr("disabled", true);
                $('#chargeImage').attr("disabled", true);

                const imageUrl = $('#fotoProducto').val().trim();
                if (imageUrl) {
                    const imageElement = `<img src="${imageUrl}" alt="Foto del Producto" style="max-width: 100%; max-height: 150px;" class="img-thumbnail">`;
                    $('#miniaturaContainer').html(imageElement);
                }

                $('#btnSave').hide();
                $('#btnEdition').show();
                $('#btnEliminar').show();
                $('#editionMode').show();
            }
            else {
                Swal.fire({
                    title: 'Error',
                    icon: "warning",
                    html: response.message,
                    timer: 1000,
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading();
                    },
                }).then(function () {
                    $('#btnCancel').click();
                });
            }
        }
    });
}