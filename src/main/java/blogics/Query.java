package blogics;

/**
 *
 * @author chiara
 */
public class Query 
    {private final String description; /*query visualizzata nella listbox es. Why the A class is unsatisfable*/
    
    private final String queryCommand; /*query da mandare a bundle es. unsat*/
    
    private final int needClass; /*se la query prevede come parametri anche classi (1o+)*/
    
    private final int needIndividual; /*se la query prevede come parametri anche individui (1o+)*/
    
    private boolean needProperty; 
    
    public Query(String queryCommand, String description, int needClass, int needIndividual, boolean needProperty)
        {this.description=description;
        this.queryCommand=queryCommand;
        this.needClass=needClass;
        this.needIndividual=needIndividual;
        this.needProperty=needProperty;
        }
    
    public String getDescription()
        {return this.description;   
        }
    
    public String getCommand()
        {return this.queryCommand;
        }
    
    public int getNeedClass()
        {return this.needClass;
        }
    
    public int getNeedIndividual()
        {return this.needIndividual;
        }
    
     public boolean getNeedProperty()
        {return this.needProperty;
        }
//     public void setNeedClass(int needClass)
//        {this.needClass=needClass;
//        }
//      
//     public void setNeedIndividual(int needIndividual)
//        {this.needIndividual=needIndividual;
//        }
     public void setNeedProperty(boolean needProperty)
        {this.needProperty=needProperty;
        }
     
     
     
    }