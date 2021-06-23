const CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');


function editPet(index) {
    let elements = $(".tablerow-" + index).children()
    let selectedPet = elements[1].innerHTML;
    elements.eq(1).html("");
    const sel = $("<select id='editPet-" + index + "' class='form-control'>").appendTo(elements.eq(1));
    sel.append($("<option>").attr('value', "").text("Select pet"));
    $(petsData).each(function () {
        sel.append($("<option>").attr('value', this.id).text(this.name + ", owner: " + this.owner));
    });

    $(`#editPet-${index} option:contains(${selectedPet})`).attr('selected', 'selected');

    elements.eq(3).html(`<input id='editDescription-${index}' type='text' class='form-control input-sm' value='${elements[3].innerHTML}'/>`)
    elements.eq(4).html(`<input id='editDate-${index}' type='date' class='form-control input-sm' value='${elements[4].innerHTML}'/>`)
    elements.eq(5).html(`<button type='button' class='btn btn-primary'onclick=sendUpdatePetRequest(${index})>Save</button>`)
}

function sendUpdatePetRequest(index) {
    $(".text-danger").remove();

    let elements = $(".tablerow-" + index).children()
    let issueId = elements[0].innerHTML;
    let petId = $(`#editPet-${index}`).val();
    let doctor = "doctor";
    let description = $(`#editDescription-${index}`).val();
    let date = $(`#editDate-${index}`).val();

    let issueRequestModel = {pet: petId, description: description, doctor: doctor, date: date};

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        dataType: 'json',
        url: CONTEXT_PATH + "api/issues?id=" + issueId,
        data: JSON.stringify(issueRequestModel),
        success: function (response) {
            if (response.status === "ok") {
                updatePetInfo(response.result, index);
            } else {
                for (let i = 0; i < response.result.length; i++) {
                    if (response.result[i].field === "pet") {
                        $(`#editPet-${index}`).after('<p class="text-danger">' + response.result[i].defaultMessage + '</p>');
                    } else if (response.result[i].field === "description") {
                        $(`#editDescription-${index}`).after('<p class="text-danger">' + response.result[i].defaultMessage + '</p>');
                    }
                }
            }
        },
        error: function (e) {
            console.log("error: " + JSON.stringify(e));
        }
    });
}

function updatePetInfo(updatedPetInfo, index) {
    let elements = $(".tablerow-" + index).children();

    let issueId = elements[0].innerHTML;

    elements.eq(1).html(updatedPetInfo.petName);
    elements.eq(2).html(updatedPetInfo.doctorFullName);
    elements.eq(3).html(updatedPetInfo.description);
    elements.eq(4).html(updatedPetInfo.date);
    elements.eq(5).html(`<button type="button" class="btn btn-primary" onclick="editPet(${index})">Edit</button>
                          <button type="button" class="btn btn-danger" onclick="deletePet(${issueId}, ${index})">Delete</button>`);
}

function deletePet(id, index) {
    const result = confirm("Are you sure you want to delete issue record?");
    if (result) {
        $.ajax({
            type: "DELETE",
            contentType: "application/json",
            url: CONTEXT_PATH + "api/issues?id=" + id,
            success: function () {
                location.reload()
            },
            error: function (e) {
                console.log("error: " + JSON.stringify(e));
            }
        });
    }
}
