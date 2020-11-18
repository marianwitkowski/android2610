package pl.alx.androidmodules;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);

        StringBuilder result = new StringBuilder();

        TextView textView = findViewById(R.id.textView);
        try {
            InputStream is = getAssets().open("employee.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();
            NodeList nList = doc.getElementsByTagName("employee");
            for(int i=0;i<nList.getLength();i++) {
                Node node = nList.item(i);
                //Log.i("alx", node.getTextContent());
                if (node.getNodeType()==Node.ELEMENT_NODE) {
                    Element el = (Element)node;
                    String fname = getTagValue("name", el);
                    String lname = getTagValue("surname", el);
                    String salary = getTagValue("salary", el);

                    result.append(String.format("%s\n%s\n%s\n\n", fname, lname, salary));
                }
            }
            textView.setText(result);


        } catch (Exception exc) {
            Log.e("alx", exc.getMessage());
        }

    }

    private String getTagValue(String tag, Element el) {
        NodeList nl = el.getElementsByTagName(tag).item(0).getChildNodes();
        String s =nl.item(0).getNodeValue();
        return s;
    }
}