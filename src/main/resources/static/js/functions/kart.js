function callPageFunctions() {
    getMyKart();
}

function getMyKart() {
    $.ajax({
        url: '/API/Compras/GetMyKart',
        type: 'GET',
        success: function(response) {
            Swal.fire({
                title: 'Exito',
                icon: "success",
                html: 'A WEVO PERRO, ANDA DORMITE LPM XD',
                timer: 5000,
                timerProgressBar: true,
                didOpen: () => {
                    Swal.showLoading();
                },
            });

            console.log(response.data);
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
            });
        }
    });
}