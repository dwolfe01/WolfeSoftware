<h4>About you</h4>
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