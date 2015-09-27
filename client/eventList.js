Template.eventList.helpers({
	list: function() {
		var events = Events.find({}, {sort: {createdAt: -1}});
		return events;
	}
});
