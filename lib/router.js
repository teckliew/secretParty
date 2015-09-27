Router.configure({
	layoutTemplate: 'home'
});

Router.map(function() {
	this.route('landing', {
		path: '/'
	});
	this.route('newEvent', {
		path: '/newevent'
	});
  this.route('scanner', {
		path: 'scanner'
	});
  this.route('eventPage', {
		path: '/eventpage'
	});
  this.route('eventList', {
    path: 'eventList'
  })
})

Router.route('/events/:params', {
  template: 'eventPage',
  data: function() {
    console.log(this.params.params);
    var currentEvent = this.params.params;
    return Events.findOne({ _id: currentEvent })
  }
})
