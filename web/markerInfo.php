<?php

// Start XML file, create parent node
$doc = new DOMDocument("1.0");
$node = $doc->createElement("markers");
$parnode = $doc->appendChild($node);


//$con = mysql_connect("localhost:3306","root","123456");
$con = mysql_connect("testpalette.com","sweDBAdmin","swe574Admin!?");

if (!$con)
{
	die('Could not connect: ' . mysql_error());
}
else
{
	//mysql_select_db("accessibilitygroup2", $con);
	mysql_select_db("AccessibilityGroup", $con);

	$result = mysql_query("SELECT Id, Comment, CoordX, CoordY FROM Entry");

// 	if(mysql_num_rows($result) == 0)
// 	{
// 		echo "sonuç dönmedi";
// 	}

	header("Content-type: text/xml");

	// Iterate through the rows, adding XML nodes for each
	while ($row = @mysql_fetch_assoc($result)){
		// ADD TO XML DOCUMENT NODE
		$node = $doc->createElement("marker");
		$newnode = $parnode->appendChild($node);

		$newnode->setAttribute("id", $row['Id']);
		$newnode->setAttribute("comment", $row['Comment']);
		$newnode->setAttribute("lat", $row['CoordX']);
		$newnode->setAttribute("lng", $row['CoordY']);
	}

	$xmlfile = $doc->saveXml();
	echo $xmlfile;

	mysql_close($con);
}
?>