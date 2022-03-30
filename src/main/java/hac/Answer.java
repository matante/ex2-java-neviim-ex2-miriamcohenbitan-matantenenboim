package hac;

public class Answer {
    private String answer;
    private int votes;

    public Answer(String otherAnswer) {
        answer = otherAnswer;
        votes = 0;
    }

    public String getAnswer() {
        return answer;
    }

    public  void vote() {
        synchronized(this) {
            votes++;
        }
    }

    public int getVotes() {
        return votes;
    }
}
