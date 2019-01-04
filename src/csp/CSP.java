package csp;

import common.Info;

public class CSP {
    private final Info info;

    public CSP(Info info) {
        this.info = info;
    }

    public CSP(){
        this(Info.getFromInput());
    }
}
