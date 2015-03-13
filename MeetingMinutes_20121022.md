## Meeting decisions - 22th October, 2012 ##

1. RSD will be finalized by adding use-case diagrams and their scenarios.

2. A draft of Specification Document (DSD) will be prepared. Activity diagrams may be added later.

3. The enumeration for "Violation Type" is added to the database. Also, n-m relation between ViolationType and Reason is created. This violation type will be provided by the system; the user won't have to select it. Reasons may have one or more violation types associated with them. These violation types will help users refine searchs; such as, a wheelchair user will be able to search for "wheelchair violations".
http://code.google.com/p/swe-574-group2/downloads/detail?name=DbModelAndScripts_ver1-1.rar&can=2&q=#makechanges

![http://swe-574-group2.googlecode.com/files/database_er_model_1.1.png](http://swe-574-group2.googlecode.com/files/database_er_model_1.1.png)


4. The latest version of the database will be deployed.

5. The implementation of WebServices, which will provide data both to the mobile app and to the web site will be started.

6. From the software architecture aspect, two layers are considered: UI and WebService. JavaScript (JQuery) will be mainly used to fetch JSON-type data from the WebService and to show on front end (mostly with HTML and minimal use of PHP).