<?php
$allowedExts = array("jpg", "jpeg", "gif", "png", "JPG", "JPEG", "GIF", "PNG");
$tmpArr = explode('.', $_FILES["file"]["name"]);
$extension = end($tmpArr);
$target = "upload/";

if ((($_FILES["file"]["type"] == "image/gif")
		|| ($_FILES["file"]["type"] == "image/jpeg")
		|| ($_FILES["file"]["type"] == "image/png")
		|| ($_FILES["file"]["type"] == "image/pjpeg")
		|| ($_FILES["file"]["type"] == "image/jpg"))
		&& ($_FILES["file"]["size"] < 5242880) //max. 5 mb
		&& in_array($extension, $allowedExts))
{
	if ($_FILES["file"]["error"] > 0)
	{
		$err = "Return Code: " . $_FILES["file"]["error"];
		$returnArray = json_encode(array('success' => 'false', 'result' => $err), JSON_FORCE_OBJECT);
		
		echo $returnArray;
	}
	else
	{
// 		echo "Upload: " . $_FILES["file"]["name"] . "<br />";
// 		echo "Type: " . $_FILES["file"]["type"] . "<br />";
// 		echo "Size: " . ($_FILES["file"]["size"] / 1024) . " Kb<br />";
// 		echo "Temp file: " . $_FILES["file"]["tmp_name"] . "<br />";
// 		echo "Lang: " . $_POST["hdnLng"] . "<br />";
// 		echo "Lat: " . $_POST["hdnLat"] . "<br />";
// 		echo "Kategori: " . $_POST["ddlCategories"] . "<br />";
// 		echo "Alt Kategori: " . $_POST["ddlChildren"] . "<br />";
// 		echo "Açýklama: " . $_POST["txtDescription"] . "<br />";

// 		if (file_exists($target . $_FILES["file"]["name"]))
// 		{
// 			echo $_FILES["file"]["name"] . " already exists. ";
// 		}
// 		else
// 		{

			$categoryId = 1;
// 			if( $_POST["ddlChildren"]  == 0)
// 			{
// 				$categoryId = $_POST["ddlCategories"];
// 			}
// 			else 
// 			{
// 				$categoryId = $_POST["ddlChildren"];
// 			}
			
			
		
			$tmpArr2 = explode('.', $_FILES["file"]["name"]);
			$fileName = current($tmpArr2);
			$extension = end($tmpArr2);
			
			$storagePath = $target . $fileName . "_" . time() . "." . $extension;
		
			//move_uploaded_file($_FILES["file"]["tmp_name"], $storagePath);
			
			//$returnArray = json_encode(array('success' => 'true', 'result' => $storagePath), JSON_FORCE_OBJECT);
			
			//echo $returnArray;
			
			$data = array('coordX' => floatval($_POST["hdnLat"]), 'coordY' => floatval($_POST["hdnLng"]), 'comment' => $_POST["txtDescription"], 'file' => $storagePath, 'category' => $categoryId);
			
			$url = 'http://testpalette.com:8080/RestAccessibilty/service/entries/add';
			
			$ch = curl_init();
			
			curl_setopt($ch, CURLOPT_URL, $url);
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
			curl_setopt($ch, CURLOPT_POST, true);
			curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
			
			$resp = curl_exec($ch);
			
			curl_close($ch);
			
			echo($resp);
			
			
//		}
	}
}
else
{
	$err = "Invalid file";
	$returnArray = json_encode(array('success' => 'false', 'result' => $err), JSON_FORCE_OBJECT);
	
	echo $returnArray;
}
?>