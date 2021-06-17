let selectedPetId;

$('#updatePet').on('show.bs.modal', function (event) {
    const button = $(event.relatedTarget);
    const objectId = button.data('object-id');
    const objectName = button.data('object-name');
    const objectType = button.data('object-type');

    selectedPetId = objectId;

    $("#updatePetForm").attr('action', '/pets/update?id=' + objectId);
    $("#editPetName").val(objectName);
    $("#editPetType").val(objectType);
})

$('#deletePet').on('show.bs.modal', function (event) {
    const button = $(event.relatedTarget);
    const objectId = button.data('object-id');

    $("#deletePetForm").attr('action', '/pets/delete?id=' + objectId);
})

function updatePestRequest() {
    $(".text-danger").remove();

    const name = $('#editPetName').val();
    const typeId = $('#editPetType').val();
    const CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');

    let petRequestModel = {name: name, type: typeId};

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        dataType: 'json',
        url: CONTEXT_PATH + "api/pets/update?id=" + selectedPetId,
        data: JSON.stringify(petRequestModel),
        success: function (response) {
            if (response.status === "ok") {
//todo hide popup form
            } else {
                for (let i = 0; i < response.result.length; i++) {
                    if (response.result[i].field === "name") {
                        $('#editPetName').after('<p class="text-danger">' + response.result[i].defaultMessage + '</p>');
                    } else if (response.result[i].field === "type") {
                        $('#editPetType').after('<p class="text-danger">' + response.result[i].defaultMessage + '</p>');
                    }

                }
            }
            alert('Yay: ' + JSON.stringify(response));
        },
        error: function (e) {
            alert('Error: ' + JSON.stringify(e));
        }
    });
}