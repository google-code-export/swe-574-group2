<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Cp1254">
<title>Fair Urban</title>
</head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0;
	padding: 0
}

#map_canvas {
	height: 100%
}
</style>
<link rel="stylesheet" href="css/baseTheme/style.css" type="text/css"
	media="all" />
<link rel="stylesheet" href="css/basic.css" type="text/css" media="all" />

<!-- <script type="text/javascript" -->
<!-- 	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCg0FRVSu5XHjjkG1NV1tev04MaGOTg5Jo&sensor=false"></script> -->
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.2.min.js"></script>

<script src="http://maps.google.com/maps/api/js?sensor=false"></script>

<script type="text/javascript" src="js/ajaxupload-min.js"></script>

<!-- <script type="text/javascript" src="js/jquery.jsonp.js"></script> -->
<script type="text/javascript">
	$(document).ready(function() {


		//var getCategoriesUrl = 'http://172.20.2.5:8080/RestAccessibilty/service/categories';
		var getCategoriesUrl = 'http://testpalette.com:8080/RestAccessibilty/service/categories';
		var getEntriesByCategoryUrl = 'http://testpalette.com:8080/RestAccessibilty/service/categories';

		var selectParent = $('#ddlCategories');
		var selectChild = $('#ddlChildren');
		$.getJSON(getCategoriesUrl, function(data) {
// 			$.each(data['data'], function(index, element) {
// 		        $('#feed').append(element.id + " " + element.title + " " + " " + element.parentReasonId + "</br>");
// 		        $.each(element.violationTypes, function(index, element2)
// 		        {
// 		        	$('#feed').append(element2.id + " " + element2.title + " " + " " + element2.description + " " + element2.imageMeta);
// 		        });
// 		    });
			$.each(data['data'], function(index, element) {
		        $(selectParent).append(
		        	$('<option></option>').val(element.id).html(element.title)
		        );
		    });
		 });
		
		selectParent.change(function() {
			if(selectParent.find(":selected").val() == "0")
			{
				selectChild.hide();
			}
			else
			{
				$.getJSON(getCategoriesUrl + "/" + selectParent.find(":selected").val(), function(data) {
					if(data['data'].length > 0){
						selectChild.empty();
						selectChild.append(
					        $('<option></option>').val(0).html("Alt kategori seçebilirsiniz...")
					    );
						
						$.each(data['data'], function(index, element) {
				        	selectChild.append(
				        		$('<option></option>').val(element.id).html(element.title)
				        	);
				    	});
					selectChild.show();
					}
					else
					{
						selectChild.hide();
					}
				 });
			}
		});
		
		selectChild.change(function() {

		});

//		var saveEntryUrl = 'http://testpalette.com:8080/RestAccessibilty/service/entries/add';
		//
//				$('#btnSave').click(function() {
//					var valToSearch = 0;
//					if(selectChild.val() == "0"){
//						valToSearch = selectParent.val();
//					}
//					else{
//						valToSearch = selectChild.val();
//					}
//					
//					
//					    $.ajax(
//					    {
//					        url: 'upload_new.php',
//					        type: 'post',
//					        dataType: 'json',
//					        beforeSend: function(){
//					        	$("#spinner").show();
//							},
//							complete: function(){
//								$("#spinner").hide();
//							},
//					        success: function(data)
//					        {
//					        	alert('osman');
//					        },
//					        error: function(data){ alert("Bir hata oluştu, lütfen tekrar deneyin.");}
//					    });
//					
//				});
		
	});
</script>

<script type="text/javascript">
// Determine support for Geolocation
if (navigator.geolocation) {
    // Locate position
    navigator.geolocation.getCurrentPosition(displayPosition, errorFunction);
} else {
    alert('Bulunduğunuz yeri otomatik olarak algılayabilmemiz için tarayıcınızın GeoLocation özelliğine (HTML5) izin vermesi gerekmektedir.');
}

// Success callback function
function displayPosition(pos) {
    var mylat = pos.coords.latitude;
    var mylong = pos.coords.longitude;
    var accuracy = pos.coords.accuracy;
    var thediv = document.getElementById('locationinfo');
    thediv.innerHTML = '<p><strong>Koordinatlarınız: </strong>' + mylong + ' , ' + mylat + '</p>'
    	+ '<p><strong>Hassasiyet: </strong>' + accuracy + ' metre</p>';

    $('#hdnLng').val(mylong);
    $('#hdnLat').val(mylat);

//Load Google Map
var latlng = new google.maps.LatLng(mylat, mylong);
    var myOptions = {
      zoom: 18,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.HYBRID
    };
   
var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

//Add marker
var marker = new google.maps.Marker({
	     position: latlng, 
	     map: map, 
	     title:"Buradasınız"
	 });
}

// Error callback function
function errorFunction(pos) {
    alert('Yerinizi belirlerken bir hata oluştu. Lütfen sayfayı yenileyin.');
}
</script>

</head>
<body>
<form action="upload_new.php" method="post" enctype="multipart/form-data">
	<input type="hidden" id="hdnLat" value="0" name="hdnLat" />
	<input type="hidden" id="hdnLng" value="0" name="hdnLng" />
	<div><?php include('master.php');?></div>
	<p style="height: 20px;"></p>
	<h2>Yeni Kayıt Girişi</h2>
	<hr width="1100px;">
	<p style="height: 20px;"></p>
	<div style="text-align: left; margin-left: 30px; float: left;">
		<span style="font-weight: bold;">Kategori:</span> &nbsp; 
		<select id="ddlCategories" name="ddlCategories">
			<option value="0">Lütfen seçiniz...</option>
		</select>
		<select id="ddlChildren" name="ddlChildren" style="display: none">
			<!--<option value="0">Tamamı</option> -->
		</select>
		<p style="height: 10px;"></p>
		<span style="font-weight: bold;">Açıklama:</span>
		<textarea class="txtDescription" name="txtDescription"
			id="txtDescription" cols="40" rows="4" style="margin-bottom: -70px;"></textarea>
		<p style="height: 85px;"></p>
		<span style="font-weight: bold;">Fotoğraf:</span>
		<p></p>
		<!-- <div class="uploader"></div> -->
		<input type="file" name="file" id="file" /> 
		<p style="height: 10px;"></p>
		<input type="submit" id="btnSave" name="submit" value="Kaydet"
			style="font-weight: bold;" />
		<span id="spinner" class="spinner" style="display:none;"><img id="img-spinner" src="images/spinner.gif" alt="Kaydediliyor..."/></span>
		<p style="height: 20px;"></p>
		<div id="locationinfo"></div>
		<p style="height: 20px;"></p>
		<a href="javascript: window.history.go(-1)" style="font-weight:bold; text-decoration: underline;"><--Geri</a>
	</div>

	<div id="map_canvas" style="width: 650px; height: 500px; margin-right: 30px; float: right;"></div>
	<p style="height: 60px;"></p>
</form>
</body>
</html>
