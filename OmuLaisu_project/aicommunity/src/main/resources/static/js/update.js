/**
 * ユーザー情報更新
 */

var input = document.getElementsByName("input")[0];
var check = document.getElementsByName("check")[0];
var submit = document.getElementsByName("update")[0];
function inputCheck(){
	 if(input.value == check.value && input.value.length != 0){
		 submit.disabled = false;
		 submit.classList.add('active');
	 } else{
		 submit.disabled = true;
		 submit.classList.remove('active');
	 }
 }
 
 input.addEventListener("input",function(){
	 inputCheck();
 });
 
 check.addEventListener("input",function(){
	 inputCheck();
 });
 
 
/*パスワード表示/非表示*/

/*チェックボックスを取得*/
var checkbox1 = document.getElementById("checkbox1");
var checkbox2 = document.getElementById("checkbox2");

/*クリックしたときに関数を実行*/
checkbox1.addEventListener("click",function(){
	passwordView(input);
});

checkbox2.addEventListener("click",function(){
	passwordView(check);
});

/*入力部分のタイプを変更*/
 function passwordView(select){
	 if(select.type == "password"){
		 select.type = "text";
	 } else {
		 select.type = "password";
	 }
 }
 
 
 