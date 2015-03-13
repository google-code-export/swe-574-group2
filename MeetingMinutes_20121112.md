## Meeting decisions - 12rd November, 2012 ##

### To be discussed ###

1. As of now, the image information is stored as bytes in a column in the Entry table. In the new version, we can store the uploaded file physically in a folder in the server and make the name of the file unique (probably by renaming it as a unique identifier - string such as php's time()). To be able to do that, we should change the data type of the ImageMeta in the Entry table to string (VARCHAR(MAX)).


### To-do for the week ###

1. Analyze the ticket-creating process in Google Code and start working with tickets.

2. Finish implementing the entry adding & user login - session for mobile app and web site.

3. Put real data to the database (especially the categories and types).

4. Deploy the web service, the web site and the mobile app to the BOUN server.

5. Developer meeting arranged: _17.11.2012 - Saturday 14:00 at Beşiktaş Starbucks_. Meeting will cover the following design issues for now:
  * How to keep an uploaded file in the server? By converting it into bytes and saving into database or by renaming it uniquely and keeping the physical file and adding the name of the file to the database?
  * Web Service entry add method has issues regarding the data types.