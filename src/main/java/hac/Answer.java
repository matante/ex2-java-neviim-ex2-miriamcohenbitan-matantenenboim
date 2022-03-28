package hac;

public class Answer {
    private String answer;
    private int votes;

    Answer(String otherAnswer){
        answer = otherAnswer.length() > 0 ? otherAnswer : "";
        votes = 0;
    }

    public String getAnswer(){
        return answer;
    }

    public void vote(){
        votes ++;
    }
    public int getVotes(){
        return votes;
    }

}
