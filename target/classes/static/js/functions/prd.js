function callPageFunctions() {
    getProducts();
}

function getProducts() {
    $.ajax({
        url: '/API/Productos',
        type: 'GET',
        success: function(response) {
            let productsContainer = $('#productsContainer');
            productsContainer.empty();

            if (response.data.length > 0) {
                response.data.forEach(function(producto) {
                    let productCard = `
                <div class="col">
                  <div class="card shadow mb-4" data-toggle="modal" data-target="#addToKartModal" onclick="getProduct(${producto.idProducto})">
                    <img src="${producto.imagen ? producto.imagen : 'https://via.placeholder.com/150'}" class="card-img-top" alt="${producto.producto}" style="object-fit: contain; height: 200px; width: 100%;">
                    <div class="card-header py-3">
                      <h6 class="m-0 font-weight-bold text-primary">${producto.producto}</h6>
                    </div>
                    <div class="card-body">
                      <p><b>Categoría: </b> ${producto.categoria.categoria}</p>
                      <p><b>Descripción: </b> ${producto.descripcion}</p>
                      <p><b>Precio: </b> $${producto.precio}</p>
                    </div>
                  </div>
                </div>
              `;
                    productsContainer.append(productCard);
                });
            } else {
                productsContainer.append('<p>No hay productos disponibles</p>');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error al obtener productos:', error);
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
                $('#IdProducto').val(response.data.idProducto);

                $('#Producto').text(response.data.producto);
                $('#Categoria').text(response.data.categoria.categoria);
                $('#Descripcion').text(response.data.descripcion);
                $('#Precio').text(response.data.precio);

                const imageUrl = response.data.imagen;
                if (imageUrl) {
                    let imagenProducto = $('#imagenProducto');
                    imagenProducto.attr('src', imageUrl);
                }
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
                    $('#addToKartModal').modal('hide');
                });
            }
        }
    });
}

$('#btnAddToKart').click(function () {
    if ($('#IdProducto').val() > 0 && $('#Cantidad').val() > 0) {
        var Obj = {
            idProducto: parseInt($('#IdProducto').val()),
            cantidad: parseInt($('#Cantidad').val())
        };

        $.ajax({
            type: 'POST',
            url: '/API/Compras/AddToKart',
            contentType: "Application/json",
            data: JSON.stringify(Obj),
            success: function (response) {
                if (response.status == 1) {
                    Swal.fire({
                        title: 'Exito',
                        icon: "success",
                        html: response.message,
                        timer: 1000,
                        timerProgressBar: true,
                        didOpen: () => {
                            Swal.showLoading();
                        },
                    }).then(function () {
                        $('#addToKartModal').modal('hide');
                        getProductsOnKart();
                    });
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
                        $('#addToKartModal').modal('hide');
                    });
                }
            }
        });
    }
});