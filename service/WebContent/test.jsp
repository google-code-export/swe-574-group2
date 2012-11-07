<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 
<html>
    <head>
        <META http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>Upload Example</title>
    </head>
    <body>
        <form method="POST" id="form_upload"  enctype="multipart/form-data" action="./service/entries/add">
                <fieldset>
                    <legend>
                        Category:
                    </legend>
                    <input type="text"  name="category" />
                </fieldset>
                <fieldset>
                    <legend>
                        X:
                    </legend>
                    <input type="text"  name="coordX" />
                </fieldset>
                <fieldset>
                    <legend>
                        Y:
                    </legend>
                    <input type="text"  name="coordY" />
                </fieldset>
                  <fieldset>
                    <legend>
						Comment:
                    </legend>
                    <input type="text"  name="comment" />
                </fieldset>
                 <fieldset>
                    <legend>
                        File:
                    </legend>
                    <input type="file" name="file">
                </fieldset>
                <input type="submit" value="Upload" />
            </form>
    </body>
</html>