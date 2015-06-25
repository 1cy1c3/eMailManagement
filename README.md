eMailManagement
===============

## Description
Manages electronic mails of contacts.

## Prerequisites

+ JRE

## Usage
At first, clone or download this project. Second, add the JAR `sqlite-jdbc` to your buildpath. Afterwards, run your console and compile the project. Second, you have to interpret the main - class with the main - method, for example:
```
cd eMailManagement-master/bin/presentationLayer
java EmailManagement
```
Finally, you are able to manage your email - contacts and administrate names, surnames and emails.

__NOTE__: In this application, the DAO - Pattern is used. If you want to use another data - layer like XML, open the `./raw/pref.xml` and set xml before sqlite.

## More information
Read more about DAO at http://en.wikipedia.org/wiki/Data_access_object.
