/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class ASqbrExpression extends PExpression
{
    private PExpression _lExp_;
    private final LinkedList _rExp_ = new TypedLinkedList(new RExp_Cast());

    public ASqbrExpression()
    {
    }

    public ASqbrExpression(
        PExpression _lExp_,
        List _rExp_)
    {
        setLExp(_lExp_);

        {
            this._rExp_.clear();
            this._rExp_.addAll(_rExp_);
        }

    }
    public Object clone()
    {
        return new ASqbrExpression(
            (PExpression) cloneNode(_lExp_),
            cloneList(_rExp_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASqbrExpression(this);
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

    public LinkedList getRExp()
    {
        return _rExp_;
    }

    public void setRExp(List list)
    {
        _rExp_.clear();
        _rExp_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_lExp_)
            + toString(_rExp_);
    }

    void removeChild(Node child)
    {
        if(_lExp_ == child)
        {
            _lExp_ = null;
            return;
        }

        if(_rExp_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_lExp_ == oldChild)
        {
            setLExp((PExpression) newChild);
            return;
        }

        for(ListIterator i = _rExp_.listIterator(); i.hasNext();)
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

    private class RExp_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PExpression node = (PExpression) o;

            if((node.parent() != null) &&
                (node.parent() != ASqbrExpression.this))
            {
                node.parent().removeChild(node);
            }

            if((node.parent() == null) ||
                (node.parent() != ASqbrExpression.this))
            {
                node.parent(ASqbrExpression.this);
            }

            return node;
        }
    }
}
