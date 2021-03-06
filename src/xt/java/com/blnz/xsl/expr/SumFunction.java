// $Id: SumFunction.java 96 2005-02-28 21:07:29Z blindsey $

package com.blnz.xsl.expr;

import com.blnz.xsl.om.*;

class SumFunction extends Function1 {
    ConvertibleExpr makeCallExpr(ConvertibleExpr e) throws ParseException {
        final NodeSetExpr nse = e.makeNodeSetExpr();
        return new ConvertibleNumberExpr() {
                public double eval(Node node, ExprContext context) throws XSLException {
                    return sum(nse.eval(node, context));
                }
            };
    }

    static private final double sum(NodeIterator iter) throws XSLException {
        double n = 0.0;
        for (;;) {
            Node node = iter.next();
            if (node == null)
                break;
            n += Converter.toNumber(Converter.toString(node));
        }
        return n;
    }
}
