package assignment1;

public class BackUpRandomAgent extends Agent {

	public BackUpRandomAgent(Board board) {
		super(board);
	}

	
	@Override
	public Move getMove() {
		Agent backUpAgent = new RandomAgent(board);
		Move move = backUpAgent.getMove();
		return move;
	}

}
