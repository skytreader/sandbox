var dater = new Date();

function getTimestamp(){
    return dater.getDate() + "/" + (dater.getMonth() + 1) + "/" + dater.getFullYear() +
      " " + dater.getHours() + ":" + dater.getMinutes() + ":" + dater.getSeconds();
}
