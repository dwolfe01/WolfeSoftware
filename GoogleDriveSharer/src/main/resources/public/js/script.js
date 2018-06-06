document.addEventListener('keydown', function (event) {
	
  var esc = event.which == 27,
      nl = event.which == 13,
      el = event.target,
      input = el.nodeName != 'INPUT' && el.nodeName != 'TEXTAREA',
      data = {};
  		
  if (input) {
    if (esc) {
      // restore state
      document.execCommand('undo');
      el.blur();
    } else if (nl) {
      var fileId=el.id;
      var xmlHttp = new XMLHttpRequest();
      xmlHttp.open("POST", "/update/" + fileId, true); // true for asynchronous 
      xmlHttp.send(el.innerHTML);
      el.blur();
      event.preventDefault();
    }
  }
}, true);

