
/**テキストエリアの入力値を確認*/
const $submitBtn = $('input'); //変数にインプット要素をセット
$submitBtn.prop('disabled', true); //ボタンは最初はdisabledでクリックできない状態に
var name01 = document.querySelector("#message"); //入力フォームを変数化

//「もし各入力欄が空だったら」という内容を関数としてセット
function judge() {
  if (name01.value !== "") {
    $submitBtn.prop('disabled', false); //空じゃなかったらボタンを活性化し、activeクラスをつける
//    $submitBtn.addClass('active');
  } else {
    $submitBtn.prop('disabled', true);
//    $submitBtn.removeClass('active');
  }
}

//先ほどの関数をここで使いまわす
name01.addEventListener("input", function() {　//第一引数にイベントの種類としてchangeではなくinputを記述
  judge();
});

/**チャットの初期位置を一番下にする*/
var chat = document.querySelector("#chat_area")
chat.scrollTop = chat.scrollHeight;

