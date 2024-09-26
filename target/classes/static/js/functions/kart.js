function callPageFunctions() {
    getMyKart();
}

function getMyKart() {
    $.ajax({
        url: '/API/Compras/GetMyKart',
        type: 'GET',
        success: function(response) {
            if (response.status == 1) {
                let KartHeader = $('#KartHeader');
                KartHeader.empty();

                let headerContent = `
                <div class="col-3">
                    <p><b class="font-weight-bolder" style="font-size: medium;">Fecha de Compra: </b><br> ${response.data.fechaCompra}</p>
                </div>
                <div class="col-3">
                    <p><b class="font-weight-bolder" style="font-size: medium;">N° de Compra: </b><br> ${response.data.idCompra}</p>
                </div>
                <div class="col-3">
                    <p><b class="font-weight-bolder" style="font-size: medium;">Estado de la Compra: </b><br> <span class="badge bg-warning text-dark">Pendiente de Pago</span></p>
                </div>
                <div class="col-3">
                    <p><b class="font-weight-bolder" style="font-size: medium;">Total de la Compra: </b><br> <span class="badge bg-info text-dark">$ ${response.data.totalCompra}</span></p>
                </div>
                `;
                KartHeader.append(headerContent);

                let KartItems = $('#KartItems');
                KartItems.empty();

                response.data.kartDetails.forEach(function (item) {
                    let kartItem = `
                    <div class="row">
                        <div class="col-2 d-flex justify-content-center align-items-center">
                            <img class="img-fluid rounded-3" style="width: 65px;"
                                src="${item.imagen}" alt="${item.producto}">
                        </div>
                        <div class="col-3">
                            <p><b class="font-weight-bolder" style="font-size: medium;">${item.producto}</b></p>
                        </div>
                        <div class="col-2 d-flex justify-content-center align-items-center">
                            <p><b class="font-weight-bolder" style="font-size: medium;">$ ${item.precioUnitario}</b></p>
                        </div>
                        <div class="col-2">
                            <div class="input-group mb-3">
                                <input type="number" class="form-control" id="Cantidad" min="1" value="${item.cantidad}">
                                <button class="btn btn-info" id="btnAddToKart"><i class="fas fa-check"></i></button>
                            </div>
                        </div>
                        <div class="col-3 d-flex justify-content-center align-items-center">
                            <p><b class="font-weight-bolder" style="font-size: medium;">$ ${item.subTotal}</b></p>
                        </div>
                    </div>
                    `;

                    KartItems.append(kartItem);
                });

                let kartFooter = $('#kartFooter');
                kartFooter.empty();

                let kartFooterContent = `
                <div class="row">
                    <div class="col-2 offset-7">
                        <b class="font-weight-bolder" style="font-size: medium;">Total de la Compra:</b>
                    </div>
                    <div class="col-3 d-flex justify-content-center align-items-center">
                        <span class="badge bg-info text-dark font-weight-bolder" style="font-size: medium;">$ ${response.data.totalCompra}</span>
                    </div>
                </div>
                `;

                kartFooter.append(kartFooterContent);
                $('#btnPagar').show();
            }
            else {
                Swal.fire({
                    title: 'Información',
                    icon: "info",
                    html: response.message,
                    timer: 3000,
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading();
                    },
                }).then(function () {
                    $('#btnKart').click();
                    $('#btnPagar').hide();
                });
            }
        },
        error: function(response) {
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
                $('#btnKart').click();
                $('#btnPagar').hide();
            });
        }
    });
}

$('#btnPagar').click(function () {
    $.ajax({
        url: '/API/Compras/PayKart',
        type: 'GET',
        success: function(response) {
            Swal.fire({
                title: 'Exito',
                icon: "success",
                html: response.message,
                timer: 3000,
                timerProgressBar: true,
                didOpen: () => {
                    Swal.showLoading();
                },
            }).then(function () {
                window.location = '/home';
            });
        },
        error: function(response) {
            Swal.fire({
                title: 'Error',
                icon: "warning",
                html: response.message,
                timer: 3000,
                timerProgressBar: true,
                didOpen: () => {
                    Swal.showLoading();
                },
            }).then(function () {
                $('#btnKart').click();
            });
        }
    });
});