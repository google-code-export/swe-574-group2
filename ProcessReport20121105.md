As of today, we've finished the requirements document and the initial database design, and started with the development. Our first prototype will basically include the following:

  * A mobile application and a web site will be present.
  * A web service will provide data (such as "get accessibility categories" or "get violations by categoryId") to both of these platforms.
  * Users will be able to browse the violations on a Google map and filter the violations by selecting predefined categories.
  * Registered users will be able to add new violations by:
    * letting the platform determine its current location automatically or by selecting a location on the map
    * selecting the most appropriate category for the violation to be submitted (these categories are extracted from the document "Mimari Erişilebilirlik Kılavuzu" prepared by Özlem Hn. and will be added to the database)
    * uploading an image of the violation
    * entering an optional description
  * Registered users will also be able to vote up and vote down an existing violation in order to emphasise the importance thereof, or to point out that its a fake entry.

More features such as "admin user" will be available in the next prototypes. Detailed information can be found in our project plan.

Since we are applying Agile-Driven Development, small details may change in the course of development. The designs (shapes, colours, fonts etc.) of the web site and the mobile application are not yet determined, so all the UI elements are in their basic forms for now. We have also started writing the Design Specifications Document and it will be ready with the first prototype. We mainly follow the meeting notes taken during scrums as design specifications for now.

Our system is based on the usual three-layer structure: There is a database, a web service, which gets the data from the database by an ORM tool and gives out to the mobile application and the web site. These two platforms consume the web service data. Web site mainly utilizes AJAX and jQuery to communicate with the web service as well as with other third party APIs, most importantly the Google Maps API, but of course prepares some more complicated data "at the back-end" using PHP.

The address of the webservice is as follows:
http://testpalette.com:8080/RestAccessibilty/service/categories (This one gets all of the categories defined in the database and returns them as JSON string)

More web service method urls will be available soon and will be shared.

As of now, basic implementations such as showing all the violations on the map or filtering violations by their category are ready and testable at the home page. Also, the front-end development of the violation submit page is finished and the web service communication thereof will be developed next.

You can access the following at our Google Code group: (http://code.google.com/p/swe-574-group2/)
  * Project plan (http://code.google.com/p/swe-574-group2/downloads/detail?name=plan1.pdf)
  * Last version of the SRS (http://code.google.com/p/swe-574-group2/downloads/detail?name=SRS.3.0.docx)
  * Database ER-Model
  * Database scripts
  * Mock-up screens
  * Latest screenshots of the web site
  * Last revision of the code base

Overall information about our process can be found at the home page of the group. We had to "upload" the docx documents to the Downloads section because Google Code Wiki doesn't provide sufficient formatting to make it look good.

We will hopefully deploy the web service and the web site to the environment provided by BOUN before the weekend; but before that, we plan to deploy these two to an environment which is "more available" to all of us and share the test urls. In the meantime, mobile application development has started but the first working prototype will not be ready until next week.

As our project plan suggests, the first prototype (v.0.1., which will include the above) will be released next week (between 12 and 18th November).