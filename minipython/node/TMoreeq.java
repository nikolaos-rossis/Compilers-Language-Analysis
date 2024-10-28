/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import minipython.analysis.*;

public final class TMoreeq extends Token
{
    public TMoreeq()
    {
        super.setText(">=");
    }

    public TMoreeq(int line, int pos)
    {
        super.setText(">=");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TMoreeq(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTMoreeq(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TMoreeq text.");
    }
}