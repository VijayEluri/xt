// $Id: ElementAvailableFunction.java 96 2005-02-28 21:07:29Z blindsey $

package com.blnz.xsl.expr;

import com.blnz.xsl.om.*;


/**
 * the XSLT function 'element-available("qname")'
 */
class ElementAvailableFunction implements Function
{
    public ConvertibleExpr makeCallExpr(ConvertibleExpr e[], Node exprNode) 
	throws ParseException 
    {
        if (e.length != 1) {
            throw new ParseException("Element Available function:: " + 
                                     " expected two argument's");
	}

        final StringExpr se = e[0].makeStringExpr();

        final NamespacePrefixMap prefixMap = exprNode.getNamespacePrefixMap();


	return new ConvertibleBooleanExpr() {
		public boolean eval(Node node, 
				    ExprContext context) 
		    throws XSLException 
		{
		    Name elementName = 
			prefixMap.expandAttributeName(se.eval(node, context),
						      node);
		    // FIXME: properly implement extension elements
		    return false;
		}
	    };
	

    }
  

}
