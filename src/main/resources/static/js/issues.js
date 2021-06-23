const CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');

function editPet(index) {
    let container = $(".tablerow-" + index);
    let elements = container.children();
    let selectedPet = elements[1].innerHTML;

    container.hide();

    container.after(
        `<tr class="editrow-${index}">
            <td>${elements[0].innerHTML}</td>
            <td><select id='editPet-${index}' class='form-control'></td>
            <td>${elements[2].innerHTML}</td>
            <td><input id='editDescription-${index}' type='text' class='form-control input-sm' value='${elements[3].innerHTML}'/>
            <td><input id='editDate-${index}' type='date' class='form-control input-sm' value='${elements[4].innerHTML}'/></td>
            <td><button type='button' class='btn btn-primary'onclick=sendUpdatePetRequest(${index})>Save</button>
                <button type='button' class='btn btn-secondary'onclick=updatePetInfo(${index})>Cancel</button></td>
        </tr>`
    );
    const sel = $(`#editPet-${index}`);
    sel.append($("<option>").attr('value', "").text("Select pet"));
    $(petsData).each(function () {
        sel.append($("<option>").attr('value', this.id).text(this.name + ", owner: " + this.owner));
    });
    $(`#editPet-${index} option:contains(${selectedPet})`).attr('selected', 'selected');
}

function sendUpdatePetRequest(index) {
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
                console.log(`response status: ${response.status}`);
                if (response.result) {
                    console.log("Update pet");
                    updatePetInfo(index, response.result);
                } else {
                    console.log("Display error");
                    $(`.editrow-${index}`).after(`<tr class='text-danger'><td colspan="6">The error occurs when trying to save the issue </td></tr>`)
                }
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
            $(".tablerow-" + index).after(`<tr class='text-danger'><td colspan="6">The error occurs when trying to save the issue </td></tr>`)
            console.log(`error: ${JSON.stringify(e)}`);
        }
    });
}

function updatePetInfo(index, updatedPetInfo) {
    $(".text-danger").remove();

    let container = $(".tablerow-" + index);
    $(`.editrow-${index}`).remove();
    container.show();
    let elements = container.children();

    if (updatedPetInfo) {
        elements.eq(1).html(updatedPetInfo.petName);
        elements.eq(2).html(updatedPetInfo.doctorFullName);
        elements.eq(3).html(updatedPetInfo.description);
        elements.eq(4).html(updatedPetInfo.date);
    }
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
