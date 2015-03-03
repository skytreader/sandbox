var dater = new Date();

function getTimestamp(){
    return dater.getDate() + "/" + (dater.getMonth() + 1) + "/" + dater.getFullYear() +
      " " + dater.getHours() + ":" + dater.getMinutes() + ":" + dater.getSeconds();
}

$(document).ready(function(){
    $("#post-status").click(function(){
        var statusPost = document.createElement("li");
        statusPost.innerHTML = getTimestamp() + " " + $("#user-status").val();
        $("#status-area").prepend(statusPost);
    });
});
