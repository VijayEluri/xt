// $Id: EqualsRelation.java 96 2005-02-28 21:07:29Z blindsey $

package com.blnz.xsl.expr;

/**
 *
 */
class EqualsRelation extends Relation 
{
    boolean relate(String s1, String s2) 
    {
        return s1.equals(s2);
    }

    boolean relate(boolean b1, boolean b2) 
    {
        return b1 == b2;
    }

    boolean relate(double d1, double d2) 
    {
        return d1 == d2;
    }
}
