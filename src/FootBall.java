/**
 * 
 */

/**
 * @author approved
 *
 */
public class FootBall {
	private String competitor1;
	private String competitor2;
	private Integer competitor1Score;
	private Integer competitor2Score;

	public FootBall(String march) {
		final String BEGIN_COMPETITOR1_TAG = "<div class=\"ply tright name\">";
		final String BEGIN_COMPETITOR2_TAG = "<div class=\"ply name\">";

		final int indexOfBeginCompetitor1Tag = 0 + BEGIN_COMPETITOR1_TAG.length();
		final int indexOfBeginCompetitor2Tag = march.indexOf(BEGIN_COMPETITOR2_TAG) + BEGIN_COMPETITOR2_TAG.length();
		String competitor1 = march.substring(indexOfBeginCompetitor1Tag, march.indexOf("</div>"));
		String competitor2 = march.substring(indexOfBeginCompetitor2Tag, march.lastIndexOf("</div>"));

		this.competitor1 = competitor1.trim();
		this.competitor2 = competitor2.trim();

		final String BEGIN_SCORE_TAG = "<div class=\"sco\">";
		final char SPECIAL_TAG_IN_SCORE_TAG = '<';
		final int indexOfBeginScoreTag = march.indexOf(BEGIN_SCORE_TAG) + BEGIN_SCORE_TAG.length();

		boolean isHaveTageInsideScoreTag = false;
		for (int i = indexOfBeginScoreTag; i < indexOfBeginScoreTag + 5; i++) {
			if (march.charAt(i) == SPECIAL_TAG_IN_SCORE_TAG) {
				isHaveTageInsideScoreTag = true;
				break;
			}
		}
		String[] scores = new String[2];
		int startIndexOfScore;
		int endIndexOfScore;
		if (isHaveTageInsideScoreTag) {
			startIndexOfScore = march.indexOf(">", indexOfBeginScoreTag) + 1;
			endIndexOfScore = march.indexOf("<", startIndexOfScore);
		} else {
			startIndexOfScore = indexOfBeginScoreTag;
			endIndexOfScore = march.indexOf("</div>", indexOfBeginScoreTag);
		}
		scores = march.substring(startIndexOfScore, endIndexOfScore).split("-");

		try {
			this.competitor1Score = Integer.parseInt(scores[0].trim());
			this.competitor2Score = Integer.parseInt(scores[1].trim());
		} catch (NumberFormatException ex) {
		}

	}

	public String getCompetitor1() {
		return competitor1;
	}

	public void setCompetitor1(String competitor1) {
		this.competitor1 = competitor1;
	}

	public String getCompetitor2() {
		return competitor2;
	}

	public void setCompetitor2(String competitor2) {
		this.competitor2 = competitor2;
	}

	public Integer getCompetitor1Score() {
		return competitor1Score;
	}

	public void setCompetitor1Score(Integer competitor1Score) {
		this.competitor1Score = competitor1Score;
	}

	public Integer getCompetitor2Score() {
		return competitor2Score;
	}

	public void setCompetitor2Score(Integer competitor2Score) {
		this.competitor2Score = competitor2Score;
	}

}
