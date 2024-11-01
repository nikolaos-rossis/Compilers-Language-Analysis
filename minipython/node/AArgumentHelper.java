/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class AArgumentHelper extends PArgumentHelper
{
    private PExpression _expression_;
    private final LinkedList _eqVal_ = new TypedLinkedList(new EqVal_Cast());

    public AArgumentHelper()
    {
    }

    public AArgumentHelper(
        PExpression _expression_,
        List _eqVal_)
    {
        setExpression(_expression_);

        {
            this._eqVal_.clear();
            this._eqVal_.addAll(_eqVal_);
        }

    }
    public Object clone()
    {
        return new AArgumentHelper(
            (PExpression) cloneNode(_expression_),
            cloneList(_eqVal_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAArgumentHelper(this);
    }

    public PExpression getExpression()
    {
        return _expression_;
    }

    public void setExpression(PExpression node)
    {
        if(_expression_ != null)
        {
            _expression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _expression_ = node;
    }

    public LinkedList getEqVal()
    {
        return _eqVal_;
    }

    public void setEqVal(List list)
    {
        _eqVal_.clear();
        _eqVal_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_expression_)
            + toString(_eqVal_);
    }

    void removeChild(Node child)
    {
        if(_expression_ == child)
        {
            _expression_ = null;
            return;
        }

        if(_eqVal_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_expression_ == oldChild)
        {
            setExpression((PExpression) newChild);
            return;
        }

        for(ListIterator i = _eqVal_.listIterator(); i.hasNext();)
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

    private class EqVal_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PEqVal node = (PEqVal) o;

            if((node.parent() != null) &&
                (node.parent() != AArgumentHelper.this))
            {
                node.parent().removeChild(node);
            }

            if((node.parent() == null) ||
                (node.parent() != AArgumentHelper.this))
            {
                node.parent(AArgumentHelper.this);
            }

            return node;
        }
    }
}
