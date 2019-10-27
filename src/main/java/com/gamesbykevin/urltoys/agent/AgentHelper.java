package com.gamesbykevin.urltoys.agent;

import static com.gamesbykevin.urltoys.util.Util.convertText;

public class AgentHelper {

    /**
     * List of commands
     */

    //check each link for additional links
    public static final String COMMAND_MAKE = convertText("make");

    //download all links
    public static final String COMMAND_GET = convertText("get");

    //only keep specified text in list
    public static final String COMMAND_KEEP = convertText("keep");

    //remove specified links with text
    public static final String COMMAND_REMOVE = convertText("remove");

    //print out all links
    public static final String COMMAND_SHOW = convertText("show");

    //write list of links to file
    public static final String COMMAND_SAVE = convertText("save");

    //load list of links to file
    public static final String COMMAND_LOAD = convertText("load");

    //remove all links from current list
    public static final String COMMAND_CLEAR = convertText("clear");

    //print a list of helpful commands
    public static final String COMMAND_HELP = convertText("help");

    public static void print() {

        System.out.println(COMMAND_MAKE.toUpperCase()   + " - checks every url in our list for additional urls");
        System.out.println(COMMAND_GET.toUpperCase()    + " - attempts to download every url in our list");
        System.out.println(COMMAND_KEEP.toUpperCase()   + " [text] - only keep the urls in our list with the specified text");
        System.out.println(COMMAND_REMOVE.toUpperCase() + " [text] - only remove the urls in our list with the specified text");
        System.out.println(COMMAND_SHOW.toUpperCase()   + " - display all the urls");
        System.out.println(COMMAND_SAVE.toUpperCase()   + " - saves the list of urls to a file");
        System.out.println(COMMAND_LOAD.toUpperCase()   + " [file path] - adds to our list of urls from the specified path");
        System.out.println(COMMAND_CLEAR.toUpperCase()  + " - removes all urls in our list");
        System.out.println(COMMAND_HELP.toUpperCase()   + " - displays this list of commands");
    }
}