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
<link rel="stylesheet" href="css/basic.css" type="text/css"
	media="all" />

<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCg0FRVSu5XHjjkG1NV1tev04MaGOTg5Jo&sensor=false"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.2.min.js"></script>

<script type="text/javascript" src="js/ajaxupload-min.js"></script>

<!-- <script type="text/javascript" src="js/jquery.jsonp.js"></script> -->


<script type="text/javascript">

	var markersArray = [];
	
	function clearOverlays() {
		  for (var i = 0; i < markersArray.length; i++ ) {
		    markersArray[i].setMap(null);
		  }
		}

	function downloadUrl(url, callback) {
		var request = window.ActiveXObject ? new ActiveXObject(
				'Microsoft.XMLHTTP') : new XMLHttpRequest;

		request.onreadystatechange = function() {
			if (request.readyState == 4) {
				request.onreadystatechange = doNothing;
				callback(request, request.status);
			}
		};

		request.open('GET', url, true);
		request.send(null);
	}

	function doNothing() {
	}

	function bindInfoWindow(marker, map, infoWindow, html) {
		markersArray.push(marker);
		google.maps.event.addListener(marker, 'click', function() {
			infoWindow.setContent(html);
			infoWindow.open(map, marker);
		});
	}

	// Set the Map variable
	var map;
	function initialize() {
		var myOptions = {
			zoom : 12,
			mapTypeId : google.maps.MapTypeId.ROADMAP,
			scrollwheel: false
		};

		//Define Marker properties
		var wheelChair = new google.maps.MarkerImage(
				'images/markers/wheelchair.png',
				// This marker is 129 pixels wide by 42 pixels tall.
				new google.maps.Size(129, 42),
				// The origin for this image is 0,0.
				new google.maps.Point(0, 0),
				// The anchor for this image is the base of the flagpole at 18,42.
				new google.maps.Point(18, 42));

		var infoWindow = new google.maps.InfoWindow;
		map = new google.maps.Map(document.getElementById('map_canvas'),
				myOptions);

		// Set the center of the map
		var pos = new google.maps.LatLng(41.08, 29.025);
		//var pos = new google.maps.LatLng(0, 0);
		map.setCenter(pos);
		function infoCallback(infowindow, marker) {
			return function() {
				infowindow.open(map, marker);
			};
		}

		downloadUrl("markerInfo.php", function(data) {
			var xml = data.responseXML;
			var markers = xml.documentElement.getElementsByTagName("marker");
			for ( var i = 0; i < markers.length; i++) {
				var comment = markers[i].getAttribute("comment");
				var entryId = markers[i].getAttribute("id");
				var point = new google.maps.LatLng(parseFloat(markers[i]
						.getAttribute("lat")), parseFloat(markers[i]
						.getAttribute("lng")));
				var html = "<b>Kayýt Id'si:</b> " + entryId
						+ "<br/> ''" + comment + "''";
				var marker = new google.maps.Marker({
					map : map,
					position : point,
					icon : wheelChair
				});
				bindInfoWindow(marker, map, infoWindow, html);
			}

			$('#divCount').html("Sistemde toplam " + markers.length + " adet ihlâl var.");
		});

// 		var marker = new google.maps.Marker({
// 			position : map.getCenter(),
// 			map : map,
// 			title : 'Click to zoom'
// 		});

// 		google.maps.event.addListener(marker, 'click', function() {
// 			map.setZoom(8);
// 			map.setCenter(marker.getPosition());
// 		});
	};

	// Initializes the Google Map
	google.maps.event.addDomListener(window, 'load', initialize);
</script>

