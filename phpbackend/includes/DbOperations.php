<?php
	
	class DbOperations{
		
		private $con;
		
		function __construct(){
			
			require_once dirname(__FILE__).'/DbConnect.php';
			
			$db = new DbConnect();
			
			$this->con = $db->connect();
		}
		
		/*Database Operations -> CRUD*/
        
        /*Create parent account and information */
		public function createParent($email, $pass, $name){
            $password = md5($pass);
            $sql = $this->con->prepare("INSERT INTO `parents` (`parent_id`, `email`, `password`, `name`) VALUES (NULL, ?, ?, ?);");
			$sql->bind_param("sss",$email,$password,$name);

            if($sql->execute()){
				return true;
			}else{
				return false;
			}
		}
        
        
		public function userLogin($email, $pass){
            $password = md5($pass);
            $sql = $this->con->prepare("SELECT parent_id FROM parents WHERE email = ? AND password = ?");
            $sql->bind_param("ss",$email,$password);
            $sql->execute();
            $sql->store_result(); 
            return $sql->num_rows > 0;
        }
 

        public function getUserByEmail($email){
            $sql = $this->con->prepare("SELECT * FROM parents WHERE email = ?");
            $sql->bind_param("s",$email);
            $sql->execute();
            return $sql->get_result()->fetch_assoc();
        }
		
	}
?>