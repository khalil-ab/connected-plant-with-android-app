<?php
if($_SERVER['REQUEST_METHOD'] == 'POST')
{
	
	$username = $_POST["username"];
	$password = $_POST["password"];
	

	require_once('conn.php');
	$sql="Select * from employee_data where username like '".$username."';";
	$result = mysqli_query($conn,$sql);

	if(mysqli_num_rows($result)==1)
	{
		echo 'username already exist';
	}
	else if(mysqli_num_rows($result)==0)
	{
 		
	$sql= "insert into employee_data(username, password) value ('$username','$password')"; 
		
	if($conn->query($sql) === TRUE)
	{ 
		echo "Insert Success"; 
	} 
	else 
	{ 
		echo"Insert not success".$mysql_qry."<br>". $conn->error; 
    } 
		$conn->close(); 
	}
}
else
{
	echo 'error';
}

?>

