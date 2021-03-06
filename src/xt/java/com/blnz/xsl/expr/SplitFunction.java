//
package com.blnz.xsl.expr;

import com.blnz.xsl.om.*;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import java.util.regex.Matcher;


import java.net.URL;

/**
 * Represents the EXSL str:split function
 * For more information consult exsl specification at: 
 * <A HREF="http://www.exslt.org/regexp/functions/strings/">Specification</A>. 
 */
class SplitFunction implements Function 
{
    /**
     *
     */
    public ConvertibleExpr makeCallExpr(ConvertibleExpr[] args,
                                        Node exprNode) throws ParseException 
    {
        if (args.length < 2 || args.length > 3) {
            throw new ParseException("expected 2 or 3 arguments");
        }
        final StringExpr srcSe = args[0].makeStringExpr();
        final StringExpr sepSe = args.length == 2 ? args[1].makeStringExpr() : new LiteralExpr(" ");

        return new ConvertibleNodeSetExpr() {
                public NodeIterator eval(Node node, 
                                         ExprContext context) 
                    throws XSLException 
                {
                    return split(node, 
                                 context,
                                 srcSe.eval(node, context),
                                 sepSe.eval(node, context));

                }
            };
    }
    
    /**
     *
     */
    static final private NodeIterator split(Node node, 
                                            ExprContext context,
                                            String src,
					    String separator) 

	throws XSLException 
    {

        String[] tokens = src.split(separator);

        //      System.out.println("tokens length from{" + src + "} with {" + separator + "} is: " + tokens.length);
        Node[] nodes = new Node[tokens.length];

        for (int i = 0; i < tokens.length; ++i) {
		    
            Node textNode 
                = new RegexTextNode( tokens[i], i, node );
		    
            nodes[i] = textNode;
        }
		
        return new ArrayNodeIterator(nodes, 0, nodes.length);
		

//	try {
	    
// 	    boolean globalReplace = false;
// 	    boolean ignoreCase = false;
	    
// 	    if ( flags.length() > 0 ) {
// 		globalReplace = flags.indexOf("g") < 0 ? false : true;
// 		ignoreCase = flags.indexOf("i") < 0 ? false : true;
// 	    }
	    
	    
// 	    Node[] groups = new Node[24];

// 	    Pattern pat = ignoreCase ? Pattern.compile(pattern, Pattern.CASE_INSENSITIVE) : Pattern.compile(pattern);
	    
//             Matcher matcher = pat.matcher(src);
            
// 	    if (matcher.find()) {
                
// 		int gc = matcher.groupCount();
                
// 		//System.out.println("matched {" + gc + "} + 1 groups");
		
// 		for (int i = 0; i < gc + 1; ++i) {
		    
// 		    Node regexGroupTextNode 
// 			= new RegexTextNode( matcher.group(i), i, node );
		    
// 		    groups[i] = regexGroupTextNode;
// 		}
		
// 		return new ArrayNodeIterator(groups, 1, gc + 1);
		
// 	    } else {
//                 return null;
//             }
	    
// 	} catch (PatternSyntaxException ex) {
// 	    return null;
// 	} catch (Exception e) {
// 	    return null;
// 	}
	
    }


    static private class RegexTextNode implements Node
    {
	Node _parent;
	
	Node _root;
	
	int _index;  // an identifier based upon node count in document order?
    
	Node _nextSibling;
    
	private String _data;
    
    
	RegexTextNode ( String regexGroupText,
			int index,
			Node parent )
	{
	    _parent = parent;
	    
	    _index = index;
	
	    _data = regexGroupText;
	
	    _root = _parent.getRoot();
	
	    _nextSibling = null;
	
	    // 	if (parent.getLastChild() == null)
	    // 	    parent.getFirstChild() = parent.getLastChild() = this;
	    // 	else {
	    // 	    parent.getLastChild().getNextSibling() = this;
	    // 	    parent.getLastChild() = this;
	    // 	}
	}
    
	public Node getParent() 
	{
	    return _parent;
	}
    
	public SafeNodeIterator getFollowingSiblings() 
	{
	    //FIXME: ?? implement this ?
	    return new RegexNodeIterator(null);
	}
    
	/**
	 * @return the base URI for this document (obtain from root?)
	 */
	public URL getURL() 
	{
	    return _parent.getURL();
	}
    
	boolean canStrip()
	{
	    return false;
	}
    
	public Node getAttribute(Name name) 
	{
	    return null;
	}
    
	public String getAttributeValue(Name name) 
	{
	    return null;
	}
    
	public SafeNodeIterator getAttributes()
	{
	    return new RegexNodeIterator(null);
	}
    
	public SafeNodeIterator getNamespaces()
	{
	    return new RegexNodeIterator(null);
	}
    
	public Name getName() 
	{
	    return null;
	}
    
	public NamespacePrefixMap getNamespacePrefixMap() 
	{
	    return getParent().getNamespacePrefixMap();
	}
    
	public int compareTo(Node node)
	{

	    //FIXME: implement this ?
	
	    // NodeImpl ni = (NodeImpl)node;
	    // 	if (root == ni.root) {
	    // 	    return index - ((Node)node).index;
	    // 	}
	    // 	return root.compareRootTo(ni.root);
	    return -1;
	}

	public Node getElementWithId(String name)
	{
	    return _root.getElementWithId(name);
	}
    
	public String getUnparsedEntityURI(String name)
	{
	    return _root.getUnparsedEntityURI(name);
	}
    
	public boolean isId(String name) 
	{
	    return false;
	}
    
	public String getGeneratedId()
	{
	    int d = _index;//getRoot().getDocumentIndex();
	    if (d == 0) {
		return "N" + String.valueOf(_index);
	    } else {
		return "N" + String.valueOf(d) + "_" + String.valueOf(_index);
	    }
	}
    
	public Node getRoot() {
	    return _root;
	}
    
	// javax.xml.trax.SourceLocator methods
	public int getLineNumber() 
	{
	    return _parent.getLineNumber();
	}
    
	public int getColumnNumber()
	{ return -1; }
    
	public String getSystemId()
	{ 
	    return getRoot().getSystemId();
	}
    
	public String getPublicId()
	{ return null; }

	public byte getType() {
	    return Node.TEXT;
	}

        public Name getSchemaTypeName()
        {
            return null;
        }

	public String getData() {
	    return _data;
	}
    
	public SafeNodeIterator getChildren() {
	    return new RegexNodeIterator(null);
	}

        public NodeExtension getNodeExtension() {
            return null;
        }
    }
    
    static private class RegexNodeIterator implements SafeNodeIterator 
    {
        private Node nextNode;
        
        RegexNodeIterator(Node nextNode) 
        {
            this.nextNode = nextNode;
        }
        
	public Node next() 
        {
	    return null;
	}
    }
}

