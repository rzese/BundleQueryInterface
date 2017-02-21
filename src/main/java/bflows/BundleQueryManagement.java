package bflows;

import java.util.*;
import blogics.*;
import com.clarkparsia.owlapiv3.OWL;
import unife.bundle.*;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import static net.sf.javabdd.BDDFactory.getProperty;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLStorerFactory;
import org.semanticweb.owlapi.util.OWLStorerFactoryImpl;
import unife.bundle.bdd.BDDFactory2;
import unife.bundle.bdd.LoadNativeLibrary;
import unife.bundle.utilities.BundleUtilities;

/**
 *
 * @author chiara
 */
public class BundleQueryManagement {

    private String ontology; //parametro ontologia che viene rimandato ogni volta

    private OWLOntology rootOntology;

    private int classa;

    private int classb;

    private int indi;

    private int inds;

    private String typeProp;

    private int prop;

    private String constant;

    private String typeConstant;

    private String querySel;

    private Query qSelected;

    //liste per l'interfaccia
    private ArrayList<String> classes;

    private ArrayList<String> individuals;

    private ArrayList<String> dataprops;

    private ArrayList<String> objprops;

    private ArrayList<Query> queries;

//liste con oggetti OWL
    private ArrayList<OWLClass> oclasses;

    private ArrayList<OWLNamedIndividual> oindividuals;

    private ArrayList<OWLDataProperty> odataprops;

    private ArrayList<OWLObjectProperty> oobjprops;

    private String status;

    private String result;

    public BundleQueryManagement() {
    }

    public void BundleQueryManagementView() {//gestione restituzione OWLOntology

        File file;
        BufferedWriter bw = null;
        try {

            file = File.createTempFile("onto", ".owl");
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(ontology);
//                bw.write("pippo baudo");
            ontology = StringEscapeUtils.escapeHtml(ontology);
            bw.flush();
            bw.close();
            String path = file.getAbsolutePath().replace("\\", "/");
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            rootOntology = manager.loadOntologyFromOntologyDocument(IRI.create("file://" + path));
            file.delete();
//            file = File.createTempFile("currDir", ".txt");
//            fw = new FileWriter(file);
//            bw = new BufferedWriter(fw);
//            bw.write(System.getProperty("user.dir"));
//            bw.close();
//            String currDir = getProperty("user.dir", ".") + getProperty("file.separator", "/");
//            String pack = "/bdd-libraries/lib";
//            URL f = BDDFactory2.class.getResource(pack);
//            String libraryDir = f.getFile();
//            file = File.createTempFile("infos", ".txt");
//            fw = new FileWriter(file);
//            bw = new BufferedWriter(fw);
//            bw.write("curDir: " + currDir + "\n");
//            bw.write("curDir2: " + System.getProperty("user.dir") + "\n");
//            bw.write("libraryDir: " + libraryDir + "\n");
//
//            String jarPath = libraryDir.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
//            bw.write("JarPath: " + jarPath + "\n");
//            JarFile jarFile = new JarFile(jarPath);
//            Enumeration<JarEntry> entries = jarFile.entries();
//            while (entries.hasMoreElements()) {
//                JarEntry entry = entries.nextElement();
//                String entryName = "/" + entry.getName();
//                bw.write("Jar entry: " + entry.getName() + "\n");
//                bw.flush();
//                String ext;
//                if (System.getProperty("os.name").startsWith("Windows")) {
//                    ext = ".dll";
//                } else if (System.getProperty("os.name").startsWith("Linux")) {
//                    ext = ".so";
//                } else if (System.getProperty("os.name").startsWith("Mac OS X")) {
//                    ext = ".jnilib";
//                } else {
//                    throw new RuntimeException("Unknown Operating System");
//                }
//                if (entryName.startsWith(pack)
//                        && entryName.length() > (pack.length() + "/".length())
//                        && entryName.substring(entryName.length() - ext.length()).equals(ext)) {
//                    bw.write("entryName: " + entryName + " starts with " + pack + "\n");
//                    bw.flush();
////                    String libFile = LoadNativeLibrary.createTempFile(entryName, currDir).getName();
//                    String libFile = createTempFile(entryName, currDir, bw).getName();
//                    File createdLibFile = new File(libFile);
//                    if (!createdLibFile.exists()) {
//                        bw.write("Library file: " + libFile + " does not exist");
//                    }
//                }
//            }
//            bw.close();
        } catch (IOException e) {  
            e.printStackTrace();
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        } 
        
        createQuery();

        if (querySel != null) {
            int i = 0;
            while (!querySel.equals(queries.get(i).getDescription())) {
                i++;
            }

            qSelected = queries.get(i);

            oclasses = new ArrayList<OWLClass>();
            classes = new ArrayList<String>();

            if (qSelected.getNeedClass() != 0) { //crea lista classi sia di Stringhe che OWL
                if (rootOntology != null) {
                    for (OWLClass a : rootOntology.getClassesInSignature()) {
                        oclasses.add(a);
                        classes.add(BundleUtilities.getManchesterSyntaxString(a));
                    }
                }
            }

            oindividuals = new ArrayList<OWLNamedIndividual>();
            individuals = new ArrayList<String>();

            if (qSelected.getNeedIndividual() != 0) {//crea lista individuals sia di Stringhe che OWL
                for (Iterator<OWLNamedIndividual> it = rootOntology.getIndividualsInSignature().iterator(); it.hasNext();) {
                    OWLNamedIndividual a = it.next();
                    oindividuals.add(a);
                    individuals.add(BundleUtilities.getManchesterSyntaxString(a));
                }
            }

            odataprops = new ArrayList<OWLDataProperty>();
            dataprops = new ArrayList<String>();
            oobjprops = new ArrayList<OWLObjectProperty>();
            objprops = new ArrayList<String>();

            if (qSelected.getNeedProperty()) {//crea lista property (--> 2liste divise per data e object)sia di Stringhe che OWL
                for (Iterator<OWLDataProperty> it = rootOntology.getDataPropertiesInSignature().iterator(); it.hasNext();) {
                    OWLDataProperty a = it.next();
                    odataprops.add(a);
                    dataprops.add(BundleUtilities.getManchesterSyntaxString(a));
                }
                for (Iterator<OWLObjectProperty> it = rootOntology.getObjectPropertiesInSignature().iterator(); it.hasNext();) {
                    OWLObjectProperty a = it.next();
                    oobjprops.add(a);
                    objprops.add(BundleUtilities.getManchesterSyntaxString(a));
                }
            }
        }

    }

