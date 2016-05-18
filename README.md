No Password Authentication
=============================
[![Build Status](https://travis-ci.org/achatain/nopasswordauthentication.svg?branch=master)](https://travis-ci.org/achatain/nopasswordauthentication)
[![Coverage Status](https://coveralls.io/repos/github/achatain/nopasswordauthentication/badge.svg?branch=master)](https://coveralls.io/github/achatain/nopasswordauthentication?branch=master)

https://github.com/achatain/nopasswordauthentication

##What is it?

Do you agree with the following:

1. Remembering passwords is boring
2. Developing authentication system is even more boring

If yes, then you've come to the right place!

**No Password Authentication** is a secure, standalone authentication server, designed to run in the Google Cloud Platform.

It allows any web application to authenticate its users with just a couple of HTTPS requests.

##How do I build it?

This could not be simpler! Just check out the master branch and run the following command:
```
mvn install
```

##How do I deploy it?

First you should be familiar with the Google Cloud Platform, especially App Engine. If this is not the case, have a look here: https://cloud.google.com/appengine/

Once you have created a cloud project in App Engine, follow these 2 simple steps to deploy your own instance of **No Password Authentication**:
1. In the 'pom.xml' file, edit the property 'app.id' and replace the existing value with your own project id
2. Run the following command to deploy:
```
mvn appengine:update
```

###Trello board
To keep track of ongoing and upcoming work
https://trello.com/b/NhaGm0kA
