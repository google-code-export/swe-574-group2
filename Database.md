# MySql database for the project is created. #

You can find the MySql - ER diagram in Downloads.

  * Database name: AccessibilityGroup2
  * Collation: utf8 - turkish - case insensitive
  * Tables and relations can be seen in the model diagram.


_Discussions - alterations on the Class Diagram:_
  * A relation table between Entry and Reason is created as there can be more than one reason for an entry.

  * HandicapCategory table may not be necessary, we can use only the Reason table. A new column named "ParentReasonId" can be added. The parent reasons will correspond to the records in the HandicapCategory table and have NULL as ParentReasonId.