package parsing;


import utils.Utils;

import java.util.List;

public class GrammarModel {

    public static final String[] illegalChar = {"~", "\\"};
    public static final List<String> illegalTypeChar = Utils.concatArraysToList(illegalChar,
            new String[]{"(", ")"});

    public static final String MODEL_TAG = "MODEL";
    public static final String SUBCLASSES_TAG = "SUBCLASSES";
    public static final String CONTAINER_TAG = "CONTAINER";
    public static final String ROLES_TAG = "ROLES";
    public static final String PARTS_TAG = "PARTS";

    public static class Decs {
        public static final String CLASS = "CLASS";
        public static final String GENERALIZATION = "GENERALIZATION";
        public static final String ASSOCIATION = "RELATION";
        public static final String AGGREGATION = "AGGREGATION";
    }

    public static class ClassContent {
        public static final String ATTRIBUTES = "ATTRIBUTES";
        public static final String OPERATIONS = "OPERATIONS";
    }

    public static class Mult {
        public static final String ONE = "ONE";
        public static final String MANY = "MANY";
        public static final String ONE_OR_MANY = "ONE_OR_MANY";
        public static final String OPTIONALLY_ONE = "OPTIONALLY_ONE";
        public static final String UNDEFINED = "UNDEFINED";
    }



}
