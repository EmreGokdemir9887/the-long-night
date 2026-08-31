import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Inventory inventory;
    private ArrayList<Room> roomHistory;
    private HashSet<Room> roomSet;        
    /**
     * Create the game, create the rooms and put the items inside the rooms, create a set which contains the room, create the inventory.
     */
    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }
    
    public Game() 
    {
        roomHistory = new ArrayList<>();
        roomSet = new HashSet<>();
        gameSetup();
        parser = new Parser();
        inventory = new Inventory(0, 10);
    }

    /**
     * Create all the rooms and link their exits together, put the items in the rooms, put the rooms inside the room set.
     */
    private void gameSetup()
    {
        Room engineRoom, newStorageRoom, oldStorageRoom, crewsRooms, kitchen, deck, crewsOffice, damagedRoom, transporterRoom;
      
        // create the rooms
        engineRoom = new Room("engine room" ,"in the engine room of the ship.", true);
        newStorageRoom = new Room("new storage room", "in the new storage room of the ship, items in the old storage room has recently been transferred to this room", true);
        oldStorageRoom = new Room("old storage room", "in the old storage room of the ship.", false);
        crewsRooms = new Room("crews rooms", "in the crews personal rooms.", true);
        kitchen = new Room("kitchen", "in the kitchen of the ship.", true);
        deck = new Room("deck", "at the deck of the ship", true);
        crewsOffice = new Room("crews office", "in the crews office rooms.", true);
        damagedRoom = new Room("damaged room", "in the room which takes in water.", true);
        transporterRoom = new Room("transporter room", "You are in the magic transporter room, soon you will be teleported to a random room.", true);
        
        // initialise room exits
        engineRoom.setExit("north", kitchen);
        engineRoom.setExit("west", newStorageRoom);
        engineRoom.setExit("downstairs", damagedRoom);
        
        kitchen.setExit("south", engineRoom);
        kitchen.setExit("west", crewsOffice);
        kitchen.setExit("northeast", deck);
        kitchen.setExit("east", oldStorageRoom);

        newStorageRoom.setExit("north", crewsOffice);
        newStorageRoom.setExit("east", engineRoom);

        crewsOffice.setExit("north", crewsRooms);
        crewsOffice.setExit("south", newStorageRoom);
        crewsOffice.setExit("east", kitchen);

        crewsRooms.setExit("east", deck);
        crewsRooms.setExit("south", crewsOffice);

        deck.setExit("west", crewsRooms);
        deck.setExit("south", oldStorageRoom);
        deck.setExit("southwest", kitchen);
        deck.setExit("north", transporterRoom);
        
        transporterRoom.setExit("south", deck);
        
        oldStorageRoom.setExit("north", deck);
        oldStorageRoom.setExit("west", kitchen);
        
        damagedRoom.setExit("upstairs", engineRoom);

        currentRoom = damagedRoom;  // start game in the damaged room
        roomHistory.add(currentRoom);
        
        roomSet.add(engineRoom);
        roomSet.add(newStorageRoom);
        roomSet.add(oldStorageRoom);
        roomSet.add(crewsRooms);
        roomSet.add(kitchen);
        roomSet.add(deck);
        roomSet.add(crewsOffice);
        roomSet.add(damagedRoom);
        
        // Create Items and put them into the rooms
        Item weldingMachine, metalPlates, waterPump, key, josh, gimgor, edd, miguel, xing, kitchenGadgets, scraps, oldEngine, engineParts, susStuff, documents, maintenanceEquipments;
        
        weldingMachine = new Item("welding-machine", 6, true, false, null);
        metalPlates = new Item("metal-plates", 5, true, false, null);
        waterPump = new Item("water-pump", 9, true, false, null);
        key = new Item("key", 2, true, false, null);
        josh = new Item("Josh", 0, false, true, null);
        gimgor = new Item("gimgor", 0, false, true, key);
        edd = new Item("Edd", 0, false, true, null);
        miguel = new Item("Miguel", 0, false, true, null);
        xing = new Item("Xing", 0, false, true, null);
        kitchenGadgets = new Item("kitchen-gadgets", 3, true, false, null);
        scraps = new Item("scraps", 4, true, false, null);
        oldEngine = new Item("old-engine", 10, false, false, null);
        engineParts = new Item("engine-parts", 5, true, false, null);
        susStuff = new Item("sus-stuff", 4, true, false, null);
        documents = new Item("documents", 3, true, false, null);
        maintenanceEquipments = new Item("maintenance-equipments", 6, false, false, null);
        
        oldStorageRoom.addItem(weldingMachine);
        oldStorageRoom.addItem(oldEngine);
        deck.addItem(metalPlates);
        deck.addItem(scraps);
        deck.addItem(xing);
        newStorageRoom.addItem(waterPump);
        newStorageRoom.addItem(engineParts);
        kitchen.addItem(josh);
        kitchen.addItem(kitchenGadgets);
        crewsRooms.addItem(gimgor);
        crewsRooms.addItem(susStuff);
        crewsOffice.addItem(edd);
        crewsOffice.addItem(documents);
        engineRoom.addItem(maintenanceEquipments);
        engineRoom.addItem(miguel);
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
        System.out.println("World of Zuul is a new, incredibly amusing adventure game.");
        System.out.println("You are on a big cargo ship in international waters.");
        System.out.println("You wake up in the middle of the night to a loud noise.");
        System.out.println("You rush to the place where the noise came from.");
        System.out.println("The wall of the ship has taken damage and it has started to take in water!");
        System.out.println("The nearest piece of land is hours away and you have to fix this mess yourself.");
        System.out.println("Before you and the crew start to work on the damage,");
        System.out.println("you have to use the water pump to take out the water accumulated inside the room.");
        System.out.println("--------------------------------");
        System.out.println("First Task: Find the water pump.");
        System.out.println("--------------------------------");
        System.out.println("Type 'help' if you need help.");
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

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("search")) {
            printItems();
        }
        else if (commandWord.equals("collect")) {
            collectItem(command);
        }
        else if (commandWord.equals("leave")) {
            leaveItem(command);
        }
        else if (commandWord.equals("inventory")) {
            printInventory();
        }
        else if (commandWord.equals("talk")) {
            checkIfHasKey();
        }
        else if (commandWord.equals("pump")) {
            turnOnWaterPump();
        }
        else if (commandWord.equals("back")) {
            goBack();
        }
        else if (commandWord.equals("repair")) {
            wantToQuit = repairRoom();
        }
        
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some uncomplete, cryptic message about the situation and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("The ship is taking in water");
        System.out.println("You have to repair it before it sinks");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    /**
     * Print the items which are present in the room the player is currently in.
     */
    private void printItems()
    {
        System.out.println("You have searched the room.");
        System.out.println();
        System.out.println("The items you have found in the room are:");
        System.out.println(currentRoom.getItemString());
        
    }
    
    /**
     * Print the items in the players inventory.
     */
    private void printInventory()
    {
        System.out.println(inventory.getItemString());
    }
    
    /**
     * Turns on the water-pump if the required conditions are satisfied.
     */
    private void turnOnWaterPump()
    {
        if (currentRoom.getRoomName().equals("damaged room") && inventory.contains("water-pump")) {
            Item item = inventory.getItem("water-pump");
            inventory.removeItem(item);
            currentRoom.addItem(item);
            item.changeCollectable(false);
            System.out.println("Water pump has been turned on.");
            System.out.println("Now find the metal plates and the welding machine to fix the damage. Use the `repair` command once you have both of the items are in the damaged room.");
        }
        else if (!currentRoom.getRoomName().equals("damaged room") && inventory.contains("water-pump")) {
            System.out.println("You should turn on the water-pump in the damaged room.");
        }
        else if (currentRoom.getRoomName().equals("damaged room") && !inventory.contains("water-pump")) {
            System.out.println("You do not have the water-pump in your inventory.");
        }
        else {
            System.out.println("You should have the water-pump with you and you have to be in the damaged room to use it.");
        }
    }
    
    /**
     * Checks if the people in the room have the key in their possession, if they do, the key is transferred to the players inventory.
     * If not, a message is printed informing the player.
     */
    private void checkIfHasKey()
    {
        boolean keyHasBeenFound = false;
        for (Item person : currentRoom.getItemSet()) {
            if (person.getIsPerson()) {
                if(person.hasItem() && person.getPossession().getItemName().equals("key")) {
                    keyHasBeenFound = true;
                    if (person.getPossession().getItemWeight() <= inventory.getAvailableSpace()) {
                        inventory.addItem(person.getPossession());
                        person.removePossession();
                        System.out.println(person.getItemName() + " has the key. Now it is in your inventory. ");
                        System.out.println(inventory.getAvailableSpace() + " units of space is left in your inventory.");
                    }
                    else {
                        System.out.println("You have found the key. But you do not have enough inventory space. Open space before collecting the key.");
                    }
                }
            }
        }
        
        if (!keyHasBeenFound && currentRoom.isEmpty()) {
            System.out.println("There is no one in the room.");
        }
        
        if (!keyHasBeenFound && !currentRoom.isEmpty()) {
            System.out.println("No one has the key.");
        }
    }
    
    /**
     * The damaged room is repaired if the conditions are satisifed, and after this the game ends.
     */
    private boolean repairRoom()
    {
        boolean wantToQuit;
        if (currentRoom.getRoomName() == "damaged room" && (inventory.contains("metal-plates") || currentRoom.contains("metal-plates")) && (inventory.contains("welding-machine") || currentRoom.contains("welding-machine"))) {
            System.out.println("You have partially repaired the wall.");
            System.out.println("While you were working on fixing the room, the crew contacted authorities and you are headed towards the nearest port.");
            System.out.println("The ship still takes in water but now you have enough time until you make it to the port.");
            System.out.println("It was a long night you managed to save your and others lives.");
            System.out.println("Congratulations!!!");
            wantToQuit = true;
        } else if (!(currentRoom.getRoomName() == "damaged room") && inventory.contains("metal-plates") && inventory.contains("welding-machine") && currentRoom.getIsPumpWorking()) {
            System.out.println("You have to be in the damaged room to repair.");
            wantToQuit = false;
        } else if (currentRoom.getRoomName() == "damaged room" && inventory.contains("metal-plates") && inventory.contains("welding-machine") && !currentRoom.getIsPumpWorking()) {
            System.out.println("You should first turn on the water-pump to repair the wall.");
            wantToQuit = false;
        } else {
            System.out.println("You should both have metal plates and welding machine to repair the wall.");
            wantToQuit = false;
        }
        return wantToQuit;
    }
    
    /**
     * Puts the specified item in the players inventory and removes it from the room.
     */
    private void collectItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Collect what?");
            return;
        }
        
        String itemNameToBeCollected = command.getSecondWord();
        
        Item itemToBeCollected = currentRoom.getItem(itemNameToBeCollected);
        if (itemToBeCollected != null && itemToBeCollected.getCollectable() == true && itemToBeCollected.getItemWeight() <= inventory.getAvailableSpace()) {
            inventory.addItem(itemToBeCollected);
            currentRoom.removeItem(itemToBeCollected);
            System.out.println("You have collected the " + itemToBeCollected.getItemName());
            System.out.println("Space left in your inventory: " + inventory.getAvailableSpace() + " units");
        }
        else if (itemToBeCollected != null && itemToBeCollected.getCollectable() == true && itemToBeCollected.getItemWeight()> inventory.getAvailableSpace()) {
            System.out.println("You do not have enough space in your inventory. Open space to collect " + itemToBeCollected.getItemName());
            System.out.println("This item has a weight of " + itemToBeCollected.getItemWeight() + " units. You have only " + inventory.getAvailableSpace() + " units left.");
        }
        else if (itemToBeCollected != null && itemToBeCollected.getIsPerson() == true) {
            System.out.println("You cannot collect people.");
        }
        else if (itemToBeCollected != null && itemToBeCollected.getCollectable() == false && itemToBeCollected.getIsPerson() == false) {
            System.out.println("You cannot collect " + itemToBeCollected.getItemName() + ".");
        }
        else {
            System.out.println("That item is not in the room");
        }
    }
    
    /**
     * Puts the specified item in the room and removes it from the players inventory.
     */
    private void leaveItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Leave what?");
            return;
        }
        
        String itemNameToBeLeft = command.getSecondWord();
        
        Item itemToBeLeft = inventory.getItem(itemNameToBeLeft);
        if (itemToBeLeft != null) {
            inventory.removeItem(itemToBeLeft);
            currentRoom.addItem(itemToBeLeft);
            System.out.println("You have left the " + itemToBeLeft.getItemName());
            System.out.println("Space left in your inventory: " + inventory.getAvailableSpace() + " units");
        }
        else {
            System.out.println("There is no such item in your inventory");
        }
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new room, otherwise print an error message.
     * If the room is accessible, the player can go in, if not an informative message is printed.
     * If the player enters the transporter room, they are teleported to a random room.
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
        else if (nextRoom != null && nextRoom.getIsAccessible() == true && !(nextRoom.getRoomName().equals("transporter room"))) {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            roomHistory.add(currentRoom);
            movePeople();
            if (!currentRoom.isEmpty()) {
                System.out.println("People in the room: " + currentRoom.getPersonString());
            }
        }
        else if (nextRoom != null && nextRoom.getIsAccessible() == true && nextRoom.getRoomName().equals("transporter room")) {
            ArrayList<Room> roomArray = new ArrayList<>();
            for (Room room : roomSet) {
                if (!room.getRoomName().equals("old storage room")) {
                    roomArray.add(room);
                }
            }
            Random rand = new Random();
            int randomRoomIndex = rand.nextInt(roomArray.size());
            Room randomRoom = roomArray.get(randomRoomIndex);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
            movePeople();
            currentRoom = randomRoom;
            System.out.println(currentRoom.getLongDescription());
            roomHistory.add(currentRoom);
            if (!currentRoom.isEmpty()) {
                System.out.println("People in the room: " + currentRoom.getPersonString());
            }
        }
        else if (nextRoom != null && nextRoom.getIsAccessible() == false) {
            if (inventory.contains("key")) {
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());
                movePeople();
                currentRoom.changeAccessibility(true);
                roomHistory.add(currentRoom);
                if (!currentRoom.isEmpty()) {
                System.out.println("People in the room: " + currentRoom.getPersonString());
                }
            }
            else {
                System.out.println("This room is locked. You need the key to enter. You can use the `talk` command to check if crew members have the key.");
            }
        }
    }
    
    /**
     * Gets the player back to the room they were previously in. You can invoke this method until the first room has been reached.
     */
    private void goBack()
    {
        if (roomHistory.size() > 1) {
            int previousRoomIndex = roomHistory.size() - 2;
            currentRoom = roomHistory.get(previousRoomIndex);
            roomHistory.remove(roomHistory.size() - 1);
            System.out.println(currentRoom.getLongDescription());
        }
        else {
            System.out.println("There is no previous room that you can go to.");
        }
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
    
    /**
     * Moves the people in the rooms randomly to the adjacent rooms. The people cannot enter the transporter room and the locked rooms.
     */
    private void movePeople()
    {
        for (Room room : roomSet) {
            HashSet<Item> itemSet = room.getItemSet();
            Iterator<Item> it = itemSet.iterator();
            while (it.hasNext()) {
                Item item = it.next();
                if(item.getIsPerson()) {
                    it.remove();
                    Room randomRoom = room.returnRandomExit();
                    randomRoom.addItem(item);
                }
            }
        }
    }
}
