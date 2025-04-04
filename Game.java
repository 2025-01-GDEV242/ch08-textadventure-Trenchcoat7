import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Katie Strong
 * @version 2025.3.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private ArrayList<Room> roomHistory;
    
    /**
     * Create the game and initialise its internal map.
    */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
    */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, sewer, cavern, hell;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        sewer = new Room("in the sewer under the lab");
        cavern = new Room("in the cavern. Its dark down here");
        hell = new Room("in hell. you went so low you are now in the underworld.");
                
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);
        pub.setExit("down", lab);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("down", sewer);
        lab.setExit("up", pub);

        office.setExit("west", lab);
        
        sewer.setExit("down", cavern);
        sewer.setExit("up", lab);
        
        cavern.setExit("down", hell);
        cavern.setExit("up", sewer);

        hell.setExit("up", cavern);
        
        //Attempted to make these two separate methods but it wouldn't work :,(
        
        // create the items
        Item cheesepuff = new Item("cheese puff", "light", "A singular cheese puff surrounded by an army of ants");
        Item stapler = new Item("stapler", "medium", "A stapler from the office. You should be careful with this.");
        Item sins = new Item("sins", "heavy", "Repent.");
        
        //choose the items' locations
        pub.addItem(cheesepuff);
        office.addItem(stapler);
        hell.addItem(sins);
        
        currentRoom = outside;  // start game outside
    }
    
    /**
    *  Main play routine.  Loops until end of play.
    */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
    * Print out the opening message for the player.
    */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case LOOK:
                look();
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    /**
     * Allows the player to go back to the previous room they were in
     * @param command The command to be processed
     */
    void back(Command command)
    {
                if(roomHistory.size() <= 1)
        {
            System.out.println("You can't go any further back!");
            return;
        }
        
        if(!command.hasSecondWord()) {
            currentRoom = (roomHistory.get(roomHistory.size() - 2));
            roomHistory.remove((roomHistory.size() - 1));
            roomHistory.remove((roomHistory.size() - 1));
            System.out.println(currentRoom.getLongDescription());
            return;
        }

        int howFarBack = Integer.parseInt(command.getSecondWord());
        if (howFarBack < roomHistory.size())
        {
            Room nextRoom = roomHistory.get(roomHistory.size() - (1 + howFarBack));
            for(int i = 0; i < howFarBack; i++)
            {
                roomHistory.remove(roomHistory.size() - (1+i));
            }
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            roomHistory.add(currentRoom);
            
       
        }else
        {
            System.out.println("You can't go back that far.");
            System.out.println("You can go back " + (roomHistory.size() - 1) + " Steps.");
        }
    }
    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * Gives he player a longer description of a room.
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
