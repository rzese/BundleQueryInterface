package bflows;

import java.util.*;
import blogics.*;
import com.clarkparsia.owlapiv3.OWL;
import unife.bundle.*;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
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
import unife.bundle.utilities.BundleUtilities;



/**
 *
 * @author chiara
 */
public class BundleQueryManagement 
    {
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
    
   private  ArrayList<OWLClass> oclasses;
    
    private ArrayList<OWLNamedIndividual> oindividuals;
    
    private ArrayList<OWLDataProperty>  odataprops;
    
    private ArrayList<OWLObjectProperty> oobjprops;
  
   
    private String status;
    
    private String result;
    
    
    public BundleQueryManagement(){}
    
   
    public void BundleQueryManagementView()
        {//gestione restituzione OWLOntology
           
        File file;
        try{
            
                file=File.createTempFile("onto", ".owl");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(ontology);
                ontology=StringEscapeUtils.escapeHtml(ontology);
               
                bw.flush();
                bw.close();
                String path=file.getAbsolutePath().replace("\\", "/");
                rootOntology=OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(IRI.create("file:///"+path));
                file.delete();
                
        }
        catch(IOException e) { 
            e.printStackTrace();
          }
        catch(OWLOntologyCreationException e)
            {e.printStackTrace(); 
            }
     
            
        
        createQuery();
        
        if(querySel!=null)
            {int i=0;
            while(!querySel.equals(queries.get(i).getDescription()))
                {i++;
                }
            
            qSelected=queries.get(i);
            
            oclasses=new ArrayList<OWLClass>();
            classes=new ArrayList<String>();
            
            if(qSelected.getNeedClass()!=0)
                { //crea lista classi sia di Stringhe che OWL
                    if(rootOntology!=null)
                for (OWLClass a : rootOntology.getClassesInSignature()) {
                    oclasses.add(a);
                    classes.add(BundleUtilities.getManchesterSyntaxString(a));
                }
                }
            
            oindividuals=new ArrayList<OWLNamedIndividual>();
            individuals=new ArrayList<String>();
            
            if(qSelected.getNeedIndividual()!=0)
                {//crea lista individuals sia di Stringhe che OWL
                  for(Iterator<OWLNamedIndividual> it=rootOntology.getIndividualsInSignature().iterator();it.hasNext();)
                    {OWLNamedIndividual a=it.next();
                    oindividuals.add(a);
                    individuals.add(BundleUtilities.getManchesterSyntaxString(a));
                    }   
                }
            
            odataprops=new ArrayList<OWLDataProperty>();
            dataprops=new ArrayList<String>();
            oobjprops=new ArrayList<OWLObjectProperty>();
            objprops=new ArrayList<String>();
            
            if(qSelected.getNeedProperty())
                {//crea lista property (--> 2liste divise per data e object)sia di Stringhe che OWL
                 for(Iterator<OWLDataProperty> it=rootOntology.getDataPropertiesInSignature().iterator();it.hasNext();)
                    {OWLDataProperty a=it.next();
                    odataprops.add(a);
                    dataprops.add(BundleUtilities.getManchesterSyntaxString(a));
                    }  
                 for(Iterator<OWLObjectProperty> it=rootOntology.getObjectPropertiesInSignature().iterator();it.hasNext();)
                    {OWLObjectProperty a=it.next();
                    oobjprops.add(a);
                    objprops.add(BundleUtilities.getManchesterSyntaxString(a));
                    }  
                }
            }
        
        }
    
    public void BundleQueryManagementExecute()
        {File file;
        try{
            file=File.createTempFile("onto", ".owl");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(StringEscapeUtils.unescapeHtml(ontology));
            bw.flush();
            bw.close();
             String path=file.getAbsolutePath().replace("\\", "/");
            rootOntology=OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(IRI.create("file:///"+path));
            file.delete();
        }
        catch(IOException e) { 
            e.printStackTrace();
          }
        catch(OWLOntologyCreationException e)
            {e.printStackTrace(); 
            }    
        
         oclasses=new ArrayList<OWLClass>();
            classes=new ArrayList<String>();
            
        createQuery();
       
        int i=0;    
        if(querySel!=null)
            while(!querySel.equals(queries.get(i).getDescription()))
                {i++;
                }
        qSelected=queries.get(i);    
            
            if(qSelected.getNeedClass()!=0)
                {//crea lista classi sia di Stringhe che OWL
                    Iterator<OWLClass> it=null;
                for(it=rootOntology.getClassesInSignature().iterator();it.hasNext();)
                    {OWLClass a=it.next();
                    oclasses.add(a);
                    classes.add(BundleUtilities.getManchesterSyntaxString(a));
                    }
                }
            
            oindividuals=new ArrayList<OWLNamedIndividual>();
            individuals=new ArrayList<String>();
            
            if(qSelected.getNeedIndividual()!=0)
                {//crea lista individuals sia di Stringhe che OWL
                  for(Iterator<OWLNamedIndividual> it=rootOntology.getIndividualsInSignature().iterator();it.hasNext();)
                    {OWLNamedIndividual a=it.next();
                    oindividuals.add(a);
                    individuals.add(BundleUtilities.getManchesterSyntaxString(a));
                    }   
                }
            
            odataprops=new ArrayList<OWLDataProperty>();
            dataprops=new ArrayList<String>();
            oobjprops=new ArrayList<OWLObjectProperty>();
            objprops=new ArrayList<String>();
            
            if(qSelected.getNeedProperty())
                {//crea lista property (--> 2liste divise per data e object)sia di Stringhe che OWL
                 for(Iterator<OWLDataProperty> it=rootOntology.getDataPropertiesInSignature().iterator();it.hasNext();)
                    {OWLDataProperty a=it.next();
                    odataprops.add(a);
                    dataprops.add(BundleUtilities.getManchesterSyntaxString(a));
                    }  
                 for(Iterator<OWLObjectProperty> it=rootOntology.getObjectPropertiesInSignature().iterator();it.hasNext();)
                    {OWLObjectProperty a=it.next();
                    oobjprops.add(a);
                    objprops.add(BundleUtilities.getManchesterSyntaxString(a));
                    }  
                }
            
        BundleWebController bwc=new BundleWebController();
        
        QueryResult q=new QueryResult();
        
        Bundle bundle=bwc.getBundle();
        
        
        bundle.loadOntologies(rootOntology);
        
        bundle.setPMap();
        
        OWLDataFactory factory=rootOntology.getOWLOntologyManager().getOWLDataFactory();
        
        
        try{
            if(qSelected.getCommand().equals("unsat"))
                q=bundle.computeQuery(factory.getOWLSubClassOfAxiom((OWLClassExpression)oclasses.get(classa), OWL.Nothing));
            
            if(qSelected.getCommand().equals("all-unsat"))
                q=bundle.computeQuery();
            
            if(qSelected.getCommand().equals("hierarchy"))
                q=bundle.computeQuery();
            
            if(qSelected.getCommand().equals("instance"))
                q=bundle.computeQuery(factory.getOWLClassAssertionAxiom((OWLClassExpression)oclasses.get(classa),(OWLIndividual)oindividuals.get(indi)));
            
            if(qSelected.getCommand().equals("subclass"))
                q=bundle.computeQuery(factory.getOWLSubClassOfAxiom((OWLClassExpression)oclasses.get(classa),(OWLClassExpression)oclasses.get(classb)));
            
            
            if(qSelected.getCommand().equals("property-value"))
                {if(typeProp.equals("Object"))
                    q=bundle.computeQuery(factory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression)oobjprops.get(prop), (OWLIndividual)oindividuals.get(indi),(OWLIndividual)oindividuals.get(inds)));
                if(typeProp.equals("Data"))
                    {int constI;
                    float constF;
                    double constD;
                    boolean constB;
                    
                    if(typeConstant.equals("str"))
                        q=bundle.computeQuery(factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression)odataprops.get(prop),(OWLIndividual)oindividuals.get(indi), constant));
                    if(typeConstant.equals("inte"))
                        {constI=Integer.getInteger(constant);
                        q=bundle.computeQuery(factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression)odataprops.get(prop),(OWLIndividual)oindividuals.get(indi), constI));
                        }
                    if(typeConstant.equals("floa"))
                        {constF=Float.valueOf(constant);
                        q=bundle.computeQuery(factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression)odataprops.get(prop),(OWLIndividual)oindividuals.get(indi), constF));
                        }
                    if(typeConstant.equals("bool"))
                        {constB=Boolean.valueOf(constant);
                        q=bundle.computeQuery(factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression)odataprops.get(prop),(OWLIndividual)oindividuals.get(indi), constB));
                        }
                    if(typeConstant.equals("doub"))
                        {constD=Double.valueOf(constant);
                        q=bundle.computeQuery(factory.getOWLDataPropertyAssertionAxiom((OWLDataPropertyExpression)odataprops.get(prop),(OWLIndividual)oindividuals.get(indi), constD));
                        }
                   }
                }
            
            if(qSelected.getCommand().equals("inconsistent"))
                q=bundle.computeQuery(factory.getOWLSubClassOfAxiom(OWL.Thing,OWL.Nothing));
            
            
               
            result=bwc.finish(q);
            bundle.disposeBDDFactory();
          
            }
        catch(OWLException e)
            {e.printStackTrace();
            }
        }    
    
    
    private void createQuery()
        {Query q1=new Query("unsat","Explain why A class is unsatisfable",1,0,false);
         Query q2=new Query("all-unsat","Explain all unsatisfable class",0,0, false);
         Query q3=new Query("inconsistent","Explain why the ontology is inconsistent",0,0, false);
         Query q4=new Query("hierarchy","Print all explanations for the class hierarchy",1,0, false);
         Query q5=new Query("subclass","Explain why A class is a subClass of B class",2,0,false);
         Query q6=new Query("instance","Explain why I individual is an instance of A class",1,1, false);
         Query q7=new Query("property","Explain why I individual has value S for property P",0,0,true);
         
        queries=new ArrayList<Query>();
        queries.add(q1);
        queries.add(q2);
        queries.add(q3);
        queries.add(q4);
        queries.add(q5);
        queries.add(q6);
        queries.add(q7);
    
        }
    
    
    public int getClassa()
        {return this.classa;
        
        }
    
    public void setClassa(int classa)
        {this.classa=classa;
        
        }
    
    public int getClassb()
        {return this.classb;
        
        }
    
    public void setClassb(int classb)
        {this.classb=classb;
        
        }
    
    public int getInds()
        {return this.inds;
        
        }
    
    public void setInds(int inds)
        {this.inds=inds;
        
        }
    
    public int getIndi()
        {return this.indi;
        
        }
    
    public void setIndi(int indi)
        {this.indi=indi;
        
        }
    
    public int getProp()
        {return this.prop;
        
        }
    
    public void setProp(int prop)
        {this.prop=prop;
        
        }
    
    public String getTypeProp()
        {return this.typeProp;

        }
    
    public void setTypeProp(String typeprop)
        {this.typeProp=typeprop;
        
        }
    
    public String getTypeConstant()
        {return this.typeConstant;

        }
    
    public void setTypeConstant(String typeconst)
        {this.typeConstant=typeconst;
        
        }
    
    public String getConstant()
        {return this.constant;

        }
    public void setConstant(String constant)
        {this.constant=constant;
        
        }
    
    public String getQuerySel()
        {return this.querySel;

        }
    
    public void setQuerySel(String querysel)
        {this.querySel=querysel;
        
        }
    
    //liste stringhe
    public ArrayList<String> getClasses()
        {return this.classes;
        
        }
    
    public String getClasses(int index)
        {return this.classes.get(index);
        
        }
    
    public void setClasses(ArrayList<String> classes)
        {this.classes=classes;
        
        }
    
    public void setClasses(int index, String classe)
        {this.classes.set(index, classe);
        
        }
    
    
    
    public ArrayList<String> getIndividuals()
        {return this.individuals;
        
        }
     public String getIndividuals(int index)
        {return this.individuals.get(index);
        
        }
     public void setIndividuals(ArrayList<String> individuals)
        {this.individuals=individuals;
        
        }
    
    public void setIndividuals(int index, String individuals)
        {this.individuals.set(index, individuals);
        
        }
    
    
    
    
    public ArrayList<String> getDataProps()
        {return this.dataprops;
        
        }
     public String getDataProps(int index)
        {return this.dataprops.get(index);
        
        }
     public void setDataProps(ArrayList<String> dataprops)
        {this.dataprops=dataprops;
        
        }
    
    public void setDataProps(int index, String dataprops)
        {this.dataprops.set(index, dataprops);
        
        }
    
    
    
    
    public ArrayList<String> getObjProps()
        {return this.objprops;
        
        }
     public String getObjProps(int index)
        {return this.objprops.get(index);
        
        }
     public void setObjProps(ArrayList<String> objprops)
        {this.objprops=objprops;
        
        }
    
    public void setObjprops(int index, String objprops)
        {this.objprops.set(index, objprops);
        
        }
    
    
    
    public ArrayList<Query> getQueries()
        {return this.queries;
        
        }
         
     public Query getQueries(int index)
        {return this.queries.get(index);
        
        }
     public void setQueries(ArrayList<Query> queries)
        {this.queries=queries;
        
        }
    
    public void setQueries(int index, Query queries)
        {this.queries.set(index, queries);
        
        }
    
    public Query getQSelected()
        {return this.qSelected;
        
        }
    
    public void setQSelected(Query qselected)
        {this.qSelected=qselected;
            
        }
    public String getOntology()
        {return this.ontology;
 
        }
    
    public void setOntology(String onto)
        {this.ontology=onto;
        
        }
    
    public String getStatus()
        {return this.status;
 
        }
    
    public void setStatus(String status)
        {this.status=status;
        
        }
    
    public String getResult()
        {return this.result;
 
        }
    
    public void setResult(String result)
        {this.result=result;
        
        }
}
