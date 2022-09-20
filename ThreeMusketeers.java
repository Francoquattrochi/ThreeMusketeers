package assignment1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import assignment1.ThreeMusketeers.GameMode;

public class ThreeMusketeers {
	private GameMode mode;
    private final Board board;
    private Agent musketeerAgent, guardAgent;
    private Agent BackUpAgent;
    private final Scanner scanner = new Scanner(System.in);
    private final List<Move> moves = new ArrayList<>();

    // All possible game modes
    public enum GameMode {
        Human("Human vs Human"),
        HumanRandom("Human vs Computer (Random)"),
        HumanGreedy("Human vs Computer (Greedy)"),
        GameHistory("Game History");

        private final String gameMode;
        GameMode(final String gameMode) {
            this.gameMode = gameMode;
        }
    }

    /**
     * Default constructor to load Starter board
     */
    public ThreeMusketeers() {
        this.board = new Board();
    }

    /**
     * Constructor to load custom board
     * @param boardFilePath filepath of custom board
     */
    public ThreeMusketeers(String boardFilePath) {
        this.board = new Board(boardFilePath);
    }

    /**
     * Play game with human input mode selector
     */
    public void play(){
        System.out.println("Welcome! \n");
        this.mode = getModeInput();
        if(mode == GameMode.GameHistory) {
        	GameHistoryHandler gameHistory = new GameHistoryHandler();
        	gameHistory.readData();
        	gameHistory.callNested();
        	
        	System.out.println("If you would like to watch a game replay, please enter the game number, otherwise enter -1");
        	String userResponse = scanner.next();
        	try {
        		int userResponseInt = Integer.parseInt(userResponse);
        		if (userResponseInt >= 0) {
        			ReplayHandler replayHandler = new ReplayHandler();
    	    		replayHandler.replayReader(userResponseInt);
    	    		replayHandler.replayGame();
        		}
        	}catch(NumberFormatException e) {
        		System.out.println("Invalid Response");
        	}
        }
        else {
        	System.out.println("Playing " + mode.gameMode);
            play(mode);
        }
    		
        		
        	this.play();
        	
        	
        	
        }

    /**
     * Play game without human input mode selector
     * @param mode the GameMode to run
     */
    public void play(GameMode mode){
        selectMode(mode);
        runGame();
    }

    /**
     * Mode selector sets the correct agents based on the given GameMode
     * @param mode the selected GameMode
     */
    private void selectMode(GameMode mode) {
        switch (mode) {
            case Human -> {
                musketeerAgent = new HumanAgent(board);
                guardAgent = new HumanAgent(board);
                BackUpAgent = null;
                // add the new create agent
                
                
            }
            case HumanRandom -> {
                String side = getSideInput();
                
                // The following statement may look weird, but it's what is known as a ternary statement.
                // Essentially, it sets musketeerAgent equal to a new HumanAgent if the value M is entered,
                // Otherwise, it sets musketeerAgent equal to a new RandomAgent
                musketeerAgent = side.equals("M") ? new HumanAgent(board) : new RandomAgent(board);
                
                guardAgent = side.equals("G") ? new HumanAgent(board) : new RandomAgent(board);
                
                BackUpAgent = null;
            }
            case HumanGreedy -> {
                String side = getSideInput();
                musketeerAgent = side.equals("M") ? new HumanAgent(board) : new GreedyAgent(board);
                guardAgent = side.equals("G") ? new HumanAgent(board) : new GreedyAgent(board);
                BackUpAgent = null;
            }
        }
    }

    /**
     * Runs the game, handling human input for move actions
     * Handles moves for different agents based on current turn 
     * @throws IOException 
     */
    private void runGame() {
        while(!board.isGameOver()) {
            System.out.println("\n" + board);

            final Agent currentAgent;
            final Agent backUpAgent = null;
            
 
            if (board.getTurn() == Piece.Type.MUSKETEER && backUpAgent == null)
            	currentAgent = musketeerAgent;
	        else if (board.getTurn() == Piece.Type.GUARD && backUpAgent == null)
	            currentAgent = guardAgent;
	        else if(board.getTurn() == Piece.Type.GUARD && backUpAgent != null)
	        	currentAgent = BackUpAgent;
	        else 
	        	currentAgent = BackUpAgent;
           

            if (currentAgent instanceof HumanAgent) // Human move
                switch (getInputOption()) {
                    case "M":
                        move(currentAgent);
                        break;
                    case "U":
                        if (moves.size() == 0) {
                            System.out.println("No moves to undo.");
                            continue;
                        }
                        else if (moves.size() == 1 || isHumansPlaying()) {
                            undoMove();
                        }
                        else {
                            undoMove();
                            undoMove();
                        }
                        break;
                    case "S":
                        board.saveBoard();
                        break;
                    case "C":
                    	String input = getBackUpInput();
                    	if (board.getTurn() == Piece.Type.MUSKETEER) {
                    		guardAgent = input.equals("G") ? new BackUpGreedyAgent(board) : new BackUpRandomAgent(board);
	                    		runGame();
                		}
                    	
                    	else if (board.getTurn() == Piece.Type.GUARD) {
                    		musketeerAgent = input.equals("G") ? new BackUpGreedyAgent(board) : new BackUpRandomAgent(board);
                    		runGame();
                    	}
			           
                    	
                }
                    // need to add case here for the change of either side player to human random or greedy
                
            else { // Computer move

                System.out.printf("[%s] Calculating move...\n", currentAgent.getClass().getSimpleName());
                move(currentAgent);
            	}
            }
        
        System.out.println(board);
        System.out.printf("\n%s won!%n", board.getWinner().getType());
        this.recordGame();
        this.recordReplay();
        this.SaveScore();
        this.displayScoreBoard();
        
    

    }

