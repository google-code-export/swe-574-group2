## REST API Documentation ##

### Rev 4.0 ###

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

url pattern: {host}/service/entries?x={coordX}&y={coordY}


---


5. Get entry by id

url pattern: {host}/service/entries/{entryid}


---


6. Add entry (Requires authentication)

url pattern: {host}/service/entries/add -> for mobile
> {host}/service/entries/add/web -> for web

Sample form: {host}/test.jsp

Multipart form post




---


7. Login

url pattern: {host}/service/login/sigin

Send json body {username:{username}, password:{password}}

Result: {status:{status} error:{error} username:{username}}

Sample form: {host}/login.html

Since web service is stateful until session expires all requests will be authorized by the service.


---


8. Logout

url pattern: {host}/jj\_spring\_security\_logout

Send json body {username:{username}, password:{password}}

Result: {status:{status} error:{error} username:{username}}

Method: http GET

After logout cookies and sessions are cleaned. For methods requiring authentication, login method has to be called.


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


12. Thumbs up and down (Requires authentication)

url pattern: {host}/service/entries/thumbs

In the POST body {entryId:{entryId}, up:{true-or-false}} if up parameter is true, then save as thumbs up and if it is false, then save as thumbs down.


---


13. Get extra field from category

While getting subcategories there will be an extra field which is in the following format:
**{key:derece, value:’’,boundary:15}** -> unit will be derece and boundary value will be 15 derece


---


14. Insert extra value to entry (Requires authentication)

This is optional. To function 6, add a post parameter named "value" to the post request.


---


15. Add new user to service

url pattern: {host}/service/login/signup

http post with "username" and "password" as parameters


---


16. Add new comment to an entry (Requires authentication)

url pattern: {host}/service/comments/add

http post with "text" and "entryId" as parameters


---


17. Get priorities

url pattern: {host}/service/priorities


---


18. Get entries by priority

url pattern:{host}/service/entries?priority={priority}

"priority" can be "low" or "critical".


---


19. Entry edit

url pattern:{host}/service/entries/edit

http post with "value" and "entryId" as parameters


---


20. Mark entry as fixed

url pattern:{host}/service/entries/edit/status

http post with "value"(bool) and "entryId" as parameters


---


21. Get extra field data for a subcategory

url pattern:{host}/service/categories/sub/{subCategoryId}