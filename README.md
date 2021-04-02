# urly
Url shortener

How to run:
-Clone the repo or download the .zip file. 
-Make sure you have a local Redis instance runnint on the standard port for Redis (6379).  
-Application runs on localhost:8080
-How it works:
  The application has 4 endpoints:
    1) POST /                     ->  Receives an URL as a plain string in the body of the requests and returns a random UUID converted to Base 62.  
    2) GET  /{shorturl}           ->  Redirect to the original URL associated with the given short url. 
    3) GET  /{shorturl}/visits    ->  Returns a JSON with information about the number of visits to the given short url and about it's destination.       
    4) GET  /top                  ->  Returns a list ranking every short URL according to their visits.
