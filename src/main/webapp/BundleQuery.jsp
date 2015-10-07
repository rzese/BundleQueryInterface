<%-- 
    Document   : BundleQuery
    Created on : 30-set-2015, 21.07.42
    Author     : chiara
--%>
<%@page info="Bundle QUery interface"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id="BundleQueryManagement" scope="page" class="bflows.BundleQueryManagement" />
<jsp:setProperty name="BundleQueryManagement" property="*" />


<% 
   String urlWebProtege="http://localhost:8080/webprotege-2.5.0";

   
    String typeProp=null;
   if(request.getParameter("typePProp")!=null)
       typeProp=request.getParameter("typeProp");
    
   int i;
   
  
   BundleQueryManagement.setStatus(request.getParameter("status"));
   
   if(BundleQueryManagement.getStatus()!=null)
        if(BundleQueryManagement.getStatus().equals("execute"))
            BundleQueryManagement.BundleQueryManagementExecute();
   
   
   BundleQueryManagement.BundleQueryManagementView();
   
   
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bundle Query Interface</title>
        
        <link rel="stylesheet" type="text/css" href="style.css"  media="screen" />
        
        <script type="text/javascript">
            function populates(chosen)
                    {if(chosen == "")
                        {alert("Inserisci una query!");
                        return;    
                        }
                     document.richarge.querySel.value=chosen;
                     document.richarge.submit();
                     return;
                    }
                    
            function populatesProp(chosen)
                    {
                     document.richargeprop.typeProp.value=chosen;
                     document.richargeprop.submit();
                     return;
                    }
           
           function verifySelected()
                {f=document.selectQuery;
                
                //controlli
                
                f.submit();
                return;

                }
           
           function cleaning()
            {cleanForm.submit();

            }
           
           function backToWB()
            {window.close();

            }
           
           
        </script>    
        
    </head>
    
    <body>
        <div class="container">
         
            <div class="titlePage">
                <span>BUNDLE QUERY</span>
            </div>
            
            <div class="bar">
                <a href="javascript:backToWB()"> <input type="button" class="button" value="Return to WebProtégé" /></a>
            </div>
            
            <div class="qContainer">
              
                
            <form name="richarge" method="post" action="BundleQuery.jsp">
                <input type="hidden" name="ontology" value="<%=BundleQueryManagement.getOntology()%>"/>
                <input type="hidden" name="querySel" />
                
                <input type="hidden" name="status" value="view"/>
            </form>
                <form name="richargeprop" method="post" action="BundleQuery.jsp">
                <input type="hidden" name="ontology" value="<%=BundleQueryManagement.getOntology()%>" />
                <input type="hidden" name="querySel" value="<%=BundleQueryManagement.getQuerySel()%>" />
                <input type="hidden" name="typeProp" />
                <input type="hidden" name="status" value="view"/>
            </form>
            
            <form name="cleanForm" method="post" action="BundleQuery.jsp">
                <input type="hidden" name="status" value="view" />
                <input type="hidden" name="ontology" value="<%=BundleQueryManagement.getOntology()%>" />
                 
            </form>
                
                <%if(BundleQueryManagement.getStatus().equals("execute")){%>
                       <ul class="resultElement">      
                           
                           <li class="query">Query Selected: <%=BundleQueryManagement.getQuerySel()%> 
                               <ul>
                              <%if(BundleQueryManagement.getTypeProp()!=null){%>
                                    <li>Property: <%=BundleQueryManagement.getDataProps(BundleQueryManagement.getProp())%> </li>
                                  <%if(BundleQueryManagement.getTypeConstant()!=null){%>
                                    <li>Constant: <%=BundleQueryManagement.getConstant()%> </li>
                                  <%}
                              }%> 
                                      
                               <%if(BundleQueryManagement.getClassa()!=0){%>
                                <li>ClassA: <%=BundleQueryManagement.getClasses(BundleQueryManagement.getClassa())%> </li>
                               %}%> 
                               
                               <%if(BundleQueryManagement.getClassb()!=0){%>
                                <li>ClassB: <%=BundleQueryManagement.getClasses(BundleQueryManagement.getClassb())%></li>
                               <%}%> 
                                
                               <%if(BundleQueryManagement.getIndi()!=0){%>
                                <li>IndividualI: <%=BundleQueryManagement.getIndividuals(BundleQueryManagement.getIndi())%> </li>
                               <%}%>
                               
                                <%if(BundleQueryManagement.getInds()!=0){%>
                                    <li>IndividualS: <%=BundleQueryManagement.getIndividuals(BundleQueryManagement.getInds())%></li>
                                <%}%>
                               </ul>
                          </li> 
                          
                          <li>
                              <div class="but">
                                <input type="button" class="button" value="Clear" onClick="cleaning()" />
                              </div> 
                          </li>
                       </ul>
                                       
                    <%}} else 
                        if(BundleQueryManagement.getStatus().equals("view")){%>
                    
                 <form name="selectQuery" method="post" action="BundleQuery.jsp">
                <div class="selQuery" >
                   
                    <label>Select query: </label>
                    <select name="querySel" onchange="populates(this.options.item(selectedIndex).value)">
                        <%if(BundleQueryManagement.getQuerySel()==null){%>
                        <option selected="selected"> </option>
                        <%}%>
                        <%for (i=0;i<BundleQueryManagement.getQueries().size();i++){%>
                            
                       
                        <option
                            <%if((BundleQueryManagement.getQuerySel()!=null)&&(BundleQueryManagement.getQuerySel().equals(BundleQueryManagement.getQueries(i).getDescription()))){%> 
                            selected="selected"
                            <%}%>                                                                                   
                        >
                            <%=BundleQueryManagement.getQueries(i).getDescription()%>
                        </option>
                        <% }%>
                    </select>
                  
                    
                </div>
                    
                <%if(BundleQueryManagement.getQuerySel()!=null){%>    
                <div class="elQuery" >
                    <table class="tableelement">
                       <!--Query Property -->
                     <%if(BundleQueryManagement.getQSelected().getNeedProperty()){%>
                     <tr>
                         <td><label>Type of property: </label>
                                <input type="radio" name="typeProp" value="Object"
                                       <%if((BundleQueryManagement.getTypeProp()!=null)&&(BundleQueryManagement.getTypeProp().equals("Object"))){%> 
                            checked="checked"
                            <%}%>  
                                       onclick="populatesProp('Object')"/>Object
                                
                                <input type="radio" name="typeProp" value="Data" 
                                       <%if((BundleQueryManagement.getTypeProp()!=null)&&(BundleQueryManagement.getTypeProp().equals("Data"))){%> 
                            checked="checked"
                            <%}%>  
                                       onclick="populatesProp('Data')" />Data
                         </td>
                         
                       <%if(BundleQueryManagement.getTypeProp()!=null){%>
                         <td><label>Select Property: </label>
                             
                             <select name="prop">
                                 <%if(BundleQueryManagement.getTypeProp().equals("Data")){
                                     for(i=0;i<BundleQueryManagement.getDataProps().size();i++){
                                        %>
                                    <option value="<%=i%>">
                                            <%=BundleQueryManagement.getDataProps(i)%>
                                    </option>
                                    <%}}%>
                                  <%if(BundleQueryManagement.getTypeProp().equals("Object")){
                                     for(i=0;i<BundleQueryManagement.getObjProps().size();i++){
                                        %>
                                    <option value="<%=i%>">
                                            <%=BundleQueryManagement.getObjProps(i)%>
                                    </option>
                                    <%}}%>  
                                </select>
                         </td>
                    </tr>
                    <%if(BundleQueryManagement.getTypeProp().equals("Data")){%>
                    <tr>
                        <td><label>Type of constant: </label>
                            <input type="radio" name="typeConstant" value="st" />String
                             <input type="radio" name="typeConstant" value="inte" />Integer
                             <input type="radio" name="typeConstant" value="floa" />Float
                             <input type="radio" name="typeConstant" value="bool" />Boolean
                             <input type="radio" name="typeConstant" value="doub" />Double
                        </td>
                        <td><label>Constant: </label>
                                <input type="text" name="constant" />
                        </td>
                    </tr>
                    <%}%>
                    <%}
                     }%> 
                        
                       <!--Query Classes -->
                       <%if(BundleQueryManagement.getQSelected().getNeedClass()!=0){%>
                       <tr>
                           <td><label>Select classA: </label>
                               
                                <select name="classa">
                                    <%for(i=0;i<BundleQueryManagement.getClasses().size();i++){%>
                                    <option value="<%=i%>">
                                        <%=BundleQueryManagement.getClasses(i)%>
                                    </option>
                                    <%}%>
                                </select>
                           </td>
                           <td>
                           </td>    
                       </tr>
                       <%}%>
                       <%if(BundleQueryManagement.getQSelected().getNeedClass()==2){%>
                       <tr>
                           <td><label>Select classB: </label>
                              
                                <select name="classb">
                                     <%for(i=0;i<BundleQueryManagement.getClasses().size();i++){%>
                                    <option value="<%=i%>">
                                            <%=BundleQueryManagement.getClasses(i)%>
                                    </option>
                                    <%}%>
                                </select>
                           </td>
                           <td>
                               
                           </td>
                    </tr>
                     <%}%>
                        <!--Query Individual -->
                     <%if(BundleQueryManagement.getQSelected().getNeedIndividual()!=0){%>   
                    <tr>    
                        <td><label>Select individual I: </label>
                            <select name="indi">
                                <%for(i=0;i<BundleQueryManagement.getIndividuals().size();i++){%>
                                <option value="<%=i%>">
                                            <%=BundleQueryManagement.getIndividuals(i)%>
                                </option>
                                <%}%>
                            </select>
                        </td>
                        <td>
                            
                        </td>
                    </tr> 
                 
                    <%if(BundleQueryManagement.getQSelected().getNeedIndividual()==2){%>
                    <tr>
                        <td><label>Select Individual S: </label>
                            <select name="inds">
                                <%for(i=0;i<BundleQueryManagement.getIndividuals().size();i++){%>
                                <option value="<%=i%>">
                                            <%=BundleQueryManagement.getIndividuals(i)%>
                                </option>
                                <%}%>
                            </select>
                        </td>
                        <td>
                            
                        </td>
                    </tr> 
                    
                    <%}}%>
                     
                        <input type="hidden" name="qSelected" value="<%=BundleQueryManagement.getQSelected()%>" />
                        <input type="hidden" name="status" value="execute" />
                        <input type="hidden" name="ontology" value="<%=BundleQueryManagement.getOntology()%>" />
                     </form> 
                    <!--Bottone invio query--> 
                    
                    <tr>
                        <td></td>
                        <td><div class="but">
                                <input type="button" class="button" value="Send" onClick="verifySelected()"  />
                            </div> 
                        </td>
                    </tr>
                    
                    <%}%>
                    
                  
                    </table>
                </div>
                <%}%>
            </div>
            
                        
            
            
            <div class="resultContainer">
                <label>Results: </label>
                <div class="rect">
                    <p> <%=BundleQueryManagement.getResult()%></p>
                </div>    
            </div>
            
        </div>
        
    </body>
</html>
