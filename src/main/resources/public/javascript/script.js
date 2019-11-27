function Joo() {
  var url = contextroot + '/profile/post/1'

  $.getJSON(url, function(comments) {
    var items = []
    $.each(data, function(comments) {
      console.log(comments.content)
      // items.push("<li id='" + comments.id + "'>" + comments.content + '</li>')
    })

    $('<ul/>', {
      class: 'my-new-list',
      html: items.join('')
    }).appendTo('demo')
  })
}
