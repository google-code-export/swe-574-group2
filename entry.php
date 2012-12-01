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

a.disabled { color:gray; }
</style>
<link rel="stylesheet" href="css/baseTheme/style.css" type="text/css"
	media="all" />
<link rel="stylesheet" href="css/basic.css" type="text/css" media="all" />

<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCg0FRVSu5XHjjkG1NV1tev04MaGOTg5Jo&sensor=false"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.2.min.js"></script>

<script type="text/javascript" src="js/ajaxupload-min.js"></script>

<!-- <script type="text/javascript" src="js/jquery.jsonp.js"></script> -->

<script type="text/javascript">

	function getParameterByName(name)
	{
	  name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
	  var regexS = "[\\?&]" + name + "=([^&#]*)";
	  var regex = new RegExp(regexS);
	  var results = regex.exec(window.location.search);
	  if(results == null)
	    return "";
	  else
	    return decodeURIComponent(results[1].replace(/\+/g, " "));
	}

	var markersArray = [];
	
	function clearOverlays() {
		  for (var i = 0; i < markersArray.length; i++ ) {
		    markersArray[i].setMap(null);
		  }
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

	function codeLatLng(latLng) {
		    var input = latLng;
		    var latlngStr = input.split(",",2);
		    var lat = parseFloat(latlngStr[0]);
		    var lng = parseFloat(latlngStr[1]);
		    var latlng = new google.maps.LatLng(lat, lng);
		    geocoder.geocode({'latLng': latlng}, function(results, status) {
		      if (status == google.maps.GeocoderStatus.OK) {
		        if (results[0]) {
		          $('#lblAddress').html(results[0].formatted_address);
		        }
		      } else {
		        alert("Adres bulma hatası: " + status);
		      }
		    });
	}

	// Set the Map variable
	var map;
	geocoder = new google.maps.Geocoder();
	function initialize() {
		var myOptions = {
			zoom : 18,
			mapTypeId : google.maps.MapTypeId.ROADMAP,
			scrollwheel: false
		};

		var infoWindow = new google.maps.InfoWindow;

		var markerImg = new google.maps.MarkerImage(
				'images/markers/marker2.png',
				// This marker is 129 pixels wide by 42 pixels tall.
				new google.maps.Size(129, 42),
				// The origin for this image is 0,0.
				new google.maps.Point(0, 0),
				// The anchor for this image is the base of the flagpole at 18,42.
				new google.maps.Point(18, 42));

		var getAllEntriesUrl = "http://swe.cmpe.boun.edu.tr:8180/rest/service/entries";
		var entryId = getParameterByName('id');
		var coordX = 0;
		var coordY = 0;
		
		$.getJSON(getAllEntriesUrl, function(data) {
			$.each(data['data'], function(index, element) {
		        if(element.id == entryId)
		        {
		        	var comment = element.comment;
					var upVote = element.upVoteCount;
					var downVote = element.downVoteCount;
					var user = element.userName;
					var category = "parent - will be added to web service";
					var childCat = "child - will be added to web service";
					var image = "http://physicsworld.com/blog/Guetamala%20hole.jpg";
					coordX = element.coordX;
					coordY = element.coordY;
					var point = new google.maps.LatLng(parseFloat(coordX), parseFloat(coordY));
					var html = "<b>Koordinatlar: </b> " + element.coordX + " , " + element.coordY;
					var marker = new google.maps.Marker({
						map : map,
						position : point,
						icon : markerImg
					});
					
					bindInfoWindow(marker, map, infoWindow, html);

					codeLatLng(coordX + ","+ coordY);
					//alert(coordX + ","+ coordY);

					$('#lblComment').html(comment);
					$('#lblCategory').html(category + " --- " + childCat);
					$('#lblUpVote').html(upVote);
					$('#lblDownVote').html(downVote);
					$('#lblUser').html(user);
					$('#hdnUpVote').val(upVote);
					$('#hdnDownVote').val(downVote);
					$('#imgEntry').attr('src', image);

					// Set the center of the map
					var pos = new google.maps.LatLng(coordX, coordY);
					map.setCenter(pos);
			    }
		    });
		 });

		map = new google.maps.Map(document.getElementById('map_canvas'),
				myOptions);

		function infoCallback(infowindow, marker) {
			return function() {
				infowindow.open(map, marker);
			};
		}
	};

	// Initializes the Google Map
	google.maps.event.addDomListener(window, 'load', initialize);

	var voteUrl = "http://swe.cmpe.boun.edu.tr:8180/rest/service/entries/thumbs";
	
	
	$(document).ready(function() {

		var entryId = getParameterByName('id');
		
		$("#lnkUpVote").click(function() {
			$.ajax({
			    url: voteUrl,
			    type: 'post',
			    //headers: {'AuthToken':'testuser:a74ff09c24dbf421a3e1d6be58c48f44867a85d4'},
			    data: JSON.stringify({entryId:entryId, up:true}),
			    dataType: 'json',
			    contentType: "application/json;charset=utf-8",
			    crossDomain: true,
			    success: function(response) {
			         alert("success");
			     },
			     error: function(xhr, errorThrown) {
			         alert('Error!  Status = ' + xhr.status + " Message = " + errorThrown);
			     }
			}).done(function(){
			    alert("up vote verildi!");
			    var upp = $('#lblUpVote').val();
			    $('#lblUpVote').val(parseInt(upp) + 1);
			});
		});
		
		$("#lnkDownVote").click(function() {
			if ($('#lnkDownVote').hasClass('disabled')) return;
			$('#lblVote').html('Down vote verildi');
			$('#msgVote').hide().fadeIn("slow", "linear");
			var upp = $('#hdnDownVote').val();
		    $('#lblDownVote').html(parseInt(upp) - 1);
		    $('#lnkDownVote').addClass('disabled');    
		});
	});
</script>

<body>
	<div>
		<?php include('master.php');?>
	</div>
	<p style="height: 20px;"></p>
	<h2>Detay</h2>
	<hr width="1100px;">
	<p style="height: 20px;"></p>
	<div style="text-align: left; margin-left: 30px; float: left;">
		<input type="hidden" id="hdnUpVote" value="0" name="hdnUpVote" />
		<input type="hidden" id="hdnDownVote" value="0" name="hdnUpVote" />
		<img border="1" src="" id="imgEntry">
		<p style="height: 10px;"></p>
		<span style="font-weight: bold;">Adres:</span>
		<span id="lblAddress"></span>
		<p style="height: 10px;"></p>
		<span style="font-weight: bold;">Açıklama:</span>
		<span id="lblComment"></span>
		<p style="height: 10px;"></p>
		<span style="font-weight: bold;">Kategori:</span>
		<span id="lblCategory"></span>
		<p style="height: 10px;"></p>
		<span style="font-weight: bold;">Olumlu Oy:</span>
		<span id="lblUpVote"></span>
		<p style="height: 10px;"></p>
		<span style="font-weight: bold;">Olumsuz Oy:</span>
		<span id="lblDownVote"></span>
		<p style="height: 10px;"></p>
		<span style="font-weight: bold;">Girişi yapan:</span>
		<span id="lblUser"></span>
		<p style="height: 10px;"></p>
		<a id="lnkUpVote" href="javascript:"><img width="50" height="50" 
			src="images/upArrow.png" title="Olumlu oy ver"></a>&nbsp;&nbsp;&nbsp;
		<a id="lnkDownVote" href="javascript:"><img width="50" height="50" 
			src="images/downArrow.png" title="Olumsuz oy ver"></a>&nbsp;&nbsp;&nbsp;
		<p id="msgVote"><span id="lblVote"></span></p>
		<p style="height: 20px;"></p>
		<a href="javascript: window.history.go(-1)" style="font-weight:bold; text-decoration: underline;"><--Geri</a>
	</div>

	<div id="map_canvas" style="width: 650px; height: 500px; margin-right: 30px; float: right;"></div>
	<p style="height: 60px;"></p>
</body>


</html>
