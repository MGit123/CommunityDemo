
function commentPost() {
    var parentId = $('#parentId').val();
    var content = $('#commentContent').val()
    target(parentId,content,1);

}

function commentPost2(e){
    var commentId =e.getAttribute("data-id");
    var content = $('#input-'+commentId).val()
    target(commentId,content,2);
}

function target(targetId,content,type){

    if(content==null||content==""){
        alert("回复内容不能为空!!!");
        return;
    }

    $.ajax({
        type: 'POST',
        url: "/comment",
        contentType: "application/json",
        dataType:"json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                alert(response.message);
            }
        }

    });
}

function collapseComment(e) {
    
    var id=e.getAttribute("data-id");
    var comments=$("#comment-"+id);

    var collapse=e.getAttribute("data-collapse");
    if(collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("activ");

    }else {
        var CommentContainer = $("#comment-" + id);

        alert(CommentContainer.children().length+"个子元素");
        if (CommentContainer.children().length !="3") {
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            alert("hehe");
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLiftElement = $("<div/>", {
                        "class": "media-left",
                    }).append($("<img/>", {
                        "class": "media-object img-rounded img-around",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body text-error"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<span/>", {
                        "class": " pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                    }));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLiftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comment"
                    }).append(mediaElement);

                    CommentContainer.prepend(commentElement);
                });

                //展开二级评论
                comments.addClass("in");
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function showSelectTag(){
    $("#select-tag").show();
}

function selectTag(e){

    var previous=$("#tag").val();
    var value=e.getAttribute("data-tag");

    if(previous.indexOf(value)==-1){
        if(previous){
            $("#tag").val(previous+","+value);
        }else{
            $("#tag").val(value);
        }
    }

}