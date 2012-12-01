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

<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.2.min.js"></script>

<script src="http://maps.google.com/maps/api/js?sensor=false"></script>

<script type="text/javascript" src="js/ajaxupload-min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		//var getCategoriesUrl = 'http://172.20.2.5:8080/RestAccessibilty/service/categories';
		//var getCategoriesUrl = 'http://testpalette.com:8080/RestAccessibilty/service/categories';
		var getCategoriesUrl = 'http://swe.cmpe.boun.edu.tr:8180/rest/service/categories';
		var getEntriesByCategoryUrl = 'http://swe.cmpe.boun.edu.tr:8180/rest/service/entries?categoryId=';

		var selectParent = $('#ddlCategories');
		var selectChild = $('#ddlChildren');
		$.getJSON(getCategoriesUrl, function(data) {
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
	});
</script>

<script type="text/javascript">

var map = null;
var marker = null;
geocoder = new google.maps.Geocoder();

var infowindow = new google.maps.InfoWindow(
{ 
	size: new google.maps.Size(150,50)
});


function codeLatLng(latLng) {
    geocoder.geocode({'latLng': latLng}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        if (results[0]) {
          var latLong = String(latLng).replace('(', '');
          latLong = latLong.replace(')', '');
          $('#locationinfo').html("<b>Seçtiğiniz adres: </b>" + results[0].formatted_address + "<p></p>"
                  + "<b>Koordinatlar: </b>" + latLong);
          
          var latlngStr = latLong.split(",",2);
		  var lat = parseFloat(latlngStr[0]);
		  var lng = parseFloat(latlngStr[1]);
		  $('#hdnLat').val(lat);
		  $('#hdnLng').val(lng);
        }
      } else {
        alert("Adres bulma hatası: " + status);
      }
    });
};

function initialize() {
// create the map
var myOptions = {
	zoom: 15,
	center: new google.maps.LatLng(41.02,28.97),
	//mapTypeControl: true,
	//mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU},
	//navigationControl: true,
	mapTypeId: google.maps.MapTypeId.ROADMAP
};
map = new google.maps.Map(document.getElementById("map_canvas"),
                           myOptions);

google.maps.event.addListener(map, 'click', function() {
   		infowindow.close();
   });

google.maps.event.addListener(map, 'click', function(event) {
//call function to create marker
    if (marker) {
       marker.setMap(null);
       marker = null;
    }
	marker = createMarker(event.latLng, "name", "<b>Location</b><br>"+event.latLng);
	codeLatLng(event.latLng);
});

};

//A function to create the marker and set up the event window function 
function createMarker(latlng, name, html) {
	var contentString = html;
	var marker = new google.maps.Marker({
   		position: latlng,
   		map: map
    });

//google.maps.event.addListener(marker, 'click', function() {
//   		infowindow.setContent(contentString); 
//   		infowindow.open(map,marker);
//   });
   
google.maps.event.trigger(marker, 'click');    
	return marker;
}


google.maps.event.addDomListener(window, 'load', initialize);

</script>

</head>
<body>
	<form
		action="http://testpalette.com:8080/RestAccessibilty/service/entries/add"
		method="post" enctype="multipart/form-data">
		<input type="hidden" id="hdnLat" value="0" name="hdnLat" /> 
		<input type="hidden" id="hdnLng" value="0" name="hdnLng" />
		<div>
			<?php include('master.php');?>
		</div>
		<p style="height: 20px;"></p>
		<h2>Yeni Kayıt Girişi</h2>
		<p style="height: 20px;"></p>
		<h3>Lütfen haritadan lokasyon seçiniz.</h3>
		<hr width="1100px;">
		<p style="height: 20px;"></p>
		<div style="text-align: left; margin-left: 30px; float: left;">
			<span style="font-weight: bold;">Kategori:</span> &nbsp; <select
				id="ddlCategories" name="ddlCategories">
				<option value="0">Lütfen seçiniz...</option>
			</select> <select id="ddlChildren" name="ddlChildren"
				style="display: none">
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
				style="font-weight: bold;" /> <span id="spinner" class="spinner"
				style="display: none;"><img id="img-spinner"
				src="images/spinner.gif" alt="Kaydediliyor..." /> </span>
			<p style="height: 20px;"></p>
			<div id="locationinfo"></div>
			<p style="height: 20px;"></p>
			<a href="javascript: window.history.go(-1)"
				style="font-weight: bold; text-decoration: underline;"><--Geri</a>
		</div>

		<div id="map_canvas"
			style="width: 650px; height: 500px; margin-right: 30px; float: right;"></div>
		<p style="height: 60px;"></p>
	</form>
</body>
</html>
