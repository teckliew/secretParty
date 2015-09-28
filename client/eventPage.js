Template.eventPage.events({
  'keyup #eventphonenum': function (evt) {
    var phone = $(evt.target).val();
    if (phone==null){
        phone=0;
    };
    Events.update(this._id, {$set:{"phone": phone}});
  },
  'keyup #eventflownum': function (evt) {
    var flow = $(evt.target).val();
    if (flow==null){
        flow=0;
    };
    Events.update(this._id, {$set:{"flow": flow}});
  }
});
