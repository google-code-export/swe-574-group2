## REST API Documentation ##
### Rev 1.0 ###


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

( {host} = http://testpalette.com:8080/ --> temporarily )