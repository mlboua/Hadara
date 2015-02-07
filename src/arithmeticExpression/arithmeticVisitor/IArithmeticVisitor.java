package arithmeticExpression.arithmeticVisitor;

import arithmeticExpression.CArithmeticTerm;
import arithmeticExpression.CMinus;
import arithmeticExpression.CMult;
import arithmeticExpression.CPlus;
import generalExpression.CNumber;
import generalExpression.CVariable;

/**
 * User: LoW
 * Date: 13/02/13
 * Time: 10:46
 */
public interface IArithmeticVisitor
{
    public Object visit(CArithmeticTerm node, Object data);
    public Object visit(CMinus node, Object data);
    public Object visit(CPlus node, Object data);
    public Object visit(CMult node, Object data);
    public Object visit(CVariable node, Object data);
    public Object visit(CNumber node, Object data);
}
