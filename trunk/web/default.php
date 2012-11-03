<!-- <html> -->
<!-- <head> -->
<!-- <link rel="stylesheet" href="css/basic.css" type="text/css"> -->
<!-- </head> -->
<!-- <body> -->
<!-- 	<p class="header"></p> -->
<?php

//date_default_timezone_set("UTC");

//phpinfo();

// header( 'Location: NewHtml.html' ) ;


// 	$con = mysql_connect("localhost:3306","root","123456");
// 	if (!$con)
	// 	{
	// 		die('Could not connect: ' . mysql_error());
	// 	}
	// 	else
		// 	{
		// 		mysql_select_db("world", $con);

		// 		$result = mysql_query("SELECT * FROM city LIMIT 10");

		// 		if(mysql_num_rows($result) == 0)
			// 		{
			// 			echo "sonuç dönmedi";
			// 		}

			// 		while($row = mysql_fetch_array($result))
				// 		{
				// 			echo $row['Name'] . " - " . $row['Population'];
				// 			echo "<br />";
				// 		}

				// 		mysql_close($con);
				// 	}

$jsonObject = json_decode(file_get_contents("http://maps.google.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&sensor=false"), true);

$objResults = $jsonObject['results'][0];

//echo $jsonObject['results'][0]['address_components'][1]['long_name'];

//print_r($jsonObject['results']);

foreach ($objResults as $key => $value) {
	//echo "<p>$key | $value</p>";
	echo "<p>$key</p>";
// 	foreach ($value as $k => $v) {
// 		echo "$k | $v <br />";
// 	}
}

// foreach ( $objResults->address_components as $comp )
// {
// 	echo "{$comp->long_name}\n";
// 	echo "{$comp->short_name}\n";
// 	//echo "{$comp->name}\n";
// }
?>
<!-- </body> -->
<!-- </html> -->