	/**
     * Gets a move from the given agent, adds a copy of the move using the copy constructor to the moves stack, and
     * does the move on the board.
     * @param agent Agent to get the move from.
     */
    protected void move(final Agent agent) {
        final Move move = agent.getMove();
        moves.add(new Move(move));
        board.move(move);
    }

    /**
     * Removes a move from the top of the moves stack and undoes the move on the board.
     */
    private void undoMove() {
        board.undoMove(moves.remove(moves.size() - 1));
        System.out.println("Undid the previous move.");
    }

    /**
     * Get human input for move action
     * @return the selected move action, 'M': move, 'U': undo, and 'S': save
     */
    private String getInputOption() {
        System.out.printf("[%s] Enter 'M' to move, 'U' to undo, 'S' to save and 'C' to Switch "
        		+ "the next player to A Random or Greedy BackUpBot: ", board.getTurn().getType());
        while (!scanner.hasNext("[MUSCmusc]")) {
            System.out.print("Invalid option. Enter 'M', 'U', 'S', 'C': ");
            scanner.next();
        }
        return scanner.next().toUpperCase();
    }

    /**
     * Returns whether both sides are human players
     * @return True if both sides are Human, False if one of the sides is a computer
     */
    private boolean isHumansPlaying() {
        return musketeerAgent instanceof HumanAgent && guardAgent instanceof HumanAgent;
    }

    /**
     * Get human input for side selection
     * @return the selected Human side for Human vs Computer games,  'M': Musketeer, G': Guard
     */
    private String getSideInput() {
        System.out.print("Enter 'M' to be a Musketeer or 'G' to be a Guard: ");
        while (!scanner.hasNext("[MGmg]")) {
            System.out.println("Invalid option. Enter 'M' or 'G': ");
            scanner.next();
        }
        return scanner.next().toUpperCase();
    }
    
  /////////////////  
    private String getBackUpInput() {
        System.out.print("Enter 'G' to be a Greedy Agent or 'R' to be a Random Agent: ");
        while (!scanner.hasNext("[GR]")) {
            System.out.println("Invalid option. Enter 'G' or 'R': ");
            scanner.next();
        }
        return scanner.next().toUpperCase();
    }
    
    
/////////////////    

    /**
     * Get human input for selecting the game mode
     * @return the chosen GameMode
     */
    private GameMode getModeInput() {
        System.out.println("""
                    0: Human vs Human
                    1: Human vs Computer (Random)
                    2: Human vs Computer (Greedy)
                    3: Game History""");
        System.out.print("Choose a game mode to play i.e. enter a number: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid option. Enter 0, 1, or 2: ");
            scanner.next();
        }
        final int mode = scanner.nextInt();
        if (mode < 0 || mode > 3) {
            System.out.println("Invalid option.");
            return getModeInput();
        }
        return GameMode.values()[mode];
    }
    
    public void recordGame() {
    	Piece.Type gameWinner = this.board.getWinner();
    	GameMode currMode = this.mode;
        GameHistoryHandler history = new GameHistoryHandler();
        history.checkForFile();
        history.addEntry(gameWinner, currMode);
    }
    
    public void SaveScore() {
    	Piece.Type gameWinner = this.board.getWinner();
    	ScoreBoard scoreB = new ScoreBoard();
    	scoreB.saveScoreToHistory(gameWinner);
    	
    	
    }
    
    public void displayScoreBoard() {
    	DisplayScoreBoard scoreB = new DisplayScoreBoard();
    	scoreB.execute();
    }
    
    public void recordReplay() {
    	for(Move move: this.moves) {
    		Cell fromCell = move.fromCell;
    		Cell toCell = move.toCell;
    		
    		Coordinate coord1 = fromCell.getCoordinate();
    		Coordinate coord2 = toCell.getCoordinate();
    		
    		String coord1Row = Integer.toString(coord1.row);
    		String coord1Col = Integer.toString(coord1.col);
    		String coord2Row = Integer.toString(coord2.row);
    		String coord2Col = Integer.toString(coord2.col);
    		try {
    			FileWriter myWriter = new FileWriter("replays.txt", true);
    			if(this.moves.lastIndexOf(move) == this.moves.size() - 1) {
    				myWriter.write(coord1Row + coord1Col + ":" + coord2Row + coord2Col + "\n");
    			}
    			else{
    				myWriter.write(coord1Row + coord1Col + ":" + coord2Row + coord2Col + ",");
    			}
    			myWriter.close();
    		}catch(IOException e){
    			System.out.println("Replay Feature, problem saving replay.");
    		}
    		
    		
    	}
    }

    public static void main(String[] args) {
        String boardFileName = "Boards/Starter.txt";
        ThreeMusketeers game = new ThreeMusketeers(boardFileName);
        game.play();
    }
}
