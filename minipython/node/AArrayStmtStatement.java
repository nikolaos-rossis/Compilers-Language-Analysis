/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class AArrayStmtStatement extends PStatement
{
    private PExpression _idExpr_;
    private PExpression _lExp_;
    private PExpression _rExp_;

    public AArrayStmtStatement()
    {
    }

    public AArrayStmtStatement(
        PExpression _idExpr_,
        PExpression _lExp_,
        PExpression _rExp_)
    {
        setIdExpr(_idExpr_);

        setLExp(_lExp_);

        setRExp(_rExp_);

    }
    public Object clone()
    {
        return new AArrayStmtStatement(
            (PExpression) cloneNode(_idExpr_),
            (PExpression) cloneNode(_lExp_),
            (PExpression) cloneNode(_rExp_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAArrayStmtStatement(this);
    }

    public PExpression getIdExpr()
    {
        return _idExpr_;
    }

    public void setIdExpr(PExpression node)
    {
        if(_idExpr_ != null)
        {
            _idExpr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _idExpr_ = node;
    }

    public PExpression getLExp()
    {
        return _lExp_;
    }

    public void setLExp(PExpression node)
    {
        if(_lExp_ != null)
        {
            _lExp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _lExp_ = node;
    }

    public PExpression getRExp()
    {
        return _rExp_;
    }

    public void setRExp(PExpression node)
    {
        if(_rExp_ != null)
        {
            _rExp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _rExp_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_idExpr_)
            + toString(_lExp_)
            + toString(_rExp_);
    }

    void removeChild(Node child)
    {
        if(_idExpr_ == child)
        {
            _idExpr_ = null;
            return;
        }

        if(_lExp_ == child)
        {
            _lExp_ = null;
            return;
        }

        if(_rExp_ == child)
        {
            _rExp_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_idExpr_ == oldChild)
        {
            setIdExpr((PExpression) newChild);
            return;
        }

        if(_lExp_ == oldChild)
        {
            setLExp((PExpression) newChild);
            return;
        }

        if(_rExp_ == oldChild)
        {
            setRExp((PExpression) newChild);
            return;
        }

    }
}