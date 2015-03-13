## Meeting decisions - 15th October, 2012 ##

_To be done until next Monday_

1. "Severity of the accessibility violation (AV)" issue will be ignored. This can be abused easily and moreover, severity of a violation can be highly relative. This decision will be added to RSD.

2. An enumeration class (and the corresponding database table if approved) will be added for violation types. A column for that will be added to the Reason class. These types include the following:
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

8. A strict deadline for the first milestone and the contents of it will be given to the instructor (**UNTIL WEEKEND AS REQUESTED BY THE INSTRUCTOR!**)