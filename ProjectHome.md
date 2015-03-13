# Mobile & Web Apps for Public Accessibility Violations #

The aim of this project is to provide a system wherein the physical difficulties encountered by disabled people at indoor and outdoor areas in everyday life, such as steep stairs or rough roads, can be recorded and audited by volunteers.

This project is non-profit and favours accessibility for every type of user.

The recording as well as the auditing processes of the difficulties will be performed via an Android-based mobile application and a web site. Users will be able to submit the violations they confront in every-day life to the system by selecting the location of the violation and with a proof thereof (namely a photograph of the violation), and comment on and vote the entries of other users. Entries will be grouped in categories for easier filtering and reporting.

The web site can be accessed at (last deployed: 07.01.2013):

http://testpalette.com:8080/RestAccessibilty/default.html

http://swe.cmpe.boun.edu.tr:8180/rest/default.html


The Android application can be downloaded [here](http://code.google.com/p/swe-574-group2/downloads/detail?name=FiarUrbanMobile.apk&can=2&q=#makechanges).


## What is done so far? ##


_07.01.2013_

We have finished up all the open issues in the system.

The software product manual can be downloaded [here](http://swe-574-group2.googlecode.com/files/FairUrban_UserManual_rev_1_0.doc).

Visuals of the latest version of the web site and the Android app can be downloaded [here](http://swe-574-group2.googlecode.com/files/LatestScreenshots.rar).


---



_17.12.2012_

Web Service Documentation Revision 4\_0 is now [available](http://code.google.com/p/swe-574-group2/wiki/RestApi_v4_0).


---


_15.12.2012_

We have extracted some more violation criteria and grouped them by risk levels according to predetermined thresholds. Full list can be found [here](http://swe-574-group2.googlecode.com/files/EngelOnemKriterleri.xls). Some of them are as follows:

![http://swe-574-group2.googlecode.com/files/EngelOnemKriterleri1.png](http://swe-574-group2.googlecode.com/files/EngelOnemKriterleri1.png)


---


_10.12.2012_

Project Plan ver.2.0 is now available:

http://swe-574-group2.googlecode.com/files/ProjectPlan_ver_2_0.PNG


---


_09.12.2012_

We are currently working on the new layout of the web site:

![http://swe-574-group2.googlecode.com/files/NewLayout_Home.png](http://swe-574-group2.googlecode.com/files/NewLayout_Home.png)

![http://swe-574-group2.googlecode.com/files/NewLayout_Login.png](http://swe-574-group2.googlecode.com/files/NewLayout_Login.png)

![http://swe-574-group2.googlecode.com/files/NewLayout_AddEntry.png](http://swe-574-group2.googlecode.com/files/NewLayout_AddEntry.png)


---


_25.11.2012_

Web Service Documentation Revision 3\_0 is now [available](http://code.google.com/p/swe-574-group2/wiki/RestApi_v3_0).


---


_22.11.2012_

Present bugs and tasks are created and assigned to the related members.

http://code.google.com/p/swe-574-group2/issues/list


---


_18.11.2012_

Map info window on home page is modified and voting functionality is added:

![http://swe-574-group2.googlecode.com/files/20121118_Web_HomePage_3.png](http://swe-574-group2.googlecode.com/files/20121118_Web_HomePage_3.png)

Entry detail screen is implemented:

![http://swe-574-group2.googlecode.com/files/20121118_Web_Detail.png](http://swe-574-group2.googlecode.com/files/20121118_Web_Detail.png)

Manual entry adding screen is implemented:
http://testpalette.com/TestPHP/new_manual.php

![http://swe-574-group2.googlecode.com/files/20121118_Web_AddEntry_ManualLocation.png](http://swe-574-group2.googlecode.com/files/20121118_Web_AddEntry_ManualLocation.png)


---


_14.11.2012_

Web Service now available on:

http://swe.cmpe.boun.edu.tr:8180/rest/service


---


_11.11.2012_

Web Service Documentation Revision 2\_0 is now [available](http://code.google.com/p/swe-574-group2/wiki/RestApi_v2_0).


---


_08.11.2012_

Web site & web service codes are now available at [SVN source](http://code.google.com/p/swe-574-group2/source/browse).


---


_06.11.2012_

The demo web site is now available at: http://testpalette.com/TestPHP/default.php

Also [the first revision of the Web Service REST API Documentation](http://code.google.com/p/swe-574-group2/wiki/RestApi_v1_0) is ready.


---


_04.11.2012_

Web Site development has started! Display and search functionalities are almost done:

http://swe-574-group2.googlecode.com/files/20121104_Web_HomePage_2.PNG

Currently developing the new entry screen:

http://swe-574-group2.googlecode.com/files/20121104_Web_AddEntry_AutoLocation_2.PNG


---


_03.11.2012_

Implementation started for web service & web site! Details available at:
http://code.google.com/p/swe-574-group2/wiki/MeetingMinutes_20121103


---


_22.10.2012_

ER-Model ver.1.1 released!

The new version comprises an enumeration for "Violation Type". Also, n-m relation between ViolationType and Reason is created. This violation type will be provided by the system; the user won't have to select it. Reasons may have one or more violation types associated with them. These violation types will help users refine searchs; such as, a wheelchair user will be able to search for "wheelchair violations".
![http://swe-574-group2.googlecode.com/files/database_er_model_1.1.png](http://swe-574-group2.googlecode.com/files/database_er_model_1.1.png)


---


_21.10.2012_

  * Categories for accessibility violation reasons are extracted from the document prepared by Özlem Hn. --> http://code.google.com/p/swe-574-group2/wiki/CategoriesReasons
  * Use-Case diagrams for anonymous, registered and admin users are added to Downloads.
  * Mock-up screens for the mobile application and the web site are added to Downloads --> ![http://swe-574-group2.googlecode.com/files/Mobile%20-%20Wireframe.png](http://swe-574-group2.googlecode.com/files/Mobile%20-%20Wireframe.png)
  * A demo web page showing violations on Google Map is implemented.


---


_16.10.2012_

New version of ER-Model released: Reason and User class changes
![http://swe-574-group2.googlecode.com/files/database_er_model_1.0.png](http://swe-574-group2.googlecode.com/files/database_er_model_1.0.png)


---


_15.10.2012_

  * Scope of the project and an overall allocation of resources are prepared.
  * Software Requirement Specifications are determined.
  * Service functions are determined and the class diagram v.1.0 is created.
  * Database ER-model (and its sql scripts) is created according to the class diagram.
  * Project name suggestions: Fair Urban, Fair Urbanism
  * IT guy is contacted for issues about the servers & hosting environment

### Meeting decisions - 15th October, 2012 ###

_To be done until next Monday_

1. "Severity of the accessibility violation (AV)" issue will be ignored. This can be abused easily and moreover, severity of a violation can be highly relative.

2. An enumeration class (and the corresponding database table if approved) will be added for violation types. A column therefor will be added to the Reason class. These types include the following:
  * visually impaired
  * hearing impaired
  * difficulty for wheelchair
  * overall difficulty against walking
  * issue for people who have difficulties using their hands
  * impaired toilet violation etc.

These enumerations will be helpful for showing different pin images for different kinds of disabilities on the map.

3. Category class will be deprecated and Reason class will be restructured so as to contain a ParentReason field so that it will contain its own parent (category-like).

4. A new field showing the type of the user (Admin, restricted admin, contributor etc.) will be added to the User class and its corresponding enumeration will be defined.

5. Use-case diagram and its scenarios will be added to SRS accordingly and thus, the final version of the SRS will be finished.

6. Google Maps API will be analyzed and and a demo will be prepared.

7. First draft of the Design Specifications Document will be prepared.