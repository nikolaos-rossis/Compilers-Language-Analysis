import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class visitor extends DepthFirstAdapter{
    private Hashtable symbolicTable;
    private List<AFunction> Functions;

    visitor(Hashtable sym, List<AFunction> fun){
        symbolicTable = sym;
        Functions = fun;
    }

    public void inAFunction(AFunction node){
        TId id = ((TId)(((AIdentExpression) node.getExpression()).getId()));
        String name = id.toString();
        int line = id.getLine();

        int[] minMax = MinMax(node);
        int minArgs = minMax[0];
        int maxArgs = minMax[1];
        boolean alreadyused = false;
        boolean error = false;
        
        for(AFunction fun:Functions){
            fun = (AFunction) fun.clone();
            if(((AIdentExpression)fun.getExpression()).getId().toString().equals(name)){
                if(!checkArgSize(line, minArgs, node)){
                    System.out.println("Line " + line + ": " +" Function " + name +" is already defined");
                    error = true;
                }else if(!checkArgSize(line, maxArgs, node)){
                    System.out.println("Line " + line + ": " +" Function " + name +" is already defined");
                    error = true;
                }
                alreadyused = true;
            }    
        }

        if(!alreadyused || !error&&alreadyused){
            Functions.add(node);
        }
    }

    public void inAArgument(AArgument node){
        AArgument cloneofArg = (AArgument) node.clone();
        TId id = (TId)((AIdentExpression)cloneofArg.getExpression()).getId();
        String name = id.toString();

        if(cloneofArg.getEqVal().size()!=0){
            AEqVal val = (AEqVal)cloneofArg.getEqVal().get(0);
            AEqStmtStatement moreId = new AEqStmtStatement(new AIdentExpression(id),((PExpression)val.getExpression()));
            symbolicTable.put(name, moreId);
        }else{
            AEqStmtStatement moreId = new AEqStmtStatement(new AIdentExpression(id), new ANoneExpression(new TNone(id.getLine(), id.getPos())));
            symbolicTable.put(name, moreId);
        }
        
    }

    public void inAArgumentHelper(AArgumentHelper node){
        AArgumentHelper clonemoreIds = (AArgumentHelper) node.clone();
        TId id = (TId)((AIdentExpression)clonemoreIds.getExpression()).getId();
        String name = id.toString();
        if(clonemoreIds.getEqVal().size()!=0){
            AEqVal val = (AEqVal) clonemoreIds.getEqVal().get(0);
            AEqStmtStatement moreId = new AEqStmtStatement(clonemoreIds.getExpression(), ((PExpression)val.getExpression()));
            symbolicTable.put(name, moreId);
        }else{
            AEqStmtStatement moreId = new AEqStmtStatement(clonemoreIds.getExpression(), new ANoneExpression(new TNone(id.getLine(), id.getPos())));
            symbolicTable.put(name, moreId);
        }
    }



    public boolean checkArgSize(int line, int amountofargs , AFunction node){
        int[] minMax = MinMax(node);
        int minArgs = minMax[0];
        int maxArgs = minMax[1];

        if(amountofargs == minArgs || amountofargs == maxArgs){
            System.out.println("Line" + line + ": found " + amountofargs + "arguments should not be equal to {"+ minArgs+", "+maxArgs+"}");
            return false;
        }
        return true;
    }

    public int[] MinMax(AFunction node){
        int[] minMax = new int[2];
        boolean isDefault;
        AArgument arg;
        TypedLinkedList arguments;
        int minArgs=0;
        int maxArgs=0;
        arguments = (TypedLinkedList)node.getArgument();

        if(arguments.size()>0){
            maxArgs++;
            arg = (AArgument)arguments.get(0);
            TypedLinkedList moreIds = (TypedLinkedList) arg.getArgumentHelper();
            isDefault = arg.getEqVal().size()==0;
            
            if(!isDefault){
                minArgs++;
            }

            for(int i=0;i<moreIds.size();i++){
                maxArgs++;
                isDefault = ((AArgumentHelper)moreIds.get(i)).getEqVal().size()==0;
                if(!isDefault){
                    minArgs++;
                }
            }
        }

        minArgs = maxArgs-minArgs;
        minMax[0]=minArgs;
        minMax[1]=maxArgs;
        
        return minMax;
    }
}