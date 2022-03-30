package hac;

import java.util.ArrayList;

public class Poll {
    String question;
    ArrayList<Answer> answers = new ArrayList<>();

    public Poll(String otherQuestion){
        question = otherQuestion;
    }

    public String getQuestion(){
        return question;
    }
    public void addAnswer(String answer){ // no duplicates?
        answers.add(new Answer(answer));
    }
    public void voteTo(String answerToVote){
        for (Answer answer : answers){
            if (answer.getAnswer().equals(answerToVote)){
                answer.vote();
                break;
            }
        }
    }

    public ArrayList<Answer> getAnswers(){
        return answers;
    }

}
