package com.gamesbykevin.urltoys.agent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.gamesbykevin.urltoys.Main.SAVE_DIR;
import static com.gamesbykevin.urltoys.agent.AgentHelper.*;
import static com.gamesbykevin.urltoys.util.Util.*;

public class Agent extends Thread {

    //default timeout is 30 seconds
    public static final int DEFAULT_TIMEOUT = 30000;

    //list of urls
    private List<String> list;

    public Agent() {

        //default constructor
        this.list = new ArrayList<>();
    }

    @Override
    public void run() {

        while (true) {

            try {

                //display how large our list of urls is
                System.out.print("Urls (" + getList().size() + "): ");

                //user will key in text here
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                //get the user entered text
                String text = reader.readLine();

                //convert text
                text = convertText(text);

                //determine which command was entered
                if (text.startsWith(COMMAND_GET)) {

                    //the directory
                    String directory = SAVE_DIR + "\\" + getRandomDirectoryName();

                    //first save the list of links to a text file
                    save(directory);

                    //download all files one by one to a new directory
                    get(directory);

                } else if (text.startsWith(COMMAND_KEEP)) {

                    text = text.replaceFirst(COMMAND_KEEP, "");
                    text = text.trim();

                    //only keep specified text
                    keep(text);

                } else if (text.startsWith(COMMAND_MAKE)) {

                    //search for additional urls
                    make();

                } else if (text.startsWith(COMMAND_REMOVE)) {

                    text = text.replaceFirst(COMMAND_REMOVE, "");
                    text = text.trim();

                    //remove the specified text
                    remove(text);

                } else if (text.startsWith(COMMAND_SHOW)) {

                    //display all our urls
                    print(getList());

                } else if (text.startsWith(COMMAND_SAVE)) {

                    //save the list of links to a text file in a new directory
                    save(SAVE_DIR + "\\" + getRandomDirectoryName());

                } else if (text.startsWith(COMMAND_LOAD)) {

                    text = text.replaceFirst(COMMAND_LOAD, "");
                    text = text.trim();

                    load(text);

                } else if (text.startsWith(COMMAND_CLEAR)) {

                    //clear the list of links
                    getList().clear();

                } else if (text.startsWith(COMMAND_HELP)) {

                    //print list of helpful commands
                    print();

                } else {

                    //anything else we assume it is a link and add to the list
                    add(text);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getList() {

        if (this.list == null)
            this.list = new ArrayList<>();

        return this.list;
    }

    private void remove(String text) {
        modify(text, false);
    }

    private void keep(String text) {
        modify(text, true);
    }

    private void modify(String text, boolean keep) {

        for (int i = 0; i < getList().size(); i++) {

            if (keep) {

                //if it doesn't contain the string, we will remove from the list
                if (!getList().get(i).contains(text)) {
                    getList().remove(i);
                    i--;
                }

            } else {

                //if it contains the string, we will remove from the list
                if (getList().get(i).contains(text)) {
                    getList().remove(i);
                    i--;
                }
            }
        }
    }

    private void load(String path) {
        try {
            //attempt to load the list of links to our list
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                getList().add(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(String saveDir) {

        try {

            //make sure path is there
            createDirectory(saveDir);

            //create our file list
            FileWriter writer = new FileWriter(saveDir + "\\list_" + System.nanoTime() + ".txt");

            //write every link to the file one line per item
            for (String str : getList()) {
                writer.write(str + System.lineSeparator());
            }

            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get(String saveDir) {

        //download each link one by one
        for (String url : getList()) {
            download(url, saveDir);
        }

        System.out.println("Done: " + saveDir);
    }

    private void add(String url) {
        getList().add(url);
    }

    private void make() {

        int max = getList().size();

        for (int i = 0; i < max; i++) {

            try {
                //our current url for checking
                String url = getList().get(i);

                //get the list of links on the current page
                List<String> tmp = make(url);

                //add to our final list
                for (String str : tmp) {
                    getList().add(str);
                }

                //remove any duplicates
                removeDuplicates(getList());

                //display status to the user
                System.out.println("checking (" + (i + 1) + " of " + max + "): " + tmp.size() + ", " + url);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> make(String url) {

        List<String> list = new ArrayList<>();

        try {

            //make sure we encode spaces
            url = url.replaceAll(" ", "%20");

            //load the web page
            Document document = Jsoup.connect(url).timeout(DEFAULT_TIMEOUT).get();

            //get the list of links
            Elements links = document.select("a[href]");

            for (Element element : links) {

                //get the absolute path url from the element
                String href = element.attr("abs:href");

                //convert to lower case
                href = convertText(href);

                //replace space with url encoded value
                href.replace(" ", "%20");

                //add the link to the list
                list.add(href);
            }

            //remove duplicates from our list
            removeDuplicates(list);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //return our list
        return list;
    }
}