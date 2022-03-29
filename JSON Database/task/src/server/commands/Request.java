package server.commands;

import com.beust.jcommander.*;

import java.util.ArrayList;
import java.util.List;


public class Request {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"-t"}, description = "Type of request")
    private String type;

    @Parameter(names = {"-i"}, description = "Index")
    private int index;

    @Parameter(names = {"-m"}, description = "Value to save", variableArity = true)
    private ArrayList<String> values = new ArrayList<>();


    public List<String> getParameters() {
        return parameters;
    }

    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return String.join(" ",values);
    }


}
