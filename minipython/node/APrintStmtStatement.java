/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class APrintStmtStatement extends PStatement
{
    private PExpression _lExpr_;
    private final LinkedList _rExpr_ = new TypedLinkedList(new RExpr_Cast());

    public APrintStmtStatement()
    {
    }

    public APrintStmtStatement(
        PExpression _lExpr_,
        List _rExpr_)
    {
        setLExpr(_lExpr_);

        {
            this._rExpr_.clear();
            this._rExpr_.addAll(_rExpr_);
        }

    }
    public Object clone()
    {
        return new APrintStmtStatement(
            (PExpression) cloneNode(_lExpr_),
            cloneList(_rExpr_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPrintStmtStatement(this);
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

    public LinkedList getRExpr()
    {
        return _rExpr_;
    }

    public void setRExpr(List list)
    {
        _rExpr_.clear();
        _rExpr_.addAll(list);
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

        if(_rExpr_.remove(child))
        {
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

        for(ListIterator i = _rExpr_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set(newChild);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

    }

    private class RExpr_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PExpression node = (PExpression) o;

            if((node.parent() != null) &&
                (node.parent() != APrintStmtStatement.this))
            {
                node.parent().removeChild(node);
            }

            if((node.parent() == null) ||
                (node.parent() != APrintStmtStatement.this))
            {
                node.parent(APrintStmtStatement.this);
            }

            return node;
        }
    }
}
