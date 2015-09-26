import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author approved
 *
 */
public class LiveScore {
	public static void main(String[] args) throws Exception {
		URL oracle = new URL("http://www.livescore.com/soccer/live/");
		URLConnection yc = oracle.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

		StringBuffer pageHTML = new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			pageHTML.append(inputLine);
		}
		in.close();

		int startContentTagLength = "<div class=\"content\">".length();
		int endContentTagLength = "</div>".length() + 1;
		int indexOfContentTag = pageHTML.indexOf("<div class=\"content\">");
		int indexOfContentTagEnd = pageHTML.indexOf("<div class=\"right-bar\">");

		String htmlContent = pageHTML.substring(indexOfContentTag + startContentTagLength, indexOfContentTagEnd
				- endContentTagLength);

		final String BEGIN_MATCH_TAG = "<div class=\"ply tright name\">";
		final String END_MATCH_TAG = "<div class=\"star hidden\" data-type=\"star\">";

		boolean isAnyMatch = true;
		int indexStart = -1;
		int indexEnd = -1;
		List<String> matches = new ArrayList<String>();
		while (isAnyMatch) {
			String match = "";
			isAnyMatch = false;
			indexStart = htmlContent.indexOf(BEGIN_MATCH_TAG);
			indexEnd = htmlContent.indexOf(END_MATCH_TAG);
			if (indexStart > 0 && indexEnd > 0) {
				isAnyMatch = true;
				match = htmlContent.substring(indexStart, indexEnd);
				matches.add(match);
				htmlContent = htmlContent.substring(indexEnd + END_MATCH_TAG.length());
			}
			System.out.println(match);
		}

		List<FootBall> footBalls = new ArrayList<FootBall>();
		for (String match : matches) {
			FootBall fb = new FootBall(match);
			System.out.println(fb.getCompetitor1() + " " + fb.getCompetitor1Score() + "----" + fb.getCompetitor2()
					+ " " + fb.getCompetitor2Score());
			footBalls.add(fb);
		}

	}
}
