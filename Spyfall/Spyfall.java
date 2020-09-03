import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Maintains information about a game of spyfall
 * It uses a map to keep track of what role each player has
 * It uses another map to keep track of which roles exist for each possible location
 */

public class Spyfall {

	

	//private final static String[] locationList = {"Ski Lodge","Bank","Beach"};
	private final Role spy = new Role();
	private Map<String, String[]> locationMap;
	private Map<Player,Role> playerMap;
	
	
	
	/**
	 * initialize both maps, and populate the locationMap with the corresponding String array of roles.
	 * mapping locations to the list of roles they have. 
	 */
	public Spyfall() {
		 playerMap = new HashMap<Player, Role>();
		 locationMap = generateLocationMap();
	}
	
	private static Map<String, String[]> generateLocationMap() {
		 String[] skiLodge = {"Ski Patrol", "Lift Technician", "Tourist", "Ski Instructor", "Owner"};
		 String[] bank = {"Teller", "Robber", "Customer", "Custodian", "Owner"};
		 String[] beach = {"Surfer", "Lifeguard", "Tourist", "Fisher", "Park Ranger"};
		 HashMap<String, String[]> location = new HashMap<String, String[]>();
		 location.put("Ski Lodge", skiLodge);
		 location.put("Bank", bank);
		 location.put("Beach", beach);
		 //TODO create a hashmap of string to string array
		 //it should map each location to the array of roles for that location
		 //return the map after storing the associations
		 
		 return location;
	}
	
	
	
	/**
	 * clears the playerMap to be ready to start a new game.
	 */
	public void resetGame() {
		 playerMap = new HashMap<Player, Role>();
	}
	
	/**
	 * checks to see if the given location is in the locationMap.
	 * @param location
	 * @return whether the location is in the locationMap
	 */
	public boolean containsLocation(String location) {
		if(location == null)
		{
			return false;
		}
		if(locationMap.containsKey(location))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * checks to see if the given player is in the playerMap.
	 * @param player
	 * @return whether the player is in the playerMap
	 */
	public boolean containsPlayer(Player player) {
		if(player == null) 
		{
			return false;
		}
		if(playerMap.containsKey(player)) 
		{
			return true;
		}
		return false;
	}
	
	/**
	 * let a player check what role they received
	 * @param name
	 * @return the role assigned to that player
	 */
	public Role getRole(Player player) {
		if(containsPlayer(player))
		{
			return playerMap.get(player);
		}
		//TODO return the role for that player, or null if that player has no role
		return null;
	}
	
	
	/**
	 * starts a new game of 'spyfall' with the players inside the players array. To start a game first
	 * assign a spy, and then assign normal roles.
	 * @param players
	 * @throws IllegalArgumentException if players is null or empty.
	 */
	public void startGame(ArrayList<Player> players) {
		if(players == null) {
			throw new IllegalArgumentException("players cannot be null");
		}
		if(players.isEmpty()) {
			throw new IllegalArgumentException("players cannot be empty");
		}
		assignSpy(players);
		
		assignNormalRoles(players);
	}
	
	
	/**
	 * randomly selects one of the players to become the spy. should use the PlayerMap to map that Player
	 * to the role of spy. This method should be called directly before assignNormalRoles is called.
	 * @param players
	 */
	public void assignSpy(ArrayList<Player> players) {
		int spyIndex = (int) (Math.random() * (players.size() -1)); // choose a random index
		playerMap.put(players.get(spyIndex), spy); // make the random index a spy inthe map
		
		//TODO choose a player randomly
		//assign that player the spy role in the player map
	}
	
	

	
	/**
	 * assigns roles to the rest of the players. this method should be called directly after assignSpy is called.
	 * That also means before this method is called, there is exactly one player (the spy) inside the playerMap.
	 * use a random number to get the name of a location, then use the locationMap to get the list of roles.
	 * use this list to give the remaining players each a different role.
	 * @param players
	 */
	public void assignNormalRoles(ArrayList<Player> players){
		int location = (int) (Math.random() * (locationMap.size()) + 1); // choose a number between 1-3
		String[] avaliableRoles = null; // holds all the roles in the location chosen
		String locationWorkingIn = null; // holds location chosen
		if(location >= 2) { // either 2nd or 3nd location chosed
			if (location == 2) // if its second
			{
				avaliableRoles = locationMap.get("Bank");
				locationWorkingIn = "Bank";
			}else { // if its third
				avaliableRoles =locationMap.get("Beach");
				locationWorkingIn = "Beach";
			}
		}else { // this means the first one is the one chosen
			avaliableRoles = locationMap.get("Ski Lodge");
			locationWorkingIn = "Ski Lodge";
		}
		int role = 0;
		for(Player i: players) // for all players
		{
			
			if(playerMap.containsKey(i)) // if the player is already in there
			{
				if(playerMap.get(i).equals(spy)) // if the player is the spy we put in earlier
				{
					continue;
				}else {
					i = new Player(i.getName() + " extra"); // if its not the spy, add a lil label to distingige between the two people with the same name
				}
			}else {
				playerMap.put(i, new Role(locationWorkingIn, avaliableRoles[role])); // if the player is not already in the map
				role++; //puts the player in the map with the location and avialiable roles. the "role" var makes sure they are given out in order
				if (role == avaliableRoles.length) // if there are no more roles but more players, go back to the start of the list of roles
				{
					role = 0;
				}
			}
		}
		
		
		//TODO randomly choose one of the locations from locationList
		//for the chosen location, get the corresponding array of roles from the location map
		//give each player a role from that array (in the player map)
		//UNLESS that player is already the spy
		//if there are as many non-spy players as roles (or fewer players) this should give each player a different role
		//if there are more, you can repeat roles
	}
	
	
	
	
	
	
	
	/**
	 * checks if the player was the spy, and if they were, all the players who were not the spy win.
	 * if the guessed player was not the spy, the spy wins.
	 * (remember to addWin() to all the wining players)
	 * Then reset the game.
	 * @param player the player guessed
	 * @throws IllegalArgumentException if the player given was not in the playerMap.
	 * @return A String saying who won.
	 */
	public String checkIfSpy(Player player) {
		if(!(playerMap.containsKey(player)))
		{
			throw new IllegalArgumentException();  // throw error if player not in map
		}
		if(playerMap.get(player).equals(spy)) // if player guessed is the spy
		{
			playerMap.forEach((k,v)->{ // add win for each non spy
				if(!(v.equals(spy)))
				{
					k.addWin();
				}
			});

			resetGame();
			return "The Non-Spies Win!"; 
		}else {
			playerMap.forEach((k,v)->{ // if player guessed is not spy
				if(v.equals(spy)) // give win to the spy
				{
					k.addWin();
					
				}
			});
			resetGame(); // resets game in both cases before returning
			return "The Spy Wins!";
		}
		//TODO make sure the player map contains this player
		//check if the player has the spy role
		//if so, add a win for each non-spy in the map (use keySet)
		//	and return "The Non-Spies Win!"
		//if not, add a win for the spy (again use keySet - this operation is not efficient)
		//  return "The Spy Wins!"
		//either way, reset the game before returning

	}
	
	
	
	/**
	 * prints out all the players and their roles. 
	 */
	public String toString() {
		if(playerMap.isEmpty())
			return "The game has not started yet";
		else {
			String output = "";
			for (Player person : playerMap.keySet()) {
				output = output + person.toString() + " " + playerMap.get(person).toString()+ "\n";
		    }
			return output;
		}
	}
	
	
}


