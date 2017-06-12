<!DOCTYPE html>
<html lang="en">
<head>

  <!-- Basic Page Needs
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
  <meta charset="utf-8">
  <title>Your page title here :)</title>
  <meta name="description" content="">
  <meta name="author" content="">

  <!-- Mobile Specific Metas
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- FONT
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
  <link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">

  <!-- CSS
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
  <link rel="stylesheet" href="css/normalize.css">
  <link rel="stylesheet" href="css/skeleton.css">

  <!-- Favicon
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
  <link rel="icon" type="image/png" href="images/favicon.png">

</head>
<body>

  <!-- Primary Page Layout
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
  <div class="container">
    <div class="row">
      <div class="one-half column" style="margin-top: 5%">
        <h2>You make your own Martini!</h2>
        <p>You make your own Martini! This is a page under construction. Watch out for more! If your name is Ito then welcome.</p>
      	<h4>Maximise your time on earth by visiting here every day.</h4>
      	Here are your latest visits:
      	<table class="u-full-width">
  		<thead>
    		<tr>
      		<th>Date</th>
      		<th>Request</th>
    		</tr>
  		</thead>
  		<tbody>
      	<#list logfile as log> 
    		<tr>
		    <td>${log.date?datetime}</td>
      		<td>${log.request}</td>
    		</tr>
		</#list>
  		</tbody>
		</table>
      	<form>
			  <div class="row">
			    <div class="six columns">
			      <label for="exampleEmailInput">Your email</label>
			      <input class="u-full-width" type="email" placeholder="test@mailbox.com" id="exampleEmailInput">
			    </div>
			    <div class="six columns">
			      <label for="exampleRecipientInput">Reason for contacting</label>
			      <select class="u-full-width" id="exampleRecipientInput">
			        <option value="Option 1">Questions</option>
			        <option value="Option 2">Admiration</option>
			        <option value="Option 3">Can I get your number?</option>
			        <option value="Option 4">Who is Ito?</option>
			      </select>
			    </div>
			  </div>
			  <label for="exampleMessage">Message</label>
			  <textarea class="u-full-width" placeholder="Hi Belvedere …" id="exampleMessage"></textarea>
			  <label class="example-send-yourself-copy">
			    <input type="checkbox">
			    <span class="label-body">Send a copy to yourself</span>
			  </label>
			  <input class="button-primary" type="submit" value="This button does nothing">
		</form>
      </div>
    </div>
  </div>

<!-- End Document
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
</body>
</html>
