// $Id: AncestorsOrSelfNodeIterator.java 96 2005-02-28 21:07:29Z blindsey $

package com.blnz.xsl.expr;

import com.blnz.xsl.om.*;

/**
 * an Iterator that walks toward thwe document root
 */
public class AncestorsOrSelfNodeIterator implements NodeIterator
{
    private Node node;

    public AncestorsOrSelfNodeIterator(Node node)
    {
        this.node = node;
    }

    public Node next()
    {
        if (node == null) {
            return null;
	}
        Node tem = node;
        node = tem.getParent();
        return tem;
    }
}
