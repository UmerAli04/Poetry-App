<?php //Syntax of php

//4 Steps to API Development;
//Connection: It connects the API with database.
//Request: Those parameters & values that will send to our applications or postman.
//Decision Making: Whatever decision we want to take in our API.
//Response: We response to our API, what do you want to send.

//Connection part of API
$servername = "localhost"; //Initialize variable name in php
$username = "root"; //Initialize variable name in php
$password = ""; //Initialize variable name in php
$db = "poetrydb"; //Initialize variable name in php

$conn = new mysqli($servername,$username,$password,$db);

if($conn -> connect_error){ // Arrow (->) is used to access property in php
	die("connection failed: " . $conn -> connect_error); //Dot (.) is used to concatenate in php
}

//In getpoetry, we are going to fetch all data in the poetry table so we don't need for request part so we skip this part.

//Decision Making part of API
$query = "SELECT * FROM poetry";
$result = $conn -> query($query);

$row =$result -> fetch_all(MYSQLI_ASSOC); //This method gives you the entire list will fetch & return the result

//Response part of API & Execute the statement
if(empty($row)){
    $response = array("status" => "0", "message" => "Record is empty...", "data" => $row);
} else {
    $response = array("status" => "1", "message" => "Record entered...", "data" => $row);
}

//API response is always in JSON because JSON format is supported in every applications & Language's so that's why we use API Response in JSON.

echo json_encode($response); //Echo is used to print anything in php

?>