<script type="text/javascript">
	$(document).ready(function() {
		
				//jQuery.support.cors = true;

// 				$.ajax({
// 					type: 'GET',
// 		            //url: 'http://testpalette.com:8080/RestAccessibilty/service/categories',
// 		            url: 'http://172.20.2.5:8080/RestAccessibilty/service/categories',
// 		          	//jsonpCallback: 'data',
// 		          	data: "{}",
// 		    		contentType: "application/json; charset=utf-8",
// 		    		dataType: 'json',
// 		          	username: 'testuser',
// 		          	password: 'swe574TEST!',
// 		//           	dataFilter: function (data, dataType) {
// 		//                 if (dataType == "json") {
// 		//                   alert('osman');
// 		//                 } else {
// 		//                   return data;
// 		//                 }
// 		//             },
// 		            success: function(msg) {
// 		                  alert(msg);
// 		            },
// 		            error:function(){
// 		                alert("hata!");
// 		            }
// 		        });

    //var getCategoriesUrl = 'http://172.20.2.5:8080/RestAccessibilty/service/categories';
		//var getCategoriesUrl = 'http://testpalette.com:8080/RestAccessibilty/service/categories';
		var getCategoriesUrl = 'http://swe.cmpe.boun.edu.tr:8180/rest/service/categories';
		var getEntriesByCategoryUrl = 'http://swe.cmpe.boun.edu.tr:8180/rest/service/entries?categoryId=';

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
				selectChild.empty();
				selectChild.append(
			        $('<option></option>').val(0).html("Tamamý")
			    );
				$.getJSON(getCategoriesUrl + "/" + selectParent.find(":selected").val(), function(data) {
					$.each(data['data'], function(index, element) {
				        selectChild.append(
				        	$('<option></option>').val(element.id).html(element.title)
				        );
				    });
					selectChild.show();
				 });
			}
		});
		
		selectChild.change(function() {

		});
			
		$('#btnSearch').click(function() {
			var valToSearch = 0;
			if(selectChild.val() == "0"){
				valToSearch = selectParent.val();
			}
			else{
				valToSearch = selectChild.val();
			}
			
			if(selectParent.val() != "osman")
			{
				var scriptUrl = getEntriesByCategoryUrl + valToSearch;
			    $.ajax(
			    {
			        url: scriptUrl,
			        type: 'get',
			        dataType: 'json',
			        beforeSend: function(){
			        	$("#spinner").show();
					},
					complete: function(){
						$("#spinner").hide();
					},
			        success: function(data)
			        {
			        	var dataS = data['data'];
		        
			        	$('#divCount').html("Filtreleme sonucu " + dataS.length + " adet sonuç bulundu.");
				        
			        	var pinImg = new google.maps.MarkerImage(
			    				'images/markers/marker2.png',
			    				// This marker is 129 pixels wide by 42 pixels tall.
			    				new google.maps.Size(22, 32),
			    				// The origin for this image is 0,0.
			    				new google.maps.Point(0, 0),
			    				// The anchor for this image is the base of the flagpole at 18,42.
			    				new google.maps.Point(18, 42));
			        	
			        	var infoWindow = new google.maps.InfoWindow;
			        	
			    		clearOverlays();

			    		$.each(dataS, function(index, element) {
			    			var comment = element.comment;
		    				var entryId = element.id;
		    				var point = new google.maps.LatLng(parseFloat(element.coordX), parseFloat(element.coordY));
		    				var html = "<b>Kayýt Id'si:</b> " + entryId
		    						+ "<br/><b>Comment:</b> " + comment;
		    				var marker = new google.maps.Marker({
		    					map : map,
		    					position : point,
		    					icon : pinImg
		    				});
		    				
		    				bindInfoWindow(marker, map, infoWindow, html);

					    });	
			        },
			        error: function(data){ alert("Bir hata oluþtu, lütfen tekrar deneyin.");}
			    });
			}
		});
		
	});
</script>

<body>
	<div><?php include('master.php');?></div>
	<div>
		<h4>Kategori filtreleme:</h4>
		<select id="ddlCategories">
			<option value="0">Tamamý</option>
		</select>
		<select id="ddlChildren" style="display:none">
			<!--<option value="0">Tamamý</option> -->
		</select>
		<input type="button" id="btnSearch" name="formSearch" value="Ara"/>
		<span id="spinner" class="spinner" style="display:none;"><img id="img-spinner" src="images/spinner.gif" alt="Yükleniyor"/></span>
		<p style="height: 7px"></p>
		<div id="divCount" style="font-weight: bold;" ></div>
	</div>
	<p style="height: 20px"></p>
	<div id="map_canvas" style="width: 900px; height: 700px; margin-left: 190px;"></div>
	<p style="height: 20px;"></p>
	<div>
		<a href="new.php" style="font-weight:bold; text-decoration: underline;">Bulunduðum lokasyona yeni giriþ</a>
		&nbsp;&nbsp;||&nbsp;&nbsp;
		<a href="#" style="font-weight:bold; text-decoration: underline;">Seçeceðim lokasyona yeni giriþ</a>
	</div>
	<p style="height: 20px"></p>
</body>


</html>
