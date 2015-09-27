<?php 
//this example accepts parameters passed to it from an outside REST request (such as a curl app) - 
//it extracts the values of the passed parameters and uses it to send, in this case, an outbound text
 
require 'tropo.class.php';
//brings in the Tropo library
 
//$session = new Session(); 


$tropo = new Tropo();

$json = file_get_contents("php://input");
// Create a new Session object and obtain the session ID valu

$result = new Result();

    $choice = $result->getValue();



//$session = new Session();
	//$from_info = $session->getFrom();
	$channel = $from_info['channel'];
	
	// Create a new instance of the Tropo object.
	
	// See if any text was sent with session start.
	//$initial_text = $session->getInitialText();
	
	// If the initial text is a zip code, skip the input collection and get weather information.
	if($choice == 1) {
		$tropo->say("Thank you for R S V P ing");
	} else if($choice == 2) {
		if($_GET['from'] == 2016540312) $tropo->say("The Super Secret LoL party will be hosted at Lair of the LoL on August 26th at 6 P M until the redbull is gone.");
		else $tropo->say("Sarahs Secret Lego Party will be at Lego Land October 1st at 8 A M until 8 P M");
	} else if($choice == 3) {
		$tropo->say("Transfering please hold.");
		$tropo->transfer("tel:+1" . $_GET['to']);
	}

	else {

		$tropo->say("Failed to get input");


	}

return $tropo->RenderJson();
?>
