package mad.nil.midterm;

/**
 * Created by Nilanjan Mhatre
 * email: nmhatre@uncc.edu
 * Student Id: 801045013
 */

public enum Code {
    DEFAULT(0),
    GET_APPS(1),
    APPLICATION(2),
    FILTER(3),
    ERROR(-1);

    // GetContentAsyncTask.java

    //Intent passing

    private Integer id;
    Code(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return id;
    }

    public static Code getCodeById(Integer id) {
        if(id == null) {
            return null;
        }
        Code selectedCode = null;
        for(Code code : Code.values()) {
            if(code.getValue().intValue() == id.intValue()) {
                selectedCode = code;
                break;
            }
        }
        return selectedCode;
    }
}
