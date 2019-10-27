# UrlToys
What is UrlToys?

UrlToys is a tool that will crawl webpages to gather a collection of urls
Have you ever visited a website and didn't have time to click every download link on the page?
Use this tool to your advantage

### Full list of commands are as follows
```
MAKE              - checks every url in our list for additional urls
GET               - attempts to download every url in our list (also saves a list of urls we are downloading)
KEEP   [text]     - only keep the urls in our list with the specified text
REMOVE [text]     - only remove the urls in our list with the specified text
SHOW              - display all the urls
SAVE              - saves the list of urls to a file
LOAD [file path]  - adds to our list of urls from the specified text file
CLEAR             - removes all urls in our list
HELP              - displays this list of commands
```
> Anything else entered that does not match a command above will be added as a url

## How to start the app
You start the app just like any .jar and specify the directory path as a parmeter as shown below
```
java -jar urltoys.jar C:\desired\save\folder
```


## How to use the tool (step by step example)
1. Enter a valid url `http://mmimageworld.dugtrio17.com`
2. Enter `MAKE` command to generate a list of urls
3. Enter `SHOW` to see the list of urls found
4. Enter `KEEP download` because in this example we only want urls that contain the word `download` in it
5. Enter `GET` and a folder will be created and the tool will attempt to download each url in the list

