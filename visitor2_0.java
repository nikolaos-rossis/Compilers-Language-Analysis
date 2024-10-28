import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class visitor2_0 extends DepthFirstAdapter{
    private Hashtable symbolicTable;
    private List<AFunction> Functions;

    visitor2_0(Hashtable sym, List<AFunction> fun){
        symbolicTable = sym;
        Functions = fun;
    }
    

    public void inAEqStmtStatement(AEqStmtStatement node){
        TId Leftid = ((TId)((AIdentExpression) node.getLExpr()).getId());
        String name = Leftid.toString();

        if(node.getRExpr() instanceof AIdentExpression){
            if(symbolicTable.containsKey(((TId)((AIdentExpression) node.getRExpr()).getId()).toString())){
                if(symbolicTable.get(((TId)((AIdentExpression) node.getRExpr()).getId()).toString()) instanceof AEqStmtStatement)
				{
					AEqStmtStatement old = (AEqStmtStatement)symbolicTable.get(((TId)((AIdentExpression) node.getRExpr()).getId()).toString());
					PExpression equals = old.getRExpr();
					AEqStmtStatement update = new AEqStmtStatement((AIdentExpression) node.getLExpr(), equals);
					symbolicTable.put(name, update);
				}
            }
        }else if(node.getRExpr() instanceof AIdentifierExpression){
            TId id = ((AIdentExpression)((AIdentifierExpression)node.getRExpr()).getExpression()).getId();
			String typeName = id.toString();
	
			if(symbolicTable.containsKey(typeName))
			{
				symbolicTable.put(name, node.getRExpr());
			}
        }else{
            symbolicTable.put(name, node);
        }
    }

    public void inAArayStmtStatement(AArrayStmtStatement node){
        TId id = ((AIdentExpression)node.getLExp()).getId();
		String arrName = id.toString();

		symbolicTable.put(arrName, node);
    }

    public void inAIdentExpression(AIdentExpression node){
        if(!(node.parent() instanceof AFunction))
		{
			TId id = node.getId();
			String fName = id.toString();
			int line = id.getLine();
			if(node.parent() instanceof AFunctionCall)
			{
				boolean exists = false;
				for(AFunction fun:Functions)
				{
					fun = (AFunction) fun.clone();
					if(fName.equals(((AIdentExpression)fun.getExpression()).getId().toString()))
					{
						exists = true;
					}
				}
	
				if(!exists)
				{
					System.out.println("Line " + line + ": " +" Function " + fName +" is not defined");
				}
			}
			else
			{
				if(!symbolicTable.containsKey(fName))
				{
					System.out.println("Line " + line + ": " +" Variable " + fName +" is not defined");
				}
			}
		}
    }

    public void inADiveqStmtStatement(ADiveqStmtStatement node){
        TId lId = ((TId)((AIdentExpression) node.getLExpr()).getId());	
		
		if(symbolicTable.containsKey(lId.toString()))
		{
			String fName = lId.toString();
			AEqStmtStatement old = ((AEqStmtStatement)symbolicTable.get(lId.toString()));
			PExpression oldExp = old.getRExpr();

			ADivisionExpression divExp = new ADivisionExpression(new AParExpression(old.getRExpr()), node.getRExpr());
			ArithmeticData data = checkExpression(divExp);
			
			if(!checkArithmetics(data))
			{
				AEqStmtStatement update = new AEqStmtStatement(node.getLExpr(), divExp);
				symbolicTable.put(fName, update);
			}
			else
			{
				AEqStmtStatement update = new AEqStmtStatement(node.getLExpr(), oldExp);
				symbolicTable.put(fName, update);
			}
		}
    }

    public void inAMinusEqualsStatement(AMineqStmtStatement node)
	{
		TId lId = ((TId)((AIdentExpression) node.getLExpr()).getId());

		if(symbolicTable.containsKey(lId.toString()))
		{
			String fName = lId.toString();
			AEqStmtStatement old = ((AEqStmtStatement)symbolicTable.get(lId.toString()));
			PExpression oldExp = old.getRExpr();

			AMinusExpression divExp = new AMinusExpression(new AParExpression(old.getRExpr()), node.getLExpr());
			ArithmeticData data = checkExpression(divExp);
			
			if(!checkArithmetics(data))
			{
				AEqStmtStatement update = new AEqStmtStatement(node.getLExpr(), divExp);
				symbolicTable.put(fName, update);
			}
			else
			{
				AEqStmtStatement update = new AEqStmtStatement(node.getLExpr(), oldExp);
				symbolicTable.put(fName, update);
			}
		}
	}

	public void inAPlusplusExpression(APlusplusExpression node)
	{
		if(node.getExpression() instanceof AIdentExpression)
		{
			TId id = ((TId)((AIdentExpression) node.getExpression()).getId());
			if(symbolicTable.containsKey(id.toString()))
			{
				String fName = id.toString();
				AEqStmtStatement old = ((AEqStmtStatement)symbolicTable.get(id.toString()));
				PExpression oldExp = (PExpression)old.getRExpr().clone();
	
				AAddExpression plplExp = new AAddExpression(new AParExpression((PExpression)old.getRExpr().clone()), new ANumExpression(new TNumber("1", id.getLine(), id.getPos())));

				ArithmeticData data = checkExpression(plplExp);
				if(!checkArithmetics(data))
				{
					AEqStmtStatement update = new AEqStmtStatement(node.getExpression(), plplExp);
					symbolicTable.put(fName, update);
				}
				else
				{
					AEqStmtStatement update = new AEqStmtStatement(node.getExpression(), oldExp);
					symbolicTable.put(fName, update);
				}
			}
		}
		else
		{
			AAddExpression plplExp = new AAddExpression(new AParExpression((PExpression)node.getExpression().clone()), new ANumExpression(new TNumber("1")));
			checkArithmetics(checkExpression(plplExp));
		}
	}

	public void inAMinusminusExpression(AMinminExpression node)
	{
		if(node.getExpression() instanceof AIdentExpression)
		{
			TId id = ((TId)((AIdentExpression) node.getExpression()).getId());
			if(symbolicTable.containsKey(id.toString()))
			{
				String fName = id.toString();
				AEqStmtStatement old = ((AEqStmtStatement)symbolicTable.get(id.toString()));
				PExpression oldExp = (PExpression)old.getRExpr().clone();
	
				AMinusExpression plplExp = new AMinusExpression(new AParExpression((PExpression)old.getRExpr().clone()), new ANumExpression(new TNumber("1", id.getLine(), id.getPos())));

				ArithmeticData data = checkExpression(plplExp);
				if(!checkArithmetics(data))
				{
					AEqStmtStatement update = new AEqStmtStatement(node.getExpression(), plplExp);
					symbolicTable.put(fName, update);
				}
				else
				{
					AEqStmtStatement update = new AEqStmtStatement(node.getExpression(), oldExp);
					symbolicTable.put(fName, update);
				}
			}
		}
		else
		{
			AMinusExpression plplExp = new AMinusExpression(new AParExpression((PExpression)node.getExpression().clone()), new ANumExpression(new TNumber("1")));
			checkArithmetics(checkExpression(plplExp));
		}
	}



	public void inAAdditionExpression(AAddExpression node)
	{
		ArithmeticData data = checkParent(node);
		if(data.getTokens().size() > 0) checkArithmetics(data);
	}

	public void inASubtractionExpression(AMinusExpression node)
	{
		ArithmeticData data = checkParent(node);
		if(data.getTokens().size() > 0) checkArithmetics(data);
	}

	public void inAMultiplicationExpression(AMultiplicationExpression node)
	{
		ArithmeticData data = checkParent(node);
		if(data.getTokens().size() > 0) checkArithmetics(data);
	}

	public void inADivExpression(ADivisionExpression node)
	{
		ArithmeticData data = checkParent(node);
		if(data.getTokens().size() > 0) checkArithmetics(data);
	}

	public void inAModExpression(AModulusExpression node)
	{
		ArithmeticData data = checkParent(node);
		if(data.getTokens().size() > 0) checkArithmetics(data);
	}

	public void inAExponentialExpression(AExponExpression node)
	{
		ArithmeticData data = checkParent(node);
		if(data.getTokens().size() > 0) checkArithmetics(data);
	}

	public void inAFunStatement(AFuncExpression node)
	{
		AFunctionCall funCall = (AFunctionCall)node.getFunctionCall();
		funCall = (AFunctionCall)funCall.clone();
		TId id = ((TId)((AIdentExpression) funCall.getExpression()).getId());	
        String fName = id.toString();
        int line = id.getLine();
		boolean found = false;
		boolean foundCorrect = false;
		int numOfArgs = 0;
		
		for(AFunction fun: Functions)
		{
			fun = (AFunction)fun.clone();
			String funName = ((AIdentExpression)fun.getExpression()).getId().toString();
			if(fName.equals(funName))
			{
				numOfArgs = 0; 
				found = true;

				if(funCall.getArglist().size() != 0)
				{
					numOfArgs++;
					if(((AArglist)funCall.getArglist().get(0)).getRExpr().size() != 0) numOfArgs = numOfArgs +  ((AArglist)funCall.getArglist().get(0)).getRExpr().size();
				}
				
				if(checkArgSize(line, numOfArgs, fun, true))
				{
					foundCorrect = true;
					symbolicTable.put(fName, runFunction(fun, funCall));
				}
			}
		}

		if(found)
		{
			if(!foundCorrect)
			{
				for(AFunction fun: Functions)
				{
					fun = (AFunction)fun.clone();
					String funName = ((AIdentExpression)fun.getExpression()).getId().toString();
					if(fName.equals(funName))
					{
						checkArgSize(line, numOfArgs, fun, false);
					}
				}
			}
		}
	}

	public void inAFunExpression(AFuncExpression node)
	{
		if(!(node.parent().clone() instanceof AArglist))
		{
			AFunctionCall funCall = (AFunctionCall)node.getFunctionCall();
			funCall = (AFunctionCall)funCall.clone();
			TId id = ((TId)((AIdentExpression) funCall.getExpression()).getId());	
			String fName = id.toString();
			int line = id.getLine();
			boolean found = false;
			boolean foundCorrect = false;
			int numOfArgs = 0;
			
			for(AFunction fun: Functions)
			{
				fun = (AFunction)fun.clone();
				String funName = ((AIdentExpression)fun.getExpression()).getId().toString();
				if(fName.equals(funName))
				{
					numOfArgs = 0; 
					found = true;
	
					if(funCall.getArglist().size() != 0)
					{
						numOfArgs++;
						if(((AArglist)funCall.getArglist().get(0)).getRExpr().size() != 0) numOfArgs = numOfArgs +  ((AArglist)funCall.getArglist().get(0)).getRExpr().size();
					}
					
					if(checkArgSize(line, numOfArgs, fun, true))
					{
						foundCorrect = true;
						symbolicTable.put(fName, runFunction(fun, funCall));
					}
				}
			}
	
			if(found)
			{
				if(!foundCorrect)
				{
					for(AFunction fun: Functions)
					{
						fun = (AFunction)fun.clone();
						String funName = ((AIdentExpression)fun.getExpression()).getId().toString();
						if(fName.equals(funName))
						{
							checkArgSize(line, numOfArgs, fun, false);
						}
					}
				}
			}
		}
	}

	public void inAFuncExpression(AFuncExpression node)
	{
		if(!(node.parent().clone() instanceof AArglist))
		{
			AFunctionCall funCall = (AFunctionCall)node.getFunctionCall();
			funCall = (AFunctionCall)funCall.clone();
			TId id = ((TId)((AIdentExpression) funCall.getExpression()).getId());	
			String fName = id.toString();
			int line = id.getLine();
			boolean found = false;
			boolean foundCorrect = false;
			int numOfArgs = 0;
			
			for(AFunction fun: Functions)
			{
				fun = (AFunction)fun.clone();
				String funName = ((AIdentExpression)fun.getExpression()).getId().toString();
				if(fName.equals(funName))
				{
					numOfArgs = 0; 
					found = true;
	
					if(funCall.getArglist().size() != 0)
					{
						numOfArgs++;
						if(((AArglist)funCall.getArglist().get(0)).getRExpr().size() != 0) numOfArgs = numOfArgs +  ((AArglist)funCall.getArglist().get(0)).getRExpr().size();
					}
					
					if(checkArgSize(line, numOfArgs, fun, true))
					{
						foundCorrect = true;
						symbolicTable.put(fName, runFunction(fun, funCall));
					}
				}
			}
	
			if(found)
			{
				if(!foundCorrect)
				{
					for(AFunction fun: Functions)
					{
						fun = (AFunction)fun.clone();
						String funName = ((AIdentExpression)fun.getExpression()).getId().toString();
						if(fName.equals(funName))
						{
							checkArgSize(line, numOfArgs, fun, false);
						}
					}
				}
			}
		}
	}

    public ArithmeticData checkParent(PExpression node)
	{
		ArithmeticData arithmeticData = new ArithmeticData();
		
		if((node.parent() instanceof PStatement) && (!(node.parent() instanceof AReturnStmtStatement)))
		{
			if(!(node.parent().parent() instanceof AFunction))
			{
				arithmeticData = checkExpression(node);
				arithmeticData.setTokens(arithmeticData.getTokens());
				arithmeticData.setExpressions(arithmeticData.getExpressions());
			}
		}

		if(node.parent() instanceof AArglist)
		{
			arithmeticData = checkExpression(node);
			arithmeticData.setTokens(arithmeticData.getTokens());
			arithmeticData.setExpressions(arithmeticData.getExpressions());
		}

		if(node.parent() instanceof PComparison)
		{
			arithmeticData = checkExpression(node);
			arithmeticData.setTokens(arithmeticData.getTokens());
			arithmeticData.setExpressions(arithmeticData.getExpressions());
		}

		return arithmeticData;
	}

	public ArithmeticData checkExpression(PExpression node)
	{
		List<Token> tokens =  new ArrayList<Token>();
		List<PExpression> expressions =  new ArrayList<PExpression>();
		ArithmeticData arithmeticData = new ArithmeticData(); 
		/**
		 * ----------- ALL TOKENS HERE -----------
		 */

		if(node instanceof AIdentExpression)
		{
			TId id = ((TId)((AIdentExpression) node).getId());
			if(!tokens.contains(id))
			{
				tokens.add(id);
				arithmeticData.addTokens(tokens);
			}	
		}

		if(node instanceof AParExpression)
		{
			arithmeticData.addAll(checkExpression(((AParExpression)node).getExpression()));
		}

		if(node instanceof AFuncExpression || node instanceof AFuncExpression)
		{
			AFunctionCall funCall;

			if(node instanceof AFuncExpression) funCall = (AFunctionCall)((AFuncExpression)node).getFunctionCall();
			else funCall = (AFunctionCall)((AFuncExpression)node).getFunctionCall();

			funCall = (AFunctionCall)funCall.clone();
			TId id = ((TId)((AIdentExpression) funCall.getExpression()).getId());	
			String fName = id.toString();
			int line = id.getLine();
			boolean found = false;
			boolean foundCorrect = false;
			int numOfArgs = 0;
			
			for(AFunction fun: Functions)
			{
				fun = (AFunction)fun.clone();
				String funName = ((AIdentExpression)fun.getExpression()).getId().toString();
				if(fName.equals(funName))
				{
					numOfArgs = 0; 
					found = true;
	
					if(funCall.getArglist().size() != 0)
					{
						numOfArgs++;
						if(((AArglist)funCall.getArglist().get(0)).getRExpr().size() != 0) numOfArgs = numOfArgs +  ((AArglist)funCall.getArglist().get(0)).getRExpr().size();
					}
					
					if(checkArgSize(line, numOfArgs, fun, true))
					{
						foundCorrect = true;
						PStatement ret = runFunction(fun, funCall);

						ret = (PStatement) ret.clone();
						if(ret instanceof AReturnStmtStatement)
						{
							PExpression retExp = (PExpression)((AReturnStmtStatement)ret).getExpression().clone();
							arithmeticData.addAll(checkExpression(retExp));
						}
						else
						{
							arithmeticData.addAll(checkExpression(new ANoneExpression(new TNone(id.getLine(), id.getPos()))));
						}
					}
				}
			}
	
			if(found)
			{
				if(!foundCorrect)
				{
					for(AFunction fun: Functions)
					{
						fun = (AFunction)fun.clone();
						String funName = ((AIdentExpression)fun.getExpression()).getId().toString();
						if(fName.equals(funName))
						{
							checkArgSize(line, numOfArgs, fun, false);
						}
					}
				}
			}
		}

		if(node instanceof ANoneExpression)
		{
			TNone id = ((TNone)((ANoneExpression) node).getNone());
			if(!tokens.contains(id))
			{
				tokens.add(id);
				arithmeticData.addTokens(tokens);
				expressions.add(node);
				arithmeticData.addExpressions(expressions);
			}	
		}

		if(node instanceof AStrExpression)
		{
			TString id = ((TString)((AStrExpression) node).getString());
			if(!tokens.contains(id))
			{
				tokens.add(id);
				arithmeticData.addTokens(tokens);
			}	
		}

		if(node instanceof ANumExpression)
		{
			TNumber id = ((TNumber)((ANumExpression) node).getNumber());
			if(!tokens.contains(id))
			{
				tokens.add(id);
				arithmeticData.addTokens(tokens);
			}	
		}

		if(node instanceof AIdentifierExpression)
		{
			TId id = ((TId)((AIdentExpression)((AIdentifierExpression) node).getExpression()).getId());
			if(!tokens.contains(id))
			{
				expressions.add(node);
				arithmeticData.addExpressions(expressions);
			}	
		}

		/**
		 * ----------- ALL ARITHMETICS HERE -----------
		 */

		if(node instanceof AAddExpression)
		{
			((AAddExpression)node).setLExp(checkElement(((AAddExpression)node).getLExp()));
			((AAddExpression)node).setRExp(checkElement(((AAddExpression)node).getRExp()));
			PExpression lExp = (PExpression)((AAddExpression)node).getLExp();
			PExpression rExp = (PExpression)((AAddExpression)node).getRExp();

			ArithmeticData lData = checkExpression(lExp);
			ArithmeticData rData = checkExpression(rExp);

			tokens.addAll(lData.getTokens());
			tokens.addAll(rData.getTokens());

			expressions.addAll(lData.getExpressions());
			expressions.addAll(rData.getExpressions());
			expressions.add(node);
			
			arithmeticData.addTokens(tokens); 
			arithmeticData.addExpressions(expressions);
		}


		if(node instanceof AMinusExpression)
		{
			((AMinusExpression)node).setLExp(checkElement(((AMinusExpression)node).getLExp()));
			((AMinusExpression)node).setRExp(checkElement(((AMinusExpression)node).getRExp()));
			PExpression lExp = (PExpression)((AMinusExpression)node).getLExp();
			PExpression rExp = (PExpression)((AMinusExpression)node).getRExp();
			
			ArithmeticData lData = checkExpression(lExp);
			ArithmeticData rData = checkExpression(rExp);

			tokens.addAll(lData.getTokens());
			tokens.addAll(rData.getTokens());

			expressions.addAll(lData.getExpressions());
			expressions.addAll(rData.getExpressions());
			expressions.add(node);
			
			arithmeticData.addTokens(tokens); 
			arithmeticData.addExpressions(expressions);
		}

		if(node instanceof AMultiplicationExpression)
		{
			((AMultiplicationExpression)node).setLExp(checkElement(((AMultiplicationExpression)node).getLExp()));
			((AMultiplicationExpression)node).setRExp(checkElement(((AMultiplicationExpression)node).getRExp()));
			PExpression lExp = (PExpression)((AMultiplicationExpression)node).getLExp();
			PExpression rExp = (PExpression)((AMultiplicationExpression)node).getRExp();
			
			ArithmeticData lData = checkExpression(lExp);
			ArithmeticData rData = checkExpression(rExp);

			tokens.addAll(lData.getTokens());
			tokens.addAll(rData.getTokens());

			expressions.addAll(lData.getExpressions());
			expressions.addAll(rData.getExpressions());
			expressions.add(node);

			arithmeticData.addTokens(tokens); 
			arithmeticData.addExpressions(expressions);
		}

		if(node instanceof ADivisionExpression)
		{
			((ADivisionExpression)node).setLExp(checkElement(((ADivisionExpression)node).getLExp()));
			((ADivisionExpression)node).setRExp(checkElement(((ADivisionExpression)node).getRExp()));
			PExpression lExp = (PExpression)((ADivisionExpression)node).getLExp();
			PExpression rExp = (PExpression)((ADivisionExpression)node).getRExp();
			
			ArithmeticData lData = checkExpression(lExp);
			ArithmeticData rData = checkExpression(rExp);

			tokens.addAll(lData.getTokens());
			tokens.addAll(rData.getTokens());

			expressions.addAll(lData.getExpressions());
			expressions.addAll(rData.getExpressions());
			expressions.add(node);

			arithmeticData.addTokens(tokens); 
			arithmeticData.addExpressions(expressions);
		}

		if(node instanceof AModulusExpression)
		{
			((AModulusExpression)node).setLExp(checkElement(((AModulusExpression)node).getLExp()));
			((AModulusExpression)node).setRExp(checkElement(((AModulusExpression)node).getRExp()));
			PExpression lExp = (PExpression)((AModulusExpression)node).getLExp();
			PExpression rExp = (PExpression)((AModulusExpression)node).getRExp();
			
			ArithmeticData lData = checkExpression(lExp);
			ArithmeticData rData = checkExpression(rExp);

			tokens.addAll(lData.getTokens());
			tokens.addAll(rData.getTokens());

			expressions.addAll(lData.getExpressions());
			expressions.addAll(rData.getExpressions());
			expressions.add(node);

			arithmeticData.addTokens(tokens); 
			arithmeticData.addExpressions(expressions);
		}

		if(node instanceof AExponExpression)
		{
			((AExponExpression)node).setLExp(checkElement(((AExponExpression)node).getLExp()));
			((AExponExpression)node).setRExp(checkElement(((AExponExpression)node).getRExp()));
			PExpression lExp = (PExpression)((AExponExpression)node).getLExp();
			PExpression rExp = (PExpression)((AExponExpression)node).getRExp();
			
			ArithmeticData lData = checkExpression(lExp);
			ArithmeticData rData = checkExpression(rExp);

			tokens.addAll(lData.getTokens());
			tokens.addAll(rData.getTokens());

			expressions.addAll(lData.getExpressions());
			expressions.addAll(rData.getExpressions());
			expressions.add(node);

			arithmeticData.addTokens(tokens); 
			arithmeticData.addExpressions(expressions);
		}
		
		return arithmeticData;
	}

	public boolean checkArithmetics(ArithmeticData data)
	{
		boolean additionOnly = true;
		boolean multOnly = true;
		int line = 0;
		
		for(PExpression expression:data.getExpressions())
		{
			if(expression instanceof ANoneExpression) 
			{
				line = ((TNone)((ANoneExpression)expression).getNone()).getLine();
				System.out.println("Line " + line + ":  None expression in arithmetics");
			}
			
			else if(expression instanceof AIdentifierExpression) 
			{
				line = ((TId)((AIdentExpression)((AIdentifierExpression)expression).getExpression()).getId()).getLine();
				System.out.println("Line " + line + ":  Type expression in arithmetics");
			}

			else if(expression instanceof AMinusExpression)
			{
				additionOnly = false;
				multOnly = false;
			}
			else if(expression instanceof AMultiplicationExpression)
			{
				additionOnly = false;
			}
			else if(expression instanceof ADivisionExpression)
			{
				additionOnly = false;
				multOnly = false;
			}
			else if(expression instanceof AModulusExpression)
			{
				additionOnly = false;
				multOnly = false;
			}
			else if(expression instanceof AExponExpression)
			{
				additionOnly = false;
				multOnly = false;
			}
			else if(expression instanceof AAddExpression)
			{
				multOnly = false;
			}
		}
		
		return checkTokens(data.getTokens(), additionOnly, multOnly);
	}

	public boolean checkTokens(List<Token> tokenIds, boolean addOnly, boolean multOnly)
	{
		boolean foundString = false;
		boolean foundNumber = false;
		boolean foundDouble = false;
		boolean foundError = false;
		int line = 0;
		
		for(Token token:tokenIds)
		{
			if(token instanceof TString)
			{
				foundString = true;
				line = token.getLine();
			}
			if(token instanceof TNumber)
			{
				foundNumber = true;
				if(token.getText().contains(".")) foundDouble = true;
				line = token.getLine();
			}
			while(token instanceof TId)
			{
				if(symbolicTable.containsKey(token.toString()))
				{
					if(symbolicTable.get(token.toString()) instanceof AEqStmtStatement)
					{
						ArithmeticData d = checkExpression(((AEqStmtStatement)symbolicTable.get(token.toString())).getRExpr());

						if(d.getTokens().size() >= 1)
						{
							if(d.getTokens().get(0) instanceof TString) 
							{
								foundString = true;
								line = token.getLine();
								break;
							}

							if(d.getTokens().get(0) instanceof TNumber) 
							{
								foundNumber = true;
								if(d.getTokens().get(0).getText().contains(".")) foundDouble = true;
								line = token.getLine();
								break;
							}

							if(d.getTokens().get(0) instanceof TNone)
							{
								break;
							}

							if(d.getTokens().get(0) instanceof TId)
							{
								if(!symbolicTable.containsKey(d.getTokens().get(0).toString()))
								{
									break;
								}
								else
								{
									token = d.getTokens().get(0);
								}
							}
						}
					}
				}
				else
				{
					break;
				}
			}
		}

		if(addOnly)
		{
			if(foundNumber && foundString)
			{
				foundError = true;
				System.out.println("Line " + line + ":  Found string(s) and number(s) in addition");
			}
		}
		else if(multOnly)
		{
			if(foundDouble)
			{
				foundError = true;
				System.out.println("Line " + line + ":  strings can only be multiplied with integers");
			}
			if(!foundNumber && foundString)
			{
				foundError = true;
				System.out.println("Line " + line + ":  strings can only be multiplied with integers");
			}
		}
		else
		{
			if(foundString)
			{
				foundError = true;
				System.out.println("Line " + line + ":  strings can only be used in additions");
			}
		}
		
		return foundError;
	}

	public boolean checkArgSize(int line, int numOfArgs, AFunction node, boolean arithmetics)
	{
		boolean isCorrect = true; //checks if argsize is correct for the defined function.
		boolean isDefault;
		AArgument arg;
		TypedLinkedList arguments;
		int minArgs = 0;
		int maxArgs = 0;
		arguments = ((TypedLinkedList)node.getArgument());
		
		if(arguments.size() != 0)
		{
			maxArgs++;
			arg = ((AArgument)arguments.get(0));
			TypedLinkedList moreIdentifiers = ((TypedLinkedList)arg.getArgumentHelper());
			isDefault = arg.getEqVal().size() == 0;

			if(!isDefault) //if it is a default value, it can be skipped during the function call.
			{
				minArgs++;
			}

			for(int i=0; i<moreIdentifiers.size(); i++)
			{
				maxArgs++;
				isDefault = ((AArgumentHelper)moreIdentifiers.get(i)).getEqVal().size() == 0;
				if(!isDefault)
				{
					minArgs++;
				}
			}
		}

		minArgs = maxArgs - minArgs;

		if(numOfArgs < minArgs || numOfArgs > maxArgs)
		{
			isCorrect = false;
			if(!arithmetics) System.out.println("Line " + line + ":  Found " + numOfArgs + " arguments, expected [" + minArgs + ", " + maxArgs + "]");
		}

		return isCorrect;
	}

	public PStatement runFunction(AFunction fun, AFunctionCall funCall)
	{
		fun = (AFunction) fun.clone();
		funCall = (AFunctionCall) funCall.clone();
		PStatement returnStatement = (PStatement)fun.getStatement().clone();
		ArithmeticData data = new ArithmeticData();
		if(funCall.getArglist().size()>0)
		{
			AArglist argFunCall = (AArglist)funCall.getArglist().get(0);
			AArgument argFun = (AArgument)fun.getArgument().get(0);

			AIdentExpression search = (AIdentExpression)argFun.getExpression().clone();
			PExpression assign = (PExpression)argFunCall.getLExpr().clone();
			
			if(assign instanceof AFuncExpression || assign instanceof AFuncExpression)
			{
				PStatement statement;
				if(assign instanceof AFuncExpression)
				{
					statement = runFunction(fun, (AFunctionCall)((AFuncExpression)assign).getFunctionCall());
				} 
				else
				{
					statement = runFunction(fun, (AFunctionCall)((AFuncExpression)assign).getFunctionCall());
				}
				
				if(statement instanceof AReturnStmtStatement)
				{
					assign = (PExpression)((AReturnStmtStatement)statement).getExpression().clone();
					data.addAll(checkExpression(assign));
				}
				else
				{
					int line = ((TId)search.getId()).getLine();
					System.out.println("Line " + line + ": no return statement found");
					assign = new ANoneExpression(new TNone(line, ((TId)search.getId()).getPos()));
					data.addAll(checkExpression(assign));
				}
			}

			AEqStmtStatement update = new AEqStmtStatement(search, assign);
			symbolicTable.put(search.getId().toString(), update);
			if(argFunCall.getRExpr().size()>0)
			{
				for(int i=0; i<argFunCall.getRExpr().size(); i++)
				{
					AArgumentHelper moreIds = (AArgumentHelper)argFun.getArgumentHelper().get(i);
					assign = (PExpression)((AArglist)argFunCall.clone()).getRExpr().get(i);
					search = (AIdentExpression)((AArgumentHelper)moreIds.clone()).getExpression();
					
					if(assign instanceof AFuncExpression || assign instanceof AFuncExpression)
					{
						PStatement statement;
						if(assign instanceof AFuncExpression)
						{
							statement = runFunction(fun, (AFunctionCall)((AFuncExpression)assign).getFunctionCall());
						} 
						else
						{
							statement = runFunction(fun, (AFunctionCall)((AFuncExpression)assign).getFunctionCall());
						}

						if(statement instanceof AReturnStmtStatement)
						{
							assign = ((AReturnStmtStatement)statement).getExpression();
							data.addAll(checkExpression(assign));
						}
						else
						{
							int line = ((TId)search.getId()).getLine();
							System.out.println("Line " + line + ": no return statement found");
							assign = new ANoneExpression(new TNone(line, ((TId)search.getId()).getPos()));
							data.addAll(checkExpression(assign));
						}
					}

					update = new AEqStmtStatement(search, assign);
					symbolicTable.put(search.getId().toString(), update);
				}
			}
		}
		
		if(fun.getStatement() instanceof AReturnStmtStatement)
		{
			PExpression returnExp = ((AReturnStmtStatement)fun.getStatement()).getExpression();
			data.addAll(checkExpression(returnExp));
			
			checkArithmetics(data);
		}
		else if(fun.getStatement() instanceof APrintStmtStatement)
		{
			APrintStmtStatement print = (APrintStmtStatement)fun.getStatement();
			checkArithmetics(checkExpression(print.getLExpr()));
			if(print.getRExpr().size() > 0)
			{
				for(int i=0; i<print.getRExpr().size(); i++)
				{
					checkArithmetics(checkExpression((PExpression)print.getRExpr().get(i)));
				}
			}
		}
		return returnStatement;
	}

	public PExpression checkElement(PExpression exp)
	{
		exp = (PExpression)exp.clone();
		if(exp instanceof AIdentExpression)
		{
			AIdentExpression id = (AIdentExpression)exp;
			if(symbolicTable.containsKey(id.getId().toString()))
			{
				if(symbolicTable.get(id.getId().toString()) instanceof AEqStmtStatement)
				{
					AEqStmtStatement old = (AEqStmtStatement) symbolicTable.get(id.getId().toString());

					if((PExpression)old.getRExpr().clone() instanceof AStrExpression)
					{
						((AStrExpression)old.getRExpr()).getString().setLine(id.getId().getLine());
					}
					if((PExpression)old.getRExpr().clone() instanceof ANumExpression)
					{
						((ANumExpression)old.getRExpr()).getNumber().setLine(id.getId().getLine());
					}
					if((PExpression)old.getRExpr().clone() instanceof ANoneExpression)
					{
						((ANoneExpression)old.getRExpr()).getNone().setLine(id.getId().getLine());
					}
					
					return (PExpression)old.getRExpr().clone();
				}
			}
		}
		return exp;
	}
}

