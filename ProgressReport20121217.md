As decided last Monday, we didn't attend the weekly class meeting today to gain speed at finishing the design and implementation of the new features. These new features will include the following:

> - We have extracted more criteria for violations such as "rampa eğimi", "sahanlık", "tavandan asılı tabela yerden yüksekliği" etc. and thresholds for them (http://swe-574-group2.googlecode.com/files/EngelOnemKriterleri.xls). We will "teach" the system these thresholds. Users will be able to provide information about these criteria while submitting violations, so that the system will be able to group the violations by their importance/priority.

> - We have made these extra info fields described above as well as the commment field of an existing violation editable by other users.

> - We have implemented a new "status" property for violations. This way, the original submitter of the violation will be able to mark a violation as fixed if he/she decides that the violation is no longer present.

> - We have altered some functions such as voting for a violation and adding/editing violation so as to require user authentication and implemented user sign-up.

> - We have also fixed some bugs and improved the web site layout and usability. All open and fixed issues can be seen here: http://code.google.com/p/swe-574-group2/issues/list . Currently we have 8 open issues to fix.

> - We have released the latest version of our web service API, which includes the functionalities described above. Mobile app and web site implementations, which will consume these new functions of the API, are in progress.

Next Monday (24.12), we are going to demonstrate the last prototype of the web site and mobile app. It will include the functionalities described above. So, we still stick with our project plan.

For the last lesson after next week, we decided to finish up all the bugs and implement a simple reporting functionality on the web site, which will list all the violations in the system and will allow users to group them by their certain properties.