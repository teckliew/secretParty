Template.newEvent.events({
    'submit form': function(event){
      event.preventDefault();
      var eventName = $('[id=eventName]').val();
      //var eventImage = $('[id=eventImage]').val();
      var eventDate = $('[id=eventDate]').val();
      var eventTime = $('[id=eventTime]').val();
      var eventLocation = $('[id=eventLocation]').val();
      var email = $('[id=email]').val();
      var secretChatcheck = $('[id=secretChatcheck]').val();
      var secretHotlinecheck = $('[id=secretHotlinecheck]').val();
      var rsvpCheck = $('[id=rsvpCheck]').val();
      var phonenum = $('[id=phonenum]').val();
      var flownum = $('[id=flownum]').val();
      var eventDetails = $('[id=eventDetails]').val();

      var post = {
          name: eventName,
          //image: eventImage,
          date: eventDate,
          time: eventTime,
          location: eventLocation,
          details: eventDetails,
          email: email,
          chat: secretChatcheck,
          hotline: secretHotlinecheck,
          showrsvp: rsvpCheck,
          phone: phonenum,
          flow: flownum
      };

      post._id = Events.insert(post);
      Router.go('events.show', post);

      $('[id=eventName]').val('');
      //$('[id=eventImage]').val('');
      $('[id=eventDate]').val('');
      $('[id=eventTime]').val('');
      $('[id=eventLocation]').val('');
      $('[id=eventDetails]').val('');
      $('[id=email]').val('');
      $('[id=secretChatcheck]').val('');
      $('[id=secretHotlinecheck]').val('');
      $('[id=rsvpCheck]').val('');
      $('[id=phonenum]').val('');
      $('[id=flownum]').val('');
    }
});
