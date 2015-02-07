package utils;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

import java.io.File;
import java.io.FileOutputStream;

/**
 * User: LoW
 * Date: 13/02/13
 * Time: 12:11
 */
public class CXMLutils
{
   public static Document loadFrom(String src)
   {
       SAXBuilder sxb = new SAXBuilder();
       Document ret = null;
       try
       {
           ret = sxb.build(new File(src));
       }
       catch(Exception e)
       {
          e.printStackTrace();
       }
       System.out.println(ret.toString());
       return ret;
   }
    public static Document loadFrom(Element dec)
    {
        SAXBuilder sxb = new SAXBuilder();
        Document ret = null;
        ret = new Document(dec);
        /*try
        {
            ret = sxb.build(new File(src));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/
        System.out.println(ret.toString());
        return ret;
    }
   public static void SaveTo(Document doc, String dest)
   {
       try
       {
           XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
           sortie.output(doc, new FileOutputStream(dest));
       }
       catch (java.io.IOException e)
       {
           e.printStackTrace();
       }
   }
}
