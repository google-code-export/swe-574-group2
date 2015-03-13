## REST API Documentation ##
### Rev 2.0 ###


1. Get main categories
url pattern: {host}/RestAccessibilty/service/categories

2. Get sub categories
url pattern: {host}/RestAccessibilty/service/categories/{mainCategoryId}

3. Get entries
url pattern: {host}/RestAccessibilty/service/entries

4. Get entry by location
url pattern: {host}/RestAccessibilty/service/entries?x=10&y=100

5. Add entry
url pattern: {host}/RestAccessibilty/service/entries/add
Sample form: http://testpalette.com:8080/RestAccessibilty/test.jsp
Multipart form post

6. Login
url pattern: {host}/ RestAccessibilty/service/login/sigin
Send json body {username: ,password:}
Result: {status: error: username: }
AuthToken header in da username:hashstring
AuthToken header should be sent for the requests after the initial request.

7. Get all entries by category
url pattern: {host}/RestAccessibilty/service/entries?categoryId=1

8. Get all entries by type
url pattern: {host}/RestAccessibilty/service/entries?typeId=1

9. Get types
url pattern: {host}/RestAccessibilty/service/types

10. Thumbs up and down
url pattern: {host}/RestAccessibilty/service/entries/thumbs
In the POST body {entryId:3, up:true} if up is true, then save as thumbs up and if up is false, then save as thumbs down

( {host} = http://testpalette.com:8080/ --> temporarily )