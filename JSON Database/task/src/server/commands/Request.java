package server.commands;

import com.beust.jcommander.*;

import java.util.ArrayList;
import java.util.List;


public class Request {

    @Parameter(names = {"-t"}, description = "Type of request")
    private String type;
    @Parameter(names = {"-k"}, description = "Key")
    private String key;
    @Parameter(names = {"-v"}, description = "Value to save", variableArity = true)
    private transient List<String> values = new ArrayList<>();
    private String value = null;

    public void valuesListToString() {
        value = String.join(" ", values);
    }

    public void nullValue() {
        value = null;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


}
