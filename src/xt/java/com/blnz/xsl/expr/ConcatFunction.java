// $Id: ConcatFunction.java 96 2005-02-28 21:07:29Z blindsey $

package com.blnz.xsl.expr;

import com.blnz.xsl.om.*;

/**
 * represents the XPath Function: string concat(string, string, string*)
 *
 * has the method makeCallExpr which will construct a String expression
 * representing the concat function
 */ 
class ConcatFunction implements Function
{
    
    public ConvertibleExpr makeCallExpr(ConvertibleExpr[] args, 
                                        Node exprNode) throws ParseException
    {
        final StringExpr[] se = new StringExpr[args.length];
        for (int i = 0; i < se.length; i++) {
            se[i] = args[i].makeStringExpr();
        }

        return new ConvertibleStringExpr()
            {
                public String eval(Node node, ExprContext context) 
                    throws XSLException 
                {
                    StringBuffer buf = new StringBuffer();
                    for (int i = 0; i < se.length; i++)
                        buf.append(se[i].eval(node, context));
                    return buf.toString();
                }
            };
    }
}
