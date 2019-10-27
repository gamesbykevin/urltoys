package com.gamesbykevin.urltoys;

import com.gamesbykevin.urltoys.agent.Agent;

import java.awt.*;

import static com.gamesbykevin.urltoys.agent.AgentHelper.COMMAND_HELP;
import static com.gamesbykevin.urltoys.util.Util.SAVE_DIR_DEFAULT;

public class Main extends Thread {

    public static void main(String[] args) {

        //pass through our save directory
        if (args != null && args.length > 0)
            SAVE_DIR = args[0];

        System.out.println("To change directory run \"java -jar urltoys.jar C:\\save_directory\"");
        System.out.println("Directory currently set to: " + SAVE_DIR);
        System.out.println("For a list of commands type " + COMMAND_HELP);

        Agent agent = new Agent();
        agent.start();
    }

    //the assigned setting
    public static String SAVE_DIR = SAVE_DIR_DEFAULT;
}