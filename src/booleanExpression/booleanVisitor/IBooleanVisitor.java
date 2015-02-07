package booleanExpression.booleanVisitor;

import arithmeticExpression.arithmeticVisitor.IArithmeticVisitor;
import booleanExpression.*;
import generalExpression.CNumber;
import generalExpression.CVariable;
import arithmeticExpression.CArithmeticTerm;
import arithmeticExpression.CMinus;
import arithmeticExpression.CMult;
import arithmeticExpression.CPlus;

/**
 * User: LoW
 * Date: 10/01/13
 * Time: 23:56
 */
public interface IBooleanVisitor extends IArithmeticVisitor
{
    public Object visit(CBooleanExpression node, Object data);
    public Object visit(CAnd node, Object data);
    public Object visit(CBooleanAtom node, Object data);
    public Object visit(CEquals node, Object data);
    public Object visit(CExist node, Object data);
    public Object visit(CForall node, Object data);
    public Object visit(CGreater node, Object data);
    public Object visit(CInDomain node, Object data);
    public Object visit(CNot node, Object data);
    public Object visit(COr node, Object data);
}
