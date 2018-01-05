		<#if blocked>
		<h1 style="color:red">YOU ARE BLOCKED</h1>
		</#if>
		<h4>About you</h4>
      	<p>This site is running MotherBlocker and we only allow 10 hits every 5 minutes. Try getting yourself blocked by refreshing your browser a number of times.
      	Note we are observing all your requests.
      	For convenience we list your latest hits here:</p>
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
		    <td>${log.date}</td>
      		<td>${log.request}</td>
    		</tr>
		</#list>
  		</tbody>
		</table>