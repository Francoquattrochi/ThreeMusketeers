package assignment1;

public class BackUpGreedyAgent extends Agent {

	public BackUpGreedyAgent(Board board) {
		super(board);
	}

	
	@Override
	public Move getMove() {
		Agent backUpAgent = new GreedyAgent(board);
		Move move = backUpAgent.getMove();
		return move;
	}
} 


