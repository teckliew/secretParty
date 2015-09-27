Template.eventPage.events({
  'keyup #eventphonenum, input': function (evt) {
    var phone = $(evt.target).val();
    if (phone==null){
        phone=0;
    };
    Events.update(this._id, {$set:{"phonenum": phone}});
  }
});
