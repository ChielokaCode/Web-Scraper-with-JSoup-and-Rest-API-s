package org.chielokacode.htmljsoup.htmljsoup.service;

import org.chielokacode.htmljsoup.htmljsoup.model.Phone;
import org.chielokacode.htmljsoup.htmljsoup.repository.PhoneRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class PhoneServiceImpl {

    private PhoneRepository phoneRepository;

    @Autowired
    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public String getAbsoluteUrl() throws IOException {
        Document doc = Jsoup.connect("https://www.jumia.com.ng/").get();

        Element link = doc.select("a").first();
        assert link != null;
        return link.attr("abs:href");
    }

    public List<Phone> getPhoneLists(String categoryId, String subId) throws IOException {
        Document doc = Jsoup.connect("https://www.jumia.com.ng/mlp-new-year-clearance-sales/" + categoryId + "/" + subId).get();

        Elements phoneS = doc.select("article.prd._fb.col.c-prd");

        List<Phone> phoneList = new ArrayList<>();
        for (Element individualPhone : phoneS) {
            String name = individualPhone.select("h3.name").text();
            String price = individualPhone.select("div.prc").text();

            Phone newPhone = new Phone();
            newPhone.setName(name);
            newPhone.setPrice(new BigDecimal(price.replaceAll("[^\\d.]", "")));
            phoneList.add(newPhone);
        }

        return phoneRepository.saveAll(phoneList);
    }

}