    public void BundleQueryManagementExecute() {
        File file;
        try {
            file = File.createTempFile("onto", ".owl");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(StringEscapeUtils.unescapeHtml(ontology));
            bw.flush();
            bw.close();
            String path = file.getAbsolutePath().replace("\\", "/");
            rootOntology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(IRI.create("file:///" + path));
            file.delete();
            ontology = StringEscapeUtils.escapeHtml(ontology);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }

        oclasses = new ArrayList<OWLClass>();
        classes = new ArrayList<String>();

        createQuery();

        int i = 0;
        if (querySel != null) {
            while (!querySel.equals(queries.get(i).getDescription())) {
                i++;
            }
        }
        qSelected = queries.get(i);

        if (qSelected.getNeedClass() != 0) {//crea lista classi sia di Stringhe che OWL
            Iterator<OWLClass> it = null;
            for (it = rootOntology.getClassesInSignature().iterator(); it.hasNext();) {
                OWLClass a = it.next();
                oclasses.add(a);
                classes.add(BundleUtilities.getManchesterSyntaxString(a));
            }
        }

        oindividuals = new ArrayList<OWLNamedIndividual>();
        individuals = new ArrayList<String>();

        if (qSelected.getNeedIndividual() != 0) {//crea lista individuals sia di Stringhe che OWL
            for (Iterator<OWLNamedIndividual> it = rootOntology.getIndividualsInSignature().iterator(); it.hasNext();) {
                OWLNamedIndividual a = it.next();
                oindividuals.add(a);
                individuals.add(BundleUtilities.getManchesterSyntaxString(a));
            }
        }

        odataprops = new ArrayList<OWLDataProperty>();
        dataprops = new ArrayList<String>();
        oobjprops = new ArrayList<OWLObjectProperty>();
        objprops = new ArrayList<String>();

        if (qSelected.getNeedProperty()) {//crea lista property (--> 2liste divise per data e object)sia di Stringhe che OWL
            for (Iterator<OWLDataProperty> it = rootOntology.getDataPropertiesInSignature().iterator(); it.hasNext();) {
                OWLDataProperty a = it.next();
                odataprops.add(a);
                dataprops.add(BundleUtilities.getManchesterSyntaxString(a));
            }
            for (Iterator<OWLObjectProperty> it = rootOntology.getObjectPropertiesInSignature().iterator(); it.hasNext();) {
                OWLObjectProperty a = it.next();
                oobjprops.add(a);
                objprops.add(BundleUtilities.getManchesterSyntaxString(a));
            }
        }

        BundleWebController bwc = new BundleWebController();

        QueryResult q = new QueryResult();

        Bundle bundle = bwc.getBundle();

        bundle.loadOntologies(rootOntology);

//        bundle.setPMap();
//        bundle.setBddFType(BDDFactoryType.J);
        bundle.init();

        OWLDataFactory factory = rootOntology.getOWLOntologyManager().getOWLDataFactory();

        try {
            if (qSelected.getCommand().equals("unsat")) {
                q = bundle.computeQuery(factory.getOWLSubClassOfAxiom((OWLClassExpression) oclasses.get(classa), OWL.Nothing));
            }

            if (qSelected.getCommand().equals("all-unsat")) {
                q = bundle.computeQuery();
            }

            if (qSelected.getCommand().equals("hierarchy")) {
                q = bundle.computeQuery();
            }

            if (qSelected.getCommand().equals("instance")) {
                q = bundle.computeQuery(factory.getOWLClassAssertionAxiom((OWLClassExpression) oclasses.get(classa), (OWLIndividual) oindividuals.get(indi)));
            }

            if (qSelected.getCommand().equals("subclass")) {
                q = bundle.computeQuery(factory.getOWLSubClassOfAxiom((OWLClassExpression) oclasses.get(classa), (OWLClassExpression) oclasses.get(classb)));
            }

            if (qSelected.getCommand().equals("property")) {
                if (typeProp.equals("Object")) {
                    q = bundle.computeQuery(factory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression) oobjprops.get(prop), (OWLIndividual) oindividuals.get(indi), (OWLIndividual) oindividuals.get(inds)));
                }
                if (typeProp.equals("Data")) {
                    int constI;
                    float constF;
                    double constD;
                    boolean constB;

                    if (typeConstant.equals("str")) {
                        q = bundle.computeQuery(factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression) odataprops.get(prop), (OWLIndividual) oindividuals.get(indi), constant));
                    }
                    if (typeConstant.equals("inte")) {
                        constI = Integer.getInteger(constant);
                        q = bundle.computeQuery(factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression) odataprops.get(prop), (OWLIndividual) oindividuals.get(indi), constI));
                    }
                    if (typeConstant.equals("floa")) {
                        constF = Float.valueOf(constant);
                        q = bundle.computeQuery(factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression) odataprops.get(prop), (OWLIndividual) oindividuals.get(indi), constF));
                    }
                    if (typeConstant.equals("bool")) {
                        constB = Boolean.valueOf(constant);
                        q = bundle.computeQuery(factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression) odataprops.get(prop), (OWLIndividual) oindividuals.get(indi), constB));
                    }
                    if (typeConstant.equals("doub")) {
                        constD = Double.valueOf(constant);
                        q = bundle.computeQuery(factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression) odataprops.get(prop), (OWLIndividual) oindividuals.get(indi), constD));
                    }
                }
            }

            if (qSelected.getCommand().equals("inconsistent")) {
                q = bundle.computeQuery(factory.getOWLSubClassOfAxiom(OWL.Thing, OWL.Nothing));
            }

            result = bwc.finish(q);
            bundle.disposeBDDFactory();

        } catch (OWLException e) {
            e.printStackTrace();
        }
    }

    private void createQuery() {
        Query q1 = new Query("unsat", "Explain why A class is unsatisfable", 1, 0, false);
        Query q2 = new Query("all-unsat", "Explain all unsatisfable class", 0, 0, false);
        Query q3 = new Query("inconsistent", "Explain why the ontology is inconsistent", 0, 0, false);
        Query q4 = new Query("hierarchy", "Print all explanations for the class hierarchy", 1, 0, false);
        Query q5 = new Query("subclass", "Explain why A class is a subClass of B class", 2, 0, false);
        Query q6 = new Query("instance", "Explain why I individual is an instance of A class", 1, 1, false);
        Query q7 = new Query("property", "Explain why I individual has value S for property P", 0, 1, true);

        queries = new ArrayList<Query>();
        queries.add(q1);
        queries.add(q2);
        queries.add(q3);
        queries.add(q4);
        queries.add(q5);
        queries.add(q6);
        queries.add(q7);

    }

    public int getClassa() {
        return this.classa;

    }

    public void setClassa(int classa) {
        this.classa = classa;

    }

    public int getClassb() {
        return this.classb;

    }

    public void setClassb(int classb) {
        this.classb = classb;

    }

    public int getInds() {
        return this.inds;

    }

    public void setInds(int inds) {
        this.inds = inds;

    }

    public int getIndi() {
        return this.indi;

    }

    public void setIndi(int indi) {
        this.indi = indi;

    }

    public int getProp() {
        return this.prop;

    }

    public void setProp(int prop) {
        this.prop = prop;

    }

    public String getTypeProp() {
        return this.typeProp;

    }

    public void setTypeProp(String typeprop) {
        this.typeProp = typeprop;

    }

    public String getTypeConstant() {
        return this.typeConstant;

    }

    public void setTypeConstant(String typeconst) {
        this.typeConstant = typeconst;

    }

    public String getConstant() {
        return this.constant;

    }

    public void setConstant(String constant) {
        this.constant = constant;

    }

    public String getQuerySel() {
        return this.querySel;

    }

    public void setQuerySel(String querysel) {
        this.querySel = querysel;

    }

    //liste stringhe
    public ArrayList<String> getClasses() {
        return this.classes;

    }

    public String getClasses(int index) {
        return this.classes.get(index);

    }

    public void setClasses(ArrayList<String> classes) {
        this.classes = classes;

    }

    public void setClasses(int index, String classe) {
        this.classes.set(index, classe);

    }

    public ArrayList<String> getIndividuals() {
        return this.individuals;

    }

    public String getIndividuals(int index) {
        return this.individuals.get(index);

    }

    public void setIndividuals(ArrayList<String> individuals) {
        this.individuals = individuals;

    }

    public void setIndividuals(int index, String individuals) {
        this.individuals.set(index, individuals);

    }

    public ArrayList<String> getDataProps() {
        return this.dataprops;

    }

    public String getDataProps(int index) {
        return this.dataprops.get(index);

    }

    public void setDataProps(ArrayList<String> dataprops) {
        this.dataprops = dataprops;

    }

    public void setDataProps(int index, String dataprops) {
        this.dataprops.set(index, dataprops);

    }

    public ArrayList<String> getObjProps() {
        return this.objprops;

    }

    public String getObjProps(int index) {
        return this.objprops.get(index);

    }

    public void setObjProps(ArrayList<String> objprops) {
        this.objprops = objprops;

    }

    public void setObjprops(int index, String objprops) {
        this.objprops.set(index, objprops);

    }

    public ArrayList<Query> getQueries() {
        return this.queries;

    }

    public Query getQueries(int index) {
        return this.queries.get(index);

    }

    public void setQueries(ArrayList<Query> queries) {
        this.queries = queries;

    }

    public void setQueries(int index, Query queries) {
        this.queries.set(index, queries);

    }

    public Query getQSelected() {
        return this.qSelected;

    }

    public void setQSelected(Query qselected) {
        this.qSelected = qselected;

    }

    public String getOntology() {
        return this.ontology;

    }

    public void setOntology(String onto) {
        this.ontology = onto;

    }

    public String getStatus() {
        return this.status;

    }

    public void setStatus(String status) {
        this.status = status;

    }

    public String getResult() {
        return this.result;

    }

    public void setResult(String result) {
        this.result = result;

    }

    public static File createTempFile(String path, String directory, BufferedWriter bw) throws IOException {
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("The path has to be absolute (start with '/').");
        }

        // Obtain basename from path
        String baseName = FilenameUtils.getBaseName(path);
        // Obtain extension from path
        String ext = "." + FilenameUtils.getExtension(path);

        // Check if the basename is okay
        if (baseName.isEmpty()) {
            throw new IllegalArgumentException("The filename is an empty string");
        }
        if (baseName.length() < 3) {
            throw new IllegalArgumentException("The filename has to be at least 3 characters long.");
        }

        // Prepare temporary file
        File dir = null;
        if (directory != null) {
            dir = new File(directory);
            if (!dir.isDirectory()) {
                throw new IOException(dir.getAbsolutePath() + " is not a directory");
            }
        }
