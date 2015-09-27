<?php

require 'tropo.class.php';

$session = new Session();
    $from_info = $session->getFrom();
    $network = $from_info['id'];   

    // Create a new instance of the Tropo object.
    $tropo = new Tropo();
    
     // See if any text was sent with session start.
//    $initial_text = $session->getInitialText();

    // If the initial text is a choice, skip the input collection and go right to results
//    if(strlen($initial_text) == 1 && is_numeric($initial_text)) {
//    valid_text($tropo, $initial_text);
//    }
    
// else {

    // Welcome prompt.
    $tropo->say("Welcome to the Secret Party phone service");

    // Set up options for input.
    $options = array("attempts" => 3, "bargein" => true, "choices" => "1,2,3", "mode" => "dtmf", "name" => "movie", "timeout" => 30);

    // Ask the caller for input, pass in options.
    $tropo->ask("Press 1 R S V P. Press 2 to Get event information. Press 3 to contact the event host", $options);

    // Tell Tropo what to do when the user has entered input, or if there's an error. This redirects to the instructions under dispatch_post('/choice', 'app_choice') or dispatch_post('/incomplete', 'app_incomplete').
    if($network == 2016540312) $tropo->on(array("event" => "continue", "next" => "test-next.php?status=choose&to=4086282073&from=" . $network, "say" => "Please hold."));
     $tropo->on(array("event" => "continue", "next" => "test-next.php?status=choose&to=4086282073&from=" . $network, "say" => "Please hold."));
         
$tropo->on(array("event" => "incomplete", "next" => "test-next.php?status=end"));
    
//    }

    // Render the JSON for the Tropo WebAPI to consume.
    return $tropo->RenderJson();

?>
