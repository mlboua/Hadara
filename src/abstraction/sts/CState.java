package abstraction.sts;

import booleanExpression.CBooleanExpression;

/**
 * User: LoW
 * Date: 10/01/13
 * Time: 15:37
 */
public class CState
{
    String  m_Name;
    CBooleanExpression m_Expr;
    public CState(String name, CBooleanExpression bExpr)
    {
        this.m_Name = name;
        this.m_Expr = bExpr;
    }
    public String GetName()
    {
        return this.m_Name;
    }
    public CBooleanExpression getExpr()
    {
        return this.m_Expr;
    }

    public String toString()
    {
        return this.m_Name+" : "+this.m_Expr.toString();
    }
    public boolean equals(Object that)
    {
        if ( this == that ) return true;

        if ( !(that instanceof CState) ) return false;

        CState typedThat = (CState)that;

        return this.GetName().equals(typedThat.GetName());
    }
}