//        File tempFile = File.createTempFile(baseName, ext, dir);
        File tempFile = new File(System.getProperty("user.dir") + "/" + baseName + ext);
        bw.write("outputfile: " + tempFile.getAbsolutePath() + "\n");
        bw.flush();
        try {
        tempFile.createNewFile();
        bw.write("file created");
        bw.flush();
        tempFile.deleteOnExit();
        bw.write("delete on exit");
        bw.flush();
        } catch(IOException e) {
            bw.write(e.getMessage()+ "\n");
            bw.close();
        }

        if (!tempFile.exists()) {
            throw new FileNotFoundException("File " + tempFile.getAbsolutePath() + " does not exist.");
        }

        // Prepare buffer for data copying
        byte[] buffer = new byte[4096];
        int readBytes;

        // Open and check input stream
        URL t = LoadNativeLibrary.class.getResource(path);
        InputStream is = LoadNativeLibrary.class.getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException("File " + path + " was not found inside JAR.");
        }

        // Open output stream and copy data between source file in JAR and the temporary file
        OutputStream os = new FileOutputStream(tempFile);
        try {
            while ((readBytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, readBytes);
            }
        } finally {
            // If read/write fails, close streams safely before throwing an exception
            os.close();
            is.close();
        }
        return tempFile;
    }

}
