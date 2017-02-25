package biz.netcentric.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * The UnknownDispatcher is fired as fallback in all cases, a command request is unknown to the Servlet
 * @author sebas.monsalve@gmailcom
 *
 */
public class UnknownDispatcher extends Dispatcher {

	@Override
	public void process() throws ServletException, IOException {
		forward("unknown.jsp");
	}

}
