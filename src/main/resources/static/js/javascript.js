function insertRow(labelID, labelValue) {
    var row = body.insertRow();
    var labelCell = row.insertCell(0);
    var inputCell = row.insertCell(1);
    var label = labelCell.appendChild(document.createElement('label'));
    label.setAttribute("for", labelID)
    label.innerText = labelValue;
    var input = inputCell.appendChild(document.createElement('input'));
    input.setAttribute("type", "text");
    input.setAttribute("id", labelID);
}

function addRow() {
    var rows = document.getElementById("table").rows;
    var length = rows.length;
    insertRow("row" + (length - 1), "Value " + (length - 1));
    var cell = rows[length].insertCell(2);
    var button = cell.appendChild(document.createElement("button"));
    button.onclick = function () {
        document.getElementById("table").deleteRow(cell.parentElement.rowIndex);
    };
    button.innerText = "Remove";
}
function createPolling() {
    var array = [];
    var rows = document.getElementById("table").rows;
    for (var i = 2; i < rows.length; i++) {
        array.push({pollingVariant: rows[i].cells[1].firstElementChild.value});
    }
    var polling = {
        name: document.getElementById("name").value,
        description: document.getElementById("description").value,
        variants: array,
        owner: null,
        startDate: null,
        endDate: null
    };
    $.ajax({
        contentType: "application/json",
        dataType: "json",
        type: "POST",
        url: "/polling/form",
        timeout: 100000,
        data: JSON.stringify(polling),
        success: function (response) {
            document.open();
            document.write(response.data);
            document.close();
        }
    });
}