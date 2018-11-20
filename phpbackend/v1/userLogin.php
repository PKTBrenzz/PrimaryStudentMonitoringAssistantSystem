<?php 
 
require_once '../includes/DbOperations.php';
 
$response = array(); 
 
if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['email']) and isset($_POST['password'])){
        $db = new DbOperations(); 
 
        $result = $db->userLogin($_POST['email'], $_POST['password']);

        if($result == 1){
            $user = $db->getParentByEmail($_POST['email']);
            $response['error'] = false; 
            $response['user_id'] = $user['parent_id'];
            $response['email'] = $user['email'];
            $response['name'] = $user['name'];
        }elseif($result == 2){
            $user = $db->getTeacherByEmail($_POST['email']);
            $response['error'] = false; 
            $response['user_id'] = $user['teacher_id'];
            $response['email'] = $user['email'];
            $response['name'] = $user['name'];
        }else{
            $response['error'] = true; 
            $response['message'] = "Invalid email or password";          
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