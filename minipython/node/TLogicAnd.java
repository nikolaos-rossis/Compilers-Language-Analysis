/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import minipython.analysis.*;

public final class TLogicAnd extends Token
{
    public TLogicAnd()
    {
        super.setText("&&");
    }

    public TLogicAnd(int line, int pos)
    {
        super.setText("&&");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TLogicAnd(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTLogicAnd(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TLogicAnd text.");
    }
}
