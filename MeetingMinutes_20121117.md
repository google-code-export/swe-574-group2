## Meeting decisions - 17rd November, 2012 ##

### To-do for the week ###

1. Authentication will be completed.

2. ImageMeta column on database will be varchar, images will be saved in a folder. A folder named "upload" will be created in server.

3. Category filtering will be implemented for mobile app.

4. The following data will be shown on the entry detail screen that will be opened when the pinpoint is clicked:
  * Comment
  * Category, also child category if available
  * Violation type
  * Image
  * Vote counts; vote up - vote down buttons

5. Image resizing will be made at service - side.

6. Entry add function will be separated into two: AddMobile and AddWeb

7. Entry submit on web site will be done solely with html form submit. Field names will be corrected. Two new pages will be created: SuccessWeb and ErrorWeb. Web service will redirect to these pages according to the result.

8. Category selector will be added to new entry add screen on mobile app.

9. Entry adding page for the location manually selected by the user will be implemented both in mobile app and web site.

10. On mobile app, show a pin on the map for the current location of the user.

11. When authentication session is dropped in service-side, service will return an error message (401).

12. Logout function will be implemented on web service.

13. Mobile app will be able to take photos while adding new entry.

OPTIONAL: Admin functions in web service will be implemented.