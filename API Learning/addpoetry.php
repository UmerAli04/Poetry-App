<?php //Syntax of php

$servername = "localhost"; //Initialize variable name in php
$username = "root"; //Initialize variable name in php
$password = ""; //Initialize variable name in php
$db = "poetrydb"; //Initialize variable name in php

$conn = new mysqli($servername,$username,$password,$db); //Connection part of API

if($conn -> connect_error){ // Arrow (->) is used to access property in php
	die("connection failed: " . $conn -> connect_error); //Dot (.) is used to concatenate in php
}

$POETRY = $_POST['poetry']; //Request part of API
$POET_NAME = $_POST['poet_name']; //Request part of API

$query = "INSERT INTO poetry(poetry_data, poet_name)VALUES('$POETRY', '$POET_NAME')"; //Decision Making part of API

$result = $conn -> query($query);

if($result == 1){ //Response part of API
	$response = array("status" => "1", "message" => "Poetry successfull inserted...");
}elase{
	$response = array("status" => "0", "message" => "Poetry not successfull inserted...")
}

//API response is always in JSON because JSON Language is supported in every applications so that's why we use API Response in JSON.

echo json_encode($response); //Echo is used to print anything in php

?>