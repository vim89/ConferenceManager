package in.vitthalmirji.conferencemanager.ooad;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Manager {
	
	private List<String> proposalList;
	List<Talk> prepareTalkList;
	private List<Track> trackList = new ArrayList<Track>();
	private List<List<Talk>> eveningList = new ArrayList<List<Talk>>();
	
	private int totalTracks;
	
	public static int totalTalkTime = 0;
	
	public void addTrack(Track track) {
		this.trackList.add(track);
	}
	
	public List<Track> getTracks() {
		return this.trackList;
	}

	public int getTotalTracks() {
		return totalTracks;
	}

	public void setTotalTracks(int totalTracks) {
		this.totalTracks = totalTracks;
	}
	
	public List<String> getProposalsList(String fileName) throws IOException {
        List<String> talkList = new ArrayList<String>();
        DataInputStream dataInputStream = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
            String strLine = bufferedReader.readLine();
            while (strLine != null) {
                talkList.add(strLine);
                strLine = bufferedReader.readLine();
            }
        } finally {
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }
        }

        return talkList;
	}

	public List<String> getProposalList() {
		return proposalList;
	}

	public void setProposalList(List<String> proposalList) {
		this.proposalList = proposalList;
	}
	
	public List<Talk> prepareTalkList(List<String> proposalList)
	{
		List<Talk> preparedTalks = new ArrayList<Talk>();
		int timeDuration;
		String title;
		for(String s : proposalList) {
			if(s.endsWith("min"))
				timeDuration = Integer.parseInt(s.substring(s.lastIndexOf(" ")+1).replaceAll("\\D+", "").trim());
			else if(s.endsWith("lightning"))
				timeDuration = 5;
			else
				timeDuration = -1;
			
			Talk talk = new Talk();
			talk.setTitle(s.substring(0, s.lastIndexOf(" ")).trim());
			talk.setScheduled(false);
			talk.setTimeDuration(timeDuration);
			talk.setScheduledTime(null);
			preparedTalks.add(talk);
		}
		
		this.prepareTalkList = preparedTalks;
		return preparedTalks;
		
	}
	
	public int getTotalTalkTime() {
		int totalTime = 0;
		
		for(Talk talk : prepareTalkList)
			totalTime += talk.getTimeDuration();
		
		totalTalkTime = totalTime;
		return totalTime;
	}
	
	
	public void prepareTrackList() {
		Collections.sort(prepareTalkList);
		Track track = new Track();
		
		Session s1 = new MorningSession();
		s1.prepareSession(prepareTalkList);
		track.addSessionToTrack(s1);
		
		Session s2 = new EveningSession();
		eveningList.add(s2.prepareSession(prepareTalkList));
		track.addSessionToTrack(s2);
		
		
		trackList.add(track);
		
		
	}
	
	public void printTrack() throws Exception
	{
		
		//if(!prepareTalkList.isEmpty())
			//throw new Exception("Unable to Schedule, Try again for tight / best fit");
		
		int i=1;
		int time = 0;
		Date d = null;
		for(Track track : trackList) {
			System.out.println("Track "+i);
			for(Session session : track.getSessionsInTrack()) {
				for(Talk talk : session.getTalksList()) {
					System.out.println(talk);
					time = talk.getTimeDuration();
					d = talk.getScheduledTime();
				}
				
				if(d.getHours()<=12)
					System.out.println("12:00 PM\tLunch");
				else if(d.getHours()>=17)
					System.out.println("05:00 PM\tNetworking Event");
				else {
					Calendar cal = Calendar.getInstance();
					cal.setTime(d);
					cal.add(Calendar.MINUTE, time);
					if(cal.getTime().getHours()<16)
						cal.setTime(new Date(2015,7,1,16,0));
					System.out.println(new SimpleDateFormat("hh:mm a").format(cal.getTime())+"\tNetworking Event");
				}
			}
			i++;
		}
		
		//System.out.println(prepareTalkList.isEmpty());
		
	}
		
}
