/**
 * クイズ機能のjs
 */

/*クリックしたときに関数を実行*/


function answerShow2(submit){
	var answer = document.getElementById("answer").value;
	var select = submit.value;
	if(answer == select){
		window.alert("○")
	} else{
		window.alert("×")
	}
	
	
}