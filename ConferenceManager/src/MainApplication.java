import in.vitthalmirji.conferencemanager.ooad.Manager;
import in.vitthalmirji.conferencemanager.ooad.Talk;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class MainApplication {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
        File[] files = new File("input/").listFiles();
        Manager manager;
        
        for(File file : files) {
        	
        	manager = new Manager();
	        List<String> proposalList = manager.getProposalsList(file.getAbsolutePath());
	        
	        manager.setProposalList(proposalList);
	        
	        List<Talk> preparedTalkList = manager.prepareTalkList(proposalList);
	        
	        
	        int days = (int) Math.ceil(manager.getTotalTalkTime() / 360 );
	        
	        //System.out.println((double)manager.getTotalTalkTime() / 420);
	        
	        //System.out.println(days);
	        //System.out.println(manager.getTotalTalkTime());
	        //System.out.println(days * 7 * 60);
	        //System.out.println(Manager.totalTalkTime/4);
	        
	        
	        manager.setTotalTracks(days);
	        
	        for(int i=0;i<days;i++)
	        	manager.prepareTrackList();
	        
	        System.out.println("--------------Output for Input File:"+file.getAbsolutePath()+"-----------");
	        
	        manager.printTrack();
	        
	        //System.out.println("-------------------------------------------------------------------------");
	        //System.out.println(Talk.getCount());
	        
	        manager = null;
	        System.gc();
	        
	        
        }
        

	}

}
