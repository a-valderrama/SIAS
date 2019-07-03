function renderDate(){

	//Aquí construimos la fecha a mostrar
	var date = new Date();
	var year = date.getYear();
	//Potencialmente demás
	if (year < 1000)
		year += 1900;
	var month = date.getMonth();
        //Hacemos el mes de dos digitos
        if(month/10 != 0)
            month = "0" + month;
	//Potencialmente demás
	var daym = date.getDate();
	//Aquí construimos la fecha a mostrar
	
	var clock = document.getElementById("dateDisplay");
	clock.textContent = "" + daym + "/" + month + "/" + year ;
	clock.innerText = "" + daym + "/" + month + "/" + year;

    setTimeout("renderDate()",1000);
}
renderDate();


