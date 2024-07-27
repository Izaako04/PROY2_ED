package GameLogic;

/**
 *
 * @author Grupo 9
 */

public class Question {
    private String questionStatement;
    private Double entropy;
    
    public Question (String questionStatement) {
        this.questionStatement = questionStatement;
        entropy = 0.0;
    }
    
    public String getStatement () {
        return questionStatement;
    }
    
    public double getEntropy () {
        return entropy;
    }
    
    public void setEntropy (double entropy) {
        this.entropy = entropy;
    }
    
    @Override
    public String toString () {
        return questionStatement;
    }
}