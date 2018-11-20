<?php
	
	class DbOperations{
		
		private $con;
		
		function __construct(){
			
			require_once dirname(__FILE__).'/DbConnect.php';
			
			$db = new DbConnect();
			
			$this->con = $db->connect();
		}
		
		/*Database Operations -> CRUD*/
		private function isUserExist($email){
			$sql = $this->con->prepare("SELECT parent_id FROM parents WHERE email = ?");
			$sql->bind_param("s", $email);
			$sql->execute();
			$sql->store_result();
			return $sql->num_rows > 0;
		}

        public function createUser($email, $pass, $name){
			if($this->isUserExist($email)){
				return 0;
			}else{
				$password = md5($pass);
				$sql = $this->con->prepare("INSERT INTO `parents` (`email`, `password`, `name`) VALUES (?,?,?);");
				$sql->bind_param("sss",$email,$password,$name);

				if($sql->execute()){
					$sql = $this->con->prepare("UPDATE `parents` SET `parent_id` = CONCAT(parent,id);");
					$sql->execute();
					return 1;
				}else{
					return 2;
				}
			}
		}
        
		public function userLogin($email, $pass){
            $password = md5($pass);
            $sql = $this->con->prepare("SELECT parent_id FROM parents WHERE email = ? AND password = ?");
            $sql->bind_param("ss",$email,$password);
			$sql->execute();
            $sql->store_result(); 
			
			if($sql->num_rows > 0){
				return 1;
			}else{
				$sql = $this->con->prepare("SELECT teacher_id FROM teachers WHERE email = ? AND password = ?");
				$sql->bind_param("ss",$email,$password);
				$sql->execute();
				return 2;
			}  
        }

        public function getParentByEmail($email){
            $sql = $this->con->prepare("SELECT * FROM parents WHERE email = ?");
            $sql->bind_param("s",$email);
            $sql->execute();
            return $sql->get_result()->fetch_assoc();
		}

		public function getTeacherByEmail($email){
            $sql = $this->con->prepare("SELECT * FROM teachers WHERE email = ?");
            $sql->bind_param("s",$email);
            $sql->execute();
            return $sql->get_result()->fetch_assoc();
		}

		public function displayStudent($id){
			$sql = $this->con->prepare("SELECT * FROM students WHERE parent_id = ? OR teacher_id = ?");
			$sql->bind_param("ss",$id,$id);
			$sql->execute();
			return $sql->get_result()->fetch_all(MYSQLI_ASSOC);	
		}
	}
?>