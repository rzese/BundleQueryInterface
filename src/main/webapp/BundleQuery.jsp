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
   if(request.getParameter("typeProp")!=null)
       typeProp=request.getParameter("typeProp");
   
   int prop=-1;
    if(request.getParameter("prop")!=null)
       prop=Integer.valueOf(request.getParameter("prop"));
    
   String cst=null;
   if(request.getParameter("constant")!=null)
       cst=request.getParameter("constant"); 
   String tcst=null;
   if(request.getParameter("typeConstant")!=null)
       tcst=request.getParameter("typeCostant");
   
   int cla=-1;
   int clb=-1;
   int ii=-1;
   int is=-1;
   
    if(request.getParameter("classa")!=null)
       cla=Integer.valueOf(request.getParameter("classa")); 
    
     if(request.getParameter("classb")!=null)
       clb=Integer.valueOf(request.getParameter("classb")); 
     
     if(request.getParameter("indi")!=null)
       ii=Integer.valueOf(request.getParameter("indi")); 
   
    if(request.getParameter("inds")!=null)
       is=Integer.valueOf(request.getParameter("inds")); 
   
    
   
   int i;
   
  if(request.getParameter("status")==null)
      BundleQueryManagement.setStatus("view");
  else
      BundleQueryManagement.setStatus(request.getParameter("status"));
   
  if(BundleQueryManagement.getStatus().equals("execute"))
       BundleQueryManagement.BundleQueryManagementExecute();
   else
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
                
              /*  if(f.classa.value == "")
                    {alert("No Class A selected!");
                    return;
                    }
                    
               if(f.classb.value == "")
                    {alert("No Class B selected!");
                    return;
                    }
               if(f.indi.value == "")
                    {alert("No Individual I selected!");
                    return;
                    }
                    
               if(f.inds.value == "")
                    {alert("No Individual S selected!");
                    return;
                    }
                    
               if(f.typeProp.value == "")
                    {alert("No Type Property selected!");
                    return;
                    }
                    
               if(f.prop.value == "")
                    {alert("No Property selected!");
                    return;
                    }
                    
               if(f.typeConstant.value == "")
                    {alert("No Type Constant selected!");
                    return;
                    }
               var typec=f.typeConstant.value;
                if(f.constant.value == "")
                            {alert("No Type Constant selected!");
                            return;
                            }
                    else
                        if((typec == "inte") || (typec == "floa") || (typec == "doub") )
                            {if(!NaN(f.constant.value))
                               {alert("Constant isn't a number!");
                                return;
                                } 
                            }
                            
                       else
                            {if(typec == "bool")
                                    if((f.constant.value != "true") || (f.constant.value != "false"))
                                            {alert("Constant must be true or false!");
                                            return;
                                            } 
                            }
                    
              
        */
        
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
                <div class="resquery"   >   
                <table class="tabResultQuery">
                    <tr>
                        <td>Query Selected: </td>
                        <td><p><%=BundleQueryManagement.getQuerySel()%></p></td>
                    </tr>
                    
                    <%if(prop>-1)
                        if(typeProp.equals("Object"))
                            {%>
                                <tr>
                                <td>Property: </td>
                                <td><%=BundleQueryManagement.getObjProps(prop)%></td>
                                </tr>
                            <%}
                        else
                            {%>
                                <tr>
                                <td>Property: </td>
                                <td><%=BundleQueryManagement.getDataProps(prop)%></td>
                                </tr>
                                
                                <tr>
                                <td>Constant: </td>
                                <td><%=cst%>, <%=tcst%></td>
                                </tr>
                            <%}%>    
                    
                    <%if(cla>-1){%>
                    <tr>
                        <td>Class A: </td>
                        <td><%=BundleQueryManagement.getClasses(cla)%></td>
                    </tr>
                    <%}%>
                    <%if(clb>-1){%>
                    <tr>
                        <td>Class B: </td>
                        <td><%=BundleQueryManagement.getClasses(clb)%></td>
                    </tr>
                    <%}%>
                    
                    <%if(ii>-1){%>
                    <tr>
                        <td>Individual I: </td>
                        <td><%=BundleQueryManagement.getIndividuals(ii)%></td>
                    </tr>
                    <%}%>
                    <%if(is>-1){%>
                    <tr>
                        <td>Individual S: </td>
                        <td><%=BundleQueryManagement.getIndividuals(is)%></td>
                    </tr>
                    <%}%>
                    
                    <tr>
                        <td></td>
                        <td>
                            <div class="but">
                            <input type="button" classs="button" value="New Query" onclick="cleaning()" />
                            </div>
                        </td>
                    </tr>
                    
                </table>
                </div>                       
                    <%} else 
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
                     <%if(BundleQueryManagement.getTypeProp().equals("Object")){%>
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
                    <%}%>
                   
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
                    <%}
                     }%> 
                       
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
                  <%if(BundleQueryManagement.getResult()==null){%>  
                    <p></p>
                  <%} else {%>  
                    <p> <%=BundleQueryManagement.getResult()%></p>
                  <%}%>  
                </div>    
            </div>
            
        </div>
        
    </body>
</html>
