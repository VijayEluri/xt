// $Id: LastFunction.java 96 2005-02-28 21:07:29Z blindsey $

package com.blnz.xsl.expr;

import com.blnz.xsl.om.*;

class LastFunction extends Function0 {
    ConvertibleExpr makeCallExpr() {
        return new ConvertibleNumberExpr() {
                public double eval(Node node, ExprContext context) throws XSLException {
                    return context.getLastPosition();
                }
            };
    }
}
