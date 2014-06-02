SailFish
=============
SailFish will accept a list of URLS, probably best to get this from your apache/tomcat log directory (other web technology is available)
It will accept a thread count which in this case is a http user. Each user will establish a new http session. 

it does not support complicated sessions, requests or threading as yet.
This tool will make every effort to max out the number of threads in the pool providing the workerfactory indicates there is more work to be done. 


Useful command for getting urls from a page
curl http://www.mirror.co.uk | grep -o '"http://[^"]*"'

java -jar Sailfish*.jar 10 /mylistOfUrls.txt