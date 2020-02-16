//回复评论
function replyComment(e) {
    var commentId = e.getAttribute("data-id")
    var content = $("#input-"+commentId).val();
    comment(commentId,content,2);
}

//回复问题
function replyQuestion() {
    var questionId = $(".question_id").val();
    var content = $(".comment_content").val();
    comment(questionId,content,1);
}

function comment(parentId,content,type) {
    if(!content){
        alert("回复内容不能为空");
        return;
    }
    $.ajax({
        type:"post",
        url:"/comment",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        data: JSON.stringify({
            "parentId":parentId,
            "comment":content,
            "type":type
        }),
        success:function (result) {
            if(result.code == 200){
                window.location.reload();
            }else{
                if (result.code == 2001){
                    var isAccepted = confirm(result.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=9cd9d3d946beac6a79c9&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }
            }
        }
    });
}

//点赞
function like(e) {
    if(!$(e).hasClass("active")){
        var id = e.getAttribute("data-id");
        var likeCount = $(e).text();
        $.getJSON("/like/"+id,function (result) {
            if(result.code==200){
                $(e).text(parseInt(likeCount)+1);
                $(e).addClass("active");
            }
        })
    }
}


/**
 * 二级评论
 * @param e
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    var hasClass = $(e).hasClass("active");

    //没有打开 即查询数据
    if(!hasClass){
        if(comments.children().length == 1 ) {
            $.getJSON("/comment/"+id,function (data) {
                $.each(data.data.reverse(), function (index,commentDto) {
                    var media_left = $("<div/>",{
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object avatar_url",
                        "src": commentDto.user.avatarUrl
                    }))

                    var media_heading = $("<div/>",{
                        "class": "media-heading",
                        "text": commentDto.user.login
                    })

                    var comment_content = $("<div/>",{
                        "text": commentDto.comment
                    })

                    var menu = $("<div/>",{
                        "class": "menu"
                    }).append($("<span/>",{
                        "class": "pull-right",
                        "text": format(commentDto.gmtCreate)
                    }))

                    var media_body = $("<div/>",{
                        "class": "media-body"
                    })
                    media_body.append(media_heading);
                    media_body.append(comment_content);
                    media_body.append(menu);

                    var media_comments = $("<div/>",{
                        "class": "media comments"
                    })

                    media_comments.append(media_left);
                    media_comments.append(media_body);
                    comments.prepend(media_comments);
                })
            })
        }
    }
    $(e).toggleClass("active");
    comments.toggleClass("in");
}

//时间处理
function add0(m){return m<10?'0'+m:m }
function format(shijianchuo) {
//shijianchuo是整数，否则要parseInt转换
    var time = new Date(shijianchuo);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y + '-' + add0(m) + '-' + add0(d);
}

//显示标签页
function showSelectTag() {
    $(".select-tag").show()
}
//选择标签
function selectTag(e) {
    var selectTag = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if(previous.indexOf(selectTag) == -1){
        if(previous){
            $("#tag").val(previous + ',' + selectTag);
        }else{
            $("#tag").val(selectTag);
        }
    }
}