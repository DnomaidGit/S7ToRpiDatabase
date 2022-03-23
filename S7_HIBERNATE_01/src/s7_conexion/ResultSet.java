package s7_conexion;


public class ResultSet {

    private int errorState, numResults;
    public Result[] results;

    public void setErrorState(int error) {
        errorState = error;
    }

    public int getErrorState() {
        return errorState;
    }

    ;

	public void setNumResults(int nr) {
        numResults = nr;
    }

    public int getNumResults() {
        return numResults;
    }
;
}
