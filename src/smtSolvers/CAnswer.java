package smtSolvers;

import java.util.HashMap;

/**
 * User: LoW
 * Date: 10/05/13
 * Time: 16:39
 */
public class CAnswer
{
    EAnswer m_Answer;
    HashMap<String,Integer> m_Model;
    public CAnswer()
    {
        this.m_Answer = EAnswer.UNKNOW;
        this.m_Model = null;
    }
    public CAnswer(EAnswer answer, HashMap<String,Integer> model)
    {
        this.m_Answer = answer;
        this.m_Model = model;
    }
    public void SetAnswer(EAnswer answer)
    {
        this.m_Answer = answer;
    }
    public void SetModel(HashMap<String,Integer> model)
    {
        this.m_Model = model;
    }
    public EAnswer GetAnswer()
    {
        return this.m_Answer;
    }
    public HashMap<String,Integer> GetModel()
    {
        return this.m_Model;
    }
}
