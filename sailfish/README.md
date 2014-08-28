SailFish
=============
SailFish will accept a list of URLS, probably best to get this from your apache/tomcat log directory (other web technology is available)
It will accept a thread count which in this case is a http user. Each user will establish a new http session. 

This tool will make every effort to max out the number of threads in the pool providing the workerfactory indicates there is more work to be done. 

Useful command for getting urls from a page
curl http://www.mirror.co.uk | grep -o '"http://[^"]*"'

java -jar Sailfish*.jar 10 /mylistOfUrls.txt

1. Support converting an apache log into a replayable list of sessions
2. This is represented as XML
3. Sailfish to process XML file
4. Fully TDD, Java 8 application
5. Configurable logging information via XML or command line config
6. Logstash to load SailFish out put into elastic search
7. Front end to represent results from the SailFish run.
8. Docker as a lightweight deployment tool.
