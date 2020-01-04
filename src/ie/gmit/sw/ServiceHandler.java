package ie.gmit.sw;

import ie.gmit.sw.lang_detector_system.LangDetectionSystem;
import ie.gmit.sw.lang_dist.LangDistStore;
import ie.gmit.sw.lang_dist.LangDistStoreBuilder;
import ie.gmit.sw.sample_parser.FileSampleParser;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/*
 * To compile this servlet, open a command prompt in the web application directory and execute the following commands:
 *
 * Linux/Mac																	Windows
 * ---------																	---------
 * cd WEB-INF/classes/															cd WEB-INF\classes\
 * javac -cp .:$TOMCAT_HOME/lib/servlet-api.jar ie/gmit/sw/*.java				javac -cp .:%TOMCAT_HOME%/lib/servlet-api.jar ie/gmit/sw/*.java
 * cd ../../																	cd ..\..\
 * jar -cf ngrams.war *															jar -cf ngrams.war *
 *
 * Drag and drop the file ngrams.war into the webapps directory of Tomcat to deploy the application. It will then be
 * accessible from http://localhost:8080.
 *
 * NOTE: the text file containing the 253 different languages needs to be placed in /data/wili-2018-Edited.txt. This means
 * that you must have a "data" directory in the root of your file system that contains a file called "wili-2018-Edited.txt".
 * Do NOT submit the wili-2018 text file with your assignment!
 *
*/

/**
 * Servlet for the asynchronous language detection system.
 */
public class ServiceHandler extends HttpServlet {
	private String languageDataSet = null; //This variable is shared by all HTTP requests for the servlet
	private static long jobNumber = 0; //The number of the task in the async queue

	private File f;

	private LangDetectionSystem langDetectionSystem;

	/**
	 * Initialises servlet and language detection system.
	 */
	public void init() {
		ServletContext ctx = getServletContext(); //Get a handle on the application context
		languageDataSet = ctx.getInitParameter("LANGUAGE_DATA_SET"); //Reads the value from the <context-param> in web.xml

		f = new File(languageDataSet);
		// TODO remove this line
		f = new File("/home/ronan/Downloads/apache-tomcat-9.0.30/bin/data/wili-2018-Edited.txt");

		// build k-mer distribution for all languages from language dataset
		LangDistStore distStore = new LangDistStoreBuilder()
			.withMappedStore(512, 3)
			.registerParser(
				new FileSampleParser(f)
			)
		.build();

		// create language detection system and start workers
		langDetectionSystem = new LangDetectionSystem(distStore, 50, 4);
		langDetectionSystem.go();
	}

	/**
	 * Responds to GET on this servlet by queueing, processing, and displaying the result of a language
	 * detection job.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html"); //Output the MIME type
		PrintWriter out = resp.getWriter(); //Write out text. We can write out binary too and change the MIME type...

		//Initialise some request variables with the submitted form info. These are local to this method and thread safe...
		String option = req.getParameter("cmbOptions"); //Change options to whatever you think adds value to your assignment...
		String s = req.getParameter("query");
		String taskNumber = req.getParameter("frmTaskNumber");

		out.print("<html><head><title>Advanced Object Oriented Software Development Assignment</title>");
		out.print("</head>");
		out.print("<body>");
		out.print("<div id=\"r\"></div>");

		out.print("<font color=\"#993333\"><b>");
		out.print("Language Dataset is located at " + languageDataSet + " and is <b><u>" + f.length() + "</u></b> bytes in size");
		out.print("<br>Option(s): " + option);
		out.print("<br>Query Text : " + s);
		out.print("</font></b><br>");

		if (taskNumber == null) {
			taskNumber = String.format("T%d", jobNumber++);
			langDetectionSystem.submitJob(taskNumber, s);
		}

		if (langDetectionSystem.isJobFinished(taskNumber)) {
			out.printf("<h2>STATUS: Job %s complete</h2>", taskNumber);
			out.printf("<h2>Detected language: <font color=\"#00ab00\">%s</font></h2>", langDetectionSystem.getLanguageResult(taskNumber));
		}
		else {
			out.printf("<h2>STATUS: Processing request for Job#: %s, please wait...</h2>", taskNumber);
			out.print("<script>");
			out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 5000);");
			out.print("</script>");
		}

		out.print("<form method=\"POST\" name=\"frmRequestDetails\">");
		out.print("<input name=\"cmbOptions\" type=\"hidden\" value=\"" + option + "\">");
		out.print("<input name=\"query\" type=\"hidden\" value=\"" + s + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");
		out.print("</body>");
		out.print("</html>");
	}

	/**
	 * Responds to a POST request on this servlet. Passes the request and response on to
	 * the GET method handler.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
 	}
}