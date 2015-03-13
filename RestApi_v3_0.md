## REST API Documentation ##

### Rev 3.0 ###

### Changes since the last revision (2.0) are indicated _in italic_. ###

### Main host: http://swe.cmpe.boun.edu.tr:8180/rest ###


1. Get main categories

url pattern: {host}/service/categories


---


2. Get sub categories

url pattern: {host}/service/categories/{mainCategoryId}


---


3. Get entries

url pattern: {host}/service/entries


---


4. Get entry by location

url pattern: {host}/service/entries?x={xCoord}&y={yCoord}


---


5. _Get entry by id_

url pattern: {host}/service/entries/{entryid}


---


6. Add entry _{Requires authentication}_

url pattern: {host}/service/entries/add

Sample form: http://testpalette.com:8080/RestAccessibilty/test.jsp

Multipart form post


---


7. _Login_

url pattern: {host}/service/login/sigin

Send json body {username: ,password:}

Result: {status: error: username: }

Sample form: {host}/login.html

Since web service is stateful until session expires all requests will be authorized by the service.


---


8. _Logout_

url pattern: {host}/jj\_spring\_security\_logout

Send json body {username: ,password:}

Result: {status: error: username: }

Method: http GET

After logout cookies and sessions are cleaned. For method requiring authentication login method has to be called.


---


9. Get all entries by category

url pattern: {host}/service/entries?categoryId={categoryId}


---


10. Get all entries by type

url pattern: {host}/service/entries?typeId={typeId}


---


11. Get types

url pattern: {host}/service/types


---


12. Thumbs up and down

url pattern: {host}/service/entries/thumbs

In the POST body {entryId:{entryId}, up:{true-or-false}} if up parameter is true, then save as thumbs up and if it is false, then save as thumbs down.