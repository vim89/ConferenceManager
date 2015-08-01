package in.vitthalmirji.conferencemanager.ooad;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.naming.LimitExceededException;
import javax.swing.text.html.MinimalHTMLWriter;

public abstract class Session {
		
	private List<Talk> talksList;
	
	public abstract List<Talk> prepareSession(List<Talk> preparedTalksListFull);

	public List<Talk> getTalksList() {
		return talksList;
	}

	public void setTalksList(List<Talk> talksList) {
		this.talksList = talksList;
	}

}

class MorningSession extends Session
{
	public List<Talk> prepareSession(List<Talk> preparedTalksListFull)
	{
		List<Talk> onlyMorningTalks = new ArrayList<Talk>();
		int totalTime = 0;
		Date date = new Date(2015,7, 1, 9, 0);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
        GetAllSubsetByStack get = new GetAllSubsetByStack();
        get.populateSubset(preparedTalksListFull, 0, preparedTalksListFull.size());
        onlyMorningTalks = get.getOneOfPossibleCombination();
        
        for(Talk talk : onlyMorningTalks)
        {
        	talk.setScheduled(true);
        	talk.setScheduledTime(cal.getTime());
        	cal.add(Calendar.MINUTE, talk.getTimeDuration());
        	preparedTalksListFull.remove(talk);
        }
        
        setTalksList(onlyMorningTalks);
		return onlyMorningTalks;
	}
}

class EveningSession extends Session
{	
	public List<Talk> prepareSession(List<Talk> preparedTalksListFull)
	{
		List<Talk> onlyEveningTalks = new ArrayList<Talk>();
		int totalTime = 0;
		Date date = new Date(2015,7, 1, 13, 0);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
        GetAllSubsetByStack get = new GetAllSubsetByStack();
        get.populateSubsetEvening(preparedTalksListFull, 0, preparedTalksListFull.size());
        onlyEveningTalks = get.getOneOfPossibleCombination();
        
        for(Talk talk : onlyEveningTalks)
        {
        	talk.setScheduled(true);
        	talk.setScheduledTime(cal.getTime());
        	cal.add(Calendar.MINUTE, talk.getTimeDuration());
        	preparedTalksListFull.remove(talk);
        }
        
        setTalksList(onlyEveningTalks);
		return onlyEveningTalks;
	}
}

class GetAllSubsetByStack {

	public static final int MOR_TARGET_SUM = 180;
    public static final int EVE_TARGET_SUM = 240;
	private static int eveningLimit = 0;



    private Stack<Talk> stack = new Stack<Talk>();
    private List<List<Talk>> possibleCombinationsList = new ArrayList<List<Talk>>();
    
    public List<Talk> getOneOfPossibleCombination() {
    	
    	Random r = new Random();
    	return possibleCombinationsList.get(0);
    }

    private int sumInStack = 0, prevTime = 0;
    
    public int getPossibleCombinationsCount() {
    	return possibleCombinationsList.size();
    }
    
    public void populateSubsetEvening(List<Talk> data, int fromIndex, int endIndex) {

        /*
        * Check if sum of elements stored in Stack is equal to the expected
        * target sum.
        * 
        * If so, call print method to print the candidate satisfied result.
        */
    	
    	
    	//if (sumInStack>MOR_TARGET_SUM && sumInStack <= EVE_TARGET_SUM) {
    		//possibleCombinationsList.add(print(stack));
        //}

        for (int currentIndex = fromIndex; currentIndex < endIndex; currentIndex++) {
        	
        	if(data.get(currentIndex).isScheduled())
        		continue;
        	
        	prevTime = sumInStack;
        	sumInStack += data.get(currentIndex).getTimeDuration();
        	
        	if(sumInStack>EVE_TARGET_SUM) 
        		sumInStack = prevTime;
        	else
        		stack.push(data.get(currentIndex));
        	
            populateSubsetEvening(data, currentIndex + 1, endIndex);
            //sumInStack -= (Integer) stack.pop().getTimeDuration();

           /* if (sumInStack + data.get(currentIndex).getTimeDuration() <= EVE_TARGET_SUM) {
                stack.push(data.get(currentIndex));
                prevTime = sumInStack;
                sumInStack += data.get(currentIndex).getTimeDuration();
                

                /*
                * Make the currentIndex +1, and then use recursion to proceed
                * further.
                
                

                
            } */
        }        
        
        possibleCombinationsList.add(print(stack));
    }
    
    

    public void populateSubset(List<Talk> data, int fromIndex, int endIndex) {

        /*
        * Check if sum of elements stored in Stack is equal to the expected
        * target sum.
        * 
        * If so, call print method to print the candidate satisfied result.
        */
        if (sumInStack == MOR_TARGET_SUM) {
            possibleCombinationsList.add(print(stack));
        }

        for (int currentIndex = fromIndex; currentIndex < endIndex; currentIndex++) {
        	
        	if(data.get(currentIndex).isScheduled())
        		continue;

            if (sumInStack + data.get(currentIndex).getTimeDuration() <= MOR_TARGET_SUM) {
                stack.push(data.get(currentIndex));
                sumInStack += data.get(currentIndex).getTimeDuration();

                /*
                * Make the currentIndex +1, and then use recursion to proceed
                * further.
                */
                populateSubset(data, currentIndex + 1, endIndex);
                sumInStack -= (Integer) stack.pop().getTimeDuration();
            }
        }
        
    }

    /**
    * Print satisfied result. i.e. 15 = 4+6+5
     * @return 
    */

    private List<Talk> print(Stack<Talk> stack) {
    	List<Talk> talkList = new ArrayList<Talk>();
        for (Talk i : stack) {
            talkList.add(i);
        }
        
        return talkList;
    }
}