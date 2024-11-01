/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class AMineqStmtStatement extends PStatement
{
    private PExpression _lExpr_;
    private PExpression _rExpr_;

    public AMineqStmtStatement()
    {
    }

    public AMineqStmtStatement(
        PExpression _lExpr_,
        PExpression _rExpr_)
    {
        setLExpr(_lExpr_);

        setRExpr(_rExpr_);

    }
    public Object clone()
    {
        return new AMineqStmtStatement(
            (PExpression) cloneNode(_lExpr_),
            (PExpression) cloneNode(_rExpr_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMineqStmtStatement(this);
    }

    public PExpression getLExpr()
    {
        return _lExpr_;
    }

    public void setLExpr(PExpression node)
    {
        if(_lExpr_ != null)
        {
            _lExpr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _lExpr_ = node;
    }

    public PExpression getRExpr()
    {
        return _rExpr_;
    }

    public void setRExpr(PExpression node)
    {
        if(_rExpr_ != null)
        {
            _rExpr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _rExpr_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_lExpr_)
            + toString(_rExpr_);
    }

    void removeChild(Node child)
    {
        if(_lExpr_ == child)
        {
            _lExpr_ = null;
            return;
        }

        if(_rExpr_ == child)
        {
            _rExpr_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_lExpr_ == oldChild)
        {
            setLExpr((PExpression) newChild);
            return;
        }

        if(_rExpr_ == oldChild)
        {
            setRExpr((PExpression) newChild);
            return;
        }

    }
}
