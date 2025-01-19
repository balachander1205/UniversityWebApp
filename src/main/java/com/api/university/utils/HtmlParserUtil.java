package com.api.university.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class HtmlParserUtil {
    public String getImageSrc(String html){
        Document document = Jsoup.parse(html);
        Element imgTag = document.getElementById("img_profile_pic");
        String src = imgTag.attr("src");
        System.out.println("Image source: " + src);
        return src;
    }
}
