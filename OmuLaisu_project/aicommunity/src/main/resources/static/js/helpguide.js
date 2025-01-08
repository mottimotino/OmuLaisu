// すべての画像要素
const images = document.querySelectorAll('img');

// 画像の説明文を表示するエリア
const caption = document.querySelector('.caption');

// 前に戻るボタンと次へ進むボタン要素
const prevBtn = document.querySelector('.previous-btn');
const nextBtn = document.querySelector('.next-btn');

// 画像の数を変数に格納する
const totalImages = images.length;

// 表示する画像を指定するための変数
let imageIndex = 0;

// ページ全体を読み込んだときに画像スライダーを更新する
window.addEventListener('load', () => {
	updateSlider();
});

// 次へ進むボタンにクリックイベントを追加
nextBtn.addEventListener('click', () => {

	// 次の画像へ表示を切り替える処理
	imageIndex++;

	// 最後の画像の次は、最初の画像を指定する
	if (imageIndex > totalImages - 1) {
		imageIndex = 0;
	}

	// 画像スライダーを更新する関数を実行する
	updateSlider();

});

// 前へ戻るボタンにクリックイベントを追加
prevBtn.addEventListener('click', () => {

	// インデックスの値を減らして前の画像を指定する
	imageIndex--;

	// 最初の画像の前は、最後の画像を指定する
	if (imageIndex < 0) {
		imageIndex = totalImages - 1;
	}

	// 画像スライダーを更新する関数を実行する
	updateSlider();
});

// 画像スライダーを更新する関数
function updateSlider() {
	  // すべての画像要素に順番に処理を行う
	images.forEach((image, index) => {
		// インデックスが同じなら
		if (index === imageIndex) {
			// その画像を表示する
			image.style.display = 'block';
			// その画像のaltを説明文として表示する
			caption.innerHTML = image.alt;
		} else {
			image.style.display = 'none';
		}
	});
}