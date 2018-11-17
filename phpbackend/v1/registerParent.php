<?php

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['email']) and 
			isset($_POST['password']) and
				isset($_POST['name']))
		{

			
			$db = new DbOperations();

			$result = $db->createUser($_POST['email'], $_POST['password'], $_POST['name']);
			
			if($result == 1){
				$response['error'] = false;
				$response['message'] = "User registered successfully";
			}elseif($result == 2){
				$response['error'] = true;
				$response['message'] = "Some error occurred please try again";
			}elseif($result == 0){
				$response['error'] = true;
				$response['message'] = "Username and email already registered!";
			}		
	    }else{
		    $response['error'] = true;
		    $response['message'] = "Required fields are missing";
	    }
    }else{
	    $response['error'] = true;
	    $response['message'] = "Invalid Request";
    }

echo json_encode($response);
?>