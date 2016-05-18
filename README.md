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

```bash
$ mvn install
```

##How do I deploy it?

First you should be familiar with the Google Cloud Platform, especially App Engine. If this is not the case, have a look here: https://cloud.google.com/appengine/

Once you have created a cloud project in App Engine, follow these 2 simple steps to deploy your own instance of **No Password Authentication**:

1. In the 'pom.xml' file, edit the property 'app.id' and replace the existing value with your own project id

 ```xml
 <properties>
   <app.id>my-app-id</app.id>
 </properties>
 ```

2. Run the following command to deploy:

 ```bash
 $ mvn appengine:update
 ```

##How does it work?

![Sequence diagram](https://www.websequencediagrams.com/cgi-bin/cdraw?lz=dGl0bGUgTm8gUGFzc3dvcmQgQXV0aGVudGljYXRpb24gZmxvdwoKVXNlci0-WW91ciB3ZWIgYXBwOiBMb2dpbiBSZXF1ZXN0CgAQDC0-ADYbc2VydmVyOgBYEABACAAZIS0-VXNlcjogRW1haWwgbm90aWYAgSQId2l0aCBhAIEyDmxpbmsAgTgHADIGQ2xpY2tzIAANDgCBTA4AgXQPY2FsbGJhY2sAgTIyVmVyAIEUCnIAgTgqAIIcI05vbmNlIHYAVgsAghckAIM0DgCBEg1vdXRjb21lAIM6DwCCZgZLaW5kIGdyZWV0aW5ncyA6LSkK&s=napkin)

In a nutshell, only two requests against the **No Password Authentication** server enables your web app to securely authenticate users, with no need for user registration, storing passwords, and all the hassle of developing a custom authentication mechanism.

1. The authentication request:

 When your end-user inputs their email address and pushes the login button on your web app, you send an authentication request to the **No Password Authentication** server, passing in your API token and the user's email address. No need to capture a password whatsoever. Your end-user will automatically receive an email with a authentication link. Cliking on this link will send a callback to your web app, including the end-user's email address and a secure nonce.

2. The authentication verification:

 When your end-user clicks on the authentication link from the email received in the first step, they are redirected to your web app. At this point, you just need to send a verification request to the **No Password Authentication** server, passing in your API token, the user's email address and the nonce. The server will perform its magic and let you know whether or not the authentication attempt was verified successfully. Then it comes down to you to manage your session and welcome your end-user appropriately!

##Is there an already deployed instance that my web app can use straight away?
Oh yes there is! And it's available here: https://no-password-authentication.appspot.com/

It's totally free of use, thanks to the generously free quota of the Google Cloud Platform :)


##Trello board
To keep track of ongoing and upcoming work, have a look at the public Trello board: https://trello.com/b/NhaGm0kA
