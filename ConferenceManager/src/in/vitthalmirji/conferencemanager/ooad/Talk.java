package in.vitthalmirji.conferencemanager.ooad;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Talk implements Comparable {
	
	private String title;
	private int timeDuration;
	private boolean scheduled = false;
	private Date scheduledTime;
	private SimpleDateFormat sda = new SimpleDateFormat("hh:mm a");;
	
	private static int count = 0;
	
	public Talk() {
		//simply
		count = count + 1;
	}
	
	public static int getCount() {
		return count;
	}
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getTimeDuration() {
		return timeDuration;
	}


	public void setTimeDuration(int timeDuration) {
		this.timeDuration = timeDuration;
	}


	public boolean isScheduled() {
		return scheduled;
	}


	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}


	public Date getScheduledTime() {
		return scheduledTime;
	}


	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	
	@Override
	public int compareTo(Object obj) {
		// TODO Auto-generated method stub
		Talk talk = (Talk) obj;
		//return this.getTimeDuration() - talk.getTimeDuration(); //ascending order 
		return talk.getTimeDuration() - this.getTimeDuration(); //descending order
	}
	
	@Override
	public String toString() {
		return sda.format(this.getScheduledTime())+"\t"+this.getTitle()+"\t"+this.getTimeDuration();
	}
}
