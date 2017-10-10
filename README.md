# AndroidIII-ReadWebAsync
Android App Dev III Exercise exploring multi-threading, background and async tasks.

http://www.vogella.com/tutorials/AndroidBackgroundProcessing/article.html#asynctask
This project ReadWebAsync implements the second activity from above tutorial.
This project uses a webview instead of a text view.  Basically, this code uses okhttp
to download html from a url into a String (in background) and when download is complete,
the String (NOT the url) is loaded into a webview for display.
