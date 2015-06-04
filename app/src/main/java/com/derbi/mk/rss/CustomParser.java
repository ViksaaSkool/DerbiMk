package com.derbi.mk.rss;

import android.net.Uri;
import android.os.Build;
import android.text.Html;

import com.derbi.mk.cnst.Static;
import com.derbi.mk.utils.LogUtil;
import com.pkmmte.pkrss.Article;
import com.pkmmte.pkrss.parser.Parser;

import org.jsoup.Jsoup;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by varsovski on 27-May-15.
 */
public class CustomParser extends Parser {
    private final List<Article> articleList = new ArrayList();
    private final DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.getDefault());
    private final XmlPullParser xmlParser;

    public CustomParser() {
        this.dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
        XmlPullParser parser = null;

        try {
            XmlPullParserFactory e = XmlPullParserFactory.newInstance();
            e.setNamespaceAware(false);
            parser = e.newPullParser();
        } catch (XmlPullParserException var3) {
            var3.printStackTrace();
        }

        this.xmlParser = parser;
    }

    public List<Article> parse(String rssStream) {
        this.articleList.clear();
        long time = System.currentTimeMillis();

        try {
            ByteArrayInputStream e = new ByteArrayInputStream(rssStream.getBytes());
            this.xmlParser.setInput(e, (String) null);
            Article article = new Article();

            for (int eventType = this.xmlParser.getEventType(); eventType != 1; eventType = this.xmlParser.next()) {
                String tagname = this.xmlParser.getName();
                switch (eventType) {
                    case 2:
                        if (tagname.equalsIgnoreCase("item")) {
                            article = new Article();
                        } else {
                            this.handleNode(tagname, article);
                        }
                        break;
                    case 3:
                        if (tagname.equalsIgnoreCase("item")) {
                            article.setId(Math.abs(article.hashCode()));
                            if (article.getImage() != null && article.getContent() != null) {
                                article.setContent(article.getContent().replaceFirst("<img.+?>", ""));
                            }

                            // this.log("Parser", article.toShortString(), 4);
                            this.articleList.add(article);
                        }
                }
            }
        } catch (IOException var8) {
            var8.printStackTrace();
        } catch (XmlPullParserException var9) {
            var9.printStackTrace();
        }

        LogUtil.eLog(Static.RSS_TAG, "Parsing took " + (System.currentTimeMillis() - time) + "ms");
        return this.articleList;
    }

    private boolean handleNode(String tag, Article article) {
        try {
            if (this.xmlParser.next() != 4) {
                return false;
            } else {
                if (tag.equalsIgnoreCase("link")) {
                    article.setSource(Uri.parse(this.xmlParser.getText()));
                } else if (tag.equalsIgnoreCase("title")) {
                    article.setTitle(this.xmlParser.getText());
                } else if (tag.equalsIgnoreCase("description")) {
                    String e = this.xmlParser.getText();
                    article.setImage(Uri.parse(this.pullImageLink(e)));
                    article.setDescription(Html.fromHtml(e.replaceAll("<img.+?>", "")).toString());
                } else if (tag.equalsIgnoreCase("content:encoded")) {
                    article.setContent(this.xmlParser.getText().replaceAll("[<](/)?div[^>]*[>]", ""));
                } else if (tag.equalsIgnoreCase("wfw:commentRss")) {
                    article.setComments(this.xmlParser.getText());
                } else if (tag.equalsIgnoreCase("category")) {
                    article.setNewTag(this.xmlParser.getText());
                } else if (tag.equalsIgnoreCase("dc:creator")) {
                    article.setAuthor(this.xmlParser.getText());
                } else if (tag.equalsIgnoreCase("pubDate")) {
                    article.setDate(this.getParsedDate(this.xmlParser.getText()));
                }

                return true;
            }
        } catch (IOException var4) {
            var4.printStackTrace();
            return false;
        } catch (XmlPullParserException var5) {
            var5.printStackTrace();
            return false;
        }
    }

    private long getParsedDate(String encodedDate) {
        try {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
                Date dateFromString = new Date();
                try {
                    dateFromString = sdf.parse(encodedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return dateFromString.getTime();
            } else
                return this.dateFormat.parse(this.dateFormat.format(this.dateFormat.parseObject(encodedDate))).getTime();
        } catch (ParseException var3) {
            LogUtil.eLog("Parser", "Error parsing date " + encodedDate + "5");
            var3.printStackTrace();
            return 0L;
        }
    }

    /* Additionally edited in order to take the last img tag of the feed */
    private String pullImageLink(String encoded) {
        try {
            return Jsoup.parse(encoded).select("img").last().absUrl("src").toString();
        } catch (Exception var7) {
            LogUtil.eLog(Static.RSS_TAG, "Error pulling image link from description!\n" + var7.getMessage() + " 5");
        }

        return "";
    }
}


