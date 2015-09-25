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

		String beginMatchTag = "<div class=\"ply tright name\">";
		String endMatchTag = "<div class=\"star hidden\" data-type=\"star\">";
		boolean isAnyMatch = true;
		int indexStart = -1;
		int indexEnd = -1;
		List<String> matches = new ArrayList<String>();
		String match = "";
		while (isAnyMatch) {
			isAnyMatch = false;
			indexStart = htmlContent.indexOf(beginMatchTag);
			indexEnd = htmlContent.indexOf(endMatchTag);
			if (indexStart > 0 && indexEnd > 0) {
				isAnyMatch = true;
				match = htmlContent.substring(indexStart, indexEnd);
				matches.add(match);
				htmlContent = htmlContent.substring(indexEnd + endMatchTag.length());
			}
		}
		String march = matches.get(0);
		

	}
}
