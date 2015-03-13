## Meeting decisions - 3rd November, 2012 ##

  * Web site implementations are added to the svn trunk.
  * Cross-domain issue while getting categories by the web service is solved.


### To-do for the website ###
1. Put the data fetched from web service into a dropdownlist.

http://testpalette.com:8080/RestAccessibilty/service/categories

This method also takes an integer parameter (parentCategoryId) to bring the child categories to be put into a second dropdownlist.

2. Implement entry adding via web site. User pins the location of the violation on the map, selects a category (and an optional sub-category) from the dropdownlist, enters an optional comment and uploads an image of the violation.

3. Implement the search functionality. User can refine the violation results shown on the map by selecting a violation category. The same dropdownlist structure as in the entry adding operation will be used.



### To-do for the web service ###
1. User login session issue will be analyzed.

2. New methods will be released; priority is getting entries by categoryId (for search functionality) and VoteUp and VoteDown for an existing entry.

3. Put the web service code to the svn trunk.

4. Start implementing the admin operations (Get entry by entryId, etc.)