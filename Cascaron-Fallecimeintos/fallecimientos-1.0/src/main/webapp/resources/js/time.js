function renderTime(){
    
    //Aquí construimos la hora a mostrar
    var time = new Date();
    var hour = time.getHours();
    var minutes = time.getMinutes();
    var seconds = time.getSeconds();
    if (hour == 24)
    	hour = 0;
    if (hour<10)
	hour = "0" + hour;
    if (minutes < 10)
	minutes = "0" + minutes;
    if(seconds < 10)
	seconds = "0" + seconds;
    //Aquí construimos la hora a mostrar
    
    var clock = document.getElementById("timeDisplay");
    clock.textContent = "" + hour + ":" + minutes + ":" + seconds;
    clock.innerText = "" + hour + ":" + minutes + ":" + seconds;
    
    setTimeout("renderTime()",1000);
}
renderTime();
