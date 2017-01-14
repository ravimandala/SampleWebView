package com.example.ravimandala.samplewebview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Ravi";
    private static final String mraidBridge = "(function(window) {'use strict';var vungle = window.vungle = window.vungle || {}; vungle.myFunction = function() {document.getElementById('demo').innerHTML = 'Hellow Worlduuu'; console.log('Injected code'); return true;};})(window);";
    private static final String FILE_PROTOCOL = "file:";
    private static final String DUMMY_URL = "http://lol.vungle.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String fileName = getCacheDir() + "/vungle/index.html";
        WebView browser = (WebView) findViewById(R.id.webview);
        browser.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);

        String path = "file:/storage/emulated/0/Android/data/com.vungle.publisher.test.adaptiveId/files/.vungle/57bf752e77472d737e00001c_57bf75b5b04432597e00000f_datasci--timeout/template/index.html";
        int index = path.lastIndexOf('*');
        String indexFileName = path.substring(index+20);
        Log.d(TAG, "MraidJsPath: " + path.replaceFirst("file:", "").replace(indexFileName, "mraid.js"));

        browser.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(final WebView view, WebResourceRequest request) {
                Log.d(TAG, "Request for " + request.getUrl().toString() + " intercepted.");

                final String fileName = request.getUrl().getPath();

                if (request.getUrl().toString().endsWith(".html")) {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            loadModifiedPage(view, fileName);
                        }
                    });

                    return null;
                } else {
                    return super.shouldInterceptRequest(view, request);
                }
            }
        });
        browser.loadUrl(FILE_PROTOCOL + fileName);
    }

    private void loadModifiedPage(WebView view, String fileName) {
        FileInputStream fis = null;
        Document document;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            fis = new FileInputStream(fileName);
            document = documentBuilder.parse(fis);
            NodeList heads = document.getElementsByTagName("head");
            if (heads.getLength() >= 0) {
                Node head = heads.item(0);

                Element mraidScript = document.createElement("script");
                mraidScript.setTextContent(mraidBridge);

                head.appendChild(mraidScript);
                Log.d(TAG, "mraidScript element = " + mraidScript.toString());

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(fileName + ".html"));
                transformer.transform(source, result);

                fileName = fileName + ".html";
            }
            Log.d(TAG, "The parsed document content is" + document.toString());
        } catch (Exception e) {
            throw new RuntimeException("Unable to parse region metadata file: " + e.getMessage(), e);
        } finally {
            if (fis != null ) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        Log.d(TAG, "Final fileName is " + fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            Log.d(TAG, "Finished spitting out modified html document: " + buffer.toString());

            view.loadDataWithBaseURL(DUMMY_URL, buffer.toString(), "text/html", "utf-8", null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the specified input stream and returns document object
     *
     * @param input
     *            The stream containing the ad template's root index file to parse.
     *
     * @return The list of parsed regions.
     */
    public Document injectMraidBridge(InputStream input) {
        Document document;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            document = documentBuilder.parse(input);
        } catch (Exception e) {
            throw new RuntimeException("Unable to parse region metadata file: " + e.getMessage(), e);
        }

        document.createElement("script").appendChild(new Node() {
            @Override
            public String getNodeName() {
                return null;
            }

            @Override
            public String getNodeValue() throws DOMException {
                return mraidBridge;
            }

            @Override
            public void setNodeValue(String nodeValue) throws DOMException {

            }

            @Override
            public short getNodeType() {
                return 0;
            }

            @Override
            public Node getParentNode() {
                return null;
            }

            @Override
            public NodeList getChildNodes() {
                return null;
            }

            @Override
            public Node getFirstChild() {
                return null;
            }

            @Override
            public Node getLastChild() {
                return null;
            }

            @Override
            public Node getPreviousSibling() {
                return null;
            }

            @Override
            public Node getNextSibling() {
                return null;
            }

            @Override
            public NamedNodeMap getAttributes() {
                return null;
            }

            @Override
            public Document getOwnerDocument() {
                return null;
            }

            @Override
            public Node insertBefore(Node newChild, Node refChild) throws DOMException {
                return null;
            }

            @Override
            public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
                return null;
            }

            @Override
            public Node removeChild(Node oldChild) throws DOMException {
                return null;
            }

            @Override
            public Node appendChild(Node newChild) throws DOMException {
                return null;
            }

            @Override
            public boolean hasChildNodes() {
                return false;
            }

            @Override
            public Node cloneNode(boolean deep) {
                return null;
            }

            @Override
            public void normalize() {

            }

            @Override
            public boolean isSupported(String feature, String version) {
                return false;
            }

            @Override
            public String getNamespaceURI() {
                return null;
            }

            @Override
            public String getPrefix() {
                return null;
            }

            @Override
            public void setPrefix(String prefix) throws DOMException {

            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public boolean hasAttributes() {
                return false;
            }

            @Override
            public String getBaseURI() {
                return null;
            }

            @Override
            public short compareDocumentPosition(Node other) throws DOMException {
                return 0;
            }

            @Override
            public String getTextContent() throws DOMException {
                Log.d(TAG, "getTextContent returning bridge code");
                return mraidBridge;
            }

            @Override
            public void setTextContent(String textContent) throws DOMException {

            }

            @Override
            public boolean isSameNode(Node other) {
                return false;
            }

            @Override
            public String lookupPrefix(String namespaceURI) {
                return null;
            }

            @Override
            public boolean isDefaultNamespace(String namespaceURI) {
                return false;
            }

            @Override
            public String lookupNamespaceURI(String prefix) {
                return null;
            }

            @Override
            public boolean isEqualNode(Node arg) {
                return false;
            }

            @Override
            public Object getFeature(String feature, String version) {
                return null;
            }

            @Override
            public Object setUserData(String key, Object data, UserDataHandler handler) {
                return null;
            }

            @Override
            public Object getUserData(String key) {
                return null;
            }
        });

        Log.d(TAG, "Modified HTML index file: " + document.toString());

        return document;
    }
}
