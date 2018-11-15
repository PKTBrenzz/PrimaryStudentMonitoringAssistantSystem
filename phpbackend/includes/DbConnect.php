<?php

	define('DB_NAME','school_300');
	define('DB_USER','root');
	define('DB_PASSWORD','');
	define('DB_HOST','localhost');

	class DbConnect{
		
		private $con;
		
		function __construct(){
			
		}
        
		function connect(){
            // Create connection
			$this->con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
            
            // Check connection
			if(!$this->con){
                die ("Connection failed: ". mysqli_connect_error());
            }
            
            echo "Connected successfully";
            
			return $this->con;
		}
			
	}
?>