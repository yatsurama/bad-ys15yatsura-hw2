package ua.yandex.shad.tries;

public class Tuple {
    public final String term;
    public final int weight;

    public int getWeight() {
        return weight;
    }
    
    public String getTerm() {
        return term;
    }
    
    public Tuple() {
        term = "";
        weight = 0;
    }
    
    public Tuple(String term, int weight) {
        this.term = term;
        this.weight = weight;
    }  
    
    @Override 
    public boolean equals(Object obj) {
        if (obj == null) {
                return false;
        }
        if(!(obj instanceof Tuple)) {
            return false;
        }    
        Tuple other = (Tuple) obj;
        return (term.equals(other.term) && weight == other.weight);   
    }
    
    @Override
    public int hashCode() {
        return 42;
    }
}
