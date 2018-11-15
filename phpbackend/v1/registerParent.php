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
			
			if($db->createParent($_POST['email'], $_POST['password'], $_POST['name'])){
				$response['error'] = false;
				$response['message'] = "User registered successfully";
			}else{
				$response['error'] = true;
				$response['message'] = "Some error occurred please try again";
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