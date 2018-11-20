<?php 
 
require_once '../includes/DbOperations.php';
 
$response = array(); 
 
if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['user_id'])){
        $db = new DbOperations(); 
 
        $response['error'] = false; 
        $response["student"] = $db->displayStudent($_POST['user_id']);

    }else{
        $response['error'] = true;
        $response['message'] = "Required fields are missing";
    }
}else{
        $response['error'] = true;
        $response['message'] = "Invalid Request";
    }

// if($_SERVER['REQUEST_METHOD']=='GET'){
//         $db = new DbOperations(); 
 
//         $response['error'] = false; 
//         $response["student"] = $db->displayStudent();

    
// }else{
//         $response['error'] = true;
//         $response['message'] = "Invalid Request";
//     }
    
echo json_encode($response);
?>