/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import minipython.analysis.*;

public final class TMinmin extends Token
{
    public TMinmin()
    {
        super.setText("--");
    }

    public TMinmin(int line, int pos)
    {
        super.setText("--");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TMinmin(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTMinmin(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TMinmin text.");
    }
}