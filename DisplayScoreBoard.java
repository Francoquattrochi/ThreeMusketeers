package assignment1;


public class DisplayScoreBoard implements ScoreBoardCommand{

	@Override
	public void execute() {
		String Score = ScoreBoard.displayScore();
		
		System.out.println("Scoreboard");
		System.out.println(Score);
		
	}

}
