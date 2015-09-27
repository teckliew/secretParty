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

Router.route('/events/:_id', {
  name: 'events.show',
  template: 'eventPage',
  data: function() {
    var currentEvent = this.params._id;
    var eventdetail = Events.findOne({ _id: currentEvent }, {_id: 0});
    return eventdetail;
  }
})


//create a txt file
// Router.map(function() {
//   this.route('txtFile', {
//     where: 'server',
//     path: '/events/:_id',
//     action: function() {
//       var filename = 'textfile' + '.txt';
//       var text = "random text";
//       var headers = {
//         'Content-Type': 'text/plain',
//         'Content-Disposition': "attachment; filename=" + filename
//       };
//
//       this.response.writeHead(200, headers);
//       return this.response.end(text);
//     }
//   })
// });
