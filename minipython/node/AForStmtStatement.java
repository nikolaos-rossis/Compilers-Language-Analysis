/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class AForStmtStatement extends PStatement
{
    private PExpression _lId_;
    private PExpression _rId_;
    private PStatement _statement_;

    public AForStmtStatement()
    {
    }

    public AForStmtStatement(
        PExpression _lId_,
        PExpression _rId_,
        PStatement _statement_)
    {
        setLId(_lId_);

        setRId(_rId_);

        setStatement(_statement_);

    }
    public Object clone()
    {
        return new AForStmtStatement(
            (PExpression) cloneNode(_lId_),
            (PExpression) cloneNode(_rId_),
            (PStatement) cloneNode(_statement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAForStmtStatement(this);
    }

    public PExpression getLId()
    {
        return _lId_;
    }

    public void setLId(PExpression node)
    {
        if(_lId_ != null)
        {
            _lId_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _lId_ = node;
    }

    public PExpression getRId()
    {
        return _rId_;
    }

    public void setRId(PExpression node)
    {
        if(_rId_ != null)
        {
            _rId_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _rId_ = node;
    }

    public PStatement getStatement()
    {
        return _statement_;
    }

    public void setStatement(PStatement node)
    {
        if(_statement_ != null)
        {
            _statement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _statement_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_lId_)
            + toString(_rId_)
            + toString(_statement_);
    }

    void removeChild(Node child)
    {
        if(_lId_ == child)
        {
            _lId_ = null;
            return;
        }

        if(_rId_ == child)
        {
            _rId_ = null;
            return;
        }

        if(_statement_ == child)
        {
            _statement_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_lId_ == oldChild)
        {
            setLId((PExpression) newChild);
            return;
        }

        if(_rId_ == oldChild)
        {
            setRId((PExpression) newChild);
            return;
        }

        if(_statement_ == oldChild)
        {
            setStatement((PStatement) newChild);
            return;
        }

    }
}
