/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bflows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import unife.bundle.Bundle;
import unife.bundle.QueryResult;

/**
 *
 * @author Chiara Pelloni
 */
public class BundleWebController {
//private Bundle bundle;

    private final Bundle bundle;
    private ByteArrayOutputStream baos;
    private PrintStream ps;

    public BundleWebController() {
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);
        bundle = new Bundle();
        bundle.setOut(ps);
    }

    public String finish(QueryResult result) {
        bundle.finish();
        String out = getOutput(result);
        bundle.disposeBDDFactory();
        return out;
    }

    public String finish(QueryResult result, boolean clearPMap) {
        bundle.finish(clearPMap);
        String out = getOutput(result);
        bundle.disposeBDDFactory();
        return out;
    }

    public String getOutput(QueryResult result) {
        bundle.printResult(result);
        ps.flush();
        String output = baos.toString();
        output = output.replace("\n", "<br/>");
        output = output.replace(" ", "&nbsp;");
        return output;
    }

    public Bundle getBundle() {
        return bundle;
    }
}
