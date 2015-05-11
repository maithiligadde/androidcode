
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
var wordsUrl="http://files.parsetfss.com/b8cb60ab-6e26-42ad-97d0-9c3da2f91e19/tfss-e64972f5-1337-4ea1-b7c1-ed70cc845cdc-wordsENShuf.txt";
Parse.Cloud.httpRequest({ url:wordsUrl }).then(function(response) {
var words=response.text.split("\n");
var word = words[Math.floor(Math.random()*words.length)];
console.log(word);
Parse.Push.send(
{where: query,

  data: {
  alert: "Word of the day: "+word,
  Word: word
  }
}, { success: function() {
  // success!
  }, error: function(err) {
  console.log(err);
  }
});
});
});
