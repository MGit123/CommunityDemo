
function commentPost() {
    var parentId = $('#parentId').val();
    var content = $('#commentContent').val();

    $.ajax({
        type: 'POST',
        url: "/comment",
        contentType: "application/json",
        dataType:"json",
        data: JSON.stringify({
            "parentId": parentId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
              $("#commentSection").hide();
              alert(response.message);
            } else {
               alert(response.message);
            }
        }

    });
}