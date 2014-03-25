SailFish
=============
SailFish will accept a list of URLS, probably best to get this from your apache/tomcat log directory (other web technology is available)
It will accept a thread count which in this case is a http user. Each user will establish a new http session. 

it does not support complicated sessions, requests or threading as yet.
It creates many users, but does wait for them all to finish before creating another batch of users.
