# urly
Url shortener
How to run:<br/>
-Clone the repo or download the .zip file.<br/>
-Make sure you have a local Redis instance runnint on the standard port for Redis (6379).<br/>  
-Application runs on localhost:8080<br/>
-How it works:<br/>
 The application has 5 endpoints:<br/>
    1) GET  /                     ->  View API documentation @ Swagger UI<br/>
    1) POST /create               ->  Receives an URL as a plain string in the body of the requests and returns a random UUID converted to Base 62.<br/>  
    2) GET  /{shorturl}           ->  Redirect to the original URL associated with the given short url.<br/> 
    3) GET  /{shorturl}/visits    ->  Returns a JSON with information about the number of visits to the given short url and about it's destination.<br/>       
    4) GET  /ranking              ->  Returns a list ranking every short URL according to their visits.u 

