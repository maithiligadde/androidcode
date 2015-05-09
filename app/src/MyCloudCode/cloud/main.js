
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});


Parse.Cloud.job("GetWordAndPush", function(request, status) {

  Parse.Cloud.httpRequest({
  	url:'http://www.parse.com/'
  }).then(function(httpResponse) {
  	// success
  	console.log(httpResponse.text);
  },function(httpResponse) {
  	// error
  	console.error('Request failed with response code ' + httpResponse.status);
  });
});



Parse.Cloud.job("Hello", function(request, status) {
console.log("Hello world");
});



Parse.Cloud.job("HelloPush", function(request, status) {
var query = new Parse.Query(Parse.Installation);
query.equalTo('appVersion', '1.01');
Parse.Push.send(
{where: query,

  data: {
  alert: "Hello",
  Word: "Hola"
  }
}, { success: function() { 
  // success!
  }, error: function(err) { 
  console.log(err);
  }
});
});
