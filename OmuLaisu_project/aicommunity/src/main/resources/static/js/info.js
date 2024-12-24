var ctx = document.getElementById('myChart');
var myDoughnutChart = new Chart(ctx,{
	type:'doughnut',
	data:{
		labels:['正解','不正解'],
		datasets: [
			{
			data:[50,50],
			backgroundColor:["#f66","#0000ff"]
			}
		]
	}
});


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