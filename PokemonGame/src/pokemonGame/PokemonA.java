package pokemonGame;
/*Mathuran Sivakaran
 * December 19, 2020
 * Creating a Pokemon Game
 */
import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
class MoveA{
	String name;//move name
	int power;//move power
	String type;//move type
	boolean status;//does it inflict a status(change in stats)
	boolean self;//does the change affect user's stats or opposing stats
	String statAffected;//which stat is affected by stat change
	double statChange;//how much is the stat changed by
}

class Item{
	public Item(int quantity) {//constructor
		this.quantity = quantity;
	}
	int quantity;//how many of an item there is
}

class Trainer{
	//Constructors
	public Trainer(String name, PokemonA first, PokemonA second, PokemonA third, int x, int y) {//trainers with three pokemon
		this.name = name;
		this.firstMon = first;
		this.secondMon = second;
		this.thirdMon = third;
		this.team = new PokemonA [] {first, second, third};
		this.x = x;
		this.y = y;
	}
	public Trainer(String name, PokemonA first, PokemonA second, int x, int y){//trainers with two pokemon
		this.name = name;
		this.firstMon = first;
		this.secondMon = second;
		this.team = new PokemonA [] {first, second};
		this.x = x;
		this.y = y;
	}
	public Trainer(String name, PokemonA first, int x, int y) {//Trainers with one pokemon
		this.name = name;
		this.firstMon = first;
		this.team = new PokemonA [] {first};
		this.x = x;
		this.y = y;
	}
	
	String name;//trainer's name
	PokemonA firstMon;//trainer's first pokemon
	PokemonA secondMon;//trainer's second pokemon
	PokemonA thirdMon;//trainer's third pokemon
	PokemonA [] team;//their team of pokemon
	boolean battled;//if they have been battled and beat by the player yet
	//trainer coordinates
	int x;//trainer's x coordinate on map
	int y;//trainer's y coordinate on map
}

public class PokemonA {
	//copy constructor
	public PokemonA(PokemonA pokemon, boolean resetHP) {
		if(pokemon != null) {
			this.name = pokemon.name;
			this.type = pokemon.type;
			this.typeWeaknesses = pokemon.typeWeaknesses;
			if(resetHP) {//sometimes I would not like to make a copy of the hp(e.g., after a battle, pokemon keeps damage taken
				this.hp = pokemon.hp;
			}
			this.attack = pokemon.attack;
			this.defense = pokemon.defense;
			this.speed = pokemon.speed;
			this.moveOne = pokemon.moveOne;
			this.moveTwo = pokemon.moveTwo;
			this.level = pokemon.level;
			this.exp = pokemon.exp;
		}
	}
	public PokemonA() {
		
	}
	////ATTRIBUTES
	String name;
	String type;
	String [] typeWeaknesses;
	//Stats
	int hp;
	double attack;
	double defense;
	double speed;
	//Moves
	MoveA moveOne = new MoveA();
	MoveA moveTwo = new MoveA();
	//Level
	int level  = 5;
	int exp = 0;
	
	/*Increasing a pokemon's stats when it levels up, and adding exp after a battle
	 * pre: One PokemonA parameter: PokemonA pokemon, int expGained
	 * post: Returns PokemonA pokemonBattleStat
	 */
	public static PokemonA levelUp(PokemonA pokemon, int expGained) {
		pokemon.exp += expGained;
		PokemonA pokemonCopy = null;
		System.out.println(pokemon.name +" gained "+ expGained+" EXP!");
		if(pokemon.exp >= pokemon.level*4) {//EXP required to level up is determined by the current level times 5
			System.out.println(pokemon.name+" levelled up! It's now a little bit stronger");
			pokemon.level ++;
			pokemon.hp += 2;
			pokemon.attack += 1;
			pokemon.defense += 1;
			pokemon.speed += 1;
			pokemon.exp = 0 ;//resetting exp gain
			pokemonCopy = new PokemonA(pokemon,true);//making a copy of the pokemon once it levels up;
		}
		return pokemonCopy;//null means pokemon didn't level up//will be assigned to battleStat copy if levelled up
	}
	
	public static void main(String[] args) throws IOException{
		Scanner input = new Scanner(System.in);
		//map files
		File homeFile = new File("C:\\Mathu\\01_School\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\maps\\00_homeMap.txt");
		File routeOne = new File("C:\\Mathu\\01_School\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\maps\\01_route1.txt");
		File ceylonCity = new File("C:\\Mathu\\01_School\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\maps\\02_ceylonCity.txt");
		//wild Pokemon/Trainer pokemon files
		File caterpie = new File("C:\\Mathu\\01_School\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\pokemonFiles\\04_caterpie.txt");
		File ratatta = new File("C:\\Mathu\\01_School\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\pokemonFiles\\05_ratatta.txt");
		File pidgey =  new File("C:\\Mathu\\01_School\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\pokemonFiles\\06_pidgey.txt");
		File bulbasaurA = new File("C:\\Mathu\\01_School\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\pokemonFiles\\01a_bulbasaur.txt");
		File charmanderA = new File("C:\\Mathu\\01_School\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\pokemonFiles\\02a_charmander.txt");
		File squirtleA = new File("C:\\Mathu\\01_School\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\pokemonFiles\\03a_squirtle.txt");
		
		
		//first map: Home Town
		String [][] map = readMap(homeFile);
		
		//placing player(1)
		int playerX = 2; 
		int playerY = 0;
		map[playerX][playerY] = "1";
		print2DArray(map,map.length,map[0].length);
		//Welcome Dialogue and Go To Professor
		System.out.println("Welcome to Pokemon! A world filled with mythical creatures and humans, trainers, "
				+ "working along side them.");
		delay(1500);
		System.out.println("Today's the day that you(1) get your first Pokemon from the professor!"
				+ " Now, make your way over to the professor's house(O) to get your starter Pokemon!");
		System.out.println("_____________________________________________________");
				
		//walking to professor's house
		while(playerX != 4 || playerY != 6) {//loops until reaching the house
			System.out.println("Enter where to go:			Press 'X' for rules and tips");
			char direction = input.next().charAt(0);
			rules(direction);
			int [] playerCoor = move(map, homeFile, map.length,map[0].length,direction,playerX, playerY);//getting new player coordinates
			playerX = playerCoor[0];//assigning new playerX value
			playerY = playerCoor[1];//assigning new playerY value
		}
		
		//Professor's house dialogue and picking a starter
		System.out.println("As you enter the professor's house, you hear a funny and cheerful voice greeting you.");
		delay(2000);
		System.out.println("\"Oh Hello, young trainer! I see you're here for a new Pokemon. Glad to meet you, my name is"
				+ " Professor Mathuran! Could you please tell me your name?\"");
		input.nextLine();
		String name = input.nextLine();
		System.out.println("_____________________________________________________");
		System.out.println("\"That's great! Nice to meet you "+name+". But that's enough of our introductions.");
		delay(3000);
		System.out.println("Let's get to the intros you've been waiting for: the Starter Pokemon!\"");
		delay(2500);
		System.out.println("Prof. Mathuran leads you to his lab, where he gives you the choice of three Pokemon.");
		delay(3000);
		System.out.println("\"Here are the three starter Pokemon: "//choosing their first pokemon
				+ "\nThe grass type, Bulbasaur,(1)"
				+ "\nThe fire type, Charmander,(2) "
				+ "\nThe water type, Squirtle. (3)\"");
		System.out.println("Enter a number corresponding to the Pokemon you want for your journey: ");
		int choice = input.nextInt();
		while(choice != 1 && choice != 2 && choice != 3) {
			System.out.println("That isn't a valid answer, pick 1,2, or 3!");
			choice = input.nextInt();
		}
		File starterFile = null;//reading the file of only the starter they chose
		switch(choice) {
			case 1:
				starterFile = new File("C:\\Mathu\\01_School\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\pokemonFiles\\01_bulbasaur.txt");
				break;
			case 2:
				starterFile = new File("C:\\Mathu\\01_School\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\pokemonFiles\\02_charmander.txt");
				break;
			case 3:
				starterFile = new File("C:\\Mathu\\01_School\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\pokemonFiles\\03_squirtle.txt");
				break;
		}
		PokemonA starter = readPokemon(starterFile);
		PokemonA starterStats = readPokemon(starterFile);//creating a copy of pokemon to use in battle
		delay(750);
		System.out.println("\"Hmm, "+starter.name+". Good choice! And now, you have your first Pokemon, "
				+ "WOW!\", Prof. Mathuran exclaims as he hands over your new friend, "+starter.name+".");
		delay(2500);
		//String [][] team = {{starter[0][0],starter[1][0]}};//2D array. Each list contains Pokemon name and max HP({Name, maxHP}
		PokemonA [] team = {starter,null,null,null};//List of 2D arrays: { {{Name}, {HP, Attack, Defense, Speed}, {MoveOne, MoveTwo}, {Level}}, .}
		PokemonA [] teamBattleStat = {new PokemonA(starter,true),null,null,null};//{starter};//The HP that will be used in battle calculations{HP}
		

		System.out.println("The professor also hands you a travel bag.");
		delay(1000);
		System.out.println("\"Here is a Travel Bag. You can store items you find on your journey, like PokeBalls, and Potions."
				+ "\nPress B to access it");
		delay(1500);
		System.out.println("You can use Pokeballs for catching wild Pokemon you can find in tall grass(|). Add some wild Pokemon to your team!");
		delay(2000);
		System.out.println("You can only have up to 4 Pokemon on your team");
		System.out.println("Potions are used to heal your Pokemon on your journey. So be sure to stock up on those!\"");
		delay(2000);
		System.out.println("Prof. Mathuran hands you 10 PokeBalls and 3 Potions.");
		Item balls = new Item(10);
		Item potions = new Item(3);
		delay(1000);
		System.out.println("\"Since your just starting out, I've given you some PokeBalls and Potions to help you out.");
		delay(1500);
		System.out.println("So, what are you waiting for? Go out there and explore the world of Pokemon!\"");
		System.out.println("_____________________________________________________");
		delay(2500);
		
		///////////moving around route one///////////////
		//initializations//
		System.out.println("ROUTE 1");
		map = readMap(routeOne);
		playerX = 0;
		playerY = 0;
		map[playerX][playerY] = "1";
		print2DArray(map,map.length, map[0].length);
		//initializing item to pick up("?") for route one
		String itemName = "potion";
		int itemAmount = 1;
		boolean pickedUp = false;//if the item was picked up
		//trainers to battle//
		Trainer joey = new Trainer("Joey", new PokemonA(readPokemon(ratatta, 5), true), new PokemonA(readPokemon(caterpie,5),true),7,5);
		makeTrainerPokemon(joey);//initializes moves of the trainer's pokeon
		Trainer wendy = new Trainer("Wendy", new PokemonA(readPokemon(pidgey,6),true),0,18);
		makeTrainerPokemon(wendy);
		boolean finishedMap = false;//making sure the user has four pokemon on their team
		
		while((playerX != 3 || playerY != 19) || !finishedMap) {
			boolean ifLost = false;
			int [] wildLevelRange = {3,5};
			System.out.println("Enter where to go:			Press 'B' for Bag");
			System.out.println("						Press 'X' for rules and tips");
			char direction = input.next().charAt(0);//char direction = input.nextLine().charAt(0);
			if(Character.compare(Character.toUpperCase(direction), 'B')  == 0) {//if player wants to access bag instead of moving
				bag(balls,potions,false, team, teamBattleStat, null, null);//return value isn't needed as here, the bag is accessed outside a battle
			}
			rules(direction);
			int [] playerCoor = move(map, routeOne, map.length,map[0].length,direction,playerX, playerY, 6, 19, pickedUp);//add itemX, itemY, and itemState
			playerX = playerCoor[0];
			playerY = playerCoor[1];
			PokemonA wildPokemon =  wildSpawn(playerX, playerY, routeOne, caterpie, ratatta, pidgey, wildLevelRange);

            if(playerX == 6 && playerY == 19 && !pickedUp) {//if player is on the item tile(?)
				pickUp(itemName, itemAmount, balls, potions);
				pickedUp = true;//player has picked up the item
			}
			if(playerX == 3 && playerY == 19) {//if player reached the end
				boolean fullTeam = true;
				for(int i = 0; i < 4; i++) {
					if(team[i] == null) {//if there is an empty slot on their team
						System.out.println("You don't have enough Pokemon to go to the next city. Make sure you have a full team");
						System.out.println("The next city's Pokemon Battle Marathon will be tough, so try to have a full team!");//won't let them advance
						delay(500);
						System.out.println("It would also be a good idea to level up your Pokemon. You never know how tough it will be!");
						finishedMap = false;
						fullTeam = false;
						break;
					}
				}
				if(fullTeam) {//this means they have a full team
					finishedMap = true;
					break;
				}
				else if(!fullTeam) {
					finishedMap = false;
					continue;
				}
			}
            
			if(wildPokemon != null) {
				System.out.println("You encountered a wild "+wildPokemon.name+"!");
				System.out.println("_____________________________________________________");
				ifLost = wildBattle(team, teamBattleStat, wildPokemon, new PokemonA(wildPokemon,true), balls, potions);//new Pokemon() creates a copy of wild pokemon(enemyBattleStat)
				delay(1000);
			}
			if(wildPokemon == null) {
				//looking for and battling Trainers
				if(meetTrainer(playerX, playerY, joey.x, joey.y, joey) == joey) {//if they run into Joey
					ifLost = trainerBattle(team, teamBattleStat, joey.team, Arrays.copyOf(joey.team, joey.team.length), balls, potions, joey);
					if(!ifLost) {//to check if player is done battling Joey
						joey.battled = true;
					}
				}
				else if(meetTrainer(playerX, playerY, wendy.x, wendy.y, wendy) == wendy) {//if they run into wendy
					ifLost = trainerBattle(team, teamBattleStat, wendy.team, Arrays.copyOf(wendy.team, wendy.team.length),balls, potions, wendy);
					if(!ifLost) {//if player is done with Wendy
						wendy.battled = true;
					}
				}
			}
			
			if(ifLost) {
				for(int i = 0; i < 4; i++) {
					if(teamBattleStat[i] != null) {
						teamBattleStat[i] = new PokemonA(team[i], true);//resetting pokemon hp
					}
				}
				String [][] tempMap = readMap(routeOne);
				if(pickedUp) {
					tempMap[6][19] = "X";
				}
				map[playerX][playerY] = tempMap[playerX][playerY];
				playerX = 0;//making the player start the route over again
				playerY = 0;
				map[playerX][playerY] = "1";
				System.out.println("Start over");
				delay(1500);
				print2DArray(map, map.length, map[0].length);
			}
			
		}//end of route one
		
		//more dialogue and story
		System.out.println("You made your way through Route 01 and are now in Ceylon City! Your pokemon were all fully healed");
		delay(2000);
		for(int i = 0; i < 4; i++) {//fully healing each team member(also add limit to exit route one at least 4 pokemon)
			if(team[i] == null) {//in case there is a null pokemon 
				continue;
			}
			else {
				teamBattleStat[i].hp = team[i].hp;//setting hp to max hp
			}
		}
		System.out.println("Here in Ceylon city, you'll be battling a lot of different trainers on Ceylon's Battle Marathon!");
		delay(2000);
		System.out.println("If you can defeat all 6 trainers in one go and make it to the finish(O), you'll become the Ceylon City Champion!");
		delay(2000);
		System.out.println("So, are you ready to take on the challenge to become a Pokemon Champion? Let's GO!!!");
		//ceylon city map
		map = readMap(ceylonCity);
		playerX = 2;
		playerY = 0;
		System.out.println("CEYLON CITY: Ceylon's Battle Marathon");
		map[playerX][playerY] = "1";
		print2DArray(map, map.length, map[0].length);
		//initializing battle marathon trainers
		Trainer silver = new Trainer("Silver", new PokemonA(readPokemon(charmanderA, 6),true), 0, 4);
		makeTrainerPokemon(silver);
		Trainer wally = new Trainer("Wally", new PokemonA(readPokemon(ratatta,6), true), new PokemonA(readPokemon(bulbasaurA, 7), true), 4, 8);
		makeTrainerPokemon(wally);
		Trainer barry = new Trainer("Barry", new PokemonA(readPokemon(pidgey, 6), true), new PokemonA(readPokemon(squirtleA, 7), true), 0, 12);
		makeTrainerPokemon(barry);
		Trainer hilda = new Trainer("Hilda", new PokemonA(readPokemon(caterpie, 7), true), new PokemonA(readPokemon(charmanderA, 8), true), 4, 16);
		makeTrainerPokemon(hilda);
		Trainer serena = new Trainer("Serena", new PokemonA(readPokemon(ratatta, 6), true), new PokemonA(readPokemon(caterpie, 7),true), new PokemonA(readPokemon(charmanderA,8),true), 0, 20); 
		makeTrainerPokemon(serena);
		Trainer gary = new Trainer("Gary", new PokemonA(readPokemon(ratatta, 7), true), new PokemonA(readPokemon(pidgey, 8), true), new PokemonA(readPokemon(squirtleA, 8), true), 4, 25);
		makeTrainerPokemon(gary);
		finishedMap = false;
		
		while(!finishedMap) {//moving around and battling trainers
			boolean lostMarathon = false;//if they lose to any trainer in the battle marathon
			Trainer[] marathonTrainers = new Trainer[] {silver, wally, barry, hilda, serena, gary};
			System.out.println("Enter where to go: \t\t\tPress 'B' for Bag");
			System.out.println("					\t\tPress 'X' for rules and tips");
			char direction = input.next().charAt(0);
			if(Character.compare(Character.toUpperCase(direction), 'B')  == 0) {//if player wants to access bag instead of moving
				bag(balls,potions,false, team, teamBattleStat, null, null);//return value isn't needed as here, the bag is accessed outside a battle
			}
			rules(direction);
			int [] playerCoor = move(map, ceylonCity, map.length,map[0].length,direction,playerX, playerY);
			playerX = playerCoor[0];
			playerY = playerCoor[1];
			//lostMarathon = marathonBattle(silver, team, teamBattleStat, balls, potions, playerY);//only the y coordinate is needed as the trainers are detect using their y coordinate
			for(Trainer trainer: marathonTrainers) {//checks for trainers after each movement
				lostMarathon = marathonBattle(trainer, team, teamBattleStat, balls, potions, playerY);
				if(lostMarathon) {//if the player lost to a trainer
					for(Trainer i: marathonTrainers) {//looping through an array
						i.battled = false;//resetting trainer's battled state
					}
					playerX = 2;//resetting player's coordinates to the beginning
					playerY = 0;
					for(int i = 0; i < 4; i++) {
						teamBattleStat[i].hp = team[i].hp;//resetting each pokemon's HP
					}
					break;//breaks the for loop for battling marathon trainer
				}
			}
			if(playerX == 2 && playerY == 28) {//player has reached the end and beat the battle marathon
				finishedMap = true;//won't loop through this map loop again
				break;//breaks the map loop since the player beat the marathon
			}
		}
		
		System.out.println("CONGRATULATIONS!!!! You just beat Ceylon City's Pokemon Battle Marathon! You've been crowned as Ceylon City's Champion");
		delay(2000);
		System.out.println("As you're being crowned the Ceylon City Champion, you see Professor Mathuran running toward you with a grin on his face.");
		delay(1500);
		System.out.println("\'WOW "+name+"!! You just became a Pokemon Champion? That's amazing!");
		delay(1500);
		System.out.println("I'm so proud of you, you learned so much about Pokemon! And once again,");
		delay(2000);
		System.out.println("CONGRATULATIONS on becoming a Pokemon Champion!");
		delay(2000);
		
		System.out.println("Thank you for playing Pokemon! Hope you enjoyed!!");//game finished. Player has beat the game
	}
	//METHODS

	/*Delaying commands and blocks of code
	 * pre: One int parameter, int mS
	 * post: N/A
	 */
	public static void delay(int mS) {//*Note: This method is more of a quality of life improvement 
		try{//than an actual method, as i will be using this sort of delay multiple times in the code
			TimeUnit.MILLISECONDS.sleep(mS);//delay by given time(in milliseconds)
		}
		catch(InterruptedException e){
			System.err.format("IOException: %s%n", e);
		}
	}
	
	/*Printing a 2D array
	 * pre: One 2D String array parameter: String [][] array, Two int parameters: int x, int y
	 * post: N/A 
	 */
	public static void print2DArray(String [][] array, int x, int y) {
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("_____________________________________________________");
		delay(500);
	}
	
	/*Moving the player on a map with no item
	 * pre: One 2D String array parameter: String [][] map, One char parameter: char direction, Four int parameters:int mapX, int mapY, int playerX, int playerY
	 * post: Returns int [] playerCoor
	 */
	public static int[] move(String [][] map, File mapFile, int mapX, int mapY, char direction, int playerX, int playerY) throws IOException {
		String [][] tempMap = readMap(mapFile); //creating a reference map from the map file

		if(Character.compare(direction, 'w') == 0 && 0 <= playerX-1 && !(tempMap[playerX-1][playerY].equalsIgnoreCase("*")) && !(tempMap[playerX-1][playerY].equalsIgnoreCase("Y"))) {//up
			map[playerX][playerY] = tempMap[playerX][playerY];//replacing current tile with original tile(refers to tempMap)
			map[playerX - 1][playerY] = "1";//above tile is replaced with "1"
			playerX--;
		}
		else if(Character.compare(direction, 's') == 0 && mapX > playerX+1 && !(tempMap[playerX+1][playerY].equalsIgnoreCase("*")) && !(tempMap[playerX+1][playerY].equalsIgnoreCase("Y"))) {//down
			map[playerX][playerY] = tempMap[playerX][playerY];
			map[playerX + 1][playerY] = "1";//below tile is replaced with "1"
			playerX++;
		}
		else if(Character.compare(direction, 'a') == 0 && 0 <= playerY-1 && !(tempMap[playerX][playerY-1].equalsIgnoreCase("*")) && !(tempMap[playerX][playerY-1].equalsIgnoreCase("Y"))) {//left
			map[playerX][playerY] = tempMap[playerX][playerY];
			map[playerX][playerY-1] = "1";//left tile is replaced with "1"
			playerY--;
		}
		else if(Character.compare(direction, 'd') == 0 && mapY > playerY+1 && !(tempMap[playerX][playerY+1].equalsIgnoreCase("*")) && !(tempMap[playerX][playerY+1].equalsIgnoreCase("Y"))) {//right
			map[playerX][playerY] = tempMap[playerX][playerY];
			map[playerX][playerY+1] = "1";//right tile is replaced with "1"
			playerY++;
		}
		print2DArray(map, mapX, mapY);//prints the map with the player in the new location
		int [] playerCoor = {playerX, playerY};//gets the modified values of playerX and playerY
		return playerCoor;//will be used to reassign to the playerX and playerY values in main
	}
	
	/*Moving the player on a map with an item
	 * pre: One 2D String array parameter: String [][] map, One char parameter: char direction, Six int parameters:int mapX, int mapY, int playerX, int playerY, int itemX, int itemY, One boolean parameter: boolean itemState
	 * post: Returns int [] playerCoor
	 */
	public static int[] move(String [][] map, File mapFile, int mapX, int mapY, char direction, int playerX, int playerY, int itemX, int itemY, boolean itemState) throws IOException {
		String [][] tempMap = readMap(mapFile); //creating a reference map from the map file

		if(Character.compare(direction, 'w') == 0 && 0 <= playerX-1 && !(tempMap[playerX-1][playerY].equalsIgnoreCase("*")) && !(tempMap[playerX-1][playerY].equalsIgnoreCase("Y"))){//up
			map[playerX][playerY] = tempMap[playerX][playerY];//replacing current tile with original tile(refers to tempMap)
			map[playerX - 1][playerY] = "1";//above tile is replaced with "1"
			playerX--;
		}
		else if(Character.compare(direction, 's') == 0 && mapX > playerX+1 && !(tempMap[playerX+1][playerY].equalsIgnoreCase("*")) && !(tempMap[playerX+1][playerY].equalsIgnoreCase("Y"))) {//down
			map[playerX][playerY] = tempMap[playerX][playerY];
			map[playerX + 1][playerY] = "1";//below tile is replaced with "1"
			playerX++;
		}
		else if(Character.compare(direction, 'a') == 0 && 0 <= playerY-1 && !(tempMap[playerX][playerY-1].equalsIgnoreCase("*")) && !(tempMap[playerX][playerY-1].equalsIgnoreCase("Y"))) {//left
			map[playerX][playerY] = tempMap[playerX][playerY];
			map[playerX][playerY-1] = "1";//left tile is replaced with "1"
			playerY--;
		}
		else if(Character.compare(direction, 'd') == 0 && mapY > playerY+1 && !(tempMap[playerX][playerY+1].equalsIgnoreCase("*")) && !(tempMap[playerX][playerY+1].equalsIgnoreCase("Y"))) {//right
			map[playerX][playerY] = tempMap[playerX][playerY];
			map[playerX][playerY+1] = "1";//right tile is replaced with "1"
			playerY++;
		}
		if(itemState && (itemX != playerX || itemY != playerY)) {
			map[itemX][itemY] = "X";//replacing picked up item tile with "X"
		}
		print2DArray(map, mapX, mapY);//prints the map with the player in the new location
		int [] playerCoor = {playerX, playerY};//gets the modified values of playerX and playerY
		return playerCoor;//will be used to reassign to the playerX and playerY values in main
	}

	
	/*Reading a file to make a map
	 * pre: One File parameter: File mapFile
	 * post: Returns String [][] map
	 */
	public static String [][] readMap(File mapFile) throws IOException {
		Scanner mapReader = new Scanner(mapFile);//Creating Input stream from file
		String [][] map = new String [0][0];//initialize blank map
		while(mapReader.hasNextLine()) {//making the map
			String line = mapReader.nextLine();
			String [] newLine = line.split("");
			map = Arrays.copyOf(map, map.length+1);//Making a copy of map but with +1 length
			map[map.length-1] = newLine;
		}
		mapReader.close();
		return map;
	}
	
	/*Reading from a file to make a Pokemon with its attributes
	 * pre: One File parameter: File pokemonFile
	 * post: Returns PokemonA pokemon
	 */
	public static PokemonA readPokemon(File pokemonFile) throws IOException {
		Scanner pokemonReader = new Scanner(pokemonFile);
		PokemonA pokemon = new PokemonA();
		String name = pokemonReader.nextLine();//"Name"
		String [] stats = pokemonReader.nextLine().split(", ");//{String HP, String Attack, String Defense, String Speed}
		String [] moves = pokemonReader.nextLine().split(", ");//{String MoveOne, String MoveTwo}
		String level = pokemonReader.nextLine();//"Level"
		String type = pokemonReader.nextLine();
		String [] typeWeaknesses = pokemonReader.nextLine().split(", ");
		pokemonReader.close();
		pokemon.name = name;
		pokemon.hp = Integer.parseInt(stats[0]);
		pokemon.attack = Integer.parseInt(stats[1]);
		pokemon.defense = Integer.parseInt(stats[2]);
		pokemon.speed = Integer.parseInt(stats[3]);
		pokemon.moveOne.name = moves[0];
		pokemon.moveTwo.name = moves[1];
		pokemon.moveOne = readMove(pokemon.moveOne.name);
		pokemon.moveTwo = readMove(pokemon.moveTwo.name);
		pokemon.level = Integer.parseInt(level);
		pokemon.type = type;
		pokemon.typeWeaknesses = typeWeaknesses;
		return pokemon;
	}
	
	/*Reading from a file to make a Pokemon with its attributes(for wild pokemon)
	 * pre: One File parameter: File pokemonFile, One int parameter: int level
	 * post: Returns PokemonA pokemon
	 */
	public static PokemonA readPokemon(File pokemonFile, int level) throws IOException {
		Scanner pokemonReader = new Scanner(pokemonFile);//stats in file are at base level, level 5
		PokemonA pokemon = new PokemonA();
		String name = pokemonReader.nextLine();//"Name"
		String [] stats = pokemonReader.nextLine().split(", ");//{String HP, String Attack, String Defense, String Speed}
		String [] moves = pokemonReader.nextLine().split(", ");//{String MoveOne, String MoveTwo}
		String type = pokemonReader.nextLine();
		String [] typeWeaknesses = pokemonReader.nextLine().split(", ");
		//String level = pokemonReader.nextLine();//"Level"
		pokemonReader.close();
		pokemon.name = name;
		pokemon.hp = Integer.parseInt(stats[0]) - (5-level)*2;//stat vairation based on level of wild pokemon
		pokemon.attack = Integer.parseInt(stats[1]) - (5-level)*2;
		pokemon.defense = Integer.parseInt(stats[2]) - (5-level)*2;
		pokemon.speed = Integer.parseInt(stats[3]) - (5-level)*2;
		pokemon.moveOne.name = moves[0];
		pokemon.moveTwo.name = moves[1];
		pokemon.moveOne = readMove(pokemon.moveOne.name);
		pokemon.moveTwo = readMove(pokemon.moveTwo.name);
		pokemon.level = level;
		pokemon.type = type;
		pokemon.typeWeaknesses = typeWeaknesses;
		return pokemon;
	}
	
	/*Reading from a file to a make a Move with it's attributes
	 * pre: One File parameter: File moveFile
	 * post: Return MoveA move
	 */
	public static MoveA readMove(String moveName) throws IOException {
		File database = new File("C:\\Mathu\\01_School\\01_PastYears\\02_2020-2021 school year\\01_Sem\\01_ICS 3U1-52\\06_Culminating\\CulminatingGame\\01_moveDatabase.txt");
		MoveA move = new MoveA();
		move.name = moveName;
		String [] moveLine = null;
		Scanner moveReader = new Scanner(database);
		boolean foundMove = false;
		while(!foundMove && moveReader.hasNextLine()) {
			String line = moveReader.nextLine();
			if(line.contains(moveName)) {
				moveLine = line.split(", ");//move is found in database
				foundMove = true;
			}
		}
		moveReader.close();
		move.power = Integer.parseInt(moveLine[1]);
		move.type = moveLine[2];
		if(moveLine.length > 3) {//if the move is a status move
			move.status = true;
			move.self = Boolean.parseBoolean(moveLine[4]);
			move.statAffected = moveLine[5];
			move.statChange = Double.parseDouble(moveLine[6]);
		}
		else {
			move.status = false;
		}
		return move;		
	}
	
	/*Initializing a trainer with their pokemon and moves
	 * pre: One Trainer parameter: Trainer trainer
	 * post: N/A
	 */
	public static void makeTrainerPokemon(Trainer trainer) throws IOException {
		for(int i = 0; i < trainer.team.length; i++) {
			trainer.team[i].moveOne = readMove(trainer.team[i].moveOne.name);
			trainer.team[i].moveTwo = readMove(trainer.team[i].moveTwo.name);
		}
	}
	
	
	/*Accessing items in a bag(array)
	 * pre: Two Item parameters: Item balls, Item potions, One boolean parameter: boolean wildBattle, Two PokemonA[] parameters: PokemonA[] team, PokemonA[] teamBattleStat, Two PokemonA parameters: PokemonA enemy, PokemonA enemyBattleStat
	 * return: Returns new boolean [] {caught, used}
	 *///change int and pre:
	public static boolean [] bag(Item balls, Item potions, boolean wildBattle, PokemonA [] team, PokemonA [] teamBattleStat, PokemonA enemy, PokemonA enemyBattleStat) {
		Scanner input = new Scanner(System.in);
		//int [] items = {balls, potions};//num of pokeballs, number of potions
		boolean caught = false; //checks if the wild pokemon was caught in a wild battle
		boolean used = false; //checks if the user used an item
		boolean finishedUsing = false;//if the user is done using the bag menu (presses B)
		boolean released = false; //if user caught a species they already have, checks whether they released it or not
		while(!finishedUsing) {
			System.out.println("______________________________________");
			System.out.println("Pick an item: 			Press 'B' to go back");
			System.out.println("Pokeballs X"+balls.quantity+"  	(1)");
			System.out.println("Potions X"+potions.quantity+"  	(2)");
			String choice = input.next();
			if(choice.equalsIgnoreCase("B")) {
				finishedUsing = true;//exits bag method
			}
			System.out.println("______________________________________");
			if(choice.equals("1") && balls.quantity > 0) {
				if(wildBattle) {//Makes sure PokeBalls can only be used when encountering a wild Pokemon
					if(team[3] != null) {//if team is full
						System.out.println("Your team is full, you can't catch anymore pokemon");
						finishedUsing = true;
						break;
					}
					//System.out.println("Catch code here");
					System.out.println("You threw a Pokeball at "+enemyBattleStat.name);
					balls.quantity--;
					used = true;
					//catch rate(lower the hp of the wild pokemon, higher the chance
					double catchChance = Math.pow(0.97, enemyBattleStat.hp)*100;
					int catchGenerator = (int)(Math.random()*100+1);
					if(catchGenerator <= catchChance) {
						System.out.println("You caught "+enemyBattleStat.name+"!! You added "+ enemyBattleStat.name+" to your team!");
						caught = true;//pokemon was caught
						for(int i = 0; i < 4; i ++) {
							if(team[i]== null) {
								break;
							}
							else {
								if(team[i].name.equalsIgnoreCase(enemy.name)) {//caught another same Pokemon
									System.out.println("You already have another Pokemon of this species. Would you like to switch "+team[i].name+
											" with the newly caught "+enemy.name+"?");
									System.out.println("YES (Y)            NO (N)");
									while(true) {
										String replaceChoice = input.next();
										if(replaceChoice.equalsIgnoreCase("Y")) {//want to replace
											System.out.println("You released your "+team[i].name+" and replaced it with the newly caught "+enemy.name);
											team[i] = null;
											released = false;
											break;
										}
										else if(replaceChoice.equalsIgnoreCase("N")) {//don't want to replace
											System.out.println("You released the newly caught "+enemy.name);
											released = true;
											break;
											
										}
										else {//invalid answer
											System.out.println("Invalid Answer.");
											continue;
										}
									}
									break;
								}
							}
						}
						for(int i = 0; i < 4; i++) {
							if(team[i] == null && !released) {
								team[i] = enemy;
								teamBattleStat[i] = new PokemonA(enemy, true);
								break;
							}
						}
					}
					else {
						System.out.println("You didn't catch it");
					}
					finishedUsing = true;
				}
				else {//If the user selects Pokeball outside a wild battle, they will be told they can't use it
					System.out.println("You can't use that now!");
					finishedUsing = true;
				}
			}
			else if(choice.equals("2") && potions.quantity > 0) {
				System.out.println("Which Pokemon would you like to heal?			Press 'B' to close the bag");
				for(int i = 0; i < team.length; i++) {//Printing options of who to heal 
					if(team[i] == null) {
						continue;
					}
					System.out.println(team[i].name+"			"+teamBattleStat[i].hp+"/"+team[i].hp+" ("+(i+1)+")");//i represents a Pokemon on their team	
				}																		//Player presses a number corresponding to the Pokemon they want to heal
				choice  = input.next();
				int monToHeal = -1;
				try {
					monToHeal = Integer.parseInt(choice);
					if(team[monToHeal-1] == null || monToHeal < 0 || monToHeal > 4) {
						System.out.println("That's not a team member");
						//finishedUsing = true;
					}
					else {
						if(team[monToHeal-1].hp == teamBattleStat[monToHeal-1].hp){//checking if the pokemon can be healed(max HP vs current HP
							System.out.println("That Pokemon is already at full HP");
						}
						else {
							teamBattleStat[monToHeal - 1].hp += 10;
							if(teamBattleStat[monToHeal - 1].hp > team[monToHeal - 1].hp) {//checking if healed health is more than max health
								int maxHP = team[monToHeal - 1].hp;
								teamBattleStat[monToHeal - 1].hp = maxHP;
							}
							System.out.println("You healed "+team[monToHeal-1].name+". "+team[monToHeal-1].name+" is now at "+teamBattleStat[monToHeal-1].hp+" HP.");
							potions.quantity --;
							used = true;
							finishedUsing = true;
						}
					}
				}
				catch(Exception e){
					if(choice.equalsIgnoreCase("B")) {
						finishedUsing = true;
					}
				}
			}
		}
		return new boolean [] {caught, used};
	}
	
	/*Adding to the user's items
	 * pre: String item, int amount
	 * post: N/A
	 */
	public static void pickUp(String item, int amount, Item balls, Item potions) {
		System.out.print("You found "+amount+" "+item);
		if(amount > 1) {
			System.out.println("s");//adding plural if necessary
		}
		System.out.println();
		if(item.equalsIgnoreCase("Potion")) {
			potions.quantity++;
		}
		else if(item.equalsIgnoreCase("Pokeball")) {
			balls.quantity++;
		}
	}
	
	/*Spawning wild Pokemon
	 * pre: Two int paramters: int playerX, int playerY, Four File parameters: File mapFile, File wildMonOne, File wildMonTwo, File wildMonThree, One int[] parameter: int [] levelRange
	 * post: Returns File wildMon
	 */
	public static PokemonA wildSpawn(int playerX, int playerY, File mapFile, File wildMonOne, File wildMonTwo, File wildMonThree, int [] levelRange) throws IOException {//a route will have three different wild pokemon
		String [][] map = readMap(mapFile);
		PokemonA wildPokemon = null;
		File [] possibleMons = {wildMonOne, wildMonTwo, wildMonThree};
		if(map[playerX][playerY].equalsIgnoreCase("|")) {//checking if the user is in tall grass
			int spawn = (int)(Math.random()*100+1);//Random num from 1-100
			if(spawn <= 55) {//a 55% spawn rate in each tile of grass
				File wildMonFile = possibleMons[(int)(Math.random()*3)];
				int level = (int)(Math.random()*(levelRange[1] - levelRange[0]+1)+levelRange[0]);
				wildPokemon = readPokemon(wildMonFile, level);
			}
		}
		return wildPokemon;//if null is returned, then there is no wild pokemon there
	}
	
	
	
	/*Battling a Pokemon(wild)
	 * pre: Two PokemonA [] array parameters: PokemonA [] team, PokemonA [] teamBattleStat, Two PokemonA parameters: PokemonA enemy, PokemonA enemyBattleStat 
	 * post: Return boolean lost
	 */
	public static boolean wildBattle(PokemonA [] team, PokemonA [] teamBattleStat, PokemonA enemy, PokemonA enemyBattleStat, Item balls, Item potions){
		//constants(variables used thorughout the whole method
		Scanner input = new Scanner(System.in);
		
		boolean lost = false;//if the player loses all pokemon in a battle
		
		boolean choosing = true;//true when player has to input an option, false when something valid is chosen
		String playerChoice = "";//player's input
		
		int monToBattle = -1;//which pokemon is chosen to battle
		PokemonA currentMon = null;//which pokemon is in battle rite now
		PokemonA [] activeMons = {null,null,null,null};//all pokemons used in battle(all gain exp at the end if won)
		MoveA [] moveList = {null,null};//moves of the current battling pokemon
		int used = -1;//keeps track of activeMons[]. Which pokemon were used in battle
		int noOfUsed = -1;
		
		
		boolean battle = false;//determines whether the battle takes place or not
		boolean battleMenu = false;//controls user input in the battle menu
		boolean beginning = true;//controls wheter the user has to back to the beginning of the code(to switch active pokemon
		boolean fromPMenu = false;//controls choosing a pokemon mid battle
		
		//new whole loop goes here
		while(beginning) {
			while(choosing) {//choosing who to send out first
				System.out.println("Who would you like to send out?\t\t\t Press 'R' to run");
				if(fromPMenu) {
					System.out.println("\t\t\t Press 'B' to go back");
				}
				for(int i = 0; i < 4; i++) {//printing options of who to send out
					if(team[i] == null) {
						continue;
					}
					System.out.println(team[i].name+"\t\t"+teamBattleStat[i].hp+"/"+team[i].hp+"("+(i+1)+")");//Name	CurrentHP/MaxHP(Number)
				}
				playerChoice = input.next();//could be letter(menu options) or number(pokemon options)
				
				try {
					monToBattle = Integer.parseInt(playerChoice) - 1;//if player wants to send out a pokemon
					//System.out.println("you chose to send out");	//-1 adjusts input to index number 
					//validating playerChoice
					if(monToBattle < 0 || monToBattle > 3) {//enters a number that is out of bounds
						System.out.println("Not a team member");
						continue;//restart choosing loop
					}
					else if(teamBattleStat[monToBattle] == null) {
						System.out.println("Not a team member");//number they chose doesn't correspond to team member
						continue;//restart choosing loop
					}
					else if(teamBattleStat[monToBattle].hp == 0) {//chosen Pokemon has 0 HP
						System.out.println("That Pokemon has fainted, it can't battle!");
						continue;//restart choosing loop
					}
					else if(fromPMenu && team[monToBattle].name.equalsIgnoreCase(currentMon.name)) {//if the player chooses the already active pokemon when switching
						System.out.println("That pokemon is already in battle, choose another!");
						continue;//restart choosing loop
					}
					else {//valid option entered
						if(fromPMenu || true) {//coming from select pokemon menu
							boolean wasUsed = false;
							for (int i = 0; i <= noOfUsed; i++) {//looping through activeMons list
								String usedName = activeMons[i].name;//getting name of the used pokemon at index i
								if(usedName.equalsIgnoreCase(team[monToBattle].name)) {//selected pokemon has been used
									wasUsed = true;
									used = i;
									break;
								}
								else {//different pokemon is used
									wasUsed = false;
									
								}
							}
							if(!wasUsed) {
								noOfUsed++;
								used = noOfUsed;
							}
						}
						activeMons[used] = teamBattleStat[monToBattle];//initializing current battling pokemon
						currentMon = activeMons[used];
						moveList = new MoveA[] {currentMon.moveOne, currentMon.moveTwo};//Array of moves the pokemon has
						System.out.println("You sent out "+currentMon.name);
						//System.out.println("valid entered"+currentMon.name);
						battle = true;//battle will begin
						battleMenu = true;
						
						if(fromPMenu) {//player gets attacked if they switched pokemon
							fromPMenu = false;//resetting fromPMenu
							MoveA [] enemyMoveList = {enemy.moveOne, enemy.moveTwo};//initalizing enemy attacks
							MoveA enemyMove = enemyMoveList[(int)(Math.random()*2)];//choosing random move for enemy
							singleAttack(enemyBattleStat, currentMon, enemyMove);//enemy attacks if the player uses an item
							if(currentMon.hp > 0) {
								System.out.println("Your "+currentMon.name+" is at "+currentMon.hp+" HP");
							}
							//copy from choosing attack: if player's pokemon faints
							if(currentMon.hp <= 0) {//player's pokemon faints
								System.out.println(currentMon.name+" fainted!");
								boolean hasOthers = true;//checks if the player has other pokemon that can battle
								for(int i = 0; i < 4; i++) {
									if(teamBattleStat[i] == null) {//no pokemon in that index
										hasOthers = false;
									}
									else if(teamBattleStat[i].hp == 0) {//pokemon at i index has no hp
										hasOthers = false;
									}
									else {//there is another pokemon with hp
										hasOthers = true;
										beginning = true;//will loop through beginning while loop
										choosing = true;//will go into and loop choosing a pokemon
										break;// breaks choosing loop, back into beginning
									}
								}
								if(!hasOthers) {//has no pokemon that can battle//battle ends
									System.out.println("You lost to the wild Pokemon");
									battleMenu = false;//won't loop through battleMenu
									beginning = false;//won't loop through beginning
									lost = true;
									break;//breaks choosingAttack loop//into battleMenu
								}
							}
						}
						break;//breaks choosing 
					}
				}
				catch(Exception e) {//player entered a letter
					if(playerChoice.equalsIgnoreCase("R")) {//player wants to escape the battle
						System.out.println("You ran away");
						battle = false;//battle won't start
						beginning = false;//won't loop beginning
						break;//breaks choosing loop//into beginnning loop
					}
					else if(playerChoice.equalsIgnoreCase("B") && fromPMenu) {//in case the player wants to go back
						battle = true;//will enter battle
						battleMenu = true;//will loop through/go to battleMenu again
						break;//breaks choosing
					}
					else {//neither run or a valid number
						System.out.println("Invalid input");
					}
				}
			}
			
			
			if(battle) {//battle starts
				
				while(battleMenu) {//choosing options while in battle(Options include run, attack, bag, and pokemon)
					
					boolean choosingAttack = false;//controls the attack menu, choosing an attack
					System.out.println("What do you want to do?\t\t Press 'R' to run"//main battle menu
							+ "\n\t\t\t\t Press 'A' to attack"
							+ "\n\t\t\t\t Press 'B' for bag"
							+ "\n\t\t\t\t Press 'P; for Pokemon");
					playerChoice = input.next();//enters a letter corresponding to each section in the menu
					
					if(playerChoice.equalsIgnoreCase("R")) {//player runs away mid battle
						System.out.println("You ran away");
						//battle = false;//ends battle
						beginning = false;//won't loop beginning
						break;//breaks battleMenu//into beginning
					}
					
					else if(playerChoice.equalsIgnoreCase("A")) {//player chooses to attack
						choosingAttack = true;//bring up attack menu
						int moveChoice = -1;//what move the player chose to use
						
						while(choosingAttack) {
							MoveA [] enemyMoveList = {enemy.moveOne, enemy.moveTwo};//initalizing enemy attacks
							MoveA enemyMove = enemyMoveList[(int)(Math.random()*2)];//choosing random move for enemy
							
							System.out.println("What attack should "+currentMon.name+" use?\t\t Press 'B' to go back");//choosing what attack the pokemon should use
							System.out.println("MOVE\t\tPOWER\t\tTYPE");
							System.out.println(moveList[0].name+"\t\t"+moveList[0].power+"\t\t"+moveList[0].type+" (1)");//moveList[0] is first move of current pokemon
							System.out.println(moveList[1].name+"\t\t"+moveList[1].power+"\t\t"+moveList[1].type+" (2)");//moveList[1] is second move of current pokemon
							playerChoice = input.next();//player chooses an attack(or to go back(B))
							try {
								moveChoice = Integer.parseInt(playerChoice)-1;//excpecting a number answer for a move
																			  //-1 to adjust to moveList[]
								if(moveChoice < 0 || moveChoice > 1) {//number is out of bounds(less than 0, greater than 1)
									System.out.println("Invalid input");
									continue;//restart choosing attack
								}
								else {//valid move chosen//attacking begins
									MoveA usedMove = moveList[moveChoice];//gets move from player's current Pokemon moveList
									System.out.println("__________________________________________________________");
									battleExecution(currentMon, enemyBattleStat, usedMove, enemyMove);//turn is executed
									
									if(enemyBattleStat.hp == 0) {//opposing pokemon faints//battle over, player pokemon gains EXP
										System.out.println("You beat the opposing Pokemon! Your pokemon gained EXP from winning");
										for(int i = 0; i <= noOfUsed; i++) {
											//System.out.println("noOfUsed"+ noOfUsed);
											for(int x = 0; x < 4; x++) {//looking for battled pokemon in team array
												if(team[x] == null) {//if no pokemon there, skip
													break;
												}
												else if(activeMons[i].name.equalsIgnoreCase(team[x].name)) {//if the pokemon used is found
													PokemonA ifLevelled = levelUp(team[x], 5);//returning a copy(into ifLevelled)
																							  //will check if pokemon levelled up if not null
													
													if(ifLevelled != null) {//not null means level up
														for(int y = 0; y < 4; y++) {//finding the pokemon in team array
															if(team[y] == null) {//if the object in that spot is null(no pokemon)
																break;//skip if there is no pokemon there
															}
															else if(activeMons[i].name.equalsIgnoreCase(team[y].name)) {
																//team[y] = new PokemonA(ifLevelled, true);//assigning levelled up pokemon to replace old one
																teamBattleStat[y] = new PokemonA(ifLevelled, true);
																activeMons[i] = new PokemonA(teamBattleStat[y], true);
																teamBattleStat[y].hp = team[y].hp;
																activeMons[i].hp = team[y].hp;
															}
															else {
																continue;//if names aren't equal, skip
															}
														}
													}
													
												}
											}
										}
										battleMenu = false;//won't loop through battleMenu after this
										beginning = false; //won't loop through beginning
										break;//breaks choosingAttack loop//back to battleMenu loop
									}
									
									if(currentMon.hp == 0) {//player's pokemon faints
										boolean hasOthers = true;//checks if the player has other pokemon that can battle
										for(int i = 0; i < 4; i++) {
											if(teamBattleStat[i] == null) {//no pokemon in that index
												hasOthers = false;
											}
											else if(teamBattleStat[i].hp == 0) {//pokemon at i index has no hp
												hasOthers = false;
											}
											else {//there is another pokemon with hp
												hasOthers = true;
												System.out.println("Choose another Pokemon to send out: ");
												battleMenu = false;//won't loop through main battleMenu loop
												battle = false;
												beginning = true;//will loop through beginning while loop
												choosing = true;//will go into and loop choosing a pokemon
												break;// breaks choosingAttack loop//into battleMenu loop
											}
										}
										if(!hasOthers) {//has no pokemon that can battle//battle ends
											System.out.println("You lost to the wild Pokemon");
											battleMenu = false;//won't loop through battleMenu
											beginning = false;//won't loop through beginning
											lost = true;
											break;//breaks choosingAttack loop//into battleMenu
										}
									}
									break;//breaks choosingAttack, back into main battleMenu
								}
							}
							catch(NumberFormatException e) {//user enters a letter instead
								if(playerChoice.equalsIgnoreCase("B")) {//user wants to go back to battle menu
									break;//breaks choosingAttack//back to battleMenu loop
								}
								System.out.println("Invalid input");
								
							}
						}
					}
					
					else if(playerChoice.equalsIgnoreCase("B")) {//player wants to open their bag
						boolean [] ifCaughtOrUsed = bag(balls, potions, true, team, teamBattleStat, enemy, enemyBattleStat);
						if(ifCaughtOrUsed[0]) {//battle should end if pokemon is caught(first index of returned)
							beginning = false;//won't loop beginning
							break;//breaks battleMenu, into beginning
						}
						else if(ifCaughtOrUsed[1]) {//if an item was used(second index of returned)
							MoveA [] enemyMoveList = {enemy.moveOne, enemy.moveTwo};//initalizing enemy attacks
							MoveA enemyMove = enemyMoveList[(int)(Math.random()*2)];//choosing random move for enemy
							singleAttack(enemyBattleStat, currentMon, enemyMove);//enemy attacks if the player uses an item
							if(currentMon.hp > 0) {
								System.out.println("Your "+currentMon.name+" is at "+currentMon.hp+" HP");
							}
							//copy from choosing attack: if player's pokemon faints
							if(currentMon.hp == 0) {//player's pokemon faints
								System.out.println(currentMon.name+" fainted!");
								boolean hasOthers = true;//checks if the player has other pokemon that can battle
								for(int i = 0; i < 4; i++) {
									if(teamBattleStat[i] == null) {//no pokemon in that index
										hasOthers = false;
									}
									else if(teamBattleStat[i].hp == 0) {//pokemon at i index has no hp
										hasOthers = false;
									}
									else {//there is another pokemon with hp
										System.out.println("Choose another Pokemon to send out");
										hasOthers = true;
										battleMenu = false;//won't loop through main battleMenu loop
										battle = false;
										beginning = true;//will loop through beginning while loop
										choosing = true;//will go into and loop choosing a pokemon
										break;// breaks choosingAttack loop//into battleMenu loop
									}
								}
								if(!hasOthers) {//has no pokemon that can battle//battle ends
									System.out.println("You can't battle anymore!");
									lost = true;
									battleMenu = false;//won't loop through battleMenu
									beginning = false;//won't loop through beginning
									break;//breaks choosingAttack loop//into battleMenu
								}
							}
						}
					}
					
					else if(playerChoice.equalsIgnoreCase("P")) {//player wants to switch their current pokemon
						beginning = true;
						choosing = true;
						fromPMenu = true;
						break;//breaks battleMenu loop, into beginning loop
					}
					
				}
			}
		}
		//loop ends here
		for(int i = 0; i <= noOfUsed; i++) {//resetting stats after battle
			if(activeMons[i] == null) {//no pokemon there or after
				break;
			}
			else {
				for(int x = 0; x < 4; x++) {//searching pokemon's copy of stats(teamBattleStat[x]
					if(teamBattleStat[x] == null) {//no pokemon there or after
						break;
					}
					else if(activeMons[i].name.equalsIgnoreCase(teamBattleStat[x].name)) {
						teamBattleStat[x] = new PokemonA(team[x],false);
						teamBattleStat[x].hp = activeMons[i].hp;
						//System.out.println(teamBattleStat[x].level);
					}
				}
			}
		}
		return lost; //resets player postion if they lost to a wild pokemon(in main method)
	}
	
	
	
	
	/*Performing a full turn, one player attack and one enemy attack
	 * pre: Two PokemonA parameters: PokemonA pokemonBattleStat, PokemonA enemyBattleStat, Two MoveA parameters: MoveA playerMove, MoveA enemyMove
	 * post: N/A
	 */
	public static void battleExecution(PokemonA pokemonBattleStat, PokemonA enemyBattleStat, MoveA playerMove, MoveA enemyMove) {
		//checking speed to see who attacks first
		if(pokemonBattleStat.speed >= enemyBattleStat.speed) {//player's pokemon is faster, so goes first
			singleAttack(pokemonBattleStat, enemyBattleStat, playerMove);//player's attack
			if(enemyBattleStat.hp <= 0) {//enemy pokemon lost all health
				enemyBattleStat.hp = 0;
				System.out.println("The opposing "+enemyBattleStat.name+" fainted!");
				System.out.println("__________________________________________________________");
				return;//stop/exit method
			}
			else {//opposing pokemon still has hp
				System.out.println("The opposing "+enemyBattleStat.name+" is at "+enemyBattleStat.hp+" HP");
				System.out.println("__________________________________________________________");
				singleAttack(enemyBattleStat, pokemonBattleStat, enemyMove);//enemy attacks
				if(pokemonBattleStat.hp <= 0) {
					pokemonBattleStat.hp = 0;
					System.out.println("Your "+pokemonBattleStat.name+" fainted!");
					return;//exit method
				}
				else {
					System.out.println("Your "+pokemonBattleStat.name+" is at "+pokemonBattleStat.hp+" HP");
				}
			}
		}
		
		else if(enemyBattleStat.speed >= pokemonBattleStat.speed) {//enemy pokemon is faster, so goes first
			singleAttack(enemyBattleStat, pokemonBattleStat, enemyMove);//enemy's attack
			if(pokemonBattleStat.hp <= 0) {//player pokemon lost all health
				pokemonBattleStat.hp = 0;
				System.out.println("Your "+pokemonBattleStat.name+" fainted!");
				return;//stop/exit method
			}
			else {//player's pokemon still has hp
				System.out.println("Your "+pokemonBattleStat.name+" is at "+pokemonBattleStat.hp+" HP");
				System.out.println("__________________________________________________________");
				singleAttack(pokemonBattleStat, enemyBattleStat, playerMove);//player attacks
				if(enemyBattleStat.hp <= 0) {
					enemyBattleStat.hp = 0;
					System.out.println("The opposing "+enemyBattleStat.name+" fainted!");
					return;//exit method
				}
				else {
					System.out.println("The opposing "+enemyBattleStat.name+" is at "+enemyBattleStat.hp+" HP");
				}
			}
		}
	}
	
	
	
	
	
	
	/*Performing damage calculations and stat changes
	 * pre: Two PokemonA parameters: PokemonA pokemonBattleStat, PokemonA enemyBattleStat, One MoveA parameter: MoveA used
	 * post: N/A
	 */
	public static void singleAttack(PokemonA pokemonBattleStat, PokemonA enemyBattleStat, MoveA used) {
		System.out.println(pokemonBattleStat.name+" used "+used.name);
		if(used.power > 0) {//checking if the move does damage
			int damage =(int)(0.15 * pokemonBattleStat.attack/enemyBattleStat.defense * used.power);
			//checking type advantages(super effective moves to twice the damage)
			for(int i = 0; i < enemyBattleStat.typeWeaknesses.length; i++) {
				if(used.type.equalsIgnoreCase(enemyBattleStat.typeWeaknesses[i])) {
					damage *= 2;
					System.out.println("It's super effective!!");
					break;
				}
			}
			enemyBattleStat.hp -= damage;
			if(enemyBattleStat.hp <= 0) {
				enemyBattleStat.hp = 0;
			}
		}
		if(used.status) {//if the move is a status move
			if(!used.self) {//the the status is inflicted on the opposing(opp) pokemon
				//checking which stat is affected
				if(used.statAffected.equalsIgnoreCase("Attack")) {//attack is affected
					enemyBattleStat.attack *= used.statChange;
					if(used.statChange < 1) {//if stat fell
						System.out.println(enemyBattleStat.name+"'s attack fell!");
					}
				}
				else if(used.statAffected.equalsIgnoreCase("Defense")) {//defense is affected
					enemyBattleStat.defense *= used.statChange;
					if(used.statChange < 1) {//if stat fell
						System.out.println(enemyBattleStat.name+"'s defense fell!");
					}
				}
				else if(used.statAffected.equalsIgnoreCase("Speed")) {//speed is affected
					enemyBattleStat.speed *= used.statChange;
					if(used.statChange < 1) {//if stat fell
						System.out.println(enemyBattleStat.name+"'s speed fell!");
					}
				}
				
			}
		}
	}
	
	/*Looking around the player for a Pokemon trainer
	 * pre: Four int parameters: int playerX, int playerY, int trainerX, int trainerY, String trainerName, One boolean parameter: boolean battled
	 * post: return boolean toBattle
	 */
	public static Trainer meetTrainer(int playerX, int playerY, int trainerX, int trainerY, Trainer trainer) {
		boolean toBattle = false;
		if(!trainer.battled) {//battle only initiates if the player hasn't battled the trainer before
			if(playerY == trainerY && ((playerX >= trainerX - 3 && playerX < trainerX)|| (playerX <= trainerX + 3 && playerX > trainerX))) {//player is within three tiles above or below trainer
				System.out.println("You met Trainer "+trainer.name+" and they want to battle!");
				toBattle = true;
			}
			if(playerX == trainerX && ((playerY >= trainerY - 3 && playerY < trainerY) || (playerY <= trainerY + 3 && playerY > trainerY))) {//player is within three tiles right or left of the trainer
				System.out.println("You met Trainer "+trainer.name+" and they want to battle!");
				toBattle = true;
			}
		}
		if(toBattle) {
			return trainer;
		}
		else {
			return null;
		}
	}
	
	/*A pokemon battle against a trainer
	 * pre: Four PokemonA[] variables: PokemonA[] team, PokemonA[] teamBattleStat, PokemonA[] enemyTeam, PokemonA[] enemyTeamBattleStat, Two Item parameters: Item balls, Item potions, One Trainer parameter: Trainer trainer
	 *post: returns boolean lost
	 */
	public static boolean trainerBattle(PokemonA [] team, PokemonA [] teamBattleStat, PokemonA [] enemyTeam, PokemonA [] enemyTeamBattleStat, Item balls, Item potions, Trainer trainer){
		//constants(variables used throughout the whole method)
		Scanner input = new Scanner(System.in);
		
		boolean lost = false;//if the player loses all pokemon in a battle
		
		boolean choosing = true;//true when player has to input an option, false when something valid is chosen
		String playerChoice = "";//player's input
		
		int monToBattle = -1;//which pokemon is chosen to battle
		PokemonA currentMon = null;//which pokemon is in battle rite now
		PokemonA [] activeMons = {null,null,null,null};//all pokemons used in battle(all gain exp at the end if won)
		MoveA [] moveList = {null,null};//moves of the current battling pokemon
		int used = -1;//keeps track of activeMons[]. Which pokemon were used in battle
		int noOfUsed = -1;
		
		
		boolean battle = false;//determines whether the battle takes place or not
		boolean battleMenu = false;//controls user input in the battle menu
		boolean beginning = true;//controls wheter the user has to back to the beginning of the code(to switch active pokemon
		boolean fromPMenu = false;//controls choosing a pokemon mid battle
		 
		
		//enemy team
		PokemonA enemy = enemyTeam[0];//trainer's first pokemon
		PokemonA enemyBattleStat = enemyTeamBattleStat[0];//copy of trainer's current pokemon's stats
		int enemyIndex = 1;//keeps track of pokemon on opposing trainer's team(starting at the second pokemon
		System.out.println("Trainer "+trainer.name + " sent out their "+enemyBattleStat.name);
		//new whole loop goes here
		while(beginning) {
			boolean breakBeginning = false;//to break the beginning loop from within another loop
			while(choosing) {//choosing who to send out first
				System.out.println("Who would you like to send out?");//can't run from trainer battle
				if(fromPMenu) {
					System.out.println("\t\t\t Press 'B' to go back");
				}
				for(int i = 0; i < 4; i++) {//printing options of who to send out
					if(team[i] == null) {
						continue;
					}
					System.out.println(team[i].name+"\t\t"+teamBattleStat[i].hp+"/"+team[i].hp+"("+(i+1)+")");//Name	CurrentHP/MaxHP(Number)
				}
				playerChoice = input.next();//could be letter(menu options) or number(pokemon options)
				
				try {
					monToBattle = Integer.parseInt(playerChoice) - 1;//if player wants to send out a pokemon
					//System.out.println("you chose to send out");	//-1 adjusts input to index number 
					//validating playerChoice
					if(monToBattle < 0 || monToBattle > 3) {//enters a number that is out of bounds
						System.out.println("Not a team member");
						continue;//restart choosing loop
					}
					else if(teamBattleStat[monToBattle] == null) {
						System.out.println("Not a team member");//number they chose doesn't correspond to team member
						continue;//restart choosing loop
					}
					else if(teamBattleStat[monToBattle].hp == 0) {//chosen Pokemon has 0 HP
						System.out.println("That Pokemon has fainted, it can't battle!");
						continue;//restart choosing loop
					}
					else if(fromPMenu && team[monToBattle].name.equalsIgnoreCase(currentMon.name)) {//if the player chooses the already active pokemon when switching
						System.out.println("That pokemon is already in battle, choose another!");
						continue;//restart choosing loop
					}
					else {//valid option entered
						if(fromPMenu || true) {//coming from select pokemon menu//true is test//take out if true later(jan 5)
							boolean wasUsed = false;
							for (int i = 0; i <= noOfUsed; i++) {//looping through activeMons list
								String usedName = activeMons[i].name;//getting name of the used pokemon at index i
								if(usedName.equalsIgnoreCase(team[monToBattle].name)) {//selected pokemon has been used
									wasUsed = true;
									used = i;
									break;
								}
								else {//different pokemon is used
									wasUsed = false;
									
								}
							}
							if(!wasUsed) {
								noOfUsed++;
								used = noOfUsed;//test
							}
						}
						/*else {
							used++;
							noOfUsed++;
						}*/
						activeMons[used] = teamBattleStat[monToBattle];//initializing current battling pokemon
						currentMon = activeMons[used];
						moveList = new MoveA[] {currentMon.moveOne, currentMon.moveTwo};//Array of moves the pokemon has
						System.out.println("You sent out "+currentMon.name);
						//System.out.println("valid entered"+currentMon.name);
						battle = true;//battle will begin
						battleMenu = true;
						
						if(fromPMenu) {//player gets attacked if they switched pokemon
							fromPMenu = false;//resetting fromPMenu
							MoveA [] enemyMoveList = {enemy.moveOne, enemy.moveTwo};//initalizing enemy attacks
							MoveA enemyMove = enemyMoveList[(int)(Math.random()*2)];//choosing random move for enemy
							singleAttack(enemyBattleStat, currentMon, enemyMove);//enemy attacks if the player uses an item
							if(currentMon.hp > 0) {
								System.out.println("Your "+currentMon.name+" is at "+currentMon.hp+" HP");
							}
							//copy from choosing attack: if player's pokemon faints
							else if(currentMon.hp <= 0) {//player's pokemon faints
								System.out.println(currentMon.name+" fainted!");
								boolean hasOthers = true;//checks if the player has other pokemon that can battle
								for(int i = 0; i < 4; i++) {
									if(teamBattleStat[i] == null) {//no pokemon in that index
										hasOthers = false;
									}
									else if(teamBattleStat[i].hp == 0) {//pokemon at i index has no hp
										hasOthers = false;
									}
									else {//there is another pokemon with hp
										hasOthers = true;
										beginning = true;//will loop through beginning while loop
										choosing = true;//will go into and loop choosing a pokemon
										break;// breaks for loop
									}
								}
								if(!hasOthers) {//has no pokemon that can battle//battle ends
									System.out.println("You lost to "+trainer.name);
									battleMenu = false;//won't loop through battleMenu
									beginning = false;//won't loop through beginning
									lost = true;
									break;//breaks choosingAttack loop//into battleMenu
								}
								else if(hasOthers) {
									breakBeginning = true;
									break;
								}
							}
						}
						break;//breaks choosing 
					}
				}
				catch(Exception e) {//player entered a letter
					if(playerChoice.equalsIgnoreCase("R")) {//player wants to escape the battle
						System.out.println("You can't run away from a trainer battle!");
						continue;//restart choosing loop
					}
					else if(playerChoice.equalsIgnoreCase("B") && fromPMenu) {//in case the player wants to go back
						battle = true;//will enter battle
						battleMenu = true;//will loop through/go to battleMenu again
						break;//breaks choosing
					}
					else {//neither run or a valid number
						System.out.println("Invalid input");
					}
				}
			}
			if(breakBeginning) {
				continue;//restarts beginning loop/check
			}
			
			if(battle) {//battle starts
				
				while(battleMenu) {//choosing options while in battle(Options include run, attack, bag, and pokemon)
					
					boolean choosingAttack = false;//controls the attack menu, choosing an attack
					System.out.println("What do you want to do?"//main battle menu
							+ "\n\t\t\t\t Press 'A' to attack"
							+ "\n\t\t\t\t Press 'B' for bag"
							+ "\n\t\t\t\t Press 'P; for Pokemon");
					playerChoice = input.next();//enters a letter corresponding to each section in the menu
					
					if(playerChoice.equalsIgnoreCase("R")) {//player runs away mid battle
						System.out.println("You can't run away from a trainer battle");
						//battle = false;//ends battle
						continue;//restart battleMenu
					}
					
					else if(playerChoice.equalsIgnoreCase("A")) {//player chooses to attack
						choosingAttack = true;//bring up attack menu
						int moveChoice = -1;//what move the player chose to use
						
						while(choosingAttack) {
							MoveA [] enemyMoveList = {enemy.moveOne, enemy.moveTwo};//initalizing enemy attacks
							MoveA enemyMove = enemyMoveList[(int)(Math.random()*2)];//choosing random move for enemy
							
							System.out.println("What attack should "+currentMon.name+" use?\t\t Press 'B' to go back");//choosing what attack the pokemon should use
							System.out.println("MOVE\t\tPOWER\t\tTYPE");
							System.out.println(moveList[0].name+"\t\t"+moveList[0].power+"\t\t"+moveList[0].type+" (1)");//moveList[0] is first move of current pokemon
							System.out.println(moveList[1].name+"\t\t"+moveList[1].power+"\t\t"+moveList[1].type+" (2)");//moveList[1] is second move of current pokemon
							playerChoice = input.next();//player chooses an attack(or to go back(B))
							try {
								moveChoice = Integer.parseInt(playerChoice)-1;//excpecting a number answer for a move
																			  //-1 to adjust to moveList[]
								if(moveChoice < 0 || moveChoice > 1) {//number is out of bounds(less than 0, greater than 1)
									System.out.println("Invalid input");
									continue;//restart choosing attack
								}
								else {//valid move chosen//attacking begins
									MoveA usedMove = moveList[moveChoice];//gets move from player's current Pokemon moveList
									System.out.println("__________________________________________________________");
									battleExecution(currentMon, enemyBattleStat, usedMove, enemyMove);//turn is executed
									
									if(enemyBattleStat.hp == 0) {//when opposing trainer's current pokemon faints
										if(enemyTeamBattleStat[enemyTeam.length-1].hp == 0) {//if last trainer's pokemon faint, player wins. 
											System.out.println("You beat "+trainer.name+"! Your pokemon gained EXP from winning");
											for(int i = 0; i <= noOfUsed; i++) {
												//System.out.println("noOfUsed"+ noOfUsed);
												for(int x = 0; x < 4; x++) {//looking for battled pokemon in team array
													if(team[x] == null) {//if no pokemon there, break
														break;
													}
													else if(activeMons[i].name.equalsIgnoreCase(team[x].name)) {//if the pokemon used is found
														PokemonA ifLevelled = levelUp(team[x], 10);//returning a copy(into ifLevelled)
																								  //will check if pokemon levelled up if not null
														
														if(ifLevelled != null) {//not null means level up
															for(int y = 0; y < 4; y++) {//finding the pokemon in team array
																if(team[y] == null) {//if the object in that spot is null(no pokemon)
																	break;//skip if there is no pokemon there
																}
																else if(activeMons[i].name.equalsIgnoreCase(team[y].name)) {
																	//team[y] = new PokemonA(ifLevelled, true);//assigning levelled up pokemon to replace old one
																	teamBattleStat[y] = new PokemonA(ifLevelled, true);
																	activeMons[i] = new PokemonA(teamBattleStat[y], true);
																	teamBattleStat[y].hp = team[y].hp;
																	activeMons[i].hp = team[y].hp;
																}
																else {
																	continue;//if names aren't equal, skip
																}
															}
														}
														
													}
												}
											}
											battleMenu = false;//won't loop through battleMenu after this
											beginning = false; //won't loop through beginning
											break;//breaks choosingAttack loop//back to battleMenu loop
										}
										else {//opposing trainer has one pokemon left
											System.out.println("Trainer "+trainer.name+" sent out "+enemyTeam[enemyIndex].name);
											enemy = enemyTeam[enemyIndex];
											enemyBattleStat = enemyTeamBattleStat[enemyIndex];
											enemyIndex++;
											break;//breaks choosingAttack, into battleMenu
											
										}
									}
									
									if(currentMon.hp == 0) {//player's pokemon faints
										//System.out.println(currentMon.name+" fainted!");
										boolean hasOthers = true;//checks if the player has other pokemon that can battle
										for(int i = 0; i < 4; i++) {
											if(teamBattleStat[i] == null) {//no pokemon in that index
												hasOthers = false;
											}
											else if(teamBattleStat[i].hp == 0) {//pokemon at i index has no hp
												hasOthers = false;
											}
											else {//there is another pokemon with hp
												hasOthers = true;
												System.out.println("Choose another Pokemon to send out: ");
												battleMenu = false;//won't loop through main battleMenu loop
												battle = false;
												beginning = true;//will loop through beginning while loop
												choosing = true;//will go into and loop choosing a pokemon
												break;// breaks choosingAttack loop//into battleMenu loop
											}
										}
										if(!hasOthers) {//has no pokemon that can battle//battle ends
											System.out.println("You lost to "+trainer.name);
											battleMenu = false;//won't loop through battleMenu
											beginning = false;//won't loop through beginning
											lost = true;
											break;//breaks choosingAttack loop//into battleMenu
										}
									}
									break;//breaks choosingAttack, back into main battleMenu
								}
							}
							catch(NumberFormatException e) {//user enters a letter instead
								if(playerChoice.equalsIgnoreCase("B")) {//user wants to go back to battle menu
									break;//breaks choosingAttack//back to battleMenu loop
								}
								System.out.println("Invalid input");
								
							}
						}
					}
					
					else if(playerChoice.equalsIgnoreCase("B")) {//player wants to open their bag
						boolean [] ifCaughtOrUsed = bag(balls, potions, false, team, teamBattleStat, enemy, enemyBattleStat);
						if(ifCaughtOrUsed[0]) {//battle should end if pokemon is caught(first index of returned)
							beginning = false;//won't loop beginning
							break;//breaks battleMenu, into beginning
						}
						else if(ifCaughtOrUsed[1]) {//if an item was used(second index of returned)
							MoveA [] enemyMoveList = {enemy.moveOne, enemy.moveTwo};//initalizing enemy attacks
							MoveA enemyMove = enemyMoveList[(int)(Math.random()*2)];//choosing random move for enemy
							singleAttack(enemyBattleStat, currentMon, enemyMove);//enemy attacks if the player uses an item
							if(currentMon.hp > 0) {
								System.out.println("Your "+currentMon.name+" is at "+currentMon.hp+" HP");
							}
							//copy from choosing attack: if player's pokemon faints
							if(currentMon.hp == 0) {//player's pokemon faints
								System.out.println(currentMon.name+" fainted");
								boolean hasOthers = true;//checks if the player has other pokemon that can battle
								for(int i = 0; i < 4; i++) {
									if(teamBattleStat[i] == null) {//no pokemon in that index
										hasOthers = false;
									}
									else if(teamBattleStat[i].hp == 0) {//pokemon at i index has no hp
										hasOthers = false;
									}
									else {//there is another pokemon with hp
										System.out.println("Choose another Pokemon to send out");
										hasOthers = true;
										battleMenu = false;//won't loop through main battleMenu loop
										battle = false;
										beginning = true;//will loop through beginning while loop
										choosing = true;//will go into and loop choosing a pokemon
										break;// breaks choosingAttack loop//into battleMenu loop
									}
								}
								if(!hasOthers) {//has no pokemon that can battle//battle ends
									System.out.println("You can't battle anymore!");
									lost = true;
									battleMenu = false;//won't loop through battleMenu
									beginning = false;//won't loop through beginning
									break;//breaks choosingAttack loop//into battleMenu
								}
							}
						}
					}
					
					else if(playerChoice.equalsIgnoreCase("P")) {//player wants to switch their current pokemon
						beginning = true;
						choosing = true;
						fromPMenu = true;
						break;//breaks battleMenu loop, into beginning loop
					}
					
				}
			}
		}
		//loop ends here
		for(int i = 0; i <= noOfUsed; i++) {//resetting stats after battle
			if(activeMons[i] == null) {//no pokemon there or after
				break;
			}
			else {
				for(int x = 0; x < 4; x++) {//searching pokemon's copy of stats(teamBattleStat[x]
					if(teamBattleStat[x] == null) {//no pokemon there or after
						break;
					}
					else if(activeMons[i].name.equalsIgnoreCase(teamBattleStat[x].name)) {
						teamBattleStat[x] = new PokemonA(team[x],false);
						teamBattleStat[x].hp = activeMons[i].hp;
						//System.out.println(teamBattleStat[x].level);
					}
				}
			}
		}
		return lost; //resets player postion if they lost to a wild pokemon
	}
	
	/*Battling trainers in the battle marathon
	 * pre: One trainer parameter: Trainer trainer, Two PokemonA [] parameters: PokemonA [] team, PokemonA [] teamBattleStat, Two Item parameters: Item balls, Item potions, One int parameter: int playerY
	 * post: return boolean lost
	 */
	public static boolean marathonBattle(Trainer trainer, PokemonA[] team, PokemonA[] teamBattleStat, Item balls, Item potions, int playerY) {
		boolean lost = false;
		if(playerY == trainer.y && !trainer.battled) {//in trainer's column
			System.out.println("You met Elite Trainer "+trainer.name+", time to battle!");
			lost = trainerBattle(team, teamBattleStat, trainer.team, Arrays.copyOf(trainer.team, trainer.team.length), balls, potions, trainer);
			if(!lost) {//won against the trainer
				trainer.battled = true;
			}
		}
		return lost;
	}
	
	/*Displaying rules and how to play the game
	 * pre: One String parameter: String playerInput
	 * post: N/A
	 */
	public static void rules(char playerInput) {
		if(playerInput == 'x') {
			System.out.println("RULES AND TIPS");
			delay(1500);
			System.out.println("_________________________________________");
			System.out.println("MOVING AROUND THE MAP");
			System.out.println("______________________");
			System.out.println("Enter 'W ' to move up, 'S' to move down, 'A' to move left, and 'D' to move right");
			System.out.println("INTERACTING WITH THE MAP");
			System.out.println("________________________");
			System.out.println("'|' represents a tall grass tile. You may run into a wild Pokemon!");
			System.out.println("'*' represents a large boulder. You can't move across it, only around it!");
			System.out.println("'Y' represents a trainer, waiting to battle you. If you are within three tiles above,"
					+ "\nbelow, to the left, or to the right of them, you will have to battle them. They are challenging, but they"
					+ "\nreward more EXP for beating them!");
			System.out.println("'?' is an item. You might find an item will travelling. Move onto its tile to pick it up!");
			System.out.println("BATTLING AND CATCHING POKEMON");
			System.out.println("_____________________________");
			System.out.println("Battling Pokemon is done in turns. The fastest Pokemon attacks first."
					+ "\nIf you are battling a wild Pokemon, you have the chance to catch it with a Pokeball."
					+ "\nIt would be better if you weaken the Pokemon first before trying to catch it!");
			System.out.println("Remember, you can only catch wild Pokemon, not Pokemon owned by Trainers."
					+ "\nAlso, you can't run away from a Trainer battle, only a wild Pokemon Battle.");
			System.out.println("USING YOUR BAG");
			System.out.println("______________");
			System.out.println("You have two options when you open your bag, to use a Pokeball or a Potion."
					+ "\nYou can only use a Pokeball in a wild Pokemon battle. You can use potions to heal a Pokemon"
					+ "\nby 10 HP. However, if you use an item in a battle, your turn ends and the opposing Pokemon attacks.");
			System.out.println("______________________________________________________________________________________________");
		}
	}
	
}