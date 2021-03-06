// $Id: AttributeAction.java 99 2005-02-28 21:37:53Z blindsey $

package com.blnz.xsl.tr;

import com.blnz.xsl.om.*;
import com.blnz.xsl.expr.StringExpr;

class AttributeAction implements Action
{
    private StringExpr nameExpr;
    private StringExpr namespaceExpr;
    private NamespacePrefixMap nsMap;
    private Action content;

    AttributeAction(StringExpr nameExpr, StringExpr namespaceExpr,
                    NamespacePrefixMap nsMap, Action content)
    {
        this.nameExpr = nameExpr;
        this.namespaceExpr = namespaceExpr;
        this.nsMap = nsMap;
        this.content = content;
    }

    public void invoke(ProcessContext context, Node sourceNode, 
                       Result result) throws XSLException
    {
        String qname = nameExpr.eval(sourceNode, context);
        Name name;
        if (namespaceExpr != null) {
            String ns = namespaceExpr.eval(sourceNode, context);
            if (ns.length() == 0) {
                name = 
                    nsMap.getNameTable().createName(qname.substring(qname.indexOf(';') + 1));
            } else {
                name = nsMap.getNameTable().createName(qname, ns);
            }
        } else {
            name = nsMap.expandAttributeName(qname, sourceNode);
        }
        StringResult s = new StringResult(result);
        content.invoke(context, sourceNode, s);
        result.attribute(name, s.toString());
    }
}
