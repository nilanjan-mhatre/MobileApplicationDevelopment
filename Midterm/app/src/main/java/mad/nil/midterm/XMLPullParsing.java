package mad.nil.midterm;

import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nilanjan Mhatre
 * email: nmhatre@uncc.edu
 * Student Id: 801045013
 */

public class XMLPullParsing {
    public void parseXML(InputStream inputStream) {
//        List<News> newsFeeds = new ArrayList<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, "UTF-8");
            int event = parser.getEventType();
            boolean item = false;
//            News news = null;

            while(event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        /*if(StringUtils.equals("item", parser.getName())) {
                            item = true;
                            news = new News();
                        } else if(StringUtils.equals("title", parser.getName()) && item && news != null) {
                            news.setTitle(parser.nextText().trim());
                        } else if(StringUtils.equals("description", parser.getName()) && item && news != null) {
                            news.setDescription(parser.nextText().trim());
                        } else if(StringUtils.equals("content", parser.getName()) && item && news != null) {
                            news.getImageUrlList().add(parser.getAttributeValue(null, "url").trim());
                        }*/
                        break;
                    case XmlPullParser.END_TAG:
                        /*if(StringUtils.equals("item", parser.getName()) && news != null) {
                            newsFeeds.add(news);
                            item = false;
                        }*/
                }
                event = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}
