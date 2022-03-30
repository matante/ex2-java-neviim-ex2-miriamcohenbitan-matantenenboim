package hac;

public class Answer {
    private String answer;
    private int votes;

    Answer(String otherAnswer){
        answer = otherAnswer;
        votes = 0;
    }

    public String getAnswer(){
        return answer;
    }

    public synchronized void vote(){
        votes ++;
    }
    public int getVotes(){
        return votes;
    }

}
