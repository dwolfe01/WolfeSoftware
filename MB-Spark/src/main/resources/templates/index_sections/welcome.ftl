 <h2>MotherBlocker</h2>
        <h4>Block Unwanted Internet Traffic</h4>
        <p>Up to 50% of all internet traffic is fake!
        Why spend time, money and effort on these bots when you should just block them?
        Please don't allow your website to be scraped or your customers' experience to be compromised.
        Download MotherBlocker here. It's FREE.</p>
        <h4>How does it work?</h4>
		<p>MotherBlocker constantly analyses the internet traffic using the web log and makes decisions based on your rules. We of course give you a very sensible set of rules to start and in most cases this will be just fine.</p>
		<h4>What features do I get for free?</h4>
		<p>Whitelisting of known IP addresses, you can simply white list IPs that should NEVER be blocked. Maybe this is someone you know like your mother.
		Fully configurable blocking algorithms, maybe you want to detect 1000 hits in every 1000 hours, or block based on USER-AGENT or simply do NOT want a specific country to ever access your website. You can also have a human unblock themselves via a captcha if the system get's it wrong.</p>
		<h4>What don't I get for free?</h4>
		<p>RestFUL web service lookups for dynamic whitelisting.
		If you are running your front end web servers as part of a cluster you may need the pro version.</p>
		<h4>Please contact us here</h4>
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
		<h4>Will you set it up for me?</h4>
		<p>Yes. We will provide a no obligation quote to wire it all up for you and offer support (if you want it). 
		We also offer a comprehensive installation guide if you have your own developers.</p>
		<h4>Tell me more</h4>
      	<p>This site is running motherblocker and we only allow 3 hits every 5 minutes. Try getting yourself blocked by refreshing your browser a number of times.
      	For convenience we list your latest hits here:
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