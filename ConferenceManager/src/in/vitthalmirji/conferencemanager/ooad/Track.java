package in.vitthalmirji.conferencemanager.ooad;

import java.util.ArrayList;
import java.util.List;

public class Track {
	
	private List<Session> sessionList = new ArrayList<Session>();
	
	public void addSessionToTrack(Session session) {
		sessionList.add(session);
	}
	
	public List<Session> getSessionsInTrack() {
		return sessionList;
	}

}
