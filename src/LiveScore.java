import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author approved
 *
 */
public class LiveScore implements Runnable {
	public static void main(String[] args) throws Exception {

		ScheduledExecutorService executors = Executors.newScheduledThreadPool(1);
		LiveScore liveScore = new LiveScore();
		executors.scheduleWithFixedDelay(liveScore, 1, 4, TimeUnit.SECONDS);

	}

	@Override
	public void run() {
		try {
			StringBuffer pageHTML = this.readPage();
			String contentHTML = this.getContentHTML(pageHTML);
			List<String> matchesInHTML = this.getMatchesHTML(contentHTML);

			List<FootBall> footBalls = new ArrayList<FootBall>();
			for (String matchHTML : matchesInHTML) {
				FootBall fb = new FootBall(matchHTML);
				System.out.println(fb.toString());
				footBalls.add(fb);
			}

			List<Match> matchesList = new ArrayList<Match>();
			Match match1 = new Match();
			match1.setCompetitor1(new Competitor("ABC"));
			match1.setCompetitor2(new Competitor("BCD"));

			Match match2 = new Match();
			match2.setCompetitor1(new Competitor("Portugalete"));
			match2.setCompetitor2(new Competitor("Fuenlabrada"));

			matchesList.add(match1);
			matchesList.add(match2);

			for (int i = 0; i < 15; i++) {
				matchesList.addAll(matchesList);
			}

			System.out.println(matchesList.size());

			footBalls
					.stream()
					.filter(fb -> fb.getCompetitor1Score() != null)
					.forEach(
							fb -> {
								matchesList.forEach(match -> {
									if (match.getCompetitor1().getName().equals(fb.getCompetitor1())
											&& match.getCompetitor2().getName().equals(fb.getCompetitor2())) {

										match.setCompetitor1Score(fb.getCompetitor1Score());
										match.setCompetitor2Score(fb.getCompetitor2Score());
									}
								});
							});

			matchesList.stream().filter(match -> match.getCompetitor1Score() != null).forEach(match -> {
				System.out.println(match.toString());
			});

		} catch (IOException e) {
		}

	}

	private StringBuffer readPage() throws IOException {
		String link = "http://www.livescore.com/soccer/live/";
		URL live = new URL(link);
		URLConnection yc = live.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

		StringBuffer pageHTML = new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			pageHTML.append(inputLine);
		}
		in.close();
		return pageHTML;
	}

	private String getContentHTML(StringBuffer pageHTML) {
		int startContentTagLength = "<div class=\"content\">".length();
		int endContentTagLength = "</div>".length() + 1;
		int indexOfContentTag = pageHTML.indexOf("<div class=\"content\">");
		int indexOfContentTagEnd = pageHTML.indexOf("<div class=\"right-bar\">");

		String htmlContent = pageHTML.substring(indexOfContentTag + startContentTagLength, indexOfContentTagEnd
				- endContentTagLength);
		return htmlContent;
	}

	private List<String> getMatchesHTML(String htmlContent) {
		final String BEGIN_MATCH_TAG = "<div class=\"ply tright name\">";
		final String END_MATCH_TAG = "<div class=\"star hidden\" data-type=\"star\">";

		boolean isAnyMatch = true;
		int indexStart = -1;
		int indexEnd = -1;
		List<String> matchesInHTML = new ArrayList<String>();
		while (isAnyMatch) {
			String match = "";
			isAnyMatch = false;
			indexStart = htmlContent.indexOf(BEGIN_MATCH_TAG);
			indexEnd = htmlContent.indexOf(END_MATCH_TAG);
			if (indexStart > 0 && indexEnd > 0) {
				isAnyMatch = true;
				match = htmlContent.substring(indexStart, indexEnd);
				matchesInHTML.add(match);
				htmlContent = htmlContent.substring(indexEnd + END_MATCH_TAG.length());
			}
		}
		return matchesInHTML;
	}

}
