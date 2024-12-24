//var ctx = document.getElementById('myChart');
//var myDoughnutChart = new Chart(ctx,{
//	type:'doughnut',
//	data:{
//		labels:["いちご","ぶどう","バナナ"],
//		datasets: [
//		{
//			data:[300,100,80],
//			backgroundColor:["#f66","#c7e","#fc2"]
//			}
//		]
//	}
//});

	const ctx = document.getElementById('myChart')

//この2つの値を設定するだけ!!
const percentage = 76 //グラフのパーセンテージ(76%)
const backgroundColor = 'rgba(255, 0, 0, 1)' //グラフの色(赤)

new Chart(ctx, {
  type: 'doughnut',
  data: {
    datasets: [{
      data: [percentage, 100 - percentage],
      backgroundColor: [
        backgroundColor, //赤色
        'rgba(0, 0, 0, 0)',
      ],
      borderWidth: 0
    }]
  }
})




//var pieData = {
//	
//	
//};
//
//var pieOptions = {
//	width: '100%',
//	height: '440px'
//};
//
//new Chart.Pie('.pie-chart',pieData,pieOptions